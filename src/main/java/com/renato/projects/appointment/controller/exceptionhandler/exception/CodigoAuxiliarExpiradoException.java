package com.renato.projects.appointment.controller.exceptionhandler.exception;

public class CodigoAuxiliarExpiradoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CodigoAuxiliarExpiradoException() {
        super("O código expirou. Solicite um novo código.");
    }
}
