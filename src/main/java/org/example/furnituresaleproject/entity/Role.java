package org.example.furnituresaleproject.entity;

import jakarta.persistence.*;
import lombok.Data;


import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "role")
public class Role implements Serializable {
    private static final Long serialVersionID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name",length = 30, nullable = false, unique = true)
    private String name;
}
