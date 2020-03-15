package com.notably.model.suggestion;

import com.notably.commons.core.path.Path;

public class SuggestionItemImpl implements SuggestionItem {
    Path path;
    String displayText;
    Runnable action;

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
