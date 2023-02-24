package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "rulename")
public class RuleName {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    Integer id;
    @Column
    String name;
    @Column
    String description;
    @Column
    String json;
    @Column
    String template;
    @Column
    String sqlStr;
    @Column
    String sqlPart;

    // TODO: Map columns in data table RULENAME with corresponding java fields
}
