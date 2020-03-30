package com.notably.commons.compiler.parser.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.notably.commons.compiler.parser.exceptions.InvalidLineException;

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
     * Checks whether or not a structural block can be created from a supplied line.
     * A structural block refers to:
     * <ul>
     *   <li>A {@link HeaderBlock},</li>
     *   <li>A {@link ListBlock}, and</li>
     *   <li>A {@link ParagraphBlock}</li>
     * </ul>
     *
     * @param line Input line
     * @return Whether or not a structural block can be created
     */
    public static boolean canCreateStructuralBlock(String line) {
        return HeaderBlock.isHeader(line)
                || ListBlock.isList(line)
                || ParagraphBlock.isParagraph(line);
    }

    /**
     * Creates a structural block from a supplied line.
     * A structural block refers to:
     * <ul>
     *   <li>A {@link HeaderBlock},</li>
     *   <li>A {@link ListBlock}, and</li>
     *   <li>A {@link ParagraphBlock}</li>
     * </ul>
     *
     * @param line Input line
     * @return The created structural block
     * @throws InvalidLineException If no structural block can be constructed from the supplied line
     */
    public static Block createStructuralBlock(String line) {
        Objects.requireNonNull(line);

        if (!canCreateStructuralBlock(line)) {
            throw new InvalidLineException("No structural block can be created from \"%s\"");
        }

        if (HeaderBlock.isHeader(line)) {
            return HeaderBlock.fromLine(line);
        }
        if (ListBlock.isList(line)) {
            return ListBlock.fromLine(line);
        }
        return ParagraphBlock.fromLine(line);
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

