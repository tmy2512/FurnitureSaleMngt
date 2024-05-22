package org.example.furnituresaleproject.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Column(name = "totalPrice")
    private float totalAmount;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate    // ko can dien, lay thoi gian khi toa moi luu vao truong nay
    @Column(name = "dateOfPurchase")
    private Date dateOfPurchase;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateOfDelivery")
    private Date dateOfDelivery;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "shippingAddress")
    private String shippingAddress;

    public enum Status {
        PROCESSING, CONFIRMED, DELIVERIED, CANCELLED
    }

}
