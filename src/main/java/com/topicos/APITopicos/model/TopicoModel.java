package com.topicos.APITopicos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Data
@Entity
public class TopicoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idTopico")
    private Integer id;
    @NotBlank
    private String titulo;
    @NotBlank
    private String mensaje;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaCreacion;
    @NotEmpty
    private String status;
    @NotEmpty
    private String autor;
    @NotEmpty
    private String curso;
}