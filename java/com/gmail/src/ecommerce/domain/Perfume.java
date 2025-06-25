package com.gmail.src.ecommerce.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Perfume {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Proszę wypełnić pole")
    @Length(max = 255)
    private String perfumeTitle;

    @NotBlank(message = "Proszę wypełnić pole")
    @Length(max = 255)
    private String perfumer;

    @NotNull(message = "Proszę wypełnić pole")
    private Integer year;

    @NotBlank(message = "Proszę wypełnić pole")
    @Length(max = 255)
    private String country;

    @NotBlank(message = "Proszę wypełnić pole")
    @Length(max = 255)
    private String perfumeGender;

    @NotBlank(message = "Proszę wypełnić pole")
    @Length(max = 255)
    private String fragranceTopNotes;

    @NotBlank(message = "Proszę wypełnić pole")
    @Length(max = 255)
    private String fragranceMiddleNotes;

    @NotBlank(message = "Proszę wypełnić pole")
    @Length(max = 255)
    private String fragranceBaseNotes;

    private String description;
    private String filename;

    @NotNull(message = "Proszę wypełnić pole")
    private Integer price;

    @NotBlank(message = "Proszę wypełnić pole")
    @Length(max = 255)
    private String volume;

    @NotBlank(message = "Proszę wypełnić pole")
    @Length(max = 255)
    private String type;
}
