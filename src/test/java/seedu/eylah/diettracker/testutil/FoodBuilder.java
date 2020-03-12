package seedu.eylah.diettracker.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.eylah.addressbook.model.util.SampleDataUtil;
import seedu.eylah.diettracker.model.food.Calories;
import seedu.eylah.diettracker.model.food.Food;
import seedu.eylah.diettracker.model.food.Name;
import seedu.eylah.diettracker.model.tag.Tag;

/**
 * A utility class to help with building Food objects.
 */
public class FoodBuilder {

    public static final String DEFAULT_NAME = "French Fries";
    public static final long DEFAULT_CALORIES = 300;

    private Name name;
    private Calories calories;
    private Set<Tag> tags;

    public FoodBuilder() {
        name = new Name(DEFAULT_NAME);
        calories = new Calories(DEFAULT_CALORIES);
        tags = new HashSet<>();
    }

    /**
     * Initializes the FoodBuilder with the data of {@code foodToCopy}.
     */
    public FoodBuilder(Food foodToCopy) {
        name = foodToCopy.getName();
        calories = foodToCopy.getCalories();
        tags = new HashSet<>(foodToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Food} that we are building.
     */
    public FoodBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Food} that we are building.
     */
    public FoodBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Builds a food.
     *
     * @return a food.
     */

    public Food build() {
        return new Food(name, calories, tags);
    }

}
