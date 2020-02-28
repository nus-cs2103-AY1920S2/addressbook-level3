package seedu.foodiebot.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.foodiebot.model.FoodieBot;
import seedu.foodiebot.model.canteen.Canteen;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalCanteens {

    // Manually added
    public static final Canteen DECK =
        new CanteenBuilder().withName("The Deck").withNumberOfStalls(0).build();
    public static final Canteen NUSFLAVORS =
        new CanteenBuilder().withName("Nus Flavors").withNumberOfStalls(0).build();

    public static final String KEYWORD_MATCHING_DECK = "Deck"; // A keyword that matches DECK

    private TypicalCanteens() {
    } // prevents instantiation

    /**
     * Returns an {@code FoodieBot} with all the typical canteens.
     */
    public static FoodieBot getTypicalFoodieBot() {
        FoodieBot ab = new FoodieBot();
        for (Canteen canteen : getTypicalCanteens()) {
            ab.addCanteen(canteen);
        }
        return ab;
    }

    public static List<Canteen> getTypicalCanteens() {
        return new ArrayList<>(Arrays.asList(DECK, NUSFLAVORS));
    }
}
