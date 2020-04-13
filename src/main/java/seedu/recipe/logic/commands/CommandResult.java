package seedu.recipe.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.recipe.ui.tab.Tab;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** Ingredient list of planned recipes should be shown to the user. */
    private final boolean showGroceryList;

    /**
     * The application should switch tab.
     */
    private final Tab switchTab;

    /** The application should exit. */
    private final boolean exit;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showGroceryList, boolean showHelp, Tab switchTab,
             boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.showGroceryList = showGroceryList;
        this.switchTab = switchTab;
        this.exit = exit;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, null, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isShowGroceryList() {
        return showGroceryList;
    }

    public boolean isSwitchTab() {
        return switchTab != null;
    }

    public boolean isExit() {
        return exit;
    }

    public Tab getTab() {
        return switchTab;
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
                && showGroceryList == otherCommandResult.showGroceryList
                && switchTab == otherCommandResult.switchTab
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, showGroceryList, switchTab, exit);
    }

}
