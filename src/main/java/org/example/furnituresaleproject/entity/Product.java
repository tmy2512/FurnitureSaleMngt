package org.example.furnituresaleproject.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "`product`")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(nullable = false)
    private float price;//30tr

    @Column( nullable = false)
    private String periodOfWarranty;

    @Column( nullable = false)
    @Lob
    private String description;

    @Column( nullable = false)
    private String image;

    @Column( nullable = false)
    private float discount;

    @Column(length = 50, nullable = false, unique = true)
    private String sku; // ma san pham

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @ManyToOne
    @JoinColumn(name = "typeOfSpace_id")
    private TypeSpace typeSpace;

    //discount; ip15  bh 30tr   5%,   1 nawm sau 30tr   20%

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus status = ProductStatus.ACTIVED;



}
