package com.github.andrebedregal.seresco.controller;

import com.github.andrebedregal.seresco.exception.MensajeError;
import com.github.andrebedregal.seresco.exception.ErrorNoEncontrado;
import com.github.andrebedregal.seresco.exception.RestException;
import com.github.andrebedregal.seresco.model.Alumno;
import com.github.andrebedregal.seresco.model.dto.AlumnoQueryDTO;
import com.github.andrebedregal.seresco.model.dto.ProfesorCommandDTO;
import com.github.andrebedregal.seresco.model.dto.ProfesorQueryDTO;
import com.github.andrebedregal.seresco.service.AlumnoService;
import com.github.andrebedregal.seresco.service.ProfesorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("profesores")
@CrossOrigin(origins = "*")
public class ProfesorController {

    private final AlumnoService alumnoService;
    private final ProfesorService profesorService;

    public ProfesorController(AlumnoService alumnoService,
                              ProfesorService profesorService) {
        this.alumnoService = alumnoService;
        this.profesorService = profesorService;
    }

    @PostMapping
    public ResponseEntity<Long> doPostProfesor(@Valid @RequestBody ProfesorCommandDTO profesorDTO) {
        ProfesorQueryDTO profesor = profesorService.crear(profesorDTO);
        return new ResponseEntity<>(profesor.getId(), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProfesorQueryDTO> doPutProfesor(@PathVariable Long id,
                                                          @Valid @RequestBody ProfesorCommandDTO profesorDTO)
            throws RestException {
        return profesorService.buscarPorId(id)
                .map(p -> {
                    ProfesorQueryDTO updated = profesorService.actualizar(id, profesorDTO);
                    return new ResponseEntity<>(updated, HttpStatus.OK);
                }).orElseThrow(() -> new ErrorNoEncontrado(MensajeError.PROFESOR_NO_ENCONTRADO));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> doDeleteProfesor(@PathVariable Long id) throws RestException {
        return profesorService.buscarPorId(id)
                .map(profesorQueryDTO -> {
                    profesorService.eliminarPorId(profesorQueryDTO.getId());
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                }).orElseThrow(() -> new ErrorNoEncontrado(MensajeError.PROFESOR_NO_ENCONTRADO));
    }

    @GetMapping("{id}")
    public ResponseEntity<ProfesorQueryDTO> doGetProfesor(@PathVariable Long id) throws RestException {
        Optional<ProfesorQueryDTO> optionalProfesor = profesorService.buscarPorId(id);
        return optionalProfesor.map(profesor -> new ResponseEntity<>(profesor, HttpStatus.OK))
                .orElseThrow(() -> new ErrorNoEncontrado(MensajeError.PROFESOR_NO_ENCONTRADO));
    }

    @GetMapping
    public ResponseEntity<List<ProfesorQueryDTO>> doGetProfesores() {
        return new ResponseEntity<>(profesorService.buscarTodos(), HttpStatus.OK);
    }

    @GetMapping("{id}/alumnos")
    public ResponseEntity<Set<AlumnoQueryDTO>> doGetAlumnosPorProfesor(@PathVariable Long id) throws RestException {
        return profesorService.traerProfesorConAlumnos(id)
                .map(profesor -> new ResponseEntity<>(profesor.getAlumnos().stream().sorted(Comparator.comparing(Alumno::getId))
                        .map(AlumnoQueryDTO::getInstance).collect(Collectors.toSet()), HttpStatus.OK))
                .orElseThrow(() -> new ErrorNoEncontrado(MensajeError.PROFESOR_NO_ENCONTRADO));
    }

    @PostMapping("{profesorId}/alumnos/{alumnoId}")
    public ResponseEntity<Void> doPostAlumnoToProfesor(@PathVariable Long profesorId,
                                                       @PathVariable Long alumnoId) throws RestException {
        Optional<ProfesorQueryDTO> optionalProfesor = profesorService.buscarPorId(profesorId);
        if (optionalProfesor.isPresent()) {
            Optional<AlumnoQueryDTO> optionalAlumno = alumnoService.buscarPorId(alumnoId);
            if (optionalAlumno.isPresent()) {
                profesorService.asignarAlumnoAProfesor(profesorId, alumnoId);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else throw new ErrorNoEncontrado(MensajeError.ALUMNO_NO_ENCONTRADO);
        } else throw new ErrorNoEncontrado(MensajeError.PROFESOR_NO_ENCONTRADO);
    }

    @DeleteMapping("{profesorId}/alumnos/{alumnoId}")
    public ResponseEntity<Void> doDeleteAlumnoFromProfesor(@PathVariable Long profesorId,
                                                           @PathVariable Long alumnoId) throws RestException {
        Optional<ProfesorQueryDTO> optionalProfesor = profesorService.buscarPorId(profesorId);
        if (optionalProfesor.isPresent()) {
            Optional<AlumnoQueryDTO> optionalAlumno = alumnoService.buscarPorId(alumnoId);
            if (optionalAlumno.isPresent()) {
                profesorService.desasignarAlumnoDeProfesor(profesorId, alumnoId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else throw new ErrorNoEncontrado(MensajeError.ALUMNO_NO_ENCONTRADO);
        } else throw new ErrorNoEncontrado(MensajeError.PROFESOR_NO_ENCONTRADO);
    }

}
