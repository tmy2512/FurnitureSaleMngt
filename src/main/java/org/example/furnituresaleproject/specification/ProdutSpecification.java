package org.example.furnituresaleproject.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.furnituresaleproject.entity.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;


public class ProdutSpecification {

    public static Specification<Product> buildWhere( String searchName) {

        Specification<Product> where = null;

        if(!StringUtils.isEmpty(searchName)) {
            searchName = searchName.trim();
            CustomSpecificationProduct search = new CustomSpecificationProduct("name", searchName);
            where =Specification.where(search);
        }
        return  where;
    }
}

@RequiredArgsConstructor
class CustomSpecificationProduct implements Specification<Product> {

    @NonNull
    private String field; // fieldName to compare
    @NonNull
    private Object value;

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        if(field.equalsIgnoreCase("name"))
            return criteriaBuilder.like(root.get("name"), "%"+value.toString()+"%");

        return null;
    }

}
