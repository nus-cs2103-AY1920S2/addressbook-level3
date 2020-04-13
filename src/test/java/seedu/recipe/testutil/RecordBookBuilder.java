package seedu.recipe.testutil;

import seedu.recipe.model.cooked.CookedRecordBook;
import seedu.recipe.model.cooked.Record;

/**
 * A utility class to help with building RecordBook objects.
 * Example usage: <br>
 *     {@code RecordBook ab = new RecordBookBuilder().withRecord("John", "Doe").build();}
 */
public class RecordBookBuilder {

    private CookedRecordBook cookedRecordBook;

    public RecordBookBuilder() {
        cookedRecordBook = new CookedRecordBook();
    }

    public RecordBookBuilder(CookedRecordBook cookedRecordBook) {
        this.cookedRecordBook = cookedRecordBook;
    }

    /**
     * Adds a new {@code Record} to the {@code RecordBook} that we are building.
     * @return recordbookbuilder object.
     */
    public RecordBookBuilder withRecord(Record record) {
        cookedRecordBook.addRecord(record);
        return this;
    }

    public CookedRecordBook build() {
        return cookedRecordBook;
    }
}
