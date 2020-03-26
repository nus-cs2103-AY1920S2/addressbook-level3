package fithelper.model.calculator;

import static fithelper.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;

import fithelper.model.entry.Entry;

/**
 * This class calculates the calorie intake and consumption.
 */
public class CalorieCalculator {

    private List<Entry> entries;
    private double foodCalorie;
    private double sportsCalorie;
    private double totalCalorie;

    public CalorieCalculator(List<Entry> entries) {
        requireAllNonNull(entries);
        this.entries = entries;
        this.foodCalorie = compFoodCalorie(entries);
        this.sportsCalorie = compSportsCalorie(entries);
        this.totalCalorie = foodCalorie - sportsCalorie;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public double getFoodCalorie() {
        return foodCalorie;
    }

    public double getSportsCalorie() {
        return sportsCalorie;
    }

    public double getTotalCalorie() {
        return this.totalCalorie;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
        this.foodCalorie = compFoodCalorie(entries);
        this.sportsCalorie = compSportsCalorie(entries);
        this.totalCalorie = foodCalorie - sportsCalorie;
    }

    /**
     * Computes the total food calories in a list of entries.
     * @param entries stores entries
     * @return total food calories
     */
    public double compFoodCalorie(List<Entry> entries) {
        double temp = 0.0;
        for (Entry entry : entries) {
            if (entry.isFood() && entry.isDone()) {
                temp += entry.getCalorie().value;
            }
        }
        return temp;
    }

    /**
     * Computes the total sports calories in a list of entries.
     * @param entries stores entries
     * @return total sports calories
     */
    public double compSportsCalorie(List<Entry> entries) {
        double temp = 0.0;
        for (Entry entry : entries) {
            if (entry.isSports() && entry.isDone()) {
                temp += entry.getCalorie().value;
            }
        }
        return temp;
    }

    /**
     * Returns true if both calculators have the same entries.
     * This defines a stronger notion of equality between two calculators.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CalorieCalculator)) {
            return false;
        }

        CalorieCalculator otherCalorieCalculator = (CalorieCalculator) other;
        return otherCalorieCalculator.getEntries().equals(getEntries());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(entries);
    }

}
