package com.notably.commons.compiler.parser.exceptions;

public class InvalidLineException extends RuntimeException {
    public InvalidLineException() {
        super();
    }

    public InvalidLineException(String message) {
        super(message);
    }
}

