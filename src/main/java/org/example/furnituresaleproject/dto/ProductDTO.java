package org.example.furnituresaleproject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ProductDTO {
    private int id;
    private String name;
    private Float price;
    private String description;
    private String image;
    private Float discount;
    private String providerName;
    private String typeSpaceName;
    private String periodOfWarranty;
}
