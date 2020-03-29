package tatracker.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tatracker.commons.exceptions.IllegalValueException;
import tatracker.model.group.Group;
import tatracker.model.group.GroupType;
import tatracker.model.student.Student;

/**
 * Jackson-friendly version of {@link Group}.
 */
class JsonAdaptedGroup {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Group's %s field is missing!";
    public static final String MESSAGE_DUPLICATE_STUDENTS = "Student list contains duplicate student(s).";

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
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Group id"));
        }

        // ==== Type ====
        if (type == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Group type"));
        }
        // TODO: is valid group type
        final GroupType modelGroupType = GroupType.valueOf(type);

        // ==== Students ====
        final List<Student> modelStudents = new ArrayList<>();
        for (JsonAdaptedStudent jsonAdaptedStudent : students) {
            Student student = jsonAdaptedStudent.toModelType();
            if (modelStudents.contains(student)) {
                throw new IllegalArgumentException(MESSAGE_DUPLICATE_STUDENTS);
            }
            modelStudents.add(student);
        }

        // ==== Build ====
        Group group = new Group(id, modelGroupType);
        modelStudents.forEach(group::addStudent);

        return group;
    }

}
