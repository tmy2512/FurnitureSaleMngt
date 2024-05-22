package org.example.furnituresaleproject.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "account")
public class Account {

    @Column(name = "id", columnDefinition = "unsigned int")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name",length = 50, nullable = false)
    private String name;

    @Column(name = "address",length = 50, nullable = false)
    private String address;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "phone",length = 12, nullable = false)
    private String phone;

    @Column(name = "gender" , nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "role")
    private Role role;

    @OneToMany(mappedBy = "customer")
    private List<Order> lstInvoice;

    @OneToMany(mappedBy = "vendor")
    private List<Order> lstVendor;

    public Account() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Order> getLstInvoice() {
        return lstInvoice;
    }

    public void setLstInvoice(List<Order> lstInvoice) {
        this.lstInvoice = lstInvoice;
    }

    public List<Order> getLstVendor() {
        return lstVendor;
    }

    public void setLstVendor(List<Order> lstVendor) {
        this.lstVendor = lstVendor;
    }

    public enum Gender {
        NAM, NU
    }

}
