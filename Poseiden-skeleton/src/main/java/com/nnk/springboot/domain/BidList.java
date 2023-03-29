package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "bidlist")
public class BidList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column
    Integer BidListId;

    @Column
    @NotBlank(message = "account is mandatory")
    String account;

    @Column
    @NotBlank(message = "type is mandatory")
    String type;

    @Column
    Double bidQuantity;

    @Column
    Double askQuantity;

    @Column
    Double bid;

    @Column
    Double ask;

    @Column
    String benchmark;

    @Column
    Timestamp bidListDate;

    @Column
    String commentary;

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

    public BidList(String account, String type, double bidQuantity) {
    }

    public BidList() {

    }

}
