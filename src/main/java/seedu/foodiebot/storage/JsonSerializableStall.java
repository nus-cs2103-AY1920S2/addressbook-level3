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
import seedu.foodiebot.model.canteen.Stall;

/** An Immutable AddressBook that is serializable to JSON format. */
@JsonRootName(value = "stalls")
class JsonSerializableStall {

    public static final String MESSAGE_DUPLICATE_STALL =
            "Stall list contains duplicate stalls(s).";

    private final List<JsonAdaptedStall> stalls = new ArrayList<>();

    /** Constructs a {@code JsonSerializableAddressBook} with the given stalls. */
    @JsonCreator
    public JsonSerializableStall(@JsonProperty("stalls") List<JsonAdaptedStall> stalls) {
        this.stalls.addAll(stalls);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code
     *     JsonSerializableAddressBook}.
     */
    public JsonSerializableStall(ReadOnlyFoodieBot source) {
        stalls.addAll(
                source.getStallList().stream()
                        .map(JsonAdaptedStall::new)
                        .collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public FoodieBot toModelType() throws IllegalValueException {
        FoodieBot foodieBot = new FoodieBot();
        for (JsonAdaptedStall jsonAdaptedStall : stalls) {
            Stall stall = jsonAdaptedStall.toModelType();
            if (foodieBot.hasStall(stall)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_STALL);
            }

            foodieBot.addStall(stall);
        }
        return foodieBot;
    }
}
