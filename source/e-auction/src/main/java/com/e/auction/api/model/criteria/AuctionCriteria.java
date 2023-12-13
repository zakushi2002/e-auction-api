package com.e.auction.api.model.criteria;

import com.e.auction.api.constant.EAuctionConstant;
import com.e.auction.api.model.*;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class AuctionCriteria implements Serializable {
    private Long id;
    private Long productId;
    private String productName;
    private Long winnerId;
    private Long sellerId;
    private Double fromBidPrice;
    private Double toBidPrice;
    private Integer status;
    private Date fromBidTime;
    private Date toBidTime;
    private Long categoryId;
    private Date now;
    private Integer sortBy = 3;

    public Specification<Auction> getSpecification() {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Auction> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (getProductId() != null) {
                    Join<Product, Auction> product = root.join("product", JoinType.INNER);
                    predicates.add(cb.equal(product.get("id"), getProductId()));
                }
                if (getProductName() != null) {
                    Join<Product, Auction> product = root.join("product", JoinType.INNER);
                    predicates.add(cb.like(product.get("name"), "%" + getProductName().trim().toLowerCase() + "%"));
                }
                if (getWinnerId() != null) {
                    Join<Account, Auction> winner = root.join("winner", JoinType.INNER);
                    predicates.add(cb.equal(winner.get("id"), getWinnerId()));
                }
                if (getSellerId() != null) {
                    Join<Account, Auction> seller = root.join("seller", JoinType.INNER);
                    predicates.add(cb.equal(seller.get("id"), getSellerId()));
                }
                if (getFromBidPrice() != null && getToBidPrice() != null) {
                    predicates.add(cb.between(root.get("currentPrice"), getFromBidPrice(), getToBidPrice()));
                } else if (getFromBidPrice() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("currentPrice"), getFromBidPrice()));
                } else if (getToBidPrice() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("currentPrice"), getToBidPrice()));
                }
                if (getFromBidTime() != null && getToBidTime() != null) {
                    predicates.add(cb.between(root.get("startDate"), getFromBidTime(), getToBidTime()));
                } else if (getFromBidTime() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"), getFromBidTime()));
                } else if (getToBidTime() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("startDate"), getToBidTime()));
                }
                if (getCategoryId() != null) {
                    Root<Product> product = query.from(Product.class);
                    Join<Category, Product> category = product.join("category", JoinType.INNER);
                    predicates.add(cb.equal(category.get("id"), getCategoryId()));
                    predicates.add(cb.equal(root.get("product").get("id"), product.get("id")));
                }

                if (getSortBy() != null) {
                    if (getSortBy().equals(EAuctionConstant.SORT_PRICE_ASC)) {
                        query.orderBy(cb.asc(root.get("currentPrice")));
                    } else if (getSortBy().equals(EAuctionConstant.SORT_PRICE_DESC)) {
                        query.orderBy(cb.desc(root.get("currentPrice")));
                    } else if (getSortBy().equals(EAuctionConstant.SORT_NAME_ASC)) {
                        Join<Product, Auction> product = root.join("product", JoinType.INNER);
                        query.orderBy(cb.asc(product.get("name")));
                    } else if (getSortBy().equals(EAuctionConstant.SORT_NAME_DESC)) {
                        Join<Product, Auction> product = root.join("product", JoinType.INNER);
                        query.orderBy(cb.desc(product.get("name")));
                    }
                }
                if (getStatus() != null) {
                    predicates.add(cb.equal(root.get("status"), getStatus()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
