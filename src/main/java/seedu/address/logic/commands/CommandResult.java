package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.core.index.Index;


/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /**
     * The application should expand the view for a recipe.
     */
    private final boolean view;

    /**
     * The index of the recipe to view;
     */
    private final Index recipeIndex;
    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.view = false;
        this.recipeIndex = null;
    }

    public CommandResult(String feedbackToUser, boolean view, Index recipeIndex) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = false;
        this.exit = false;
        this.view = view;
        this.recipeIndex = recipeIndex;
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

    public boolean isViewRecipe() {
        return view;
    }

    public Index getRecipeIndex() {
        return recipeIndex;
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
