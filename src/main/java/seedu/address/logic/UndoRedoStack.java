package seedu.address.logic;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UndoableCommand;

import java.util.Stack;

public class UndoRedoStack {
    private Stack<UndoableCommand> undoStack;
    private Stack<UndoableCommand> redoStack;

    public UndoRedoStack() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    public void push(Command command) {
        if (!(command instanceof UndoCommand) && !(command instanceof RedoCommand)) {
            redoStack.clear();
        }

        if (!(command instanceof UndoableCommand)) {
            return;
        }

        undoStack.add((UndoableCommand) command);
    }

    public void clear() {
        redoStack.clear();
        undoStack.clear();
    }

    public UndoableCommand popUndo() {
        UndoableCommand toUndo = undoStack.pop();
        redoStack.push(toUndo);
        return toUndo;
    }

    public UndoableCommand popRedo() {
        UndoableCommand toRedo = redoStack.pop();
        undoStack.push(toRedo);
        return toRedo;
    }

    public boolean canUndo() {
        return !undoStack.empty();
    }

    public boolean canRedo() {
        return !redoStack.empty();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UndoRedoStack)) {
            return false;
        }

        UndoRedoStack stack = (UndoRedoStack) other;

        return undoStack.equals(stack.undoStack)
                && redoStack.equals(stack.redoStack);
    }
}
