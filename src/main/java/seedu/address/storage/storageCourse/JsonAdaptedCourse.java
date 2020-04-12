package seedu.address.storage.storageCourse;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelCourse.Course;
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
class JsonAdaptedCourse {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Courses's %s field is missing!";

    private final String name;
    private final String courseID;
    private final String amount;
    private final String assignedStaff;
    private final List<JsonCourseAdaptedID> assignedStudentsID = new ArrayList<>();
    private final List<JsonCourseAdaptedID> assignedAssignmentsID = new ArrayList<>();
    private final List<JsonCourseAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedCourse(@JsonProperty("name") String name,
                             @JsonProperty("courseID") String courseID,
                             @JsonProperty("amount") String amount,
                             @JsonProperty("assignedStaff") String assignedStaff,
                             @JsonProperty("assignedStudentsID") List<JsonCourseAdaptedID> assignedStudentsID,
                             @JsonProperty("assignedAssignmentsID") List<JsonCourseAdaptedID> assignedAssignmentsID,
                             @JsonProperty("tagged") List<JsonCourseAdaptedTag> tagged) {
        this.name = name;
        this.courseID = courseID;
        this.amount = amount;

        this.assignedStaff = assignedStaff;

        if (assignedStudentsID != null) {
            this.assignedStudentsID.addAll(assignedStudentsID);
        }
        if (assignedAssignmentsID != null) {
            this.assignedAssignmentsID.addAll(assignedAssignmentsID);
        }
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Assignment} into this class for Jackson use.
     */
    public JsonAdaptedCourse(Course source) {
        name = source.getName().fullName;
        courseID = source.getId().value;
        amount = source.getAmount().value;
        ID assignedStaffID = source.getAssignedStaffID();
        String assignedStaffTemp = "";
        if (assignedStaffID != null) {
            assignedStaffTemp = assignedStaffID.toString();
        }
        assignedStaff = assignedStaffTemp;
        assignedStudentsID.addAll(source.getAssignedStudentsID().stream()
                .map(JsonCourseAdaptedID::new)
                .collect(Collectors.toList()));
        assignedAssignmentsID.addAll(source.getAssignedAssignmentsID().stream()
                .map(JsonCourseAdaptedID::new)
                .collect(Collectors.toList()));
        tagged.addAll(source.getTags().stream()
                .map(JsonCourseAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted Assignment object into the model's {@code Assignment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     *                               Assignment.
     */
    public Course toModelType() throws IllegalValueException, CommandException {
        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (courseID == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, ID.class.getSimpleName()));
        }
        if (!ID.isValidId(courseID)) {
            throw new IllegalValueException(ID.MESSAGE_CONSTRAINTS);
        }
        final ID modelId = new ID(courseID);

        if (amount == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(amount)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }
        final Amount modelAmount = new Amount(amount);

        if (assignedStaff == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, AssignedStaff.class.getSimpleName()));
        }
        if (!AssignedStaff.isValidAssignedStaff(assignedStaff)) {
            throw new IllegalValueException(AssignedStaff.MESSAGE_CONSTRAINTS);
        }
        final ID modelAssignedStaff = new ID(assignedStaff);

        final List<ID> CourseAssignedStudentsID = new ArrayList<>();
        for (JsonCourseAdaptedID id : assignedStudentsID) {
            CourseAssignedStudentsID.add(id.toModelType());
        }
        final Set<ID> modelAssignedStudentsID = new HashSet<>(CourseAssignedStudentsID);

        final List<ID> CourseAssignedAssignmentsID = new ArrayList<>();
        for (JsonCourseAdaptedID id : assignedAssignmentsID) {
            CourseAssignedAssignmentsID.add(id.toModelType());
        }
        final Set<ID> modelAssignedAssignmentsID = new HashSet<>(CourseAssignedAssignmentsID);


        final List<Tag> CourseTags = new ArrayList<>();
        for (JsonCourseAdaptedTag tag : tagged) {
            CourseTags.add(tag.toModelType());
        }
        final Set<Tag> modelTags = new HashSet<>(CourseTags);

        Course courseReadFromFile = new Course(modelName, modelId, modelAmount, modelTags);

        courseReadFromFile.addStaff(modelAssignedStaff);
        courseReadFromFile.addStudents(modelAssignedStudentsID);
        courseReadFromFile.addAssignments(modelAssignedAssignmentsID);

        return courseReadFromFile;
    }

}
