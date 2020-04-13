package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.Skill;
import seedu.address.model.item.field.Level;
import seedu.address.model.item.field.Name;
import seedu.address.model.tag.Tag;


/**
 * Jackson-friendly version of {@link Skill}.
 */
public class JsonAdaptedSkill {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Skill's %s field is missing!";

    private final String name;
    private final String id;
    private final String level;

    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedSkill} with the given details.
     */
    @JsonCreator
    public JsonAdaptedSkill(@JsonProperty("name") String name, @JsonProperty("id") String id,
                                 @JsonProperty("level") String level, @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.id = id;
        this.level = level;
        if (tags != null) {
            this.tagged.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Skill} into this class for Jackson use.
     */
    public JsonAdaptedSkill(Skill skill) {
        this.name = skill.getName().fullName;
        this.id = String.valueOf(skill.getId());
        this.level = skill.getLevel().toString();
        tagged.addAll(skill.getTags().stream().map(JsonAdaptedTag::new).collect(Collectors.toList()));

    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Skill} object.
     */
    public Skill toModelType() throws IllegalValueException {
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

        if (level == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Level.class.getSimpleName()));
        }
        Level modelLevel;
        try {
            modelLevel = Level.valueOf(level);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(Level.MESSAGE_CONSTRAINTS);
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

        return new Skill(modelName, modelLevel, Set.copyOf(tags), modelId);

    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof JsonAdaptedSkill
                && name.equals(((JsonAdaptedSkill) other).name)
                && level.equals(((JsonAdaptedSkill) other).level)
                && tagged.containsAll(((JsonAdaptedSkill) other).tagged)
                && ((JsonAdaptedSkill) other).tagged.containsAll(tagged));
    }
}
