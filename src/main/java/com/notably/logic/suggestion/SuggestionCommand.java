package com.notably.logic.suggestion;

import com.notably.model.Model;

/**
 * Represents the Suggestion Command instance.
 */
interface SuggestionCommand {
    /**
     * Gets a list of suggestions (paths or notes that match the current input).
     * Adds this list to Suggestion Model.
     * @param model The Suggestion Model.
     */
    void execute(Model model);
}
