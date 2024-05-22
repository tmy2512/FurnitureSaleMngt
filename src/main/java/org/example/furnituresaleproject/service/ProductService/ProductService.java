package org.example.furnituresaleproject.service.ProductService;

import org.example.furnituresaleproject.entity.Order;
import org.example.furnituresaleproject.entity.Product;
import org.example.furnituresaleproject.entity.StatusProduct;
import org.example.furnituresaleproject.form.Order.CreateOrderForm;
import org.example.furnituresaleproject.form.product.CreateProductForAdminForm;
import org.example.furnituresaleproject.form.product.UpdateProductForAdmin;
import org.example.furnituresaleproject.repository.IProductRepository;
import org.example.furnituresaleproject.specification.ProdutSpecification;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService implements IProductService{

    @Autowired
    private IProductRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<Product> getAllProducts(Pageable pageable, String search) {

        Specification<Product> spec = ProdutSpecification.buildWhere(search);
//                .and(CustomProductSpecification.searchDescription(""));
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        Page<Product> list =repository.findAll(spec, pageable);
        return list;
    }

    @Override
    public void createProductForAdmin(CreateProductForAdminForm form) {
        TypeMap<CreateProductForAdminForm, Product> typeMap = modelMapper.getTypeMap(CreateProductForAdminForm.class, Product.class);
        if (typeMap == null) {
            modelMapper.addMappings(new PropertyMap<CreateProductForAdminForm, Product>() {

                @Override
                protected void configure() {
                    skip(destination.getId());
                }
            });
        }
        // convert form to entity
        Product product = modelMapper.map(form, Product.class);
        repository.save(product);
    }

    @Override
    public void updateProductForAdmin(UpdateProductForAdmin form) {
        Optional<Product> productOptional = repository.findById(form.getId());
        Product product = productOptional.get();
        product.setName(form.getName());
        product.setPrice(form.getPrice());
        product.setPeriodOfWarranty(form.getPeriodOfWarranty());
        product.setDescription(form.getDescription());
        product.setImage(form.getImage());
        product.setDiscount(form.getDiscount());
        repository.save(product);

    }

    @Override
    public void deleteLogicProduct(int id, String status) {
        Optional<Product> productOptional = repository.findById(id);
        Product product = productOptional.get();
        product.setStatus(StatusProduct.valueOf(status.toUpperCase()));
        repository.save(product);
    }



}
