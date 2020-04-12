package seedu.address.logic.messages;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.bluetooth.CommandList;

import java.util.ArrayList;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class HelpCommandMessage extends  AppMessage {
    private  final String feedbackToUser;
    private ArrayList<CommandList> toDisplayList;
    private Boolean RENDER_FLAG;
    public final String IDENTIFIER = "HelpList";

    private final boolean exit;

    public HelpCommandMessage(String feedbackToUser, boolean exit)
    {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.exit = exit;
        this.RENDER_FLAG = false;
    }

    public HelpCommandMessage(String feedbackToUser)
    {
        this(feedbackToUser, false);
    }

    public String getFeedbackToUser()
    {
        return feedbackToUser;
    }

    public Boolean getRenderFlag()
    {
        return this.RENDER_FLAG;
    }

    public String getIdentifier()
    {
        return this.IDENTIFIER;
    }

    public ObservableList getDisplayAsObservable()
    {
        return FXCollections.observableArrayList(this.toDisplayList);
    }

    public void setToDisplayList(ArrayList<CommandList> displayList)
    {
        this.toDisplayList = displayList;
        this.RENDER_FLAG = true;
    }

    public boolean isExit()
    {
        return exit;
    }

    public boolean equals(Object other)
    {
        if (other == this)
        {
            return true;
        }
        if (!(other instanceof HelpCommandMessage))
        {
            return false;
        }

        HelpCommandMessage otherCommandResult = (HelpCommandMessage) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && exit == otherCommandResult.exit;
    }

    public int hashCode()
    {
        return Objects.hash(feedbackToUser, exit);
    }

}
