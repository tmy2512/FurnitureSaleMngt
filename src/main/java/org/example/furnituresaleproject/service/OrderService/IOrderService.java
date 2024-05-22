package org.example.furnituresaleproject.service.OrderService;

import org.example.furnituresaleproject.form.Order.CreateOrderForm;

import java.text.ParseException;

public interface IOrderService {
    public void createOrder(CreateOrderForm form) throws ParseException;
}
