package org.example.furnituresaleproject.service.OrderService;

import org.example.furnituresaleproject.entity.*;
import org.example.furnituresaleproject.form.Order.CreateOrderForm;
import org.example.furnituresaleproject.repository.IAccountRepository;
import org.example.furnituresaleproject.repository.IOrderDetailsRepository;
import org.example.furnituresaleproject.repository.IOrderRepository;
import org.example.furnituresaleproject.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    @Transactional
    public void createOrder(CreateOrderForm form) throws ParseException {
        // create order from createOrderForm
        Account account = accountRepository.findById(1).get();
        Order order = new Order();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateOfDelivery = dateFormat.parse(form.getDateOfDelivery());
        order.setDateOfDelivery(dateOfDelivery);
        order.setCustomer(account);
        order.setVendor(account);
        order.setShippingAddress(form.getShippingAddress());
        float totalAmount = 0;
        List<Integer> ids = new ArrayList<>();
        Map<Product, Integer> map = new HashMap<>();
//        List<Integer> ids = form.getCreateOrderDetailForms().stream().map(CreateOrderForm.CreateOrderDetailForm::getProductId).toList();
        for (CreateOrderForm.CreateOrderDetailForm i :form.getCreateOrderDetailForms() ) {
            ids.add(i.getProductId());
        }
        List<Product> products = productRepository.findByIdIn(ids);//1s
        for (int i = 0; i < form.getCreateOrderDetailForms().size(); i++) {
            Optional<Product> productOptional = productRepository.findById(form.getCreateOrderDetailForms().get(i).getProductId());//0.5
            Product product = productOptional.get();
            totalAmount += product.getPrice()*form.getCreateOrderDetailForms().get(i).getQuantity()*(100-product.getDiscount())/100;
        }


        order.setTotalAmount(totalAmount);
//        order.setStatus(OrderStatus.PROCESSING);
        orderRepository.save(order);

        // create orderDetail

        for (int i = 0; i < form.getCreateOrderDetailForms().size(); i++) {
            Optional<Product> productOptional = productRepository.findById(form.getCreateOrderDetailForms().get(i).getProductId());
            Product product = productOptional.get();
            OrderDetails  orderDetails = new OrderDetails();
            orderDetails.setOrder(order);
            orderDetails.setProduct(product);
            orderDetails.setDiscount(product.getDiscount());
            orderDetails.setQuantity(form.getCreateOrderDetailForms().get(i).getQuantity());
            orderDetailsRepository.save(orderDetails);
        }

    }
}
