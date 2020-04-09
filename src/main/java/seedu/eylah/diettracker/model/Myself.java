package seedu.eylah.diettracker.model;

import static java.util.Objects.requireNonNull;

import seedu.eylah.diettracker.model.self.Height;
import seedu.eylah.diettracker.model.self.Self;
import seedu.eylah.diettracker.model.self.Weight;

/**
 * Wraps all data at the diet tracker user level
 * Duplicates are not allowed (by static Self class)
 */
public class Myself implements ReadOnlyMyself {

    public Myself() {}

    /**
     * Creates a FoodBook using the Foods in the {@code toBeCopied}
     */
    public Myself(ReadOnlyMyself toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the height of the personal user with {@code newHeight}.
     */
    public void setHeight(Height newHeight) {
        Self.setHeight(newHeight);
    }

    /**
     * Replaces the weight of the personal user with {@code newWeight}.
     */
    public void setWeight(Weight newWeight) {
        Self.setWeight(newWeight);
    }

    /**
     * Replaces the mode of the personal user with {@code newMode}.
     */
    public void setMode(Mode newMode) {
        Self.setMode(newMode);
    }

    /**
     * Resets the existing data of this {@code Myself} with {@code newData}.
     */
    public void resetData(ReadOnlyMyself newData) {
        requireNonNull(newData);

        setHeight(newData.getHeight());
        setWeight(newData.getWeight());
        setMode(newData.getMode());
    }

    //// metric-level operations

    //// util methods
    @Override
    public String toString() {
        return (new Self()).toString();
    }

    @Override
    public Height getHeight() {
        return Self.getHeight();
    }

    @Override
    public Weight getWeight() {
        return Self.getWeight();
    }

    @Override
    public Mode getMode() {
        return Self.getMode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Myself);
    }

    @Override
    public int hashCode() {
        return (new Self()).hashCode();
    }
}
