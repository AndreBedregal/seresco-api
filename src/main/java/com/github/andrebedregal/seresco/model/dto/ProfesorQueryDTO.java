package com.github.andrebedregal.seresco.model.dto;

import com.github.andrebedregal.seresco.model.Profesor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfesorQueryDTO {

    private Long id;
    private String nombres;
    private String apellidos;

    public static ProfesorQueryDTO getInstance(Profesor profesor) {
        return new ProfesorQueryDTO(profesor.getId(), profesor.getNombres(), profesor.getApellidos());
    }

}
