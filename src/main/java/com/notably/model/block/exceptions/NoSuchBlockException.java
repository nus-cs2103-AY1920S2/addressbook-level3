package com.notably.model.block.exceptions;

import java.util.NoSuchElementException;

/**
 * Signals that the block with the specified title string does not exist.
 */
public class NoSuchBlockException extends NoSuchElementException {
    public NoSuchBlockException(String blockTitle) {
        super("Block with the title " + blockTitle + " does not exist!");
    }
}
