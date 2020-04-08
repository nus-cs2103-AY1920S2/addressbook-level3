package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AssignmentSchedule;
import seedu.address.model.ReadOnlyAssignmentSchedule;
import seedu.address.model.assignment.Assignment;

/**
 * An unmodifiable scheduler that is serializable in JSON format.
 */
@JsonRootName(value = "scheduler")
public class JsonSerializableAssignmentSchedule {
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "Assignment list contains duplicate assignments.";

    private final List<JsonAdaptedAssignment> assignments = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given assignments.
     */
    @JsonCreator
    public JsonSerializableAssignmentSchedule(@JsonProperty("assignments") List<JsonAdaptedAssignment> assignments) {
        this.assignments.addAll(assignments);
    }

    /**
     * Converts a given {@code ReadOnlyAssignmentSchedule} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAssignmentSchedule}.
     */
    public JsonSerializableAssignmentSchedule(ReadOnlyAssignmentSchedule source) {
        assignments.addAll(source.getAssignmentsList().stream()
                .map(JsonAdaptedAssignment::new).collect(Collectors.toList()));
    }

    /**
     * Converts this scheduler into the model's {@code AssignmentSchedule} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AssignmentSchedule toModelType() throws IllegalValueException {
        AssignmentSchedule assignmentSchedule = new AssignmentSchedule();
        for (JsonAdaptedAssignment jsonAdaptedAssignment : assignments) {
            Assignment assignment = jsonAdaptedAssignment.toModelType();

            if (assignmentSchedule.hasAssignment(assignment)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ASSIGNMENT);
            }

            assignmentSchedule.addAssignment(assignment);
        }
        return assignmentSchedule;
    }
}
