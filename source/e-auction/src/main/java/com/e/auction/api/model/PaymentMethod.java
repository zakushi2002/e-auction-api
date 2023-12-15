package com.e.auction.api.model;

import com.e.auction.api.model.audit.Auditable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "payment_method")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class PaymentMethod extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
