package com.notably.logic.suggestion;

/**
 * Represents the Suggestion Engine instance.
 * TThis is the class that manages the generation of suggestions.
 */
interface SuggestionEngine {
    /**
     * Generates suggestions.
     * @param input User's input.
     */
    void suggest(String input);
}
