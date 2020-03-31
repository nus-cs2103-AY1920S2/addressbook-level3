package seedu.recipe.model.cooked;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.exceptions.DuplicateRecipeException;
import seedu.recipe.model.recipe.exceptions.RecipeNotFoundException;

public class UniqueRecordList implements Iterable<Record> {

    private final ObservableList<Record> internalList = FXCollections.observableArrayList();
    private final ObservableList<Record> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public void setRecords(UniqueRecordList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code recipes}.
     * {@code recipes} must not contain duplicate recipes.
     */
    public void setRecords(List<Record> records) {
        requireAllNonNull(records);
        if (!recordsAreUnique(records)) {
            throw new DuplicateRecipeException();
        }

        internalList.setAll(records);
    }

    /**
     * Returns true if {@code recipes} contains only unique recipes.
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
            throw new DuplicateRecipeException();
        }
        internalList.add(toAdd);
    }

    public void setRecord(Record target, Record editedRecord) {
        requireAllNonNull(target, editedRecord);

        int index = internalList.indexOf(target);
        if (index == -1) {
            //exception to be changed
            throw new RecipeNotFoundException();
        }

        if (!target.isSameRecord(editedRecord) && contains(editedRecord)) {
            throw new DuplicateRecipeException();
        }

        internalList.set(index, editedRecord);
    }

    public void remove(Record toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new RecipeNotFoundException();
        }
    }

    public ObservableList<Record> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Record> iterator() {
        return internalList.iterator();
    }
}
