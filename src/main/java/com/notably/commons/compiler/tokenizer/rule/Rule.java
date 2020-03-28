package com.notably.commons.compiler.tokenizer.rule;

import java.util.Optional;

import com.notably.commons.compiler.tokenizer.Token;

/**
 * TODO: Add Javadoc
 */
public interface Rule {
    /**
     * TODO: Add Javadoc
     */
    Optional<Token> extractFront(String input);
}

