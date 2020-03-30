package com.notably.commons.compiler.parser.block;

import java.util.Objects;
import java.util.stream.Collectors;

import com.notably.commons.compiler.parser.exceptions.InvalidLineException;

public class ParagraphBlock extends Block {
    public static boolean isParagraph(String line) {
        Objects.requireNonNull(line);

        return TextBlock.isText(line);
    }

    public static ParagraphBlock fromLine(String line) {
        Objects.requireNonNull(line);

        if (!isParagraph(line)) {
            throw new InvalidLineException(String.format("\"%s\" is not a valid paragraph", line));
        }

        ParagraphBlock paragraph = new ParagraphBlock();
        TextBlock text = TextBlock.fromLine(line);
        paragraph.addChild(text);

        return paragraph;
    }

    @Override
    public boolean next(String line) {
        if (!isParagraph(line)) {
            close();
            return false;
        }

        TextBlock text = TextBlock.fromLine(line);
        getChildren().add(text);

        return true;
    }

    @Override
    public String toHtml() {
        return String.format("<p>%s</p>",
                getChildren().stream().map(Block::toHtml).collect(Collectors.joining("<br>")));
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ParagraphBlock)) {
            return false;
        }
        return super.equals(object);
    }
}

