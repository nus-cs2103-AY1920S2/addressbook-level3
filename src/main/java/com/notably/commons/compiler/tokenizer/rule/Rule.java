package com.notably.commons.compiler.tokenizer.rule;

import java.util.Optional;

import com.notably.commons.compiler.tokenizer.Token;

/**
 * Represents a rule that dictates how to convert a single/a bunch of characters into a single {@link Token}.
 */
public interface Rule {
    /**
     * Extracts a single {@link Token}, if possible, from the front of an input string.
     *
     * @param input Input string
     * @return An extracted {@link Token}, or {@code Optional.empty()} if unsuccessful
     */
    Optional<Token> extractFront(String input);
}

