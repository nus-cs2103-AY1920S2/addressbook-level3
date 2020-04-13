package seedu.eylah.commons.logic.command;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {
    private final String feedbackToUser;

    /** The application should exit. */
    private final boolean exit;

    /** The application should back to main menu. */
    private final boolean back;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean exit, boolean back) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.exit = exit;
        this.back = back;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isBack() {
        return back;
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
                && exit == otherCommandResult.exit
                && back == otherCommandResult.back;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, exit, back);
    }

}

