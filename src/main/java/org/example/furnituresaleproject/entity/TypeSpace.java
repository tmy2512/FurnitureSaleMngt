package org.example.furnituresaleproject.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "typespace")
public class TypeSpace {

    @Column(name = "id", columnDefinition = "unsigned int")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name",length = 50, nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "typeSpace")
    private List<Product> lstProduct;

    public TypeSpace() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getLstProduct() {
        return lstProduct;
    }

    public void setLstProduct(List<Product> lstProduct) {
        this.lstProduct = lstProduct;
    }
}
