package fithelper.model.weight;

import static fithelper.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import fithelper.model.weight.exceptions.DuplicateWeightException;
import fithelper.model.weight.exceptions.WeightNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of weight records that enforces uniqueness between its elements and does not allow nulls.
 * Aa weight record is considered unique by comparing using {@code Weight#isSameWeight(Weight)}.
 * As such, adding and updating of weight records uses Weight#isSameWeight(Weight) for equality
 * so as to ensure that the weight record being added or updated is
 * unique in terms of date in the UniqueWeightList.
 *
 * Supports a minimal set of list operations.
 *
 * @see Weight#isSameWeight(Weight)
 */
public class UniqueWeightList implements Iterable<Weight> {

    private final ObservableList<Weight> internalList = FXCollections.observableArrayList();
    private final ObservableList<Weight> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent weight as the given argument.
     */
    public boolean contains(Weight toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameWeight);
    }

    /**
     * Adds a weight to the list.
     * The weight must not already exist in the list.
     */
    public void add(Weight toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateWeightException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the weight {@code target} in the list with {@code editedWeight}.
     * {@code target} must exist in the list.
     * The weight identity of {@code editedWeight} must not be the same as another existing weight in the list.
     */
    public void setWeight(Weight target, Weight editedWeight) {
        requireAllNonNull(target, editedWeight);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new WeightNotFoundException();
        }

        if (!target.isSameWeight(editedWeight) && contains(editedWeight)) {
            throw new DuplicateWeightException();
        }

        internalList.set(index, editedWeight);
    }

    /**
     * Replaces the contents of this list with {@code weights}.
     * {@code weights} must not contain duplicate weights.
     */
    public void setWeights(List<Weight> weights) {
        requireAllNonNull(weights);
        if (!weightsAreUnique(weights)) {
            throw new DuplicateWeightException();
        }
        internalList.setAll(weights);
    }

    public void setWeights(UniqueWeightList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Removes the equivalent weight from the list.
     * The weight must exist in the list.
     */
    public void remove(Weight toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new WeightNotFoundException();
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Weight> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Weight> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueWeightList // instanceof handles nulls
                && internalList.equals(((UniqueWeightList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code weights} contains only unique weights.
     */
    private boolean weightsAreUnique(List<Weight> weights) {
        for (int i = 0; i < weights.size() - 1; i++) {
            for (int j = i + 1; j < weights.size(); j++) {
                if (weights.get(i).isSameWeight(weights.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
