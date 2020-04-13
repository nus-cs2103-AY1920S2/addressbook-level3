package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    private ViewType viewType;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, ViewType viewType) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.viewType = viewType;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser}, and other fields
     * set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, ViewType.NORMAL);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public ViewType getViewType() {
        return viewType;
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
                && viewType == otherCommandResult.viewType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, viewType);
    }
}
