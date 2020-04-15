package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlySchoolworkTracker;
import seedu.address.model.SchoolworkTracker;
import seedu.address.model.assignment.Assignment;

/**
 * An unmodifiable schoolwork tracker that is serializable in JSON format.
 */
@JsonRootName(value = "scheduler")
public class JsonSerializableSchoolworkTracker {
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "Assignment list contains duplicate assignments.";

    private final List<JsonAdaptedAssignment> assignments = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given assignments.
     */
    @JsonCreator
    public JsonSerializableSchoolworkTracker(@JsonProperty("assignments") List<JsonAdaptedAssignment> assignments) {
        this.assignments.addAll(assignments);
    }

    /**
     * Converts a given {@code ReadOnlySchoolworkTracker} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableSchoolworkTracker}.
     */
    public JsonSerializableSchoolworkTracker(ReadOnlySchoolworkTracker source) {
        assignments.addAll(source.getAssignmentsList().stream()
                .map(JsonAdaptedAssignment::new).collect(Collectors.toList()));
    }

    /**
     * Converts this schoolwork tracker into the model's {@code SchoolworkTracker} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public SchoolworkTracker toModelType() throws IllegalValueException {
        SchoolworkTracker schoolworkTracker = new SchoolworkTracker();
        for (JsonAdaptedAssignment jsonAdaptedAssignment : assignments) {
            Assignment assignment = jsonAdaptedAssignment.toModelType();

            if (schoolworkTracker.hasAssignment(assignment)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ASSIGNMENT);
            }

            schoolworkTracker.addAssignment(assignment);
        }
        return schoolworkTracker;
    }
}
