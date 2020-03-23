package com.notably.commons.core.path.exceptions;

import com.notably.commons.core.path.Path;

/**
 * {@link RuntimeException} to be thrown when an invalid representation of a path is supplied during
 * {@link Path} creation.
 */
public class InvalidPathException extends RuntimeException {
    public InvalidPathException(String message) {
        super(message);
    }
}

