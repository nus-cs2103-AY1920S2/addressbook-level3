package com.notably.commons.compiler.parser.block;

import java.util.Objects;
import java.util.stream.Collectors;

import com.notably.commons.compiler.parser.exceptions.InvalidLineException;

/**
 * Represents a document (root) node in a Markdown Abstract Syntax Tree (AST).
 */
public class DocumentBlock extends Block {
    @Override
    public boolean next(String line) {
        Objects.requireNonNull(line);

        if (getChildren().isEmpty()) {
            if (canCreateChildBlock(line)) {
                addChild(createChildBlock(line));
            }
            return true;
        }

        Block lastChild = BlockUtil.getLast(getChildren());
        if (!lastChild.isOpen()) {
            if (canCreateChildBlock(line)) {
                addChild(createChildBlock(line));
            }
            return true;
        }

        if (!lastChild.next(line)) {
            if (canCreateChildBlock(line)) {
                addChild(createChildBlock(line));
            }
            return true;
        }

        return true;
    }

    /**
     * Checks whether or not a child block can be created from a supplied line.
     *
     * @param line Input line
     * @return Whether or not a child block can be created
     */
    private boolean canCreateChildBlock(String line) {
        return HeaderBlock.isHeader(line)
                || ListBlock.isList(line)
                || ParagraphBlock.isParagraph(line);
    }

    /**
     * Creates a child block from a supplied line.
     *
     * @param line Input line
     * @return Child block
     */
    private Block createChildBlock(String line) {
        Objects.requireNonNull(line);

        if (!canCreateChildBlock(line)) {
            throw new InvalidLineException(String.format("Cannot create child block from \"%s\"", line));
        }

        if (HeaderBlock.isHeader(line)) {
            return HeaderBlock.fromLine(line);
        }
        if (ListBlock.isList(line)) {
            return ListBlock.fromLine(line);
        }
        return ParagraphBlock.fromLine(line);
    }

    @Override
    public String toHtml() {
        return String.format("<html><body>%s</body></html>",
                getChildren().stream().map(Block::toHtml).collect(Collectors.joining()));
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DocumentBlock)) {
            return false;
        }
        return super.equals(object);
    }
}

