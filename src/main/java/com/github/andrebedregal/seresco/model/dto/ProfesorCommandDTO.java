package com.github.andrebedregal.seresco.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProfesorCommandDTO {

    @NotBlank(message = "No deben estar en blanco")
    private String nombres;

    @NotBlank(message = "No deben estar en blanco")
    private String apellidos;

}
