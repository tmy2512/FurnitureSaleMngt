package org.example.furnituresaleproject.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "order")
public class Order {

    @Column(name = "orderID", columnDefinition = "unsigned int")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "vendorID")
    private Account vendor;

    @ManyToOne
    @JoinColumn(name = "customerID")
    private Account customer;

    @Column(name = "totalPrice")
    private float totalAmount;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name = "dateOfPurchase")
    private Date dateOfPurchase;

//    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateOfDelivery")
    private Date dateOfDelivery;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "shippingAddress")
    private String shippingAddress;

    public enum Status {
        PROCESSING, CONFIRMED, DELIVERIED, CANCELLED
    }

}
