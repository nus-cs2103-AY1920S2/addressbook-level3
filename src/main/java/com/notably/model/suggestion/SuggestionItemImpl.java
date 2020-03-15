package com.notably.model.suggestion;

import com.notably.commons.core.path.Path;

/**
 * Implementation class for Suggestion Item.
 */
public class SuggestionItemImpl implements SuggestionItem {
    private Path path;
    private String displayText;
    private Runnable action;

    public SuggestionItemImpl(Path path) {
        this.path = path;
    }

    @Override
    public String getDisplayText() {
        return displayText;
    }

    @Override
    public Runnable run() {
        return action;
    }
}
