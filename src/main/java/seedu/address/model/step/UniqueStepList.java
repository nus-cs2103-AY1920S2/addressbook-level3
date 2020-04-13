package seedu.address.model.step;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.step.exceptions.DuplicateStepException;
import seedu.address.model.step.exceptions.StepNotFoundException;

/**
 * A list of steps that enforces uniqueness between its elements and does not allow nulls.
 * A step is considered unique by comparing using {@code Step#equals(Object)}. As such, adding and updating of
 * Steps uses Step#equals(Object) for equality so as to ensure that the step being added, removed or updated is
 * unique in terms of identity in the UniqueStepList.
 *
 * Supports a minimal set of list operations.
 *
 * @see Step#equals(Object)
 */
public class UniqueStepList implements Iterable<Step> {

    private final ObservableList<Step> internalList = FXCollections.observableArrayList();
    private final ObservableList<Step> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent step as the given argument.
     */
    public boolean contains(Step toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(step -> step.equals(toCheck));
    }

    /**
     * Adds a step to the list.
     * The step must not already exist in the list.
     */
    public void add(Step toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateStepException();
        }
        internalList.add(toAdd);
    }

    /**
     * Adds a step to given index in the list.
     * The step must not already exist in the list.
     */
    public void add(Index index, Step toAdd) {
        requireNonNull(toAdd);
        requireAllNonNull(index);
        if (contains(toAdd)) {
            throw new DuplicateStepException();
        }
        internalList.add(index.getZeroBased(), toAdd);
    }

    /**
     * Replaces the step {@code target} in the list with {@code editedStep}.
     * {@code target} must exist in the list.
     * The stepDescription of {@code editedStep} must not be the same as another existing step in the list.
     */
    public void setStep(Step target, Step editedStep) {
        requireAllNonNull(target, editedStep);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new StepNotFoundException();
        }

        if (!target.equals(editedStep) && contains(editedStep)) {
            throw new DuplicateStepException();
        }

        internalList.set(index, editedStep);
    }

    /**
     * Removes the equivalent step from the list.
     * The step must exist in the list.
     */
    public void remove(Step toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new StepNotFoundException();
        }
    }

    /**
     * Removes the step from given index  in the list.
     * The step must exist in the list.
     */
    public Step remove(Index index) {
        requireNonNull(index);
        Step toRemove = internalList.remove(index.getZeroBased());
        return toRemove;
    }

    public void setSteps(UniqueStepList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code Step}.
     * {@code Step} must not contain duplicate persons.
     */
    public void setSteps(List<Step> steps) {
        requireAllNonNull(steps);
        if (!stepsAreUnique(steps)) {
            throw new DuplicateStepException();
        }

        internalList.setAll(steps);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Step> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Step> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueStepList // instanceof handles nulls
                && internalList.equals(((UniqueStepList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code Step} contains only unique steps.
     */
    private boolean stepsAreUnique(List<Step> steps) {
        for (int i = 0; i < steps.size() - 1; i++) {
            for (int j = i + 1; j < steps.size(); j++) {
                if (steps.get(i).equals(steps.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < internalList.size(); i++) {
            sb.append("\n").append(i + 1).append(": ").append(internalList.get(i).toString());
        }

        return sb.toString();
    }
}
