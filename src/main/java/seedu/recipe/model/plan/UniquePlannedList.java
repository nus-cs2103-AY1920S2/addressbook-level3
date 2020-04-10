package seedu.recipe.model.plan;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

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

    private ObservableList<Plan> internalList = FXCollections.observableArrayList();
    private ObservableList<Plan> unmodifiableObservableList = FXCollections
            .unmodifiableObservableList(internalList);

    /**
     * Replaces the contents of this map with {@code plans}.
     * {@code plans} must be unique.
     */
    public void setPlannedDates(ObservableList<Plan> plans) {
        requireAllNonNull(plans);
        if (!plansAreUnique(plans)) {
            throw new DuplicatePlannedRecipeException();
        }

        internalList.clear();
        internalList.setAll(plans);
    }

    /**
     * Replaces {@code target} with {@code newPlan}.
     */
    public void setPlan(Plan target, Plan newPlan) {
        int index = internalList.indexOf(target);
        System.out.println("planneddate index is: " + index); //todo deletePlan after cooked command debugged
        internalList.set(index, newPlan);
        System.out.println("============Recipes in internalList =============\n" + internalList);
    }

    /**
     * Adds {@code plan} to the list.
     * todo throws
     */
    public void addPlan(Plan plan) {
        requireNonNull(plan);
        if (containsPlan(plan)) {
            throw new DuplicatePlannedRecipeException();
        }
        internalList.add(plan);
    }

    /**
     * Removes {@code plan} from the list.
     */
    public void deletePlan(Plan plan) {
        requireNonNull(plan);
        if (!internalList.remove(plan)) {
            throw new PlannedRecipeNotFoundException();
        }
    }

    /**
     * Returns true if {@code plan} contains only unique plans.
     */
    private boolean plansAreUnique(List<Plan> plan) {
        for (int i = 0; i < plan.size() - 1; i++) {
            for (int j = i + 1; j < plan.size(); j++) {
                if (plan.get(i).equals(plan.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean containsPlan(Plan plan) {
        return internalList.contains(plan);
    }

    /**
     * Sorts the list then returns it as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Plan> asUnmodifiableObservableList() {
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

}
