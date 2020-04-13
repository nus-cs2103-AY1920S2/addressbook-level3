//@@author potatocombat

package tatracker.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tatracker.commons.exceptions.IllegalValueException;
import tatracker.model.group.Group;
import tatracker.model.group.GroupType;
import tatracker.model.student.Matric;
import tatracker.model.student.Student;

/**
 * Jackson-friendly version of {@link Group}.
 */
class JsonAdaptedGroup {
    public static final String MESSAGE_DUPLICATE_STUDENTS = "Group's list of students contains duplicate student(s).";

    private static final String MISSING_FIELD_MESSAGE_FORMAT = "Group's %s field is missing!";

    public static final String MISSING_GROUP_ID = String.format(MISSING_FIELD_MESSAGE_FORMAT, "id");
    public static final String MISSING_GROUP_TYPE = String.format(MISSING_FIELD_MESSAGE_FORMAT, "type");

    private final String id;
    private final String type;
    private final List<JsonAdaptedStudent> students = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedGroup} with the given group details.
     */
    @JsonCreator
    public JsonAdaptedGroup(@JsonProperty("id") String id,
                            @JsonProperty("type") String type,
                            @JsonProperty("students") List<JsonAdaptedStudent> students) {
        this.id = id;
        this.type = type;
        if (students != null) {
            this.students.addAll(students);
        }
    }

    /**
     * Converts a given {@code Group} into this class for Jackson use.
     */
    public JsonAdaptedGroup(Group source) {
        id = source.getIdentifier();
        type = source.getGroupType().name();
        students.addAll(source.getStudentList()
                .stream()
                .map(JsonAdaptedStudent::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted group object into the model's {@code Group} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted group.
     */
    public Group toModelType() throws IllegalValueException {
        // ==== ID ====
        if (id == null) {
            throw new IllegalValueException(MISSING_GROUP_ID);
        }
        if (id.isBlank()) {
            throw new IllegalValueException(Group.CONSTRAINTS_GROUP_CODE);
        }

        // ==== Type ====
        if (type == null) {
            throw new IllegalValueException(MISSING_GROUP_TYPE);
        }
        if (!GroupType.isValidGroupType(type)) {
            throw new IllegalValueException(GroupType.MESSAGE_CONSTRAINTS);
        }
        final GroupType modelGroupType = GroupType.getGroupType(type);

        // ==== Students ====
        final Map<Matric, Student> modelStudents = new HashMap<>();
        for (JsonAdaptedStudent jsonAdaptedStudent : students) {
            Student student = jsonAdaptedStudent.toModelType();
            if (modelStudents.containsKey(student.getMatric())) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_STUDENTS);
            }
            modelStudents.put(student.getMatric(), student);
        }

        // ==== Build ====
        Group group = new Group(id, modelGroupType);
        modelStudents.values().forEach(group::addStudent);

        return group;
    }
}
