package com.notably.commons.compiler.parser.block;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.notably.commons.compiler.parser.exceptions.InvalidLineException;

public class ListItemBlock extends Block {
    private static final Pattern CREATE_PATTERN = Pattern.compile("^(?<leader> *- *)(?<text>(?<= +).*)?$");
    private static final Pattern NEXT_PATTERN = Pattern.compile("^(?<leader> *)(?<text>.*)$");

    private final int indentation;

    public ListItemBlock(int indentation) {
        this.indentation = indentation;
    }

    public static boolean isListItem(String line) {
        Objects.requireNonNull(line);

        return CREATE_PATTERN.matcher(line).lookingAt();
    }

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

