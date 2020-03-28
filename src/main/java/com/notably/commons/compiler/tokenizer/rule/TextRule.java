package com.notably.commons.compiler.tokenizer.rule;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.notably.commons.compiler.tokenizer.Token;
import com.notably.commons.compiler.tokenizer.TokenType;

/**
 * TODO: Add Javadoc
 */
public class TextRule implements Rule {
    private static final Pattern PATTERN = Pattern.compile("^([^-# \\r\\n]+)");

    @Override
    public Optional<Token> extractFront(String input) {
        Matcher matcher = PATTERN.matcher(input);
        if (!matcher.find()) {
            return Optional.empty();
        }
        return Optional.of(new Token(TokenType.TEXT, matcher.group(1)));
    }
}

