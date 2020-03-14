package com.notably.model.suggestion;

/**
 * Represents the instance of the suggested item.
 */
interface SuggestionItem {
    /**
     * Gets the note path and/ or title displayed in the UI, e.g. "open -t root/cs2103".
     * @return The note's path and/ or title.
     */
    String getDisplayText();

    /**
     * Executes the action taken after the suggestion item is chosen.
     * The action will be different depending on the type of SuggestionCommand.
     * @return The action taken after the suggestion item is chosen.
     */
    Runnable run();
}
