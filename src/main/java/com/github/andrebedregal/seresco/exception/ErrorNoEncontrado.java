package com.github.andrebedregal.seresco.exception;

public class ErrorNoEncontrado extends RestException {

    public ErrorNoEncontrado(MensajeError mensajeError) {
        super("Recurso no encontrado", mensajeError.key);
    }

}
