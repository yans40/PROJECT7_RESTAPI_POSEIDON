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
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    Integer id;
    @Column
    @NotBlank(message = "moodysRating is mandatory")
    String moodysRating;

    @Column
    @NotBlank(message = "sandPRating is mandatory")
    String sandPRating;

    @Column
    @NotBlank(message = "fitchRating is mandatory")
    String fitchRating;

    @Column
    Integer orderNumber;

    public Rating() {

    }
}
