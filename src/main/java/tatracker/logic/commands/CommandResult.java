package tatracker.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Tabs should be switched. */
    //private final boolean switchTab;

    /** Help information should be shown to the user. */
    //private final boolean showHelp;

    /** The application should exit. */
    //private final boolean exit;

    public enum Action {
        GOTO_STUDENT,
        GOTO_SESSION,
        GOTO_CLAIMS,
        HELP,
        EXIT,
        NONE
    }

    private final Action nextAction;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, Action nextAction) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.nextAction = nextAction;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, Action.NONE);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public Action getNextAction() {
        return nextAction;
    }

    //public boolean isSwitchTab() {
    //    return switchTab;
    //}

    //public boolean isShowHelp() {
    //    return showHelp;
    //}

    //public boolean isExit() {
    //    return exit;
    //}

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
