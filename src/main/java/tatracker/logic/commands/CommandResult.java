//@@author fatin99

package tatracker.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /**
     * Represents all the actions that can occur in the GUI.
     * Note that each action cannot happen at the same time.
     */
    public enum Action {
        DONE,
        EXIT,
        FILTER_STUDENT,
        FILTER_SESSION,
        FILTER_CLAIMS,
        GOTO_STUDENT,
        GOTO_SESSION,
        GOTO_CLAIMS,
        HELP,
        LIST,
        NONE;
    }

    private final Action nextAction;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, Action nextAction) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.nextAction = nextAction;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public Action getNextAction() {
        return nextAction;
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
                && nextAction == otherCommandResult.nextAction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, nextAction);
    }

}
