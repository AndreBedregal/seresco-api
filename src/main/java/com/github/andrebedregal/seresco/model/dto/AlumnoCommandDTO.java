package com.github.andrebedregal.seresco.model.dto;

import com.github.andrebedregal.seresco.model.GeneroEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class AlumnoCommandDTO {

    @NotBlank(message = "No deben estar en blanco")
    private String nombres;

    @NotBlank(message = "No deben estar en blanco")
    private String apellidos;

    @NotNull(message = "No debe ser nulo")
    private Date fechaNacimiento;

    @NotNull(message = "No debe ser nulo")
    private GeneroEnum genero;

}
