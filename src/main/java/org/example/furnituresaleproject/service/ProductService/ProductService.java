package org.example.furnituresaleproject.service.ProductService;

import org.example.furnituresaleproject.entity.Product;
import org.example.furnituresaleproject.entity.ProductStatus;
import org.example.furnituresaleproject.entity.Provider;
import org.example.furnituresaleproject.entity.TypeSpace;
import org.example.furnituresaleproject.form.product.CreateProductForAdminForm;
import org.example.furnituresaleproject.form.product.UpdateProductForAdmin;
import org.example.furnituresaleproject.repository.IProductRepository;
import org.example.furnituresaleproject.repository.IProviderRepository;
import org.example.furnituresaleproject.repository.ITypeSpaceRepository;
import org.example.furnituresaleproject.service.provider.IProviderService;
import org.example.furnituresaleproject.specification.ProdutSpecification;
import org.junit.platform.commons.util.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService implements IProductService{

    @Autowired
    private IProductRepository repository;

    @Autowired
    private IProviderRepository providerRepository;

    @Autowired
    private ITypeSpaceRepository typeSpaceRepository;

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
        // ignore id của thuộc tinh khóa chính khi set id cho khóa ngoại
                @Override
                protected void configure() {
                    skip(destination.getId());
                }
            });
        }
        // convert form to entity
        Product product = modelMapper.map(form, Product.class);
        product.setSku(generateSku(product, form));
        repository.save(product);
    }

    // generate SKU product
    private String generateSku(Product product,CreateProductForAdminForm form) {
        String nameProduct = form.getName();
        StringBuilder productInitials = new StringBuilder();
        //  get first letter of name's words
        String[] words = nameProduct.split(" ");
        // Loop through each word and take the first letter
        for (String word : words) {
            if (!word.isEmpty()) {
                productInitials.append(Character.toUpperCase(word.charAt(0)));
            }
        }
        productInitials.toString();

        // Get the first letter of the provider's name
        StringBuilder typeSpaceInitial = new StringBuilder();  // Default value if provider not found
        if (form.getTypeSpaceId() > 0) {
            TypeSpace typeSpace = typeSpaceRepository.findById(form.getTypeSpaceId()).orElse(null);
            if (typeSpace != null) {
                String[] typeSpaceName = typeSpace.getName().split(" ");
                for(String name : typeSpaceName) {
                    typeSpaceInitial.append(Character.toUpperCase(name.charAt(0)));
                }
            }
        }

        // Generate a unique sequence number based on existing products
        int productCount = repository.countByTypeSpaceIdAndProviderId(form.getTypeSpaceId(), form.getProviderId()) + 1;

        // Combine these to form the SKU
        return String.format("%s-%s-%03d", typeSpaceInitial, productInitials, productCount);
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
        product.setStatus(ProductStatus.valueOf(status.toUpperCase()));
        repository.save(product);
    }



}
