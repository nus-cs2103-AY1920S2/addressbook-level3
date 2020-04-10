package seedu.eylah.diettracker.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.eylah.commons.exceptions.IllegalValueException;
import seedu.eylah.diettracker.model.Myself;
import seedu.eylah.diettracker.model.ReadOnlyMyself;
import seedu.eylah.diettracker.model.self.Self;

/**
 * An Immutable Myself that is serializable to JSON format.
 */
@JsonRootName(value = "myself")
class JsonSerializableMyself {

    private final JsonAdaptedSelf self;

    /**
     * Constructs a {@code JsonSerializableMyself} with the given persons.
     */
    @JsonCreator
    public JsonSerializableMyself(@JsonProperty("self") JsonAdaptedSelf self) {
        this.self = self;
    }

    /**
     * Converts a given {@code ReadOnlyMyself} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableMyself}.
     */
    public JsonSerializableMyself(ReadOnlyMyself source) {
        this.self = new JsonAdaptedSelf(source.getHeight(), source.getWeight(), source.getMode());
    }

    /**
     * Converts myself (User Details) into the model's {@code Myself} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Myself toModelType() throws IllegalValueException {
        Self self = this.self.toModelType();
        Myself myself = new Myself(self);
        return myself;
    }

}
