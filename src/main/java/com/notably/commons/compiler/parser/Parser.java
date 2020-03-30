package com.notably.commons.compiler.parser;

import com.notably.commons.compiler.parser.block.DocumentBlock;

public class Parser {
    public static DocumentBlock parse(String input) {
        DocumentBlock documentBlock = new DocumentBlock();

        input.lines().forEach(line -> {
            documentBlock.next(line);
        });

        return documentBlock;
    }
}

