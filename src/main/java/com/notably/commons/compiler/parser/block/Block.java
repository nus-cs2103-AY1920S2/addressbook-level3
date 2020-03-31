package com.notably.commons.compiler.parser.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a node in a Markdown Abstract Syntax Tree (AST).
 * Read more: https://github.github.com/gfm/#appendix-a-parsing-strategy
 */
public abstract class Block {
    private List<Block> children;
    private boolean open;

    /**
     * Creates an open {@link Block}.
     */
    public Block() {
        this(true);
    }

    /**
     * Creates a {@link Block} with the specified open flag.
     *
     * @param open Open flag
     */
    public Block(boolean open) {
        this.open = open;
        children = new ArrayList<>();
    }

    /**
     * Accepts the next input line, which might cause:
     * <ul>
     *   <li>One or more children is/are closed, or</li>
     *   <li>A children {@link Block} is constructed and added to the AST, or both.</li>
     * </ul>
     *
     * @param line Next input line
     * @return Whether or not a {@link Block} is inserted to the AST
     */
    public abstract boolean next(String line);

    /**
     * Converts the AST, starting from this node, to an HTML string.
     *
     * @return HTML string representation of the AST starting from this node
     */
    public abstract String toHtml();

    /**
     * Gets a list of children {@link Block}s.
     *
     * @return List of children {@link Block}s
     */
    public List<Block> getChildren() {
        return children;
    }

    /**
     * Adds a {@link Block} to the AST, as this block's child.
     *
     * @param child {@link Block} to be added
     */
    public void addChild(Block child) {
        children.add(child);
    }

    /**
     * Checks whether or not this block is open.
     *
     * @return Whether or not this block is open
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * Closes the block.
     */
    public void close() {
        open = false;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Block)) {
            return false;
        }

        Block another = (Block) object;
        return Objects.equals(children, another.children);
    }
}

