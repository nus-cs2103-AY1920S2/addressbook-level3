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

    private Self self;

    public Myself() {
        this.self = new Self();
    }

    public Myself(Self self) {
        this.self = self;
    }

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
        self.setHeight(newHeight);
    }

    /**
     * Replaces the weight of the personal user with {@code newWeight}.
     */
    public void setWeight(Weight newWeight) {
        self.setWeight(newWeight);
    }

    /**
     * Replaces the mode of the personal user with {@code newMode}.
     */
    public void setMode(Mode newMode) {
        self.setMode(newMode);
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
        return self.toString();
    }

    @Override
    public Height getHeight() {
        return self.getHeight();
    }

    @Override
    public Weight getWeight() {
        return self.getWeight();
    }

    @Override
    public Mode getMode() {
        return self.getMode();
    }

    @Override
    public Self getSelf() {
        return this.self;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Myself)
                && self.equals(((Myself) other).getSelf());
    }

    @Override
    public int hashCode() {
        return (new Self()).hashCode();
    }
}
