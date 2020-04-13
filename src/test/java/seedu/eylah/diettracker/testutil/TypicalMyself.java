package seedu.eylah.diettracker.testutil;

import seedu.eylah.diettracker.model.Myself;
import seedu.eylah.diettracker.model.food.Date;
import seedu.eylah.diettracker.model.food.Food;

/**
 * A utility class containing a list of {@code Food} objects to be used in tests.
 */
public class TypicalMyself {

    public static final Food PASTA =
            new FoodBuilder().withName("Mushroom Pasta").withCalories(1200).withDate(new Date(
                    "2020-03-25T16:49:40.210009")).withTags("italian").build();

    // Manually added
    public static final Food MILKSHAKE = new FoodBuilder().withName("Chocolate Milkshake").withDate(new Date(
            "2020-03-25T16:49:40.210009")).withCalories(1200).build();
    public static final Food BAGUETTE = new FoodBuilder().withName("Baguette").withDate(new Date(
            "2020-03-25T16:49:40.210009")).withCalories(420).build();

    public static final String KEYWORD_MATCHING_PIZZA = "pizza"; // A keyword that matches PIZZA

    private TypicalMyself() {} // prevents instantiation

    /**
     * Returns a {@code Myself} with all the typical user metrics.
     */
    public static Myself getTypicalMyself() {
        Myself ms = new Myself();
        return ms;
    }
}
