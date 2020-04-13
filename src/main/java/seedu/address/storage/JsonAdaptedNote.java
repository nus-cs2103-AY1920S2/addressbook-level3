package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.Note;
import seedu.address.model.item.field.Name;
import seedu.address.model.item.field.Time;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Note}.
 */
public class JsonAdaptedNote {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Note's %s field is missing!";

    private final String name;
    private final String id;
    private final String time;
    private final String isDone;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    @JsonCreator
    public JsonAdaptedNote(@JsonProperty("name") String name, @JsonProperty("id") String id,
                           @JsonProperty("time") String time, @JsonProperty("isDone") String isDone,
                           @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.id = id;
        this.time = time;
        this.isDone = isDone;
        if (tags != null) {
            this.tagged.addAll(tags);
        }
    }

    public JsonAdaptedNote(Note note) {
        this.name = note.getName().fullName;
        this.id = String.valueOf(note.getId());
        this.time = note.getTime().toString();
        this.isDone = String.valueOf(note.isDone());
        tagged.addAll(note.getTags().stream().map(JsonAdaptedTag::new).collect(Collectors.toList()));
    }

    /**
     * Convert Json Note to model-typed Note
     */
    public Note toModelType() throws IllegalValueException {
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

        final boolean modelIsDone;

        if (isDone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Done"));
        }

        // Workaround check to ensure the stored data is indeed either "true" or "false"
        if (isDone.equals(String.valueOf(true)) || isDone.equals(String.valueOf(false))) {
            modelIsDone = Boolean.parseBoolean(isDone);
        } else {
            throw new IllegalValueException("A boolean field can only be true or false.");
        }

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

        return new Note(modelName, modelTime, modelIsDone, Set.copyOf(tags), modelId);
    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof JsonAdaptedNote
                && name.equals(((JsonAdaptedNote) other).name)
                && time.equals(((JsonAdaptedNote) other).time)
                && isDone.equals(((JsonAdaptedNote) other).isDone)
                && tagged.containsAll(((JsonAdaptedNote) other).tagged)
                && ((JsonAdaptedNote) other).tagged.containsAll(tagged));
    }
}
