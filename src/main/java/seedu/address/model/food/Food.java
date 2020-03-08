package seedu.address.model.food;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Food in the diet tracker of EYLAH.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Food {

    // Identity fields
    private final Name name;
    private final Calories calories;

    // Data Fields
    private final Carb carb;
    private final Fat fat;
    private final Protein protein;
    private final Sugar sugar;

    /**
     * Every field must be present
     * name and calories fields must not be null.
     */
    public Food(Name name, Calories calories, Carb carb, Fat fat, Protein protein, Sugar sugar) {
        requireAllNonNull(name, calories);
        this.name = name;
        this.calories = calories;
        this.carb = carb;
        this.fat = fat;
        this.protein = protein;
        this.sugar = sugar;
    }

    public Name getName() {
        return name;
    }

    public Calories getCalories() {
        return calories;
    }

    public Carb getCarb() {
        return carb;
    }

    public Fat getFat() {
        return fat;
    }

    public Protein getProtein() {
        return protein;
    }

    public Sugar getSugar() {
        return sugar;
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
                && otherFood.getCarb().equals(getCarb())
                && otherFood.getFat().equals(getFat())
                && otherFood.getProtein().equals(getProtein())
                && otherFood.getSugar().equals(getSugar());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, calories, carb, fat, protein, sugar);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Calories: ")
                .append(getCalories())
                .append(" Carb: ")
                .append(getCarb())
                .append(" Fat: ")
                .append(getFat())
                .append(" Protein: ")
                .append(getProtein())
                .append(" Sugar: ")
                .append(getSugar());
        return builder.toString();
    }
}
