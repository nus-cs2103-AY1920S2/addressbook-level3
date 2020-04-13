package seedu.address.model.util.action;

import seedu.address.model.Model;

/**
 * Represents a DoableAction that can be done, undone, or redone is ModManager.
 * A DoableAction is based on an object of type T.
 */
public interface DoableAction<T> {
    /**
     * Undo action on the {@code model}.
     */
    void undo(Model model);

    /**
     * Redo action on the {@code model}.
     */
    void redo(Model model);

    /**
     * Get the target entity of this action.
     */
    T getTarget();

    /**
     * Get the possible replacement entity.
     */
    T getReplacement();

    /**
     * Get the type of this action.
     */
    DoableActionType getType();
}
