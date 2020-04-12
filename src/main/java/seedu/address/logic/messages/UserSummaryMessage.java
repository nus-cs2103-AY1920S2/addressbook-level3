package seedu.address.logic.messages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.bluetooth.Person;

import java.util.ArrayList;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class UserSummaryMessage extends AppMessage {
    private final String feedbackToUser;
    private ArrayList<Person> toDisplayList;
    private Boolean RENDER_FLAG;
    public final String IDENTIFIER = "UserSummary";

    /** The application should exit. */
    private final boolean exit;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public UserSummaryMessage(String feedbackToUser, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.exit = exit;
        this.RENDER_FLAG = false;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public UserSummaryMessage(String feedbackToUser) {
        this(feedbackToUser, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public Boolean getRenderFlag() { return this.RENDER_FLAG; }

    public String getIdentifier() { return this.IDENTIFIER; }

    public ObservableList getDisplayAsObservable() { return FXCollections.observableArrayList(this.toDisplayList); }

    /**
     * A display list contains the necessary items needed to be rendered on the screen
     * If this function is called, we set the RENDER_FLAG to true as signal that there is something to be displayed
     *
     * @param displayList   List to be rendered on the screen
     */
    public void setToDisplayList(ArrayList<Person> displayList) {
        this.toDisplayList  = displayList;
        this.RENDER_FLAG    = true;
    }

    public boolean isExit() {
        return exit;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BluetoothPingsMessage)) {
            return false;
        }

        UserSummaryMessage otherCommandResult = (UserSummaryMessage) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, exit);
    }
}
