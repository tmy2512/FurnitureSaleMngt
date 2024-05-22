package org.example.furnituresaleproject.controller;

import jakarta.validation.Valid;
import org.example.furnituresaleproject.dto.ProductDTO;
import org.example.furnituresaleproject.entity.Product;
import org.example.furnituresaleproject.form.product.CreateProductForAdminForm;
import org.example.furnituresaleproject.form.product.UpdateProductForAdmin;
import org.example.furnituresaleproject.service.ProductService.IProductService;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/products")
@CrossOrigin("*")
@Validated
public class ProductController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IProductService service;

    @GetMapping()
    public Page<ProductDTO> getAllProduct(Pageable pageable, @RequestParam(required = false)String search) {
        Page<Product> entityProduct = service.getAllProducts(pageable, search);
        List<ProductDTO> dtos = modelMapper.map(entityProduct.getContent(), new TypeToken<List<ProductDTO>>() {}.getType());
        Page<ProductDTO> dtoPage = new PageImpl<>(dtos);
        return dtoPage;

    }

    @PostMapping()
    public ResponseEntity<?> createProductForAdmin(@RequestBody @Valid CreateProductForAdminForm form){
        service.createProductForAdmin(form);
        return new ResponseEntity<>("Successful", HttpStatus.OK);
    }

    @PutMapping()
    public void updateProductForAdmin(@RequestParam int idU, @RequestBody UpdateProductForAdmin form) {
        form.setId(idU);
        service.updateProductForAdmin(form);
    }

    @PutMapping("/deleteLogic")
    public void deleteLogicProduct(@RequestParam int idDelete, @RequestParam String status) {
        service.deleteLogicProduct(idDelete, status);
    }
}
