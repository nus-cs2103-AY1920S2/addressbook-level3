package com.notably.model.block;

/**
 * API of the Block component.
 *
 * A Block is a data node containing a title and a (optionally empty) body.
 */
public interface Block {
    /**
     * Gets the title of a block.
     */
    Title getTitle();

    /**
     * Gets the body content of a block.
     */
    Body getBody();
}
