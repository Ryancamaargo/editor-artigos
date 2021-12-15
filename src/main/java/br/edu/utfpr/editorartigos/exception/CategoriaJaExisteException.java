package br.edu.utfpr.editorartigos.exception;

public class CategoriaJaExisteException extends Exception {

    private final String message;

    public CategoriaJaExisteException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
