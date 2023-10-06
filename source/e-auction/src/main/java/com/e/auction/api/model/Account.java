package com.e.auction.api.model;

import com.e.auction.api.model.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "account")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account extends Auditable<String> {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "com.e.auction.api.service.id.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;
    private Integer kind;
    private String email;
    @JsonIgnore
    private String password;
    private String fullName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;
    private Date lastLogin;
    @Column(name = "avatar_path", columnDefinition = "LONGTEXT")
    private String avatarPath;
    @Column(name = "reset_pwd_code")
    private String resetPwdCode;
    @Column(name = "reset_pwd_time")
    private Date resetPwdTime;
    @Column(name = "attempt_forget_pwd")
    private Integer attemptCode;
    private Integer attemptLogin;
    private Boolean isSuperAdmin = false;
}
