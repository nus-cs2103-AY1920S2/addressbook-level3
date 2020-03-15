package seedu.eylah.diettracker.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.eylah.diettracker.model.FoodBook;
import seedu.eylah.diettracker.model.food.Food;

/**
 * A utility class containing a list of {@code Food} objects to be used in tests.
 */
public class TypicalFood {

    public static final Food PASTA = new FoodBuilder().withName("Mushroom Pasta").withTags("italian").build();
    public static final Food BURGER = new FoodBuilder().withName("Chicken Burger").withTags("fastfood", "western")
            .build();
    public static final Food FRIES = new FoodBuilder().withName("French Fries").build();
    public static final Food LATTE = new FoodBuilder().withName("Vanilla Latte").withTags("drinks").build();
    public static final Food COKE = new FoodBuilder().withName("Coke").build();
    public static final Food PIZZA = new FoodBuilder().withName("BBQ Pizza").build();
    public static final Food HOTDOG = new FoodBuilder().withName("Hotdog").build();

    // Manually added
    public static final Food MILKSHAKE = new FoodBuilder().withName("Chocolate Milkshake").build();
    public static final Food BAGUETTE = new FoodBuilder().withName("Baguette").build();

    // ADD AFTER INCORPORATING COMMANDS
    //// Manually added - Food's details found in {@code CommandTestUtil}
    //public static final Food AMY = new FoodBuilder().withName(VALID_NAME_AMY).withTags(VALID_TAG_FRIEND).build();
    //public static final Food BOB = new FoodBuilder().withName(VALID_NAME_BOB)
    //        .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_PIZZA = "pizza"; // A keyword that matches PIZZA

    private TypicalFood() {} // prevents instantiation

    /**
     * Returns an {@code FoodBook} with all the typical food.
     */
    public static FoodBook getTypicalFoodBook() {
        FoodBook ab = new FoodBook();
        for (Food food : getTypicalFood()) {
            ab.addFood(food);
        }
        return ab;
    }

    public static List<Food> getTypicalFood() {
        return new ArrayList<>(Arrays.asList(PASTA, BURGER, FRIES, LATTE, COKE, PIZZA, HOTDOG));
    }
}
