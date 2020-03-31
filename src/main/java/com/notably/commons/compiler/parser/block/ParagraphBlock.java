package com.notably.commons.compiler.parser.block;

import java.util.Objects;
import java.util.stream.Collectors;

import com.notably.commons.compiler.parser.exceptions.InvalidLineException;

/**
 * Represents a paragraph node in a Markdown Abstract Syntax Tree (AST).
 */
public class ParagraphBlock extends Block {
    /**
     * Checks whether or not a paragraph block can be created from a supplied line.
     *
     * @param line Input line
     * @return Whether or not a paragraph block can be created
     */
    public static boolean isParagraph(String line) {
        Objects.requireNonNull(line);

        return TextBlock.isText(line);
    }

    /**
     * Checks whether or not a paragraph block can be created from a supplied line.
     *
     * @param line Input line
     * @return Whether or not a paragraph block can be created
     */
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

