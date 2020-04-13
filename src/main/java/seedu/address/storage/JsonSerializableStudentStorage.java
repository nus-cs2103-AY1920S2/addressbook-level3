package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.student.ReadOnlyStudent;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentTAble;

/**
 * An Immutable StudentTAble that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableStudentStorage {

    public static final String MESSAGE_DUPLICATE_STUDENT = "Students list contains duplicate student(s).";

    private final List<JsonAdaptedStudent> students = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableStudentStorage} with the given students.
     */
    @JsonCreator
    public JsonSerializableStudentStorage(@JsonProperty("students") List<JsonAdaptedStudent> students) {
        this.students.addAll(students);
    }

    /**
     * Converts a given {@code ReadOnlyStudent} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableStudentStorage}.
     */
    public JsonSerializableStudentStorage(ReadOnlyStudent source) {
        students.addAll(source.getStudentList().stream().map(JsonAdaptedStudent::new).collect(Collectors.toList()));
    }

    /**
     * Converts this TAble into the model's {@code StudentTAble} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public StudentTAble toModelType() throws IllegalValueException {
        StudentTAble studentTAble = new StudentTAble();
        for (JsonAdaptedStudent jsonAdaptedStudent : students) {
            Student student = jsonAdaptedStudent.toModelType();
            if (studentTAble.hasStudent(student)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_STUDENT);
            }
            studentTAble.addStudent(student);
        }
        return studentTAble;
    }

}
