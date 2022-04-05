package com.github.andrebedregal.seresco.controller;

import com.github.andrebedregal.seresco.exception.MensajeError;
import com.github.andrebedregal.seresco.exception.ErrorNoEncontrado;
import com.github.andrebedregal.seresco.exception.RestException;
import com.github.andrebedregal.seresco.model.dto.AlumnoCommandDTO;
import com.github.andrebedregal.seresco.model.dto.AlumnoQueryDTO;
import com.github.andrebedregal.seresco.service.AlumnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("alumnos")
@CrossOrigin(origins = "*")
public class AlumnoController {

    private final AlumnoService alumnoService;

    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @PostMapping
    public ResponseEntity<Long> doPostAlumno(@Valid @RequestBody AlumnoCommandDTO alumnoNuevo) {
        AlumnoQueryDTO alumno = alumnoService.crear(alumnoNuevo);
        return new ResponseEntity<>(alumno.getId(), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<AlumnoQueryDTO> doPutAlumno(@PathVariable Long id,
                                                      @Valid @RequestBody AlumnoCommandDTO alumnoDTO) throws RestException {
        return alumnoService.buscarPorId(id)
                .map((a) -> {
                    AlumnoQueryDTO alumno = alumnoService.actualizar(id, alumnoDTO);
                    return new ResponseEntity<>(alumno, HttpStatus.OK);
                }).orElseThrow(() -> new ErrorNoEncontrado(MensajeError.ALUMNO_NO_ENCONTRADO));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> doDeleteAlumno(@PathVariable Long id) throws RestException {
        Optional<AlumnoQueryDTO> alumnoQueryDTO = alumnoService.buscarPorId(id);
        if (alumnoQueryDTO.isPresent()) {
            alumnoService.eliminarPorId(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new ErrorNoEncontrado(MensajeError.ALUMNO_NO_ENCONTRADO);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<AlumnoQueryDTO> doGetAlumno(@PathVariable Long id) throws RestException {
        return alumnoService.buscarPorId(id)
                .map(alumno -> new ResponseEntity<>(alumno, HttpStatus.OK))
                .orElseThrow(() -> new ErrorNoEncontrado(MensajeError.ALUMNO_NO_ENCONTRADO));
    }

    @GetMapping
    public ResponseEntity<Iterable<AlumnoQueryDTO>> doGetAlumnos() {
        return new ResponseEntity<>(alumnoService.traerTodos(), HttpStatus.OK);
    }

}
