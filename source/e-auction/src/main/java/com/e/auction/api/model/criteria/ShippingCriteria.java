package com.e.auction.api.model.criteria;

import com.e.auction.api.constant.EAuctionConstant;
import com.e.auction.api.model.Address;
import com.e.auction.api.model.Shipping;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ShippingCriteria implements Serializable{
    private Long id;
    private String postalCode;
    private Long provinceId;
    private Long districtId;
    private Long wardId;
    private String address;
    private String phone;

    public Specification<Shipping> getSpecification() {
        return new Specification<Shipping>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Shipping> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }

                if (getPostalCode() != null) {
                    predicates.add(cb.like(cb.lower(root.get("postalCode")), "%" + getPostalCode().trim().toLowerCase() + "%"));
                }
                if (getProvinceId() != null || getDistrictId() != null || getWardId() != null) {
                    Join<Shipping, Address> province = root.join("province", JoinType.INNER);
                    predicates.add(cb.equal(province.get("kind"), EAuctionConstant.ADDRESS_KIND_PROVINCE));
                }
                if (getDistrictId() != null) {
                    Join<Shipping, Address> district = root.join("district", JoinType.INNER);
                    predicates.add(cb.equal(district.get("kind"), EAuctionConstant.ADDRESS_KIND_DISTRICT));
                }
                if (getWardId() != null) {
                    Join<Shipping, Address> ward = root.join("ward", JoinType.INNER);
                    predicates.add(cb.equal(ward.get("kind"), EAuctionConstant.ADDRESS_KIND_WARD));
                }
                if (getPhone() != null) {
                    predicates.add(cb.like(cb.lower(root.get("phone")), "%" + getPhone().toLowerCase() + "%"));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
