package seedu.address.logic.commands.results;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {
    /** Preview information about a resume. */
    protected boolean isShowPreview = false;

    /** Help information should be shown to the user. */
    protected boolean isShowHelp = false;

    /** The application should exit. */
    protected boolean isExit = false;

    protected final String feedbackToUser;
    protected final String dataToUser;
    protected final String displayType;

    /**
     * Constructs a {@code CommandResult} with the specified {@code dataToUser} and {@code feedbackToUser},
     * {@code displayType}.
     * @param dataToUser data to show user.
     * @param feedbackToUser feedback to user.
     * @param displayType the alias of the item type.
     */
    public CommandResult(String dataToUser, String feedbackToUser, String displayType) {
        this.dataToUser = requireNonNull(dataToUser);
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.displayType = requireNonNull(displayType);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public String getDataToUser() {
        return dataToUser;
    }

    public String getDisplayType() {
        return this.displayType;
    }

    public boolean isShowPreview() {
        return isShowPreview;
    }

    public boolean isShowHelp() {
        return isShowHelp;
    }

    public boolean isExit() {
        return isExit;
    }

    public boolean hasItemChanged() {
        return !dataToUser.equals("");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && dataToUser.equals(otherCommandResult.dataToUser)
                && displayType.equals(otherCommandResult.displayType)
                && isShowPreview == otherCommandResult.isShowPreview
                && isShowHelp == otherCommandResult.isShowHelp
                && isExit == otherCommandResult.isExit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataToUser, feedbackToUser, dataToUser,
                displayType, isShowPreview, isShowHelp, isExit);
    }

}
