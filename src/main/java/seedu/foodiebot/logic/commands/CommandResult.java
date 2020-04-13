package seedu.foodiebot.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/** Represents the result of a command execution. */
public class CommandResult {
    public final String commandName;

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    private final boolean isLocationSpecified;

    /** Constructs a {@code CommandResult} with the specified fields. */
    public CommandResult(String commandName, String feedbackToUser, boolean showHelp, boolean exit) {
        this.commandName = commandName;
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.isLocationSpecified = false;
    }

    /** Constructs a {@code CommandResult} with the specified fields. */
    public CommandResult(String commandName, String feedbackToUser, boolean showHelp, boolean exit,
                         boolean isLocationSpecified) {
        this.commandName = commandName;
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.isLocationSpecified = isLocationSpecified;
    }
    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser}, and other
     * fields set to their default value.
     */
    public CommandResult(String commandName, String feedbackToUser) {
        this(commandName, feedbackToUser,
            false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isLocationSpecified() {
        return isLocationSpecified;
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
        return commandName.equals(otherCommandResult.commandName)
                && feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }
}
