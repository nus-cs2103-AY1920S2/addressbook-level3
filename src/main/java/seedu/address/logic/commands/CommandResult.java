package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** The third panel should display the specified person. */
    private final boolean get;

    /** The third panel should display the assignment list. */
    private final boolean assignment;

    /** The third panel should display the event schedule. */
    private final boolean event;

    /** The third panel should display the restaurant list. */
    private final boolean restaurant;

    /** The third panel should display the upcoming birthdays for the next five days (Including today). */
    private final boolean showBirthday;

    /** The third panel should display the estimated workload for the next n days based on saved assignments. */
    private final boolean showSchedule;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean get,
                         boolean assignment, boolean event, boolean birthday, boolean restaurant,
                         boolean showSchedule) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.get = get;
        this.assignment = assignment;
        this.event = event;
        this.restaurant = restaurant;
        this.showBirthday = birthday;
        this.showSchedule = showSchedule;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, false,
                false, false, false, false);
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

    public boolean isGet() {
        return get;
    }

    public boolean isAssignment() {
        return assignment;
    }

    public boolean isEvent() {
        return event;
    }

    public boolean isRestaurant() {
        return restaurant;
    }

    public boolean isShowBirthday() {
        return showBirthday;
    }

    public boolean isShowSchedule() {
        return showSchedule;
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
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && get == otherCommandResult.get
                && assignment == otherCommandResult.assignment
                && event == otherCommandResult.event
                && restaurant == otherCommandResult.restaurant
                && showBirthday == otherCommandResult.showBirthday
                && showSchedule == otherCommandResult.showSchedule;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, get,
                assignment, event, showBirthday, restaurant, showSchedule);
    }

}
