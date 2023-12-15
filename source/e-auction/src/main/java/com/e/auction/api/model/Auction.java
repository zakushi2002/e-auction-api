package com.e.auction.api.model;

import com.e.auction.api.model.audit.Auditable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "auction")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Auction extends Auditable<String> {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "com.e.auction.api.service.id.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seller_id")
    private Account seller;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_id")
    private Account winner;
    private Integer maxBidders;
    private Double currentPrice;
    private Double minBidPrice;
    private Date startDate;
    private Date endDate;
    @OneToMany(mappedBy = "auction", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BidHistory> bidHistories;
}
