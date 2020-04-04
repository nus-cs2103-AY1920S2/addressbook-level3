package seedu.address.logic.messages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.bluetooth.BluetoothPings;

import java.util.ArrayList;
import java.util.Objects;
import static java.util.Objects.requireNonNull;

public class BluetoothPingsMessage implements AppMessage {

    private final String feedbackToUser;
    private ArrayList<BluetoothPings> toDisplayList;
    private Boolean RENDER_FLAG;

    /** The application should exit. */
    private final boolean exit;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public BluetoothPingsMessage(String feedbackToUser, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.exit = exit;
        this.RENDER_FLAG = false;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public BluetoothPingsMessage(String feedbackToUser) {
        this(feedbackToUser, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public Boolean getRenderFlag() { return this.RENDER_FLAG; }

    public ObservableList<BluetoothPings> getDisplayAsObservable() { return FXCollections.observableArrayList(this.toDisplayList); }

    /**
     * A display list contains the necessary items needed to be rendered on the screen
     * If this function is called, we set the RENDER_FLAG to true as signal that there is something to be displayed
     *
     * @param displayList   List to be rendered on the screen
     */
    public void setToDisplayList(ArrayList<BluetoothPings> displayList) {
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

        BluetoothPingsMessage otherCommandResult = (BluetoothPingsMessage) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, exit);
    }
}
