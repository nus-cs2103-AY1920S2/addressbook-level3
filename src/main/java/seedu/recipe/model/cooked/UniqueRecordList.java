package seedu.recipe.model.cooked;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.recipe.model.cooked.exceptions.DuplicateRecordException;
import seedu.recipe.model.cooked.exceptions.RecordNotFoundException;
import seedu.recipe.model.goal.Goal;
import seedu.recipe.model.goal.GoalCount;
import seedu.recipe.model.goal.exceptions.InvalidGoalException;
import seedu.recipe.model.recipe.ingredient.MainIngredientType;

/**
 * A list of records that enforces uniqueness between its elements and does not allow nulls.
 * A record is considered unique by comparing using {@code Record#isSameRecord(Record)}. As such, adding and updating of
 * records uses Record#isSameRecord(Record) for equality so as to ensure that the record being added or updated is
 * unique in terms of identity in the UniqueRecordList. However, the removal of a record uses Record#equals(Object) so
 * as to ensure that the record with exactly the same fields will be removed.
 *
 * Contains list of internal records along with list of internal GoalCount for each record.
 *
 * Supports a minimal set of list operations.
 *
 * @see Record#isSameRecord(Record)
 */
public class UniqueRecordList implements Iterable<Record> {

    public static final int VEG_INDEX = 0;
    public static final int FRUIT_INDEX = 1;
    public static final int PROTEIN_INDEX = 2;
    public static final int GRAIN_INDEX = 3;

    private final ObservableList<Record> internalList = FXCollections.observableArrayList();
    private final ObservableList<Record> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    private final ObservableList<GoalCount> internalGoalsList = FXCollections.observableArrayList();
    private final ObservableList<GoalCount> internalUnmodifiableGoalsList =
            FXCollections.unmodifiableObservableList(internalGoalsList);

    /**
     * Replaces the contents of this list with {@code records}.
     * {@code records} must not contain duplicate records.
     */
    public void setRecords(List<Record> records) {
        requireAllNonNull(records);
        if (!recordsAreUnique(records)) {
            throw new DuplicateRecordException();
        }

        internalList.setAll(records);
        setGoalsTally();
    }

    /**
     * Returns true if the list contains an equivalent record as the given argument.
     */
    public boolean contains(Record toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameRecord);
    }

    /**
     * Adds a record to the list.
     * The record must not already exist in the list.
     */
    public void add(Record toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateRecordException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the record {@code target} in the list with {@code editedRecord}.
     * {@code target} must exist in the list.
     * The record identity of {@code editedRecord} must not be the same as another existing record in the list.
     */
    public void setRecord(Record target, Record editedRecord) {
        requireAllNonNull(target, editedRecord);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new RecordNotFoundException();
        }

        if (!target.isSameRecord(editedRecord) && contains(editedRecord)) {
            throw new DuplicateRecordException();
        }

        internalList.set(index, editedRecord);
    }

    /**
     * Removes the equivalent record from the list.
     * The record must exist in the list.
     */
    public void remove(Record toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new RecordNotFoundException();
        }
    }

    public ObservableList<Record> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Record> iterator() {
        return internalList.iterator();
    }

    /**
     * Returns true if {@code records} contains only unique records.
     */
    private boolean recordsAreUnique(List<Record> records) {
        for (int i = 0; i < records.size() - 1; i++) {
            for (int j = i + 1; j < records.size(); j++) {
                if (records.get(i).isSameRecord(records.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Sets goal tally when records are set initially.
     */
    private void setGoalsTally() {
        HashMap<MainIngredientType, Integer> goalMap = new HashMap<>();
        goalMap.put(MainIngredientType.VEGETABLE, 0);
        goalMap.put(MainIngredientType.FRUIT, 0);
        goalMap.put(MainIngredientType.PROTEIN, 0);
        goalMap.put(MainIngredientType.GRAIN, 0);

        for (Record record : internalList) {
            List<Goal> currGoals = new ArrayList<>(record.getGoals());
            for (Goal currGoal : currGoals) {
                MainIngredientType goalType = currGoal.getMainIngredientType();
                if (goalMap.containsKey(goalType)) {
                    Integer prevCount = goalMap.get(goalType);
                    goalMap.put(goalType, prevCount + 1);
                }
            }
        }
        addToGoalsList(goalMap);
    }

    /**
     * Adds goalCount to internalGoalsList  with the specified index for each food group.
     * @param goalMap of goals and respective tally.
     */
    public void addToGoalsList(HashMap<MainIngredientType, Integer> goalMap) {
        internalGoalsList.clear();

        internalGoalsList.add(VEG_INDEX,
                new GoalCount(new Goal(MainIngredientType.VEGETABLE), goalMap.get(MainIngredientType.VEGETABLE)));
        internalGoalsList.add(FRUIT_INDEX,
                new GoalCount(new Goal(MainIngredientType.FRUIT), goalMap.get(MainIngredientType.FRUIT)));
        internalGoalsList.add(PROTEIN_INDEX,
                new GoalCount(new Goal(MainIngredientType.PROTEIN), goalMap.get(MainIngredientType.PROTEIN)));
        internalGoalsList.add(GRAIN_INDEX,
                new GoalCount(new Goal(MainIngredientType.GRAIN), goalMap.get(MainIngredientType.GRAIN)));

        assert internalGoalsList.size() == 4 : "there should be only 4 main goals";
    }

    /**
     * Updates goals for a record where index.
     * 0: Herbivores 1: Fruity Fiesta.
     * 2: Bulk Like the Hulk. 3:Wholesome Wholemeal.
     */
    public void updateGoalsTally(Record record) {
        List<Goal> currGoals = new ArrayList<>(record.getGoals());
        for (Goal currGoal: currGoals) {
            MainIngredientType goalType = currGoal.getMainIngredientType();
            int goalIndex;
            switch(goalType) {
            case VEGETABLE:
                goalIndex = VEG_INDEX;
                break;
            case FRUIT:
                goalIndex = FRUIT_INDEX;
                break;
            case PROTEIN:
                goalIndex = PROTEIN_INDEX;
                break;
            case GRAIN:
                goalIndex = GRAIN_INDEX;
                break;
            default:
                throw new InvalidGoalException("Unexpected value: " + goalType);
            }
            GoalCount goalTally = internalGoalsList.get(goalIndex);
            goalTally.incrementCount();
            internalGoalsList.set(goalIndex, goalTally);
        }
    }

    /**
     * Returns goalTally for all main {@code goal} in cookedRecordBook.
     */
    public ObservableList<GoalCount> getGoalsTally() {
        return internalUnmodifiableGoalsList;
    }
}
