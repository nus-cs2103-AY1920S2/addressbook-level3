package seedu.address.model.entry;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents an Exercise in the fit helper log book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Exercise extends Entry {

    /**
     * Every field must be present and not null.
     */
    public Exercise(String name, String location, String time, String calorie) {
        super(name, location, time, calorie);
        requireAllNonNull(name, time, location, calorie);
    }

    /**
     * Returns true if both exercises have all the same properties.
     * This defines a weaker notion of equality between two exercises.
     */
    public boolean isSameExercise(Exercise anotherExercise) {
        if (anotherExercise == this) {
            return true;
        }

        return anotherExercise != null
                && anotherExercise.getName().equals(getName())
                && anotherExercise.getTime().equals(getTime()) && anotherExercise.getLocation().equals(getCalorie());
    }

}
