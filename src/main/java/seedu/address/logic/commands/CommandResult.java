package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    private final CommandType type;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, CommandType type) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.type = type;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public CommandType getType() {
        return type;
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
                && type.equals(otherCommandResult.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, type);
    }

    @Override
    public String toString() {
        return feedbackToUser;
    }
}
