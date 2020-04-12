package seedu.address.storage.storageStudent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.modelObjectTags.Gender;
import seedu.address.model.modelObjectTags.ID;
import seedu.address.model.modelObjectTags.Name;
import seedu.address.model.modelStudent.Student;
import seedu.address.model.tag.Tag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Jackson-friendly version of {@link Student}.
 */
class JsonAdaptedStudent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Student's %s field is missing!";

    private final String name;
    private final String studentID;
    private final String gender;
    private final List<JsonStudentAdaptedID> assignedCoursesID = new ArrayList<>();
    private final List<JsonStudentAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedStudent(@JsonProperty("name") String name,
                              @JsonProperty("studentID") String studentID,
                              @JsonProperty("gender") String gender,
                              @JsonProperty("assignedCourses") String assignedCourses,
                              @JsonProperty("assignedCoursesID") List<JsonStudentAdaptedID> assignedCoursesID,
                              @JsonProperty("tagged") List<JsonStudentAdaptedTag> tagged) {
        this.name = name;
        this.studentID = studentID;
        this.gender = gender;
        if (assignedCoursesID != null) {
            this.assignedCoursesID.addAll(assignedCoursesID);
        }
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Student} into this class for Jackson use.
     */
    public JsonAdaptedStudent(Student source) {
        name = source.getName().fullName;
        studentID = source.getId().value;
        gender = source.getGender().value;
        assignedCoursesID.addAll(source.getAssignedCoursesID().stream()
                .map(JsonStudentAdaptedID::new)
                .collect(Collectors.toList()));
        tagged.addAll(source.getTags().stream()
                .map(JsonStudentAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted course object into the model's {@code Student} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     *                               course.
     */
    public Student toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (studentID == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, ID.class.getSimpleName()));
        }
        if (!ID.isValidId(studentID)) {
            throw new IllegalValueException(ID.MESSAGE_CONSTRAINTS);
        }
        final ID modelID = new ID(studentID);

        if (gender == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Gender.class.getSimpleName()));
        }
        if (!Gender.isValidGender(gender)) {
            throw new IllegalValueException(Gender.MESSAGE_CONSTRAINTS);
        }
        final Gender modelGender = new Gender(gender);

        final List<ID> StudentAssignedCoursesID = new ArrayList<>();
        for (JsonStudentAdaptedID id : assignedCoursesID) {
            StudentAssignedCoursesID.add(id.toModelType());
        }
        final Set<ID> modelAssignedCoursesID = new HashSet<>(StudentAssignedCoursesID);

        final List<Tag> courseTags = new ArrayList<>();
        for (JsonStudentAdaptedTag tag : tagged) {
            courseTags.add(tag.toModelType());
        }

        final Set<Tag> modelTags = new HashSet<>(courseTags);
        return new Student(modelName, modelID, modelGender, modelAssignedCoursesID, modelTags);
    }

}
