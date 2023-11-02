package com.e.auction.api.model.criteria;

import com.e.auction.api.model.Address;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class AddressCriteria implements Serializable {
    private Long id;
    private String name;
    private String postalCode;
    private Integer kind;
    private Long parentId;

    public Specification<Address> getSpecification() {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Address> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (!StringUtils.isEmpty(getName())) {
                    predicates.add(cb.like(cb.lower(root.get("name")), "%" + getName().toLowerCase() + "%"));
                }
                if (!StringUtils.isEmpty(getPostalCode())) {
                    predicates.add(cb.like(cb.lower(root.get("postalCode")), "%" + getPostalCode().toLowerCase() + "%"));
                }
                if (getKind() != null) {
                    predicates.add(cb.equal(root.get("kind"), getKind()));
                }
                if (getParentId() != null) {
                    Join<Address, Address> parentAddress = root.join("parent", JoinType.INNER);
                    predicates.add(cb.equal(parentAddress.get("id"), getParentId()));
                } else {
                    predicates.add(cb.isNull(root.get("parent")));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
