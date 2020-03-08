package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Description;
import seedu.address.model.person.Done;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Priority;
import seedu.address.model.tag.Tag;

/** Jackson-friendly version of {@link Person}. */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String priority;
    private final String email;
    private final String description;
    private final String done;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /** Constructs a {@code JsonAdaptedPerson} with the given person details. */
    @JsonCreator
    public JsonAdaptedPerson(
            @JsonProperty("name") String name,
            @JsonProperty("priority") String priority,
            @JsonProperty("email") String email,
            @JsonProperty("description") String description,
            @JsonProperty("done") String done,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.priority = priority;
        this.email = email;
        this.description = description;
        this.done = done;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /** Converts a given {@code Person} into this class for Jackson use. */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        priority = source.getPriority().value;
        email = source.getEmail().value;
        description = source.getDescription().value;
        done = source.getDone().toString();
        tagged.addAll(
                source.getTags().stream().map(JsonAdaptedTag::new).collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     *     person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (priority == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Priority.class.getSimpleName()));
        }
        if (!Priority.isValidPriority(priority)) {
            throw new IllegalValueException(Priority.MESSAGE_CONSTRAINTS);
        }
        final Priority modelPriority = new Priority(priority);

        if (email == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (description == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelAddress = new Description(description);

        if (!Done.isValidDone(done)) {
            throw new IllegalValueException(Done.MESSAGE_CONSTRAINTS);
        }
        final Done modelDone = new Done(done);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Person(modelName, modelPriority, modelEmail, modelAddress, modelDone, modelTags);
    }
}
