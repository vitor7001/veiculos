package com.gerenciador.veiculos.exception;

public class ListaVaziaException extends  RuntimeException{
    private static final long serialVersionUID = 1L;

    public ListaVaziaException(String string) {
        super(string);
    }
}
