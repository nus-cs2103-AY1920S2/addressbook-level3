package seedu.address.model.util.action;

import java.util.Stack;

import seedu.address.model.Model;

/**
 * A list of actions in a usage.
 */
public class DoableActionList {
    private final Stack<DoableAction<?>> primary;
    private final Stack<DoableAction<?>> secondary;

    public DoableActionList() {
        primary = new Stack<>();
        secondary = new Stack<>();
    }

    public boolean canUndo() {
        return !primary.isEmpty();
    }

    public boolean canRedo() {
        return !secondary.isEmpty();
    }

    /**
     * Undo the latest done action.
     */
    public DoableAction<?> undo(Model model) {
        assert canUndo();

        DoableAction<?> pTop = primary.pop();
        pTop.undo(model);
        secondary.push(pTop);
        return pTop;
    }

    /**
     * Redo the most previously undone action.
     */
    public DoableAction<?> redo(Model model) {
        assert canRedo();

        DoableAction<?> sTop = secondary.pop();
        sTop.redo(model);
        primary.push(sTop);
        return sTop;
    }

    /**
     * Adds action to the history of usage. Cannot redo immediately.
     */
    public void addAction(DoableAction<?> action) {
        primary.push(action);
        secondary.empty();
    }
}
