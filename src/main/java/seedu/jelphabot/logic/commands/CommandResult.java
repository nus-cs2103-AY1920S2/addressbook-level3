package seedu.jelphabot.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /**
     * Help information should be shown to the user.
     */
    private final boolean showHelp;

    /**
     * The application should exit.
     */
    private final boolean exit;

    private boolean calendarCommand;
    private LocalDate date;
    private YearMonth yearMonth;
    private SwitchTab toSwitch = SwitchTab.STAY_ON_CURRENT;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    public CommandResult(String feedbackToUser, LocalDate date, YearMonth yearMonth) {
        this(feedbackToUser, false, false);
        this.date = date;
        this.yearMonth = yearMonth;
        this.calendarCommand = true;
    }

    /**
     * The application should switch to the Task List tab
     * @return Sets the "switch to window" flag to Task List.
     */
    public CommandResult isShowDateTaskList() {
        this.toSwitch = SwitchTab.TASK_LIST_DATE;
        return this;
    }

    /**
     * The application should switch to the Task List tab
     * @return Sets the "switch to window" flag to Task List.
     */
    public CommandResult isShowModuleTaskList() {
        this.toSwitch = SwitchTab.TASK_LIST_MODULE;
        return this;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public LocalDate getDate() {
        return date;
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public boolean isCalendarCommand() {
        return calendarCommand;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    /**
     * The application should switch to the productivity tab.
     * @return Sets the "toSwitch" flag to Productivity.
     */
    public CommandResult isShowProductivity() {
        this.toSwitch = SwitchTab.PRODUCTIVITY;
        return this;
    }

    /**
     * The application should switch to the Calendar tab.
     * @return Sets the "toSwitch" flag to Calendar.
     */
    public CommandResult isShowCalendar() {
        this.toSwitch = SwitchTab.CALENDAR;
        return this;
    }

    /**
     * The application should switch to the Summary tab.
     * @return Sets the "toSwitch" flag to Summary.
     */
    public CommandResult isShowSummary() {
        this.toSwitch = SwitchTab.SUMMARY;
        return this;
    }

    /**
     * The application should switch to the Calendar tab.
     * @return Sets the "toSwitch" flag to Calendar.
     */
    public CommandResult isShowReminder() {
        this.toSwitch = SwitchTab.REMINDER;
        return this;
    }

    public SwitchTab getTabSwitch() {
        return toSwitch;
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
                   && toSwitch == otherCommandResult.toSwitch;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, toSwitch);
    }

    /**
     * Enum representing switch that indicates if the command should switch to another window.
     */
    public enum SwitchTab {
        CALENDAR,
        PRODUCTIVITY,
        SUMMARY,
        TASK_LIST_DATE,
        TASK_LIST_MODULE,
        STAY_ON_CURRENT,
        REMINDER
    }

}
