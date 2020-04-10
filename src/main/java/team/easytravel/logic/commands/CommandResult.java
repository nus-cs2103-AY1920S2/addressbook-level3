package team.easytravel.logic.commands;

import static team.easytravel.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    /**
     * Represents the action to be taken by the UI component.
     */
    public enum Action {
        NONE,
        HELP,
        TRIP_SET,
        TRIP_DELETE,
        TRIP_EDIT,
        STATUS,
        EXIT,
        PRESET,
        SWITCH_TAB_SCHEDULE,
        SWITCH_TAB_ACTIVITY,
        SWITCH_TAB_ACCOMMODATION,
        SWITCH_TAB_TRANSPORT,
        SWITCH_TAB_PACKING_LIST,
        SWITCH_TAB_FIXED_EXPENSE
    }

    private final String feedbackToUser;

    private final List<String> checkStatus;

    private final Action action;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, List<String> checkStatus, Action action) {
        requireAllNonNull(feedbackToUser, action);
        this.feedbackToUser = feedbackToUser;
        this.checkStatus = checkStatus;
        this.action = action;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, Action action) {
        this(feedbackToUser, null, action);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isSetTrip() {
        return action.equals(Action.TRIP_SET);
    }

    public boolean isEditTrip() {
        return action.equals(Action.TRIP_EDIT);
    }

    public boolean isDeleteTrip() {
        return action.equals(Action.TRIP_DELETE);
    }

    public boolean isShowHelp() {
        return action.equals(Action.HELP);
    }

    public boolean isCheckStatus() {
        return action.equals(Action.STATUS);
    }

    public boolean isListPreset() {
        return action.equals(Action.PRESET);
    }

    public List<String> getStatusFeedback() {
        assert isCheckStatus();
        return checkStatus;
    }

    public boolean isExit() {
        return action.equals(Action.EXIT);
    }

    public boolean isActivity() {
        return action.equals(Action.SWITCH_TAB_ACTIVITY);
    }

    public boolean isTransportation() {
        return action.equals(Action.SWITCH_TAB_TRANSPORT);
    }

    public boolean isAccommodation() {
        return action.equals(Action.SWITCH_TAB_ACCOMMODATION);
    }

    public boolean isPackingList() {
        return action.equals(Action.SWITCH_TAB_PACKING_LIST);
    }

    public boolean isFixedExpense() {
        return action.equals(Action.SWITCH_TAB_FIXED_EXPENSE);
    }

    public boolean isSchedule() {
        return action.equals(Action.SWITCH_TAB_SCHEDULE);
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

        if (checkStatus == null) {
            return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                    && action.equals(otherCommandResult.action);
        } else {
            return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                    && checkStatus.equals(otherCommandResult.checkStatus)
                    && action.equals(otherCommandResult.action);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, checkStatus, action);
    }

}
