package org.example.furnituresaleproject.form.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.example.furnituresaleproject.entity.Product;
import org.hibernate.validator.constraints.Length;

@Data
public class CreateProductForAdminForm {

    @NotBlank(message = "{Product.createProductForAdminForm.NotBlank}")
//    @Length(max = 50, message = "{Product.createProductForAdminForm.Length.name}")
    private String name;

    @NotNull(message = "{Product.createProductForAdminForm.price}")
    private float price;

    private String periodOfWarranty;

    @NotBlank(message = "{Product.createProductForAdminForm.NotBlank}")
    private String description;

    @NotBlank(message = "{Product.createProductForAdminForm.NotBlank}")
    private String image;

    @NotNull(message = "{Product.createProductForAdminForm.discount}")
    private Float discount;

    private int typeSpaceId;

    private int providerId;

}
