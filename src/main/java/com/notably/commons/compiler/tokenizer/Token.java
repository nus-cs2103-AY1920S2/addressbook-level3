package com.notably.commons.compiler.tokenizer;

import java.util.Objects;

/**
 * TODO: Add Javadoc
 */
public class Token {
    public static final Token MINUS = new Token(TokenType.MINUS, "-");
    public static final Token HASH = new Token(TokenType.HASH, "#");
    public static final Token CR = new Token(TokenType.CR, "\r");
    public static final Token LF = new Token(TokenType.LF, "\n");
    public static final Token SPACE = new Token(TokenType.SPACE, " ");
    public static final Token EOF = new Token(TokenType.EOF, "");

    private final TokenType type;
    private final String value;

    public Token(TokenType type, String value) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(value);

        this.type = type;
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public int getLength() {
        return value.length();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Token)) {
            return false;
        }

        Token anotherToken = (Token) object;
        return Objects.equals(type, anotherToken.type)
                && Objects.equals(value, anotherToken.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }
}

