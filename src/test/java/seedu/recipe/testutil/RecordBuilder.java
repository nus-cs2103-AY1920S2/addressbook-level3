package seedu.recipe.testutil;

import seedu.recipe.model.cooked.Record;
import seedu.recipe.model.recipe.Name;

/**
 * A utility class to help with building Record objects.
 */
public class RecordBuilder {

    public static final String DEFAULT_NAME = "Egg Tomato Stir Fry";

    private Name name;

    public RecordBuilder() {
        name = new Name(DEFAULT_NAME);
    }

    /**
     * Initializes the RecordBuilder with the data of {@code recordToCopy}.
     */
    public RecordBuilder(Record recordToCopy) {
        name = recordToCopy.getName();
    }

    /**
     * Sets the {@code Name} of the {@code Record} that we are building.
     */
    public RecordBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }



    public Record build() {
        return new Record(name);
    }

}
