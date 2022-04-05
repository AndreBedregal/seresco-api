package com.github.andrebedregal.seresco.repository;

import com.github.andrebedregal.seresco.model.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {

    @Query("SELECT p FROM Profesor p LEFT JOIN FETCH p.alumnos WHERE p.id = :profesorId")
    Optional<Profesor> traerProfesorConAlumnos(Long profesorId);

}
