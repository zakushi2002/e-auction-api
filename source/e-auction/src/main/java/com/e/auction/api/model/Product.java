package com.e.auction.api.model;

import com.e.auction.api.model.audit.Auditable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "product")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Product extends Auditable<String> {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "com.e.auction.api.service.id.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;
    private String name;
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    private Double startBidPrice;
    private Double buyNowPrice;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seller_id")
    private Account seller;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    private Integer bidTime;
    @Column(columnDefinition = "LONGTEXT")
    private String mainImage;
    @Column(columnDefinition = "LONGTEXT")
    private String subImage1;
    @Column(columnDefinition = "LONGTEXT")
    private String subImage2;
    @Column(columnDefinition = "LONGTEXT")
    private String subImage3;
}
