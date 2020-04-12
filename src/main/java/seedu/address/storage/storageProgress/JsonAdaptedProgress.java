package seedu.address.storage.storageProgress;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelObjectTags.CompositeID;
import seedu.address.model.modelObjectTags.ID;
import seedu.address.model.modelObjectTags.Person;
import seedu.address.model.modelProgress.Progress;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedProgress {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Assignment's %s field is missing!";
    private final String assignmentID;
    private final String studentID;
    private final String isDone;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedProgress(@JsonProperty("assignmentID") String assignmentID, @JsonProperty("studentID") String studentID, @JsonProperty("isDone") String isDone) {
        this.assignmentID = assignmentID;
        this.studentID = studentID;
        this.isDone = isDone;
    }

    /**
     * Converts a given {@code Assignment} into this class for Jackson use.
     */
    public JsonAdaptedProgress(Progress source) {
        assignmentID = source.getId().getAssignmentID().value;
        studentID = source.getId().getStudentID().value;
        if (source.getIsDone()) {
            isDone = Boolean.TRUE.toString();
        } else {
            isDone = Boolean.FALSE.toString();
        }
    }

    /**
     * Converts this Jackson-friendly adapted Assignment object into the model's {@code Assignment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     *                               Assignment.
     */
    public Progress toModelType() throws IllegalValueException, CommandException {
        if (assignmentID == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, ID.class.getSimpleName()));
        }
        if (!ID.isValidId(assignmentID)) {
            throw new IllegalValueException(ID.MESSAGE_CONSTRAINTS);
        }
        final ID modelAssignmentID = new ID(assignmentID);

        if (studentID == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, ID.class.getSimpleName()));
        }
        if (!ID.isValidId(studentID)) {
            throw new IllegalValueException(ID.MESSAGE_CONSTRAINTS);
        }
        final ID modelStudentID = new ID(studentID);

        if (isDone == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Boolean.class.getSimpleName())
            );
        }
        boolean isTrue = Boolean.TRUE.toString().equals(isDone);
        boolean isFalse = Boolean.FALSE.toString().equals(isDone);

        // if its not true and if its not false, means that there's an issue
        if (!isTrue && !isFalse) {
            throw new IllegalValueException("isDone must be in the format of true/false");
        }

        final Boolean modelIsDone = Boolean.valueOf(isDone);

        CompositeID curr = new CompositeID(modelAssignmentID, modelStudentID);

        return new Progress(curr, modelIsDone);
    }

}
