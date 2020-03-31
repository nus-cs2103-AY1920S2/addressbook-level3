package com.notably.commons.compiler.parser.block;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        if (canCreateChildBlock(text)) {
            listItem.addChild(createChildBlock(text));
        }

        return listItem;
    }

    /**
     * Checks whether or not a child block can be created from a supplied line.
     *
     * @param line Input line
     * @return Whether or not a child block can be created
     */
    private static boolean canCreateChildBlock(String line) {
        return HeaderBlock.isHeader(line)
                || ListBlock.isList(line)
                || TextBlock.isText(line);
    }

    /**
     * Creates a child block from a supplied line.
     *
     * @param line Input line
     * @return Child block
     */
    private static Block createChildBlock(String line) {
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
        return TextBlock.fromLine(line);
    }

    @Override
    public boolean next(String line) {
        Objects.requireNonNull(line);

        Matcher matcher = NEXT_PATTERN.matcher(line);
        matcher.find();

        int nextIndentation = matcher.group("leader").length();
        if (nextIndentation < indentation) {
            close();
            return false;
        }

        String text = padSpaceLeft(matcher.group("text"), nextIndentation - indentation);

        Block lastChild = BlockUtil.getLast(getChildren());
        if (!lastChild.isOpen()) {
            if (canCreateChildBlock(text)) {
                addChild(createChildBlock(text));
            }
            return true;
        }

        if (!lastChild.next(text)) {
            if (canCreateChildBlock(text)) {
                addChild(createChildBlock(text));
            }
            return true;
        }

        return true;
    }

    private String padSpaceLeft(String text, int count) {
        return " ".repeat(count) + text;
    }

    @Override
    public String toHtml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<li>");

        for (int i = 0; i < getChildren().size(); i++) {
            sb.append(getChildren().get(i).toHtml());
            if (i + 1 < getChildren().size()
                    && (getChildren().get(i) instanceof TextBlock)
                    && (getChildren().get(i + 1) instanceof TextBlock)) {
                sb.append("<br>");
            }
        }

        sb.append("</li>");
        return sb.toString();
    }
}

