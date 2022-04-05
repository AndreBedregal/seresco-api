package com.github.andrebedregal.seresco.model;

import com.github.andrebedregal.seresco.model.dto.AlumnoCommandDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 *
 */
@Entity
@Table(name = "alumnos")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "alumno_nombres")
    private String nombres;

    @Column(name = "alumno_apellidos")
    private String apellidos;

    @Column(name = "alumno_fechanacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @Column(name = "alumno_genero")
    @Enumerated(EnumType.STRING)
    private GeneroEnum genero;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Alumno alumno = (Alumno) o;
        return id != null && Objects.equals(id, alumno.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public Alumno(AlumnoCommandDTO alumnoCommandDTO) {
        this.nombres = alumnoCommandDTO.getNombres();
        this.apellidos = alumnoCommandDTO.getApellidos();
        this.fechaNacimiento = alumnoCommandDTO.getFechaNacimiento();
        this.genero = alumnoCommandDTO.getGenero();
    }

    public Alumno setId(Long id) {
        this.id = id;
        return this;
    }

}
