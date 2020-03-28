package com.notably.commons.compiler.tokenizer.exceptions;

import com.notably.commons.compiler.tokenizer.Tokenizer;

/**
 * Represents an error thrown when an unknown token is encountered by {@link Tokenizer}.
 */
public class UnknownTokenException extends RuntimeException {
    public UnknownTokenException() {
        super();
    }

    public UnknownTokenException(String message) {
        super(message);
    }
}

