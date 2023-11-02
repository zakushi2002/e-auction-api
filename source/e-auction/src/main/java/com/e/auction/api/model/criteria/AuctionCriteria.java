package com.e.auction.api.model.criteria;

import com.e.auction.api.model.Account;
import com.e.auction.api.model.Auction;
import com.e.auction.api.model.Product;
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
    private Long winnerId;
    private Long sellerId;
    private Double fromBidPrice;
    private Double toBidPrice;
    private Integer status;
    private Date fromBidTime;
    private Date toBidTime;

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
                }
                else if (getFromBidPrice() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("currentPrice"), getFromBidPrice()));
                }
                else if (getToBidPrice() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("currentPrice"), getToBidPrice()));
                }
                if (getFromBidTime() != null && getToBidTime() != null) {
                    predicates.add(cb.between(root.get("startDate"), getFromBidTime(), getToBidTime()));
                }
                else if (getFromBidTime() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"), getFromBidTime()));
                }
                else if (getToBidTime() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("startDate"), getToBidTime()));
                }
                if (getStatus() != null) {
                    predicates.add(cb.equal(root.get("status"), getStatus()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
