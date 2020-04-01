package com.notably.commons.compiler.parser.block;

import java.util.Objects;
import java.util.stream.Collectors;

import com.notably.commons.compiler.parser.exceptions.InvalidLineException;

/**
 * Represents a list node in a Markdown Abstract Syntax Tree (AST).
 */
public class ListBlock extends Block {
    /**
     * Checks whether or not a list block can be created from a supplied line.
     *
     * @param line Input line
     * @return Whether or not a list block can be created
     */
    public static boolean isList(String line) {
        Objects.requireNonNull(line);

        return ListItemBlock.isListItem(line);
    }

    /**
     * Creates a list block from a supplied line.
     *
     * @param line Input line
     * @return The created list block
     * @throws InvalidLineException If no list block can be constructed from the supplied line
     */
    public static ListBlock fromLine(String line) {
        Objects.requireNonNull(line);

        if (!isList(line)) {
            throw new InvalidLineException(String.format("\"%s\" is not a valid list", line));
        }

        ListBlock list = new ListBlock();
        ListItemBlock listItem = ListItemBlock.fromLine(line);
        list.addChild(listItem);

        return list;
    }

    @Override
    public boolean next(String line) {
        Objects.requireNonNull(line);

        Block lastChild = BlockUtil.getLast(getChildren());
        if (!lastChild.isOpen()) {
            if (!isList(line)) {
                close();
                return false;
            }

            ListItemBlock listItem = ListItemBlock.fromLine(line);
            addChild(listItem);
            return true;
        }

        if (!lastChild.next(line)) {
            if (!isList(line)) {
                close();
                return false;
            }

            ListItemBlock listItem = ListItemBlock.fromLine(line);
            addChild(listItem);
            return true;
        }

        return true;
    }

    @Override
    public String toHtml() {
        return String.format("<ul>%s</ul>",
                getChildren().stream().map(Block::toHtml).collect(Collectors.joining()));
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ListBlock)) {
            return false;
        }
        return super.equals(object);
    }
}

