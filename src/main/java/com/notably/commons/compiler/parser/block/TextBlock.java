package com.notably.commons.compiler.parser.block;

import java.util.Objects;

public class TextBlock extends Block {
    final String text;

    public TextBlock(String text) {
        Objects.requireNonNull(text);

        this.text = text;
    }

    public static boolean isText(String line) {
        Objects.requireNonNull(line);

        return !line.isBlank()
                && !HeaderBlock.isHeader(line)
                && !ListBlock.isList(line);
    }

    public static TextBlock fromLine(String line) {
        Objects.requireNonNull(line);

        return new TextBlock(line.trim());
    }

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

