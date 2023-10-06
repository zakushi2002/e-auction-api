package com.e.auction.api.model.criteria;

import com.e.auction.api.model.Group;
import com.e.auction.api.model.Account;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class AccountCriteria implements Serializable {
    private Long id;
    private Integer kind;
    private String email;
    private String fullName;
    private Long groupId;
    private Boolean isSuperAdmin;
    private Integer status;

    public Specification<Account> getSpecification() {
        return new Specification<Account>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (getKind() != null) {
                    predicates.add(cb.equal(root.get("kind"), getKind()));
                }
                if (getEmail() != null) {
                    predicates.add(cb.like(cb.lower(root.get("email")), "%" + getEmail().toLowerCase() + "%"));
                }
                if (getFullName() != null) {
                    predicates.add(cb.like(cb.lower(root.get("fullName")), "%" + getFullName().toLowerCase() + "%"));
                }
                if (getGroupId() != null) {
                    Join<Group, Account> group = root.join("group", JoinType.INNER);
                    predicates.add(cb.equal(group.get("id"), getGroupId()));
                }
                if (getIsSuperAdmin() != null) {
                    predicates.add(cb.equal(root.get("isSuperAdmin"), getIsSuperAdmin()));
                }
                if (getStatus() != null) {
                    predicates.add(cb.equal(root.get("status"), getStatus()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
