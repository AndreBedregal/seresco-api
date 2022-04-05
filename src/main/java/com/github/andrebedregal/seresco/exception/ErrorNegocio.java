package com.github.andrebedregal.seresco.exception;

public class ErrorNegocio extends RestException {

    public ErrorNegocio(MensajeError mensajeError) {
        super("Error de lógica de negocio", mensajeError.key);
    }

}