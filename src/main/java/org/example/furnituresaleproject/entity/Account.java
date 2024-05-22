package org.example.furnituresaleproject.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String address;

    @Temporal(TemporalType.TIMESTAMP)
    private Date birthday;

    @Column(length = 12, nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public enum Gender {
        NAM, NU
    }

}
