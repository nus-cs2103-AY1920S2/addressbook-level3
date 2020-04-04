package seedu.address.logic.messages;

import javafx.collections.ObservableList;

public abstract class AppMessage {
    public String IDENTIFIER;
    public abstract Boolean getRenderFlag();
    public abstract String getIdentifier();
    public abstract ObservableList getDisplayAsObservable();
}
