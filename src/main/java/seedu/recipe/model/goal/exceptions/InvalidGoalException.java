package seedu.recipe.model.goal.exceptions;

/**
 * Signals that the operation results in an invalid state where tested goal should not exist.
 */
public class InvalidGoalException extends IllegalStateException {
    public InvalidGoalException(String exception) {
        super(exception);
    }
}
