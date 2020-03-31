package seedu.recipe.model.cooked;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.recipe.model.ReadOnlyCookedRecordBook;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameRecipe comparison)
 */
public class CookedRecordBook implements ReadOnlyCookedRecordBook {

    private final UniqueRecordList records;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        records = new UniqueRecordList();
    }

    public CookedRecordBook() {}

    /**
     * Creates a RecipeBook using the Recipes in the {@code toBeCopied}
     */
    public CookedRecordBook(ReadOnlyCookedRecordBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the recipe list with {@code records}.
     * {@code records} must not contain duplicate recipes.
     */
    public void setRecords(List<Record> records) {
        this.records.setRecords(records);
    }

    /**
     * Resets the existing data of this {@code CookedRecordBook} with {@code newData}.
     * @param newData
     */
    public void resetData(ReadOnlyCookedRecordBook newData) {
        requireNonNull(newData);

        setRecords(newData.getRecordsList());
    }


    //// recipe-level operations

    /**
     * Returns true if a record with the same identity as {@code record} exists in the cookedrecord.
     */
    public boolean hasRecord(Record record) {
        requireNonNull(record);
        return records.contains(record);
    }

    /**
     * Adds a record to the cookedrecords.
     * The record must not already exist in the address book.
     */
    public void addRecord(Record p) {
        records.add(p);
    }

    /**
     * Replaces the given recipe {@code target} in the list with {@code editedRecipe}.
     * {@code target} must exist in the address book.
     * The recipe identity of {@code editedRecipe} must not be the same as another existing recipe in the address book.
     */
    public void setRecord(Record target, Record editedRecord) {
        requireNonNull(editedRecord);

        records.setRecord(target, editedRecord);
    }

    /**
     * Removes {@code key} from this {@code CookedRcord}.
     * {@code key} must exist in the cookedrecord.
     */
    public void removeRecord(Record key) {
        records.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return records.asUnmodifiableObservableList().size() + " records";
        // TODO: refine later
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CookedRecordBook // instanceof handles nulls
                && records.equals(((CookedRecordBook) other).records));
    }

    @Override
    public int hashCode() {
        return records.hashCode();
    }

    @Override
    public ObservableList<Record> getRecordsList() {
        return records.asUnmodifiableObservableList();
    }
}
