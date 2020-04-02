package seedu.recipe.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.recipe.model.Date;
import seedu.recipe.model.cooked.Record;
import seedu.recipe.model.goal.Goal;
import seedu.recipe.model.recipe.Name;
import seedu.recipe.model.util.SampleDataUtil;

/**
 * A utility class to help with building Record objects.
 */
public class RecordBuilder {

    public static final String DEFAULT_NAME = "Egg Tomato Stir Fry";
    public static final String DEFAULT_DATE = "2020-04-01";

    private Name name;
    private Date date;
    private Set<Goal> goals;

    public RecordBuilder() {
        name = new Name(DEFAULT_NAME);
        date = new Date(DEFAULT_DATE);
        goals = new HashSet<>();
    }

    /**
     * Initializes the RecordBuilder with the data of {@code recordToCopy}.
     */
    public RecordBuilder(Record recordToCopy) {
        name = recordToCopy.getName();
        date = recordToCopy.getDate();
        goals = recordToCopy.getGoals();
    }

    /**
     * Sets the {@code Name} of the {@code Record} that we are building.
     */
    public RecordBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Record} that we are building.
     */
    public RecordBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Parses the {@code goals} into a {@code Set<Goal>} and set it to the {@code Record} that we are building.
     */
    public RecordBuilder withGoals(String ... goals) {
        this.goals = SampleDataUtil.getGoalSet(goals);
        return this;
    }

    public Record build() {
        return new Record(name, date, goals);
    }

}
