package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyScheduler;
import seedu.address.model.Scheduler;
import seedu.address.model.assignment.Assignment;

/**
 * An unmodifiable scheduler that is serializable in JSON format.
 */
@JsonRootName(value = "scheduler")
public class JsonSerializableScheduler {
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "Assignment list contains duplicate assignments.";

    private final List<JsonAdaptedAssignment> assignments = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given assignments.
     */
    @JsonCreator
    public JsonSerializableScheduler(@JsonProperty("assignments") List<JsonAdaptedAssignment> assignments) {
        this.assignments.addAll(assignments);
    }

    /**
     * Converts a given {@code ReadOnlyScheduler} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableScheduler}.
     */
    public JsonSerializableScheduler(ReadOnlyScheduler source) {
        assignments.addAll(source.getAssignmentsList().stream()
                .map(JsonAdaptedAssignment::new).collect(Collectors.toList()));
    }

    /**
     * Converts this scheduler into the model's {@code Scheduler} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Scheduler toModelType() throws IllegalValueException {
        Scheduler scheduler = new Scheduler();
        for (JsonAdaptedAssignment jsonAdaptedAssignment : assignments) {
            Assignment assignment = jsonAdaptedAssignment.toModelType();

            if (scheduler.hasAssignment(assignment)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ASSIGNMENT);
            }

            scheduler.addAssignment(assignment);
        }
        return scheduler;
    }
}
