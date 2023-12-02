package com.e.auction.api.model;

import com.e.auction.api.model.audit.Auditable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "address")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Address extends Auditable<String> {
    @Id
    private String id;
    private String name;
    private String postalCode;
    private Integer kind;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "parent_id")
    private Address parent;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Address> nations;
}
