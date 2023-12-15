package com.e.auction.api.model;

import com.e.auction.api.model.audit.Auditable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "shipping")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Shipping extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String postalCode;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id")
    private Address province;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private Address district;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ward_id")
    private Address ward;
    @Column(columnDefinition = "TEXT")
    private String address;
    private String phone;
}
