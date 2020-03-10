package seedu.address.model.food;

import seedu.address.model.tag.Tag;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a Food in the diet tracker of EYLAH.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Food {

    // Identity fields
    private final Name name;
    private final Calories calories;

    // Data Fields
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present
     * name and calories fields must not be null.
     */
    public Food(Name name, Calories calories, Set<Tag> tags) {
        requireAllNonNull(name, calories);
        this.name = name;
        this.calories = calories;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Calories getCalories() {
        return calories;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both food of the same name have the same calories.
     * This defines a weaker notion of equality between two food.
     */
    public boolean isSameFood(Food otherFood) {
        if (otherFood == this) {
            return true;
        }

        return otherFood != null
                && otherFood.getName().equals(getName())
                && otherFood.getCalories().equals(getCalories());
    }

    /**
     * Returns true if both food have the same identity and data fields.
     * This defines a stronger notion of equality between two food.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Food)) {
            return false;
        }

        Food otherFood = (Food) other;
        return otherFood.getName().equals(getName())
                && otherFood.getCalories().equals(getCalories())
                && otherFood.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, calories, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Calories: ")
                .append(getCalories())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
