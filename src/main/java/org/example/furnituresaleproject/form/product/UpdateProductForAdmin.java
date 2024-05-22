package org.example.furnituresaleproject.form.product;

import lombok.Data;

@Data
public class UpdateProductForAdmin {
    private int id;
    private String name;
    private float price;
    private String periodOfWarranty;
    private String description;
    private String image;
    private  float discount;
}
