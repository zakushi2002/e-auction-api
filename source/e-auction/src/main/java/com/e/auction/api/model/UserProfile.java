package com.e.auction.api.model;

import com.e.auction.api.model.audit.Auditable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "user_profile")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class UserProfile extends Auditable<String> {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "com.e.auction.api.service.id.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;
    private Integer gender;
    private String phone;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    @MapsId
    private Account account;
    private Date birthdate;
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
}
