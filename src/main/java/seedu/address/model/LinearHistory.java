package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of Version with a linear list.
 * @param <T> a class that can produce independent copies
 */
public class LinearHistory<T extends Copyable<T>> implements Version<T> {
    private T currentState;
    private List<T> history = new ArrayList<>();
    private int statePointer;

    /**
     * Creates a {@code LinearHistory} with a starting state.
     * @param object the initial state
     */
    public LinearHistory(T object) {
        history.add(object);
        currentState = object.copy();
        statePointer = 0;
    }

    @Override
    public void commit() {
        history = history.subList(0, statePointer + 1);
        history.add(getCurrentState().copy());
        statePointer++;
    }

    @Override
    public void undo() throws StateNotFoundException {
        try {
            T previousState = history.get(statePointer - 1);
            statePointer--;
            currentState = previousState.copy();
        } catch (IndexOutOfBoundsException e) {
            throw new StateNotFoundException();
        }
    }

    @Override
    public void redo() throws StateNotFoundException {
        try {
            T nextState = history.get(statePointer + 1);
            statePointer++;
            currentState = nextState.copy();
        } catch (IndexOutOfBoundsException e) {
            throw new StateNotFoundException();
        }
    }

    @Override
    public T getCurrentState() {
        return currentState;
    }
}
