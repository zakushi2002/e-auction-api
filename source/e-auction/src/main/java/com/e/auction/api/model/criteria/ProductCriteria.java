package com.e.auction.api.model.criteria;

import com.e.auction.api.model.Account;
import com.e.auction.api.model.Category;
import com.e.auction.api.model.Product;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductCriteria implements Serializable {
    private Long id;
    private String name;
    private Long categoryId;
    private Integer status;
    private Long sellerId;
    private Double fromStartBidPrice;
    private Double toStartBidPrice;
    private Double fromBuyNowPrice;
    private Double toBuyNowPrice;

    public Specification<Product> getSpecification() {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (!StringUtils.isEmpty(getName())) {
                    predicates.add(cb.like(cb.lower(root.get("name")), "%" + getName().toLowerCase() + "%"));
                }
                if (getStatus() != null) {
                    predicates.add(cb.equal(root.get("status"), getStatus()));
                }
                if (getCategoryId() != null) {
                    Join<Product, Category> category = root.join("category", JoinType.INNER);
                    predicates.add(cb.equal(category.get("id"), getCategoryId()));
                }
                if (getSellerId() != null) {
                    Join<Product, Account> seller = root.join("seller", JoinType.INNER);
                    predicates.add(cb.equal(seller.get("id"), getSellerId()));
                }
                if (getFromStartBidPrice() != null && getToStartBidPrice() != null) {
                    predicates.add(cb.between(root.get("startBidPrice"), getFromStartBidPrice(), getToStartBidPrice()));
                }
                else if (getFromStartBidPrice() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("startBidPrice"), getFromStartBidPrice()));
                }
                else if (getToStartBidPrice() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("startBidPrice"), getToStartBidPrice()));
                }
                if (getFromBuyNowPrice() != null && getToBuyNowPrice() != null) {
                    predicates.add(cb.between(root.get("buyNowPrice"), getFromBuyNowPrice(), getToBuyNowPrice()));
                }
                else if (getFromBuyNowPrice() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("buyNowPrice"), getFromBuyNowPrice()));
                }
                else if (getToBuyNowPrice() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("buyNowPrice"), getToBuyNowPrice()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
