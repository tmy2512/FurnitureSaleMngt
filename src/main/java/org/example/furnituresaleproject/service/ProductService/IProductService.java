package org.example.furnituresaleproject.service.ProductService;

import org.example.furnituresaleproject.entity.Product;
import org.example.furnituresaleproject.form.Order.CreateOrderForm;
import org.example.furnituresaleproject.form.product.CreateProductForAdminForm;
import org.example.furnituresaleproject.form.product.UpdateProductForAdmin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


public interface IProductService {
    public Page<Product> getAllProducts(Pageable pageable, String search);
    public void createProductForAdmin(CreateProductForAdminForm form);
    public void updateProductForAdmin(UpdateProductForAdmin form);
    public void deleteLogicProduct(int id, String status);
}