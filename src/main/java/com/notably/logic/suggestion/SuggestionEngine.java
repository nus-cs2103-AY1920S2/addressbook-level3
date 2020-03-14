package com.notably.logic.suggestion;

/**
 * Represents the Suggestion Engine instance.
 * This is the class that we interact with when the user is typing an input.
 */
interface SuggestionEngine {
    /**
     * Generates suggestions.
     * When input cannot get corrected, the "error" message will be shown in commandTextProperty.
     * @param input User's input.
     */
    void suggest(String input);
}
