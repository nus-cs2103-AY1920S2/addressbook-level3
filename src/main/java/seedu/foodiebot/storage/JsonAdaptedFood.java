package seedu.foodiebot.storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.foodiebot.commons.exceptions.IllegalValueException;
import seedu.foodiebot.model.canteen.Name;
import seedu.foodiebot.model.food.Food;
import seedu.foodiebot.model.tag.Tag;


/** Jackson-friendly version of {@link Food}. */
class JsonAdaptedFood {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Food's %s field is missing!";

    //Identity fields
    private final String name;
    private final int price;
    private final String description;
    private final String foodImageName;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();
    private final String canteen;
    private final String stallName;
    private final int stallNo;

    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /** Constructs a {@code JsonAdaptedFood} with the given person details. */
    @JsonCreator
    public JsonAdaptedFood(
            @JsonProperty("name") String name,
            @JsonProperty("price") String price,
            @JsonProperty("description") String description,
            @JsonProperty("foodImageName") String foodImageName,
            @JsonProperty("stallNo") String stallNo,
            @JsonProperty("canteen") String canteen,
            @JsonProperty("stallName") String stallName) {
        this.name = name;
        this.price = Integer.parseInt(price);
        this.description = description;
        this.foodImageName = foodImageName;
        this.stallNo = Integer.parseInt(stallNo);
        this.canteen = canteen;
        this.stallName = stallName;
    }

    /** Converts a given {@code Food} into this class for Jackson use. */
    public JsonAdaptedFood(Food source) {
        name = source.getName();
        this.price = source.getPrice();
        this.description = source.getDescription();
        this.foodImageName = source.getFoodImage().toString();
        this.stallNo = source.getStallNo();
        this.canteen = source.getCanteen();
        this.stallName = source.getStallName();
    }

    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings).map(Tag::new).collect(Collectors.toSet());
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Food} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     *     person.
     */
    public Food toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final String modelName = name;

        final String modelStallName = stallName;

        return new Food(modelName, price, description, foodImageName, stallNo,
               canteen, modelStallName, getTagSet("1"));
    }
}
