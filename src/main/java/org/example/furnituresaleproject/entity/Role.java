package org.example.furnituresaleproject.entity;

import jakarta.persistence.*;


import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "role")
public class Role implements Serializable {
    private static final Long serialVersionID = 1L;

    @Column(name = "id", columnDefinition = "unsigned int")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name",length = 30, nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "role")
    private List<Account> lstAccount;


    public Role() {
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


}
