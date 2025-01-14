package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;


@Entity
@Getter
@Setter
@Table(name = "trade")
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    Integer id;
    @Column
    Integer trade_Id;
    @Column
    @NotBlank(message = "account is mandatory")
    String account;
    @Column
    @NotBlank(message = "type is mandatory")
    String type;
    @Column
    Double buyQuantity;
    @Column
    Double sellQuantity;
    @Column
    Double buyPrice;
    @Column
    Double sellPrice;
    @Column
    String benchmark;
    @Column
    Timestamp tradeDate;
    @Column
    String security;
    @Column
    String status;
    @Column
    String trader;
    @Column
    String book;
    @Column
    String creationName;
    @Column
    Timestamp creationDate;
    @Column
    String revisionName;
    @Column
    Timestamp revisionDate;
    @Column
    String dealName;
    @Column
    String dealType;
    @Column
    String sourceListId;
    @Column
    String side;

    public Trade(String tradeAccount, String type) {
    }

    public Trade() {

    }

}
