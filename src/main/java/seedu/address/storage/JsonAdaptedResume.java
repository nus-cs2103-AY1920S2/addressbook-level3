package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.Resume;
import seedu.address.model.item.field.Name;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Resume}.
 */
public class JsonAdaptedResume {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Resume's %s field is missing!";

    private final String name;
    private final String id;

    private final List<String> containedInternshipIds = new ArrayList<>();
    private final List<String> containedProjectIds = new ArrayList<>();
    private final List<String> containedSkillIds = new ArrayList<>();
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedResume} with the given details.
     */
    @JsonCreator
    public JsonAdaptedResume(@JsonProperty("name") String name, @JsonProperty("id") String id,
                             @JsonProperty("internships") List<String> internshipIds,
                             @JsonProperty("projects") List<String> projectIds,
                             @JsonProperty("skills") List<String> skillIds,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.id = id;
        if (tags != null) {
            this.tagged.addAll(tags);
        }
        if (internshipIds != null) {
            this.containedInternshipIds.addAll(internshipIds);
        }
        if (projectIds != null) {
            this.containedProjectIds.addAll(projectIds);
        }
        if (skillIds != null) {
            this.containedSkillIds.addAll(skillIds);
        }
    }

    /**
     * Converts a given {@code Resume} into this class for Jackson use.
     */
    public JsonAdaptedResume(Resume res) {
        this.name = res.getName().fullName;
        this.id = String.valueOf(res.getId());
        tagged.addAll(res.getTags().stream().map(JsonAdaptedTag::new).collect(Collectors.toList()));
        containedInternshipIds.addAll(
                res.getInternshipIds().stream().map(String::valueOf).collect(Collectors.toList()));
        containedProjectIds.addAll(
                res.getProjectIds().stream().map(String::valueOf).collect(Collectors.toList()));
        containedSkillIds.addAll(
                res.getSkillIds().stream().map(String::valueOf).collect(Collectors.toList()));

    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Resume} object.
     */
    public Resume toModelType() throws IllegalValueException {
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

        final int modelId = parseId(id);

        Resume resume = new Resume(modelName, modelId, Set.copyOf(tags));

        for (String internship : containedInternshipIds) {
            resume.addInternshipId(parseId(internship));
        }

        for (String project : containedProjectIds) {
            resume.addProjectId(parseId(project));
        }

        for (String skill : containedSkillIds) {
            resume.addSkillId(parseId(skill));
        }

        return resume;
    }

    /**
     * Parses item index from {@code String} to {@code int}.
     * @param index the {@code String} representing the item index.
     * @return the {@code int} representing the item index.
     * @throws IllegalValueException if index is not a non-negative integer.
     */
    private static int parseId(String index) throws IllegalValueException {
        int id;
        if (index == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Id"));
        }
        try {
            id = Integer.parseInt(index);
        } catch (NumberFormatException e) {
            throw new IllegalValueException("The id field can only be an integer.");
        }
        if (id < 0) {
            throw new IllegalValueException("The id field must not be negative.");
        }
        return id;
    }

    @Override
    public boolean equals(Object other) {
        return (this == other)
                || (other instanceof JsonAdaptedResume
                && name.equals(((JsonAdaptedResume) other).name)
                && containedInternshipIds.equals(((JsonAdaptedResume) other).containedInternshipIds)
                && containedProjectIds.equals(((JsonAdaptedResume) other).containedProjectIds)
                && containedSkillIds.equals(((JsonAdaptedResume) other).containedSkillIds)
                && tagged.containsAll(((JsonAdaptedResume) other).tagged))
                && ((JsonAdaptedResume) other).tagged.containsAll(tagged);

    }
}
