package seedu.eylah.diettracker.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.eylah.commons.exceptions.IllegalValueException;
import seedu.eylah.diettracker.model.Mode;
import seedu.eylah.diettracker.model.self.Height;
import seedu.eylah.diettracker.model.self.Self;
import seedu.eylah.diettracker.model.self.Weight;

/**
 * Jackson-friendly version of {@link Self}.
 */
class JsonAdaptedSelf {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Self's %s field is missing!";

    private final Height height;
    private final Weight weight;
    private final Mode mode;


    /**
     * Constructs a {@code JsonAdaptedSelf} with the given user metric details.
     */
    @JsonCreator
    public JsonAdaptedSelf(@JsonProperty("height") Height height, @JsonProperty("weight") Weight weight,
                           @JsonProperty("mode") Mode mode) {
        this.height = new Height(height.toString(), height.getHeightFloat());
        this.weight = new Weight(weight.toString(), weight.getWeightFloat());
        this.mode = mode;

        //Self.setWeight(this.weight);
        //Self.setHeight(this.height);
        //Self.setMode(this.mode);
    }

    /**
     * Converts a given {@code Self} into this class for Jackson use.
     */
    public JsonAdaptedSelf(Self source) {
        height = Self.getHeight();
        weight = Self.getWeight();
        mode = Self.getMode();
    }

    /**
     * Converts this Jackson-friendly adapted self object into the model's {@code Self} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Self.
     */
    public Self toModelType() throws IllegalValueException {
        Self self = new Self(height, weight);
        Self.setMode(mode);
        //Self.setHeight(height);
        //Self.setWeight(weight);
        //Self.setMode(mode);
        return self;
    }
}
