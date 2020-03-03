package seedu.foodiebot.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.foodiebot.commons.exceptions.IllegalValueException;
import seedu.foodiebot.model.FoodieBot;
import seedu.foodiebot.model.ReadOnlyFoodieBot;
import seedu.foodiebot.model.canteen.Canteen;

/** An Immutable AddressBook that is serializable to JSON format. */
@JsonRootName(value = "canteens")
class JsonSerializableFoodieBot {

    public static final String MESSAGE_DUPLICATE_CANTEEN =
            "Canteens list contains duplicate canteen(s).";

    private final List<JsonAdaptedCanteen> canteens = new ArrayList<>();
    private final List<JsonAdaptedCanteen> stalls = new ArrayList<>();

    /** Constructs a {@code JsonSerializableAddressBook} with the given canteens. */
    @JsonCreator
    public JsonSerializableFoodieBot(@JsonProperty("canteens") List<JsonAdaptedCanteen> canteens) {
        this.canteens.addAll(canteens);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code
     *     JsonSerializableAddressBook}.
     */
    public JsonSerializableFoodieBot(ReadOnlyFoodieBot source) {
        canteens.addAll(
                source.getCanteenList().stream()
                        .map(JsonAdaptedCanteen::new)
                        .collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public FoodieBot toModelType() throws IllegalValueException {
        FoodieBot foodieBot = new FoodieBot();
        for (JsonAdaptedCanteen jsonAdaptedCanteen : canteens) {
            Canteen canteen = jsonAdaptedCanteen.toModelType();
            if (foodieBot.hasCanteen(canteen)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CANTEEN);
            }

            foodieBot.addCanteen(canteen);
        }
        return foodieBot;
    }
}
