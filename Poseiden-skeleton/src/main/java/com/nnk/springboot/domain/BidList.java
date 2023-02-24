package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Required;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "bidlist")
public class BidList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column
    Integer BidListId;
    @Column
    String account;
    @Column
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
    // TODO: Map columns in data table BIDLIST with corresponding java fields
}
