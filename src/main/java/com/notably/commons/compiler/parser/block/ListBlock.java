package com.notably.commons.compiler.parser.block;

import java.util.Objects;
import java.util.stream.Collectors;

import com.notably.commons.compiler.parser.exceptions.InvalidLineException;

public class ListBlock extends Block {
    public static boolean isList(String line) {
        Objects.requireNonNull(line);

        return ListItemBlock.isListItem(line);
    }

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

        Block lastChild = getChildren().get(getChildren().size() - 1);
        if (!lastChild.isOpen()) {
            if (!isList(line)) {
                close();
                return false;
            }

            ListItemBlock listItem = ListItemBlock.fromLine(line);
            addChild(listItem);
            return true;
        }

        boolean successful = lastChild.next(line);
        if (!successful) {
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

