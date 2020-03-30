package seedu.eylah.diettracker.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.eylah.commons.exceptions.IllegalValueException;
import seedu.eylah.diettracker.model.FoodBook;
import seedu.eylah.diettracker.model.ReadOnlyFoodBook;
import seedu.eylah.diettracker.model.food.Food;

/**
 * An Immutable FoodBook that is serializable to JSON format.
 */
@JsonRootName(value = "foodbook")
class JsonSerializableFoodBook {

    public static final String MESSAGE_DUPLICATE_FOOD = "Food list contains duplicate food(s).";

    private final List<JsonAdaptedFood> foods = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableFoodBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableFoodBook(@JsonProperty("foods") List<JsonAdaptedFood> foods) {
        this.foods.addAll(foods);
    }

    /**
     * Converts a given {@code ReadOnlyFoodBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableFoodBook}.
     */
    public JsonSerializableFoodBook(ReadOnlyFoodBook source) {
        foods.addAll(source.getFoodList().stream().map(JsonAdaptedFood::new).collect(Collectors.toList()));
    }

    /**
     * Converts this food book into the model's {@code FoodBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public FoodBook toModelType() throws IllegalValueException {
        FoodBook foodBook = new FoodBook();
        for (JsonAdaptedFood jsonAdaptedFood : foods) {
            Food food = jsonAdaptedFood.toModelType();
            if (foodBook.hasFood(food)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_FOOD);
            }
            foodBook.addFood(food);
        }
        return foodBook;
    }

}
