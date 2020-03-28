package com.notably.commons.compiler.tokenizer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class TokenizerTest {
    @Test
    public void tokenize() {
        final String input = "## 2nd-level header \r\n";

        final List<Token> expectedTokens = List.of(
                Token.HASH,
                Token.HASH,
                Token.SPACE,
                new Token(TokenType.TEXT, "2nd"),
                Token.MINUS,
                new Token(TokenType.TEXT, "level"),
                Token.SPACE,
                new Token(TokenType.TEXT, "header"),
                Token.SPACE,
                Token.CR,
                Token.LF,
                Token.EOF
        );

        List<Token> tokens = Tokenizer.tokenize(input);

        assertEquals(expectedTokens, tokens);
    }
}

