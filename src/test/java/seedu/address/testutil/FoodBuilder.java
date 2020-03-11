package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.food.Calories;
import seedu.address.model.food.Food;
import seedu.address.model.food.Name;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

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
        tags = new HashSet<>(foodToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public FoodBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public FoodBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Builds a person.
     *
     * @return a Person.
     */

    public Food build() {
        return new Food(name, calories, tags);
    }

}
