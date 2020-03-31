package com.notably.commons.compiler;

import com.notably.commons.compiler.parser.Parser;

/**
 * A Markdown to HTML compiler.
 */
public class Compiler {
    /**
     * Compiles a Markdown string to an HTML string.
     *
     * @param markdown Markdown string
     * @return HTML string
     */
    public static String compile(String markdown) {
        return Parser.parse(markdown).toHtml();
    }
}

