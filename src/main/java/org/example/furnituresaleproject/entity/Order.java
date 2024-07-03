package org.example.furnituresaleproject.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
@Entity
@Table(name = "`order`")
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Account vendor;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Account customer;

    private float totalAmount;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate    // ko can dien, lay thoi gian khi toa moi luu vao truong nay
    private Date dateOfPurchase;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfDelivery;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String shippingAddress;



}
