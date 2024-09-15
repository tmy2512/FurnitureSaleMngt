package org.example.furnituresaleproject.service.OrderService;

import org.example.furnituresaleproject.dto.OrderDTO;
import org.example.furnituresaleproject.entity.Order;
import org.example.furnituresaleproject.form.Order.CreateOrderForm;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface IOrderService {
    public void createOrder(CreateOrderForm form) throws ParseException;
    public OrderDTO getOrderById(Integer orderId);
    List<OrderDTO> getAllOrder();
//    List<CreateOrderForm> readExcelFile(MultipartFile file) throws IOException;
    String readExcelFile(MultipartFile file) throws IOException;
}
