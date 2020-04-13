package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.Project;
import seedu.address.model.item.field.Description;
import seedu.address.model.item.field.Name;
import seedu.address.model.item.field.Time;
import seedu.address.model.item.field.Website;
import seedu.address.model.tag.Tag;


/**
 * Jackson-friendly version of {@link Project}.
 */
public class JsonAdaptedProject {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Project's %s field is missing!";

    private final String name;
    private final String id;
    private final String website;
    private final String time;
    private final String description;

    private final List<JsonAdaptedTag> tagged = new ArrayList<>();


    /**
     * Constructs a {@code JsonAdaptedProject} with the given details.
     */
    @JsonCreator
    public JsonAdaptedProject(@JsonProperty("name") String name, @JsonProperty("id") String id,
                              @JsonProperty("time") String time, @JsonProperty("website") String website,
                              @JsonProperty("description") String description,
                              @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.id = id;
        this.website = website;
        this.time = time;
        this.description = description;
        if (tags != null) {
            this.tagged.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Project} into this class for Jackson use.
     */
    public JsonAdaptedProject(Project project) {
        this.name = project.getName().fullName;
        this.id = String.valueOf(project.getId());
        this.time = project.getTime().toString();
        this.website = project.getWebsite().toString();
        this.description = project.getDescription().toString();
        tagged.addAll(project.getTags().stream().map(JsonAdaptedTag::new).collect(Collectors.toList()));

    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Project} object.
     */
    public Project toModelType() throws IllegalValueException {
        final List<Tag> tags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            tags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }
        if (!Time.isValidTime(time)) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }
        final Time modelTime = new Time(time);

        if (website == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Website.class.getSimpleName()));
        }
        if (!Website.isValidWebsite(website)) {
            throw new IllegalValueException(Website.MESSAGE_CONSTRAINTS);
        }
        final Website modelWebsite = new Website(website);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Id"));
        }

        final int modelId;
        try {
            modelId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new IllegalValueException("The id field can only be an integer.");
        }
        if (modelId < 0) {
            throw new IllegalValueException("The id field must not be negative.");
        }

        return new Project(modelName, modelTime, modelWebsite, modelDescription, Set.copyOf(tags), modelId);

    }


    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof JsonAdaptedProject
                && name.equals(((JsonAdaptedProject) other).name)
                && time.equals(((JsonAdaptedProject) other).time)
                && website.equals(((JsonAdaptedProject) other).website)
                && description.equals(((JsonAdaptedProject) other).description)
                && tagged.containsAll(((JsonAdaptedProject) other).tagged)
                && ((JsonAdaptedProject) other).tagged.containsAll(tagged));
    }
}
