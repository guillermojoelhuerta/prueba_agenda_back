package com.agenda.agenda.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name="agenda")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Agenda {
    @Id
    @Digits(integer = 10, fraction = 0, message = "El número de teléfono debe tener 10 dígitos")
    //@Min(value = 10, message = "El número de teléfono debe tener 10 dígitos")
    //@Max(value = 10, message = "El número de teléfono debe tener 10 dígitos")
    private Long telefono;
    @NotBlank(message = "El nombre es requerido")
    private String nombre;
    @Column(columnDefinition = "TEXT")
    @NotBlank(message = "La dirección es requerida")
    private String direccion;
}
