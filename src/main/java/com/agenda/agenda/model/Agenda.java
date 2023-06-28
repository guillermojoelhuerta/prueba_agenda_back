package com.agenda.agenda.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name="agenda")
@Data
public class Agenda {
    @Id
    private Long telefono;
    @NotBlank(message = "El nombre es requerido")
    private String nombre;
    @Column(columnDefinition = "TEXT")
    @NotBlank(message = "La dirección es requerida")
    private String direccion;
}
