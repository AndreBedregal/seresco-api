package com.github.andrebedregal.seresco.model;

import com.github.andrebedregal.seresco.model.dto.ProfesorCommandDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "profesores")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "profesor_nombres")
    private String nombres;

    @Column(name = "profesor_apellidos")
    private String apellidos;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "profesores_alumnos",
            joinColumns = @JoinColumn(name = "profesor_id"),
            inverseJoinColumns = @JoinColumn(name = "alumno_id"))
    private Set<Alumno> alumnos;

    public Profesor(ProfesorCommandDTO profesorCommandDTO) {
        this.nombres = profesorCommandDTO.getNombres();
        this.apellidos = profesorCommandDTO.getApellidos();
    }

}
