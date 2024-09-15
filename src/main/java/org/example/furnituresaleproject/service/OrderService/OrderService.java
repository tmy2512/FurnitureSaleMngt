package org.example.furnituresaleproject.service.OrderService;

import org.apache.poi.ss.usermodel.*;
import org.example.furnituresaleproject.dto.OrderDTO;
import org.example.furnituresaleproject.entity.*;
import org.example.furnituresaleproject.form.Order.CreateOrderForm;
import org.example.furnituresaleproject.repository.IAccountRepository;
import org.example.furnituresaleproject.repository.IOrderDetailsRepository;
import org.example.furnituresaleproject.repository.IOrderRepository;
import org.example.furnituresaleproject.repository.IProductRepository;
import org.example.furnituresaleproject.service.AccountService.IAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import javax.swing.text.html.Option;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.HashMap;

@Service
public class OrderService  implements  IOrderService{

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private IOrderDetailsRepository orderDetailsRepository;
    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private IAccountService accountService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public void createOrder(CreateOrderForm form) throws ParseException {
        // create order from createOrderForm
        // lấy thoogn tin nguwoif dùng từ SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof UserDetails)) {
            throw new RuntimeException("User not authenticated");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        Integer customerId = accountService.getUserIdByEmail(email);

        // kiểm tra và lấy địa chỉ mặc định nếu để trống shippingAddress
        String shippingAddress = form.getShippingAddress();
        Optional<Account> accountOptional = accountRepository.findById(customerId);
        if (shippingAddress == null || shippingAddress.isEmpty()) {
           // accountOptional = accountRepository.findById(customerId);
            if (!accountOptional.isPresent()) {
                throw new RuntimeException("Customer not found");
            }
            shippingAddress = accountOptional.get().getAddress();
        }

        // mapping createOrderForm and Order
        Order order = new Order();
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //Date dateOfDelivery = dateFormat.parse(form.getDateOfDelivery());
        order.setDateOfDelivery(new Date());
        order.setCustomer(accountOptional.get());
        order.setVendor(null);
        order.setShippingAddress(shippingAddress);
        order.setStatus(OrderStatus.PROCESSING);

        // tinh totalAmount
        float totalAmount = 0;
        List<Integer> ids = form.getCreateOrderDetailForms().stream()
                .map(CreateOrderForm.CreateOrderDetailForm::getProductId)
                .collect(Collectors.toList());
        List<Product> products = productRepository.findByIdIn(ids);
        Map<Integer, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, product -> product));
        for (CreateOrderForm.CreateOrderDetailForm detailForm : form.getCreateOrderDetailForms()) {
            Product product = productMap.get(detailForm.getProductId());
            if (product != null) {
                totalAmount += product.getPrice() * detailForm.getQuantity() * (100 - product.getDiscount()) / 100;
            } else {
                throw new RuntimeException("Product not found with ID: " + detailForm.getProductId());
            }
        }
        order.setTotalAmount(totalAmount);
        orderRepository.save(order);

        // create orderDetail

        for (int i = 0; i < form.getCreateOrderDetailForms().size(); i++) {
            Optional<Product> productOptional = productRepository.findById(form.getCreateOrderDetailForms().get(i).getProductId());
            Product product = productOptional.get();
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setOrder(order);
            orderDetails.setProduct(product);
            orderDetails.setDiscount(product.getDiscount());
            orderDetails.setQuantity(form.getCreateOrderDetailForms().get(i).getQuantity());
            orderDetailsRepository.save(orderDetails);
        }

    }

    // show order throw orderId
    @Override
    public OrderDTO getOrderById(Integer orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        return convertToDTO(order);
    }

    @Override
    public List<OrderDTO> getAllOrder() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public String readExcelFile(MultipartFile file) throws IOException {
        List<CreateOrderForm>  orderForms = new ArrayList<>();
        Map<Integer, String> dataCusName = new HashMap<>();
        int columnIndex = 0; // Cột mà muốn đọc dữ liệu (ví dụ: cột A là 0, cột B là 1,...)
        //Open excel file
        // at first, check type of file, excel or not
        String fileName = file.getOriginalFilename();
        if (fileName == null || !(fileName.endsWith(".xlsx") || fileName.endsWith(".xls"))) {
            throw new IllegalArgumentException("File is not an Excel file");
        }

        // open file using InputStream
        InputStream inputStream = file.getInputStream();
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        // duyet qua tung hang trong sheet
        for(Row row:sheet) {
            int rowNumber = sheet.getLastRowNum();
            if(row.getRowNum() == 0) continue;
            Cell cell = row.getCell(columnIndex); // Lấy cell tại cột chỉ định
            if (cell != null) {
                int rowIndex = row.getRowNum(); // Lấy số thứ tự của dòng
                String cellValue = cell.getStringCellValue(); // Đọc dữ liệu từ cell

                // Lưu dữ liệu vào Map
                dataCusName.put(rowIndex+1, cellValue);
            }
        }

        // check customerName exist in DB
        // Lấy danh sách các tên khách hàng từ Map
        Set<String> customerNamesSet = dataCusName.values().stream().collect(Collectors.toSet());
        // Sử dụng findByNameNotIn để tìm các khách hàng không tồn tại trong DB
        List<Account> customersInDB = accountRepository.findByNameIn(customerNamesSet);
        // Chuyển danh sách khách hàng không tồn tại thành Set để kiểm tra nhanh hơn
        Set<String> customersInDBSet = customersInDB.stream()
                .map(Account::getName)
                .collect(Collectors.toSet());

        List<String> errs = new ArrayList<>();
        dataCusName.forEach((index, name) -> {
            if(!customersInDBSet.contains(name)) {
                errs.add(name+" is in row "+ index+" not exist in DB ");
            }
        });

        // Nếu có lỗi, ghi chúng vào tệp văn bản
        if (!errs.isEmpty()) {
            String filePath = "errors.txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (String error : errs) {
                    writer.write(error);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return filePath; // Trả về đường dẫn đến file lỗi
        }

        // clode workbook after read
        workbook.close();

        return null;

    }

   public OrderDTO mapRowToOrderDTO(Row row) {
       OrderDTO orderDTO = new OrderDTO();
       orderDTO.setId((int) row.getCell(0).getNumericCellValue());
       orderDTO.setCustomerName(row.getCell(1).getStringCellValue());
       orderDTO.setCustomerPhone(row.getCell(2).getStringCellValue());
       orderDTO.setDateOfDelivery(String.valueOf(row.getCell(3).getDateCellValue()));
       orderDTO.setShippingAddress(row.getCell(4).getStringCellValue());
       orderDTO.setStatus(row.getCell(5).getStringCellValue());
       orderDTO.setTotalAmount((float) row.getCell(6).getNumericCellValue());
       orderDTO.setDateOfPurchase(String.valueOf(row.getCell(7).getDateCellValue()));

       OrderDTO.OrderDetailDTO orderDetailDTO = new OrderDTO.OrderDetailDTO();

//       select id from product where sku in [rows.getCell(8)];
//       => null => sku khong ton tai
//               20 - 1000
//   30


       orderDetailDTO.setProductId((int) row.getCell(8).getNumericCellValue());
       orderDetailDTO.setProductName(row.getCell(9).getStringCellValue());
       orderDetailDTO.setProductPrice((float) row.getCell(10).getNumericCellValue());
       orderDetailDTO.setQuantity((int) row.getCell(11).getNumericCellValue());
       orderDetailDTO.setDiscount((float) row.getCell(12).getNumericCellValue());
       orderDetailDTO.setTotalPrice((float) row.getCell(13).getNumericCellValue());

       orderDTO.setOrderDetails(new ArrayList<>());
       orderDTO.getOrderDetails().add(orderDetailDTO);

       return orderDTO;
   }

    // convert to DTO
        private OrderDTO convertToDTO(Order order) {

            OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
            orderDTO.setOrderDetails(
                    orderDetailsRepository.findByOrderId(order.getId()).stream()
                            .map(orderDetail -> {
                                OrderDTO.OrderDetailDTO detailDTO = modelMapper.map(orderDetail, OrderDTO.OrderDetailDTO.class);
                                detailDTO.setProductName(productRepository.findById(orderDetail.getProduct().getId()).get().getName());
                                detailDTO.setProductPrice(productRepository.findById(orderDetail.getProduct().getId()).get().getPrice());
                                detailDTO.setTotalPrice(
                                        orderDetail.getQuantity() * detailDTO.getProductPrice() * (100 - detailDTO.getDiscount())/100
                                );
                                return detailDTO;
                            }).collect(Collectors.toList())
            );
            return orderDTO;
        }

}
