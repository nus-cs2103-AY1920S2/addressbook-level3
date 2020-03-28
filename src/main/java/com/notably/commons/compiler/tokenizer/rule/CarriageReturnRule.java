package com.notably.commons.compiler.tokenizer.rule;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.notably.commons.compiler.tokenizer.Token;

/**
 * TODO: Add Javadoc
 */
public class CarriageReturnRule implements Rule {
    private static final Pattern PATTERN = Pattern.compile("^\\r");

    @Override
    public Optional<Token> extractFront(String input) {
        Matcher matcher = PATTERN.matcher(input);
        if (!matcher.find()) {
            return Optional.empty();
        }
        return Optional.of(Token.CR);
    }
}

