package seedu.address.storage.storageAssignments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.modelAssignment.Assignment;
import seedu.address.model.modelObjectTags.*;
import seedu.address.model.tag.Tag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedAssignment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Assignment's %s field is missing!";
    private final String name;
    private final String assignmentID;
    private final String courseID;
    private final String deadline;
    private final List<JsonAssignmentAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedAssignment(@JsonProperty("name") String name,
                                 @JsonProperty("assignmentID") String assignmentID, @JsonProperty("deadline") String deadline, @JsonProperty("courseID") String courseID,
                                 @JsonProperty("tagged") List<JsonAssignmentAdaptedTag> tagged) {
        this.name = name;
        this.assignmentID = assignmentID;
        this.courseID = courseID;
        this.deadline = deadline;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Assignment} into this class for Jackson use.
     */
    public JsonAdaptedAssignment(Assignment source) {
        name = source.getName().fullName;
        assignmentID = source.getId().value;
        if (source.isAssignedToCourse()) {
            courseID = source.getAssignedCourseID().value;
        } else {
            courseID = null;
        }
        deadline = source.getDeadline().toString();
        tagged.addAll(source.getTags().stream()
                .map(JsonAssignmentAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted Assignment object into the model's {@code Assignment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     *                               Assignment.
     */
    public Assignment toModelType() throws IllegalValueException {
        final List<Tag> AssignmentTags = new ArrayList<>();
        for (JsonAssignmentAdaptedTag tag : tagged) {
            AssignmentTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (assignmentID == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, ID.class.getSimpleName()));
        }
        if (!ID.isValidId(assignmentID)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final ID modelId = new ID(assignmentID);

        if (deadline == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Deadline.class.getSimpleName())
            );
        }
        if (!Deadline.isValidDeadline(deadline)) {
            throw new IllegalValueException(Deadline.MESSAGE_CONSTRAINTS);
        }
        ;

        final Date modelDeadline = new Date(deadline);

        final Set<Tag> modelTags = new HashSet<>(AssignmentTags);

        // if assignment is assigned to course already
        if (courseID == null) {
            return new Assignment(modelName, modelId, modelDeadline, modelTags);
        } else {
            final ID modelCourseID = new ID(courseID);
            return new Assignment(modelName, modelId, modelCourseID, modelDeadline, modelTags);
        }
    }

}
