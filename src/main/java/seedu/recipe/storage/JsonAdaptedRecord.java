package seedu.recipe.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.recipe.commons.exceptions.IllegalValueException;
import seedu.recipe.model.Date;
import seedu.recipe.model.cooked.Record;
import seedu.recipe.model.goal.Goal;
import seedu.recipe.model.recipe.Name;

/**
 * Jackson-friendly version of {@link Record}.
 */
class JsonAdaptedRecord {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Record's %s field is missing!";

    private final String name;
    private final String date;
    private final List<JsonAdaptedGoal> goals = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedRecord} with the given recipe details.
     */
    @JsonCreator
    public JsonAdaptedRecord(@JsonProperty("name") String name, @JsonProperty("date") String date,
                             @JsonProperty("goals") List<JsonAdaptedGoal> goals) {
        this.name = name;
        this.date = date;
        if (goals != null) {
            this.goals.addAll(goals);
        }
    }

    /**
     * Converts a given {@code Record} into this class for Jackson use.
     */
    public JsonAdaptedRecord(Record source) {
        name = source.getName().fullName;
        date = source.getDate().toStringForJson();
        goals.addAll(source.getGoals().stream()
                .map(JsonAdaptedGoal::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted record object into the model's {@code Record} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted recipe.
     */
    public Record toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);
        final Date modelDate = new Date(date);
        final List<Goal> recordGoals = new ArrayList<>();
        for (JsonAdaptedGoal goal : goals) {
            recordGoals.add(goal.toModelType());
        }
        final Set<Goal> modelGoals = new HashSet<>(recordGoals);

        return new Record(modelName, modelDate, modelGoals);
    }

}
