package com.notably.commons.compiler.parser.block;

import java.util.Objects;

/**
 * Represents a text node in a Markdown Abstract Syntax Tree (AST).
 */
public class TextBlock extends Block {
    final String text;

    /**
     * Creates a text block with the specified text string.
     *
     * @param text Text string
     */
    public TextBlock(String text) {
        super(false);

        Objects.requireNonNull(text);

        this.text = text;
    }

    /**
     * Checks whether or not a text block can be created from a supplied line.
     *
     * @param line Input line
     * @return Whether or not a text block can be created
     */
    public static boolean isText(String line) {
        Objects.requireNonNull(line);

        return !line.isBlank()
                && !HeaderBlock.isHeader(line)
                && !ListBlock.isList(line);
    }

    /**
     * Checks whether or not a text block can be created from a supplied line.
     *
     * @param line Input line
     * @return Whether or not a text block can be created
     */
    public static TextBlock fromLine(String line) {
        Objects.requireNonNull(line);

        return new TextBlock(line.trim());
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException A text block cannot accept a next line.
     */
    @Override
    public boolean next(String line) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toHtml() {
        return text;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TextBlock)) {
            return false;
        }

        TextBlock another = (TextBlock) object;
        return super.equals(another)
                && Objects.equals(text, another.text);
    }
}

