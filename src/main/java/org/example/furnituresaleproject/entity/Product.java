package org.example.furnituresaleproject.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "products")
@NoArgsConstructor
public class Product {

    @Column(name = "productID", columnDefinition = "unsigned int")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name",length = 100, nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private float price;//30tr

    @Column(name = "periodOfWarranty", nullable = false)
    private String periodOfWarranty;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "discount", nullable = false)
    private float discount;

    @ManyToOne
    @JoinColumn(name = "provider")
    private Provider provider;

    @ManyToOne
    @JoinColumn(name = "typeOfSpace")
    private TypeSpace typeSpace;

    //discount; ip15  bh 30tr   5%,   1 nawm sau 30tr   20%

    @Column(name = "status", columnDefinition = "default 'ACTIVED'")
    @Enumerated(EnumType.STRING)
    private StatusProduct status;



}
