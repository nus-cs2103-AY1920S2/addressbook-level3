package seedu.eylah.diettracker.model.util;

import seedu.eylah.diettracker.model.FoodBook;
import seedu.eylah.diettracker.model.ReadOnlyFoodBook;
import seedu.eylah.diettracker.model.food.Calories;
import seedu.eylah.diettracker.model.food.Food;
import seedu.eylah.diettracker.model.food.Name;
import seedu.eylah.diettracker.model.tag.Tag;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static Food[] getSampleFoods() {
        return new Food[] {
            new Food(new Name("Burger"), new Calories(290), getTagSet("fastfood")),
            new Food(new Name("Fries"), new Calories(120), getTagSet("fastfood")),
            new Food(new Name("Coke"), new Calories(150), getTagSet("fastfood", "drinks")),
            new Food(new Name("Hotdog"), new Calories(230), getTagSet("fastfood")),
            new Food(new Name("Spaghetti"), new Calories(450), getTagSet("italian")),
            new Food(new Name("Sandwich"), new Calories(300), getTagSet("healthy"))
        };
    }

    public static ReadOnlyFoodBook getSampleFoodBook() {
        FoodBook sampleFb = new FoodBook();
        for (Food sampleFood: getSampleFoods()) {
            sampleFb.addFood(sampleFood);
        }
        return sampleFb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
