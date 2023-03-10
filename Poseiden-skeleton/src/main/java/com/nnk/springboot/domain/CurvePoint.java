package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "curvepoint")
public class CurvePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    Integer id;
    @Column
    Integer curveId;
    @Column
    Timestamp asOfDate;
    @Column
    Double term;
    @Column
    Double value;
    @Column
    Timestamp creationDate;


    public CurvePoint() {

    }
}
