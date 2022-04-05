package com.github.andrebedregal.seresco.exception;

public class ErrorInesperado extends RestException {

    public ErrorInesperado(MensajeError mensajeError) {
        super("Error inesperado", mensajeError.key);
    }

}
