package org.jfantasy.member.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.jfantasy.framework.dao.BaseBusEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * 钱包
 */
@Entity
@Table(name = "WALLET")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Wallet extends BaseBusEntity {
    /**
     * 钱包ID
     */
    @Id
    @Column(name = "ID", updatable = false)
    @GeneratedValue(generator = "fantasy-sequence")
    @GenericGenerator(name = "fantasy-sequence", strategy = "fantasy-sequence")
    private Long id;
    /**
     * 会员
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", updatable = false, foreignKey = @ForeignKey(name = "FK_WALLET_MEMBER_MID"))
    private Member member;
    /**
     * 账户余额
     */
    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;
    /**
     * 累积收入
     */
    @Column(name = "INCOME", nullable = false)
    private BigDecimal income;
    /**
     * 资金账户
     */
    @Column(name = "ACCOUNT", nullable = false, updatable = false)
    private String account;
    @OneToMany(mappedBy="wallet", fetch = FetchType.LAZY)
    private List<WalletBill> bills;
    /**
     * 有效的积分
     */
    @Column(name = "POINTS", nullable = false)
    private Long points;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

}
