package com.notably.commons.compiler;

import com.notably.commons.compiler.parser.Parser;

public class Compiler {
    public static String compile(String markdown) {
        return Parser.parse(markdown).toHtml();
    }
}

