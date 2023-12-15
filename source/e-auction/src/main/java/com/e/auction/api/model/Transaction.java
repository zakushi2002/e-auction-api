package com.e.auction.api.model;

import com.e.auction.api.model.audit.Auditable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "transaction")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Transaction extends Auditable<String> {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "com.e.auction.api.service.id.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bidder_id")
    private Account bidder;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "auction_id")
    private Auction auction;
    private String address;
    @ManyToOne(fetch = FetchType.LAZY)
    private ShippingCost shippingCost;
    @ManyToOne(fetch = FetchType.LAZY)
    private PaymentMethod paymentMethod;
    private Date shippingDate;
    private Integer duration;
    private Date receiveDate;
}
