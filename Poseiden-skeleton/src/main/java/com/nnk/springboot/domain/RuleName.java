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
    @NotBlank(message = "name is mandatory")
    String name;

    @Column
    @NotBlank(message = "description is mandatory")
    String description;

    @Column
    @NotBlank(message = "json is mandatory")
    String json;

    @Column
    @NotBlank(message = "template is mandatory")
    String template;

    @Column
    @NotBlank(message = "sqlStr is mandatory")
    String sqlStr;

    @Column
    @NotBlank(message = "sqlPart is mandatory")
    String sqlPart;

    public RuleName() {

    }
}
