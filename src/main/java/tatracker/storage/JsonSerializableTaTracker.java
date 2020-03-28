package tatracker.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import tatracker.commons.exceptions.IllegalValueException;
import tatracker.model.ReadOnlyTaTracker;
import tatracker.model.TaTracker;
import tatracker.model.student.Student;

/**
 * An Immutable TaTracker that is serializable to JSON format.
 */
@JsonRootName(value = "tatracker")
class JsonSerializableTaTracker {

    public static final String MESSAGE_DUPLICATE_STUDENT = "Students list contains duplicate student(s).";

    private final List<JsonAdaptedStudent> students = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTaTracker} with the given students.
     */
    @JsonCreator
    public JsonSerializableTaTracker(@JsonProperty("students") List<JsonAdaptedStudent> students) {
        this.students.addAll(students);
    }

    /**
     * Converts a given {@code ReadOnlyTaTracker} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableTaTracker}.
     */
    public JsonSerializableTaTracker(ReadOnlyTaTracker source) {
        //students.addAll(source.getStudentList().stream().map(JsonAdaptedStudent::new).collect(Collectors.toList()));
    }

    /**
     * Converts this Ta Tracker into the model's {@code TaTracker} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TaTracker toModelType() throws IllegalValueException {
        TaTracker taTracker = new TaTracker();
        for (JsonAdaptedStudent jsonAdaptedStudent : students) {
            Student student = jsonAdaptedStudent.toModelType();
            if (taTracker.hasStudent(student)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_STUDENT);
            }
            taTracker.addStudent(student);
        }
        return taTracker;
    }

}
