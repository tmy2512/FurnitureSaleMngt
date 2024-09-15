package org.example.furnituresaleproject.controller;
import org.example.furnituresaleproject.dto.OrderDTO;
import org.example.furnituresaleproject.entity.Order;
import org.example.furnituresaleproject.form.Order.CreateOrderForm;
import org.example.furnituresaleproject.service.OrderService.IOrderService;
import org.example.furnituresaleproject.service.OrderService.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/orders")
@CrossOrigin("*")
@Validated
public class OrderController {

    @Autowired
    private IOrderService service;
    @Autowired
    private OrderService orderService;

    @PostMapping("")
    public void createOrder(@RequestBody CreateOrderForm form) throws ParseException {
        service.createOrder(form);
    }

    @GetMapping("")
    public OrderDTO getOrderDTO(@RequestParam int orderID) {
        try {
            return service.getOrderById(orderID);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<OrderDTO>> getAllOrder() {
        List<OrderDTO> orders = service.getAllOrder();
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadExcelFile(@RequestParam MultipartFile file) throws IOException {
            String errFilePath = orderService.readExcelFile(file);

            if(errFilePath != null) {
                File errFile = new File(errFilePath);
                InputStreamResource resource = new InputStreamResource(new FileInputStream(errFile));
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=errors.txt");
                headers.setContentType(MediaType.TEXT_PLAIN);
                return new ResponseEntity<>(resource, headers, HttpStatus.OK);
            }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
