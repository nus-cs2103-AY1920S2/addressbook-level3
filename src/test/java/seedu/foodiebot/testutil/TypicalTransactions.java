package seedu.foodiebot.testutil;

import static seedu.foodiebot.model.util.SampleDataUtil.getTagSet;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.foodiebot.model.FoodieBot;
import seedu.foodiebot.model.rating.Rating;
import seedu.foodiebot.model.rating.Review;
import seedu.foodiebot.model.transaction.PurchasedFood;

/**
 * A utility class containing a list of {@code PurchasedFood} objects to be used in tests.
 */
public class TypicalTransactions {

    public static final PurchasedFood ROASTED_CHICKEN_RICE = new PurchasedFood(
            "Roasted Chicken Rice",
            4,
            "Roasted Meat and Chicken Rice",
            "Roasted Chicken Rice.png",
            1,
            "Nus Flavors",
            "Duck and Chicken Rice",
            getTagSet("asian", "cheap", "rice"),
            LocalDate.now(),
            LocalTime.now(),
            new Rating(),
            new Review());

    public static final PurchasedFood BEE_HOON = new PurchasedFood(
            "Fish Bee Hoon",
            4,
            "Been Hoon with Fish",
            "Fish Bee Hoon.png",
            2,
            "Nus Flavors",
            "Five Grains Bee Hoon",
            getTagSet("asian", "cheap", "noodle"),
            LocalDate.now(),
            LocalTime.now(),
            new Rating(),
            new Review()
    );

    private TypicalTransactions() {
    } // prevents instantiation

    /**
     * Returns a {@code FoodieBot} with typical transactions.
     */
    public static FoodieBot getTypicalFoodieBot() {
        FoodieBot fb = new FoodieBot();
        for (PurchasedFood pf : getTypicalTransactions()) {
            fb.addPurchasedFood(pf);
        }
        return fb;
    }

    /**
     * Returns a list of {@code PurchasedFood}.
     */
    public static List<PurchasedFood> getTypicalTransactions() {
        return new ArrayList<>(Arrays.asList(ROASTED_CHICKEN_RICE, BEE_HOON));
    }


}
