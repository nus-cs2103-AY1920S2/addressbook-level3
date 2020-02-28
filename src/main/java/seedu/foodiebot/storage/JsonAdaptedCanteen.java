package seedu.foodiebot.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.foodiebot.commons.exceptions.IllegalValueException;
import seedu.foodiebot.model.canteen.Canteen;
import seedu.foodiebot.model.canteen.CanteenStub;
import seedu.foodiebot.model.canteen.Name;
import seedu.foodiebot.model.tag.Tag;



/** Jackson-friendly version of {@link Canteen}. */
class JsonAdaptedCanteen {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Canteen's %s field is missing!";

    private final String name;
    private final int numberOfStalls;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /** Constructs a {@code JsonAdaptedCanteen} with the given person details. */
    @JsonCreator
    public JsonAdaptedCanteen(
            @JsonProperty("name") String name,
            @JsonProperty("distance") String distance,
            @JsonProperty("numberOfStalls") String numberOfStalls,
            @JsonProperty("nearestBlockName") String nearestBlockName,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.numberOfStalls = Integer.parseInt(numberOfStalls);
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /** Converts a given {@code Canteen} into this class for Jackson use. */
    public JsonAdaptedCanteen(Canteen source) {
        name = source.getName().fullName;
        numberOfStalls = source.getNumberOfStalls();
        tagged.addAll(
                source.getTags().stream().map(JsonAdaptedTag::new).collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Canteen} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     *     person.
     */
    public CanteenStub toModelType() throws IllegalValueException {
        final List<Tag> canteenTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            canteenTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        final int modelNumberOfStalls = numberOfStalls;

        final Set<Tag> modelTags = new HashSet<>(canteenTags);
        return new CanteenStub(modelName, modelNumberOfStalls, modelTags);
    }
}
