package org.example.furnituresaleproject.form.Order;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
public class CreateOrderForm {

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Future(message = "{Product.createOrderForm.dateOfDelivery}")
    private String dateOfDelivery;

    @NotBlank(message = "{Product.createOrderForm.shippingAddress}")
    private String shippingAddress;
    private String note;
    //id nguoi mua se lay tu account dang nhap
    @NotEmpty(message = "required list products")
    private List<@Valid CreateOrderDetailForm> createOrderDetailForms;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateOrderDetailForm{

        private int productId;
//        @Positive(message = "{Product.createOrderDetailForm.quantity}")
        private int quantity;
        private float discount;
    }
}
