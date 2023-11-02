package com.e.auction.api.model.criteria;

import com.e.auction.api.model.Account;
import com.e.auction.api.model.UserProfile;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserProfileCriteria implements Serializable {
    private Long id;
    private String email;
    private String fullName;
    private String phone;
    private Integer gender;
    private Integer status;

    public Specification<UserProfile> getSpecification() {
        return new Specification<UserProfile>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<UserProfile> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (getGender() != null) {
                    predicates.add(cb.equal(root.get("gender"), getGender()));
                }
                Join<UserProfile, Account> account = root.join("account", JoinType.INNER);
                if (getStatus() != null) {
                    predicates.add(cb.equal(account.get("status"), getStatus()));
                }
                if (!StringUtils.isEmpty(getEmail())) {
                    predicates.add(cb.like(cb.lower(account.get("email")), "%" + getEmail().toLowerCase() + "%"));
                }
                if (!StringUtils.isEmpty(getFullName())) {
                    predicates.add(cb.like(cb.lower(account.get("fullName")), "%" + getFullName().toLowerCase() + "%"));
                }
                if (!StringUtils.isEmpty(getPhone())) {
                    predicates.add(cb.like(cb.lower(root.get("phone")), "%" + getPhone().toLowerCase() + "%"));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
