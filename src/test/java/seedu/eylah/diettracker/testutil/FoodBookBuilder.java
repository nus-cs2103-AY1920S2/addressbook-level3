package seedu.eylah.diettracker.testutil;

import seedu.eylah.diettracker.model.FoodBook;
import seedu.eylah.diettracker.model.food.Food;

/**
 * A utility class to help with building Foodbook objects.
 * Example usage: <br>
 *     {@code FoodBook ab = new FoodBookBuilder().withFood("John", "Doe").build();}
 */
public class FoodBookBuilder {

    private FoodBook foodBook;

    public FoodBookBuilder() {
        foodBook = new FoodBook();
    }

    public FoodBookBuilder(FoodBook foodBook) {
        this.foodBook = foodBook;
    }

    /**
     * Adds a new {@code Food} to the {@code FoodBook} that we are building.
     */
    public FoodBookBuilder withFood(Food food) {
        foodBook.addFood(food);
        return this;
    }

    public FoodBook build() {
        return foodBook;
    }
}
