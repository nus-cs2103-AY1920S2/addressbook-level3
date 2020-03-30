package com.notably.commons.compiler.parser.block;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.notably.commons.compiler.parser.exceptions.InvalidLineException;

/**
 * Represents a list item node in a Markdown Abstract Syntax Tree (AST).
 */
public class ListItemBlock extends Block {
    private static final Pattern CREATE_PATTERN = Pattern.compile("^(?<leader> *- *)(?<text>(?<= +).*)?$");
    private static final Pattern NEXT_PATTERN = Pattern.compile("^(?<leader> *)(?<text>.*)$");

    private final int indentation;

    /**
     * Creates a list item block with the specified indentation.
     *
     * @param indentation List item indentation
     */
    public ListItemBlock(int indentation) {
        this.indentation = indentation;
    }

    /**
     * Checks whether or not a list item block can be created from a supplied line.
     *
     * @param line Input line
     * @return Whether or not a list item block can be created
     */
    public static boolean isListItem(String line) {
        Objects.requireNonNull(line);

        return CREATE_PATTERN.matcher(line).lookingAt();
    }

    /**
     * Creates a list item block from a supplied line.
     *
     * @param line Input line
     * @return The created list item block
     * @throws InvalidLineException If no list item block can be constructed from the supplied line
     */
    public static ListItemBlock fromLine(String line) {
        Objects.requireNonNull(line);

        Matcher matcher = CREATE_PATTERN.matcher(line);
        if (!matcher.find()) {
            throw new InvalidLineException(String.format("\"%s\" is not a valid list item", line));
        }

        int indentation = matcher.group("leader").length();
        String text = matcher.group("text");

        ListItemBlock listItem = new ListItemBlock(indentation);
        if (Block.canCreateStructuralBlock(text)) {
            listItem.addChild(Block.createStructuralBlock(text));
        }

        return listItem;
    }

    @Override
    public boolean next(String line) {
        Objects.requireNonNull(line);

        Matcher matcher = NEXT_PATTERN.matcher(line);
        if (!matcher.find()) {
            throw new InvalidLineException(String.format("\"%s\" is not a valid list item", line));
        }

        int nextIndentation = matcher.group("leader").length();
        if (nextIndentation < indentation) {
            close();
            return false;
        }

        String text = matcher.group("text");

        if (Block.canCreateStructuralBlock(text)) {
            addChild(Block.createStructuralBlock(text));
        }

        return true;
    }

    @Override
    public String toHtml() {
        return String.format("<li>%s</li>",
                getChildren().stream().map(Block::toHtml).collect(Collectors.joining()));
    }
}

