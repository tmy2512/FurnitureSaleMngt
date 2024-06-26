package org.example.furnituresaleproject.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "orderdetails")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id") // name in SQL
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "discount")
    private float discount;//5%    1h sau 20%


}
