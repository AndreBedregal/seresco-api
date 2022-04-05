package com.github.andrebedregal.seresco.service;

import com.github.andrebedregal.seresco.model.Alumno;
import com.github.andrebedregal.seresco.model.Profesor;
import com.github.andrebedregal.seresco.model.dto.ProfesorCommandDTO;
import com.github.andrebedregal.seresco.model.dto.ProfesorQueryDTO;
import com.github.andrebedregal.seresco.repository.ProfesorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProfesorService {

    private final ProfesorRepository repository;

    public ProfesorService(ProfesorRepository repository) {
        this.repository = repository;
    }

    public ProfesorQueryDTO crear(ProfesorCommandDTO profesorCommandDTO) {
        log.info("Creando profesor...");
        return ProfesorQueryDTO.getInstance(repository.save(new Profesor(profesorCommandDTO)));
    }

    @CachePut(value = "profesoresCache", key = "#id")
    public ProfesorQueryDTO actualizar(Long id, ProfesorCommandDTO profesorCommandDTO) {
        log.info("Actualizando profesor " + id + "...");
        return repository.traerProfesorConAlumnos(id).map(profesor_ -> {
            profesor_.setNombres(profesorCommandDTO.getNombres());
            profesor_.setApellidos(profesorCommandDTO.getApellidos());
            return ProfesorQueryDTO.getInstance(repository.save(profesor_));
        }).orElse(null);
    }

    @CacheEvict(value = "profesoresCache", key = "#id")
    public void eliminarPorId(Long id) {
        log.info("Eliminando profesor " + id + "...");
        repository.deleteById(id);
    }

    @Cacheable(value = "profesoresCache", key = "#id", unless = "#result == null")
    public Optional<ProfesorQueryDTO> buscarPorId(Long id) {
        log.info("Buscando profesor por id...");
        return repository.findById(id).map(ProfesorQueryDTO::getInstance);
    }

    public List<ProfesorQueryDTO> buscarTodos() {
        log.info("Buscando todos los profesores...");
        return repository.findAll(Sort.by("id")).stream().map(ProfesorQueryDTO::getInstance).collect(Collectors.toList());
    }

    public Optional<Profesor> traerProfesorConAlumnos(Long profesorId) {
        return repository.traerProfesorConAlumnos(profesorId);
    }

    public void asignarAlumnoAProfesor(Long profesorId, Long alumnoId) {
        log.info("Asignando el alumno " + alumnoId + " al profesor " + profesorId + "...");
        traerProfesorConAlumnos(profesorId).ifPresent((p) -> {
            p.getAlumnos().add(new Alumno().setId(alumnoId));
            repository.save(p);
        });
    }

    public void desasignarAlumnoDeProfesor(Long profesorId, Long alumnoId) {
        log.info("Desasignando el alumno " + alumnoId + " al profesor " + profesorId + "...");
        traerProfesorConAlumnos(profesorId).ifPresent((p) -> {
            p.getAlumnos().remove(new Alumno().setId(alumnoId));
            repository.save(p);
        });
    }

}
