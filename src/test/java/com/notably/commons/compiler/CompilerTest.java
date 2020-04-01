package com.notably.commons.compiler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class CompilerTest {
    private static final Path PATH_TO_MARKDOWN =
            Paths.get("src", "test", "data", "CompilerTest", "Markdown.md");
    private static final Path PATH_TO_COMPILED =
            Paths.get("src", "test", "data", "CompilerTest", "Compiled.html");

    @Test
    public void compile() throws IOException {
        String markdown = Files.readString(PATH_TO_MARKDOWN);
        String expected = Files.readString(PATH_TO_COMPILED);

        String compiled = Compiler.compile(markdown);

        assertEquals(expected.trim(), compiled.trim());
    }
}

