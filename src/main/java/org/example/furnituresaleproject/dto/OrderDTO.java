package org.example.furnituresaleproject.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderDTO {
    private Integer id;
    private Float totalAmount;
    private String dateOfPurchase;
    private String dateOfDelivery;
    private String shippingAddress;
    private String status;
//    private String note;

    // thong tin khach hang
    private String customerName;
    private String customerPhone;

    // thong tin chi tiet don hang
    private List<OrderDetailDTO> orderDetails;

    @Data
    public static class OrderDetailDTO {
        private Integer productId;
        private String productName;
        private Float productPrice;
        private Integer quantity;
        private Float discount;
        private Float totalPrice;

    }


}
