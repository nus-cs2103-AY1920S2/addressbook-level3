package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.model.event.tutorial.Tutorial;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** The application should show the calendar. */
    private final boolean showCalendar;

    /** The application should show the listed tab. */
    private final boolean showList;

    /** The application should close the calendar window. */
    private final boolean closeCalendar;

    /** The application should show the relevant attendance list. */
    private final boolean showAttendance;

    /** The application should reset attendance list and refresh student tutorial tags. */
    private final boolean refreshTutorial;

    /** Information for the application to show the correct attendance list. */
    private final Tutorial tutorialToShow;
    private final int weekZeroBased;

    /**
     * Constructs a {@code CommandResult} for a command which requires a list to be shown.
     */
    public CommandResult(String feedbackToUser, boolean showCalendar, boolean showHelp,
                         boolean showList, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showCalendar = showCalendar;
        this.showHelp = showHelp;
        this.showList = showList;
        this.showAttendance = false;
        this.refreshTutorial = false;
        this.exit = exit;
        this.tutorialToShow = null;
        this.weekZeroBased = 0;
        this.closeCalendar = false;
    }

    /**
     * Constructs a {@code CommandResult} for displaying attendance.
     */
    public CommandResult(String feedbackToUser, Tutorial tutorial, int weekZeroBased) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showCalendar = false;
        this.showHelp = false;
        this.exit = false;
        this.showList = false;
        this.showAttendance = true;
        this.refreshTutorial = false;
        this.tutorialToShow = tutorial;
        this.weekZeroBased = weekZeroBased;
        this.closeCalendar = false;
    }

    /**
     * Constructs a {@code CommandResult} for a command which will show default attendance list after
     * deleting a tutorial.
     */
    public CommandResult(String feedbackToUser, boolean showCalendar, boolean showHelp,
                         boolean showList, boolean deleteTutorial, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showCalendar = showCalendar;
        this.showHelp = showHelp;
        this.showList = showList;
        this.showAttendance = false;
        this.refreshTutorial = deleteTutorial;
        this.exit = exit;
        this.tutorialToShow = null;
        this.weekZeroBased = 0;
        this.closeCalendar = false;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.showCalendar = false;
        this.showList = false;
        this.showAttendance = false;
        this.refreshTutorial = false;
        this.tutorialToShow = null;
        this.weekZeroBased = 0;
        this.closeCalendar = false;
    }

    /**
     * Constructs a {@code CommandResult} with all default false, except to delete tutorial.
     */
    public CommandResult(String feedbackToUser, boolean closeCalendar) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showAttendance = false;
        this.refreshTutorial = false;
        this.showHelp = false;
        this.exit = false;
        this.showCalendar = false;
        this.showList = false;
        this.tutorialToShow = null;
        this.weekZeroBased = 0;
        this.closeCalendar = true;
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

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isShowCalendar() {
        return showCalendar;
    }

    public boolean isCloseCalendar() {
        return closeCalendar;
    }

    public boolean isShowList() {
        return showList;
    }

    public boolean isShowAttendance() {
        return showAttendance;
    }

    public boolean isRefreshTutorial() {
        return refreshTutorial;
    }

    public Tutorial getTutorialToShow() {
        return tutorialToShow;
    }

    public int getWeekZeroBased() {
        return weekZeroBased;
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
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }

}
