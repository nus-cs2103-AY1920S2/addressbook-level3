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

    private final float height;
    private final float weight;
    private final String mode;


    /**
     * Constructs a {@code JsonAdaptedSelf} with the given user metric details.
     */
    @JsonCreator
    public JsonAdaptedSelf(@JsonProperty("height") float height, @JsonProperty("weight") float weight,
                           @JsonProperty("mode") String mode) {
        this.height = height;
        this.weight = weight;
        this.mode = mode;
    }

    /**
     * Converts a given {@code Self} into this class for Jackson use.
     */
    public JsonAdaptedSelf(Height height, Weight weight, Mode mode) {
        this.height = height.getHeightFloat();
        this.weight = weight.getWeightFloat();
        this.mode = mode.name();
    }

    /**
     * Converts this Jackson-friendly adapted self object into the model's {@code Self} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Self.
     */
    public Self toModelType() throws IllegalValueException {
        Self self = new Self(new Height(height), new Weight(weight));
        Mode mode = Mode.MAINTAIN;
        if (this.mode.equals("LOSS")) {
            mode = Mode.LOSS;
        } else if (this.mode.equals("GAIN")) {
            mode = Mode.GAIN;
        }
        self.setMode(mode);
        return self;
    }
}
