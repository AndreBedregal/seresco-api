package com.github.andrebedregal.seresco.service;

import com.github.andrebedregal.seresco.exception.MensajeError;
import com.github.andrebedregal.seresco.exception.ErrorNegocio;
import com.github.andrebedregal.seresco.exception.RestException;
import com.github.andrebedregal.seresco.model.Alumno;
import com.github.andrebedregal.seresco.model.dto.AlumnoCommandDTO;
import com.github.andrebedregal.seresco.model.dto.AlumnoQueryDTO;
import com.github.andrebedregal.seresco.repository.AlumnoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AlumnoService {

    private final AlumnoRepository repository;

    public AlumnoService(AlumnoRepository repository) {
        this.repository = repository;
    }

    public AlumnoQueryDTO crear(AlumnoCommandDTO alumnoCommandDTO) {
        log.info("Creando alumno...");
        return AlumnoQueryDTO.getInstance(repository.save(new Alumno(alumnoCommandDTO)));
    }

    @CachePut(value = "alumnosCache", key = "#id")
    public AlumnoQueryDTO actualizar(Long id, AlumnoCommandDTO alumnoCommandDTO) {
        log.info("Actualizando alumno " + id + "...");
        return repository.findById(id).map(alumno_ -> {
            alumno_.setNombres(alumnoCommandDTO.getNombres());
            alumno_.setApellidos(alumnoCommandDTO.getApellidos());
            alumno_.setFechaNacimiento(alumnoCommandDTO.getFechaNacimiento());
            alumno_.setGenero(alumnoCommandDTO.getGenero());
            return AlumnoQueryDTO.getInstance(repository.save(alumno_));
        }).orElse(null);
    }

    @CacheEvict(value = "alumnosCache", key = "#id")
    public void eliminarPorId(Long id) throws RestException {
        log.info("Eliminando alumno " + id + "...");
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new ErrorNegocio(MensajeError.ALUMNO_NO_PUEDE_SER_ELIMINADO);
        }
    }

    @Cacheable(value = "alumnosCache", key = "#id", unless = "#result == null")
    public Optional<AlumnoQueryDTO> buscarPorId(Long id) {
        log.info("Buscando alumno por id...");
        return repository.findById(id).map(AlumnoQueryDTO::getInstance);
    }

    public List<AlumnoQueryDTO> traerTodos() {
        log.info("Buscando todos los alumnos...");
        return repository.findAll(Sort.by("id")).stream().map(AlumnoQueryDTO::getInstance).collect(Collectors.toList());
    }

}
