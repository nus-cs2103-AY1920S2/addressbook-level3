package com.notably.commons.compiler.parser;

import com.notably.commons.compiler.parser.block.DocumentBlock;

/**
 * A Markdown to HTML parser.
 */
public class Parser {
    /**
     * Creates a Markdown AST from a Markdown string.
     *
     * @param input Input markdown string
     * @return Markdown AST
     */
    public static DocumentBlock parse(String input) {
        DocumentBlock documentBlock = new DocumentBlock();

        input.lines().forEach(line -> {
            documentBlock.next(line);
        });

        return documentBlock;
    }
}

