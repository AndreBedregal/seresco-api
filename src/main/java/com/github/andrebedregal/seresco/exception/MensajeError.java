package com.github.andrebedregal.seresco.exception;

public enum MensajeError {

    ALUMNO_NO_ENCONTRADO("No existe un alumno con el id proporcionado."),
    ALUMNO_NO_PUEDE_SER_ELIMINADO("Este alumno no puede ser eliminado porque está asignado a un profesor."),
    PROFESOR_NO_ENCONTRADO("No existe un profesor con el id proporcionado.");

    final String key;

    MensajeError(String key) {
        this.key = key;
    }

}
