package org.example.furnituresaleproject.controller;

import org.example.furnituresaleproject.form.Order.CreateOrderForm;
import org.example.furnituresaleproject.service.OrderService.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/orders")
@CrossOrigin("*")
@Validated
public class OrderController {

    @Autowired
    private IOrderService service;

    @PostMapping("")
    public void createOrder(@RequestBody CreateOrderForm form) {
        service.createOrder(form);
    }
}
