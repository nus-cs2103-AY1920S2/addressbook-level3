package com.notably.model.block.exceptions;

/**
 * Signals that the operation will result in duplicate Block(Blocks are considered duplicates if they have the same
 * parent and title).
 */
public class CannotModifyRootException extends Exception {
    public CannotModifyRootException() {
        super("Root item cannot be modified");
    }
}
