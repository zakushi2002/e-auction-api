package com.e.auction.api.model.criteria;

import com.e.auction.api.model.Auction;
import com.e.auction.api.model.BidHistory;
import com.e.auction.api.model.Account;
import com.e.auction.api.model.Product;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class BidHistoryCriteria implements Serializable {
    private Long id;
    private Long productId;
    private Long bidderId;
    private Date fromBidTime;
    private Date toBidTime;

    public Specification<BidHistory> getSpecification() {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<BidHistory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (getProductId() != null) {
                    Join<Auction, BidHistory> auction = root.join("auction", JoinType.INNER);
                    Join<Product, Auction> product = auction.join("product", JoinType.INNER);
                    predicates.add(cb.equal(product.get("id"), getProductId()));
                }
                if (getBidderId() != null) {
                    Join<Account, BidHistory> bidder = root.join("bidder", JoinType.INNER);
                    predicates.add(cb.equal(bidder.get("id"), getBidderId()));
                }
                if (getFromBidTime() != null && getToBidTime() != null) {
                    predicates.add(cb.between(root.get("createdDate"), getFromBidTime(), getToBidTime()));
                } else if (getFromBidTime() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("createdDate"), getFromBidTime()));
                } else if (getToBidTime() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("createdDate"), getToBidTime()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
