package seedu.eylah.diettracker.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.eylah.diettracker.model.FoodBook;
import seedu.eylah.diettracker.model.Myself;
import seedu.eylah.diettracker.model.ReadOnlyFoodBook;
import seedu.eylah.diettracker.model.ReadOnlyMyself;
import seedu.eylah.diettracker.model.food.Calories;
import seedu.eylah.diettracker.model.food.Date;
import seedu.eylah.diettracker.model.food.Food;
import seedu.eylah.diettracker.model.food.Name;
import seedu.eylah.diettracker.model.tag.Tag;

/**
 * Contains utility methods for populating {@code FoodBook} with sample data.
 */
public class SampleDataUtil {

    public static Food[] getSampleFoods() {
        return new Food[] {
            new Food(new Name("Burger"), new Calories(290), new Date("2020-02-24T02:50:22.279828"),
                    getTagSet("fastfood")),
            new Food(new Name("Fries"), new Calories(120), new Date("2020-03-15T02:50:22.279828"),
                    getTagSet("fastfood")),
            new Food(new Name("Coke"), new Calories(150), new Date("2020-03-20T02:50:22.279828"),
                    getTagSet("fastfood", "drinks")),
            new Food(new Name("Hotdog"), new Calories(230), new Date("2020-03-30T02:50:22.279828"),
                    getTagSet("fastfood")),
            new Food(new Name("Spaghetti"), new Calories(450), new Date("2020-04-01T02:50:22.279828"),
                    getTagSet("italian")),
            new Food(new Name("Sandwich"), new Calories(300), new Date("2020-04-02T02:50:22.279828"),
                    getTagSet("healthy"))
        };
    }

    public static ReadOnlyMyself getSampleMyself() {
        Myself sampleMs = new Myself();
        return sampleMs;
    }

    public static ReadOnlyFoodBook getSampleFoodBook() {
        FoodBook sampleFb = new FoodBook();
        for (Food sampleFood: getSampleFoods()) {
            sampleFb.addFood(sampleFood);
        }
        return sampleFb;
    }

    public static ReadOnlyFoodBook getEmptyFoodBook() {
        FoodBook emptyFb = new FoodBook();
        return emptyFb;
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
