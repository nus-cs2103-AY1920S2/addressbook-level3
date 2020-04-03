package seedu.recipe.model.plan;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of planned dates that enforces uniqueness between its elements and does not allow nulls.
 * Ensures that at any point in time, the items in the list of planned dates each has a unique Date.
 * This means that PlannedDate items with the same Date must not exist in the list.
 *
 * A planned date is considered unique by comparing using {@code PlannedDate#equals(object)}.
 *
 *  Supports a minimal set of list operations.
 */
public class UniquePlannedList {

    private ObservableList<PlannedDate> internalList = FXCollections.observableArrayList();
    private ObservableList<PlannedDate> unmodifiableObservableList = FXCollections
            .unmodifiableObservableList(internalList);

    /**
     * Replaces the contents of this map with {@code plannedDates}.
     * {@code plannedDates} must not contain duplicate recipes on the same date.
     */
    public void setPlannedDates(ObservableList<PlannedDate> plannedDates) {
        requireAllNonNull(plannedDates);
        if (!plannedDatesAreUnique(plannedDates)) {
            throw new DuplicatePlannedRecipeException();
        }

        internalList.clear();
        internalList.setAll(plannedDates);
    }

    /**
     * Adds {@code plannedDate} to the list.
     */
    public void add(PlannedDate plannedDate) {
        requireNonNull(plannedDate);
        if (internalList.contains(plannedDate) || hasPlannedDate(plannedDate)) {
            throw new DuplicatePlannedRecipeException();
        }
        Optional<PlannedDate> optionalSameDate = withSameDate(plannedDate);
        if (optionalSameDate.isEmpty()) {
            internalList.add(plannedDate); // planned on a new day
        } else {
            PlannedDate sameDate = optionalSameDate.get();
            internalList.remove(sameDate);
            sameDate = sameDate.addRecipes(plannedDate);
            internalList.add(sameDate);
            System.out.println("reached, internal list: " + internalList.size());
        }
    }

    /**
     * Removes {@code plannedDate} from the list.
     */
    public void remove(PlannedDate plannedDate) {
        requireNonNull(plannedDate);
        System.out.println("removing + " + plannedDate); //todo remove
        if (!internalList.remove(plannedDate)) {
            throw new PlannedRecipeNotFoundException();
        }
    }

    /**
     * Returns true if the list contains the same recipes at the planned date from {@code otherPlannedDate}.
     */
    public boolean hasPlannedDate(PlannedDate otherPlannedDate) {
        if (internalList.contains(otherPlannedDate)) {
            return true;
        }
        for (PlannedDate plannedDate : internalList) {
            if (plannedDate.hasSameRecipeInPlan(otherPlannedDate)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns an optional planned date with the same date as {@code otherPlannedDate}.
     * Returns optional empty if no such planned date exists.
     */
    private Optional<PlannedDate> withSameDate(PlannedDate otherPlannedDate) {
        List<PlannedDate> copyOfInternal = new ArrayList<>(internalList);
        return copyOfInternal.stream().filter(plan -> plan.hasSameDate(otherPlannedDate)).findFirst();
    }

    /**
     * Returns true if {@code plannedDates} contains only unique planned dates.
     */
    private boolean plannedDatesAreUnique(List<PlannedDate> plannedDates) {
        for (int i = 0; i < plannedDates.size() - 1; i++) {
            if (!plannedDates.get(i).recipesAreUnique()) {
                return false; // checks if the recipes in each plannedDate is unique
            }
            for (int j = i + 1; j < plannedDates.size(); j++) {
                if (plannedDates.get(i).equals(plannedDates.get(j))) {
                    return false;
                }
            }
        }
        int lastItem = plannedDates.size() - 1;
        if (lastItem > 0 && !plannedDates.get(plannedDates.size() - 1).recipesAreUnique()) {
            return false; // checks if the recipes in the last plannedDate is unique
        }
        return true;
    }

    /**
     * Sorts the list then returns it as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<PlannedDate> asUnmodifiableObservableList() {
        FXCollections.sort(internalList);
        return unmodifiableObservableList;
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePlannedList // instanceof handles nulls
                && internalList.equals(((UniquePlannedList) other).internalList));
    }

    public String size() {
        return internalList.size() + "";
    }
}
