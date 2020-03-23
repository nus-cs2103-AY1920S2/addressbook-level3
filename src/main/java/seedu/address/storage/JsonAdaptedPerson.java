package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.course.CourseName;

/**
 * Jackson-friendly version of {@link Profile}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Profile's %s field is missing!";

    private final String name;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given profile details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name) {
        this.name = name;
    }

    /**
     * Converts a given {@code Profile} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Profile source) {
        name = source.getName().fullName;
    }

    /**
     * Converts this Jackson-friendly adapted profile object into the model's {@code Profile} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted profile.
     */
    public Profile toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);
        final CourseName modelCourseName = new CourseName("Computer Science");

        return new Profile(modelName, modelCourseName, 1, null);
    }

}
