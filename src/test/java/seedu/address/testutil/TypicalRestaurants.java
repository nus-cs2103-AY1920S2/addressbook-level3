package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.RestaurantBook;
import seedu.address.model.restaurant.Restaurant;

/**
 * A utility class containing a list of {@Restaurant} objects to be used in tests
 */
public class TypicalRestaurants {
    public static final Restaurant AMEENS = new RestaurantBuilder().withName("Ameens")
            .withLocation("Clementi").withHours("0900:2300").withPrice("$")
            .withCuisine("Indian").withVisit("Yes").build();
    public static final Restaurant SWEE_CHOON = new RestaurantBuilder().withName("Swee Choon")
            .withLocation("Lavender").withHours("1800:0600").withPrice("$")
            .withCuisine("Dim Sum").withVisit("Yes").build();
    public static final Restaurant HAI_DI_LAO = new RestaurantBuilder().withName("Hai Di Lao")
            .withLocation("Jurong East").withHours("2230:0600").withPrice("$$$")
            .withCuisine("Chinese steamboat").withVisit("No").build();
    public static final Restaurant MCD = new RestaurantBuilder().withName("Mcdonalds")
            .withLocation("Tampines").withHours("0000:0000").withPrice("$")
            .withCuisine("Fast Food").build();
    public static final Restaurant TIAN_TIAN = new RestaurantBuilder().withName("Tian Tian Chicken Rice")
            .withLocation("Clementi").withHours("1000:2000").withPrice("$").withCuisine("Chinese")
            .withVisit("Yes").build();
    public static final Restaurant KOH_GRILL = new RestaurantBuilder().withName("Koh Grill")
            .withLocation("Orchard").withHours("1130:2200").withPrice("$$").withCuisine("Japanese")
            .withVisit("No").build();

    //Manually added
    public static final Restaurant KFC = new RestaurantBuilder().withName("KFC").withLocation("Clementi")
            .withHours("0800:2230").withPrice("$").withCuisine("Fast Food")
            .withVisit("Yes").build();

    private TypicalRestaurants() {};

    /**
     * Returns a RestaurantBook with all the typical restaurants in loaded.
     */
    public static RestaurantBook getTypicalRestaurantBook() {
        RestaurantBook rb = new RestaurantBook();
        for (Restaurant restaurant : getTypicalRestaurants()) {
            rb.addRestaurant(restaurant);
        }
        return rb;
    }

    public static List<Restaurant> getTypicalRestaurants() {
        return new ArrayList<>(Arrays.asList(AMEENS, SWEE_CHOON, HAI_DI_LAO,
                MCD, TIAN_TIAN, KOH_GRILL));
    }
}
