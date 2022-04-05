package com.github.andrebedregal.seresco.model.dto;

import com.github.andrebedregal.seresco.model.Alumno;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlumnoQueryDTO {

    private Long id;

    private String nombres;

    private String apellidos;

    private Date fechaNacimiento;

    private String genero;

    public static AlumnoQueryDTO getInstance(Alumno alumno) {
        return new AlumnoQueryDTO(alumno.getId(),
                alumno.getNombres(),
                alumno.getApellidos(),
                alumno.getFechaNacimiento(),
                alumno.getGenero().name());
    }

}
