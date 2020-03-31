package com.notably.commons.compiler.parser.exceptions;

/**
 * A {@link RuntimeException} thrown when an invalid line is encountered during Markdown parsing.
 */
public class InvalidLineException extends RuntimeException {
    public InvalidLineException() {
        super();
    }

    public InvalidLineException(String message) {
        super(message);
    }
}

