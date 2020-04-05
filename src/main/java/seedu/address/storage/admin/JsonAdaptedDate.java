package seedu.address.storage.admin;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.admin.Date;
import seedu.address.model.student.Student;
import seedu.address.storage.JsonAdaptedStudent;

/**
 * Jackson-friendly version of {@link Date}.
 */
public class JsonAdaptedDate {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Date's %s field is missing!";

    private final LocalDate date;
    private final List<JsonAdaptedStudent> students = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedDate} with the given date details.
     */
    @JsonCreator
    public JsonAdaptedDate(@JsonProperty("date") LocalDate date,
                           @JsonProperty("students") List<JsonAdaptedStudent> students) {

        this.date = date;
        if (students != null) {
            this.students.addAll(students);
        }
    }

    /**
     * Converts a given {@code Date} into this class for Jackson use.
     */
    public JsonAdaptedDate(Date source) {
        date = source.getDate();
        List<Student> allStudents = source.getStudents();
        for (Student n : allStudents) {
            students.add(new JsonAdaptedStudent(n));
        }
    }

    /**
     * Converts this Jackson-friendly adapted date object into the model's {@code Date} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted date.
     */
    public Date toModelType() throws IllegalValueException {
        final List<Student> studentList = new ArrayList<>();
        for (JsonAdaptedStudent student : students) {
            studentList.add(student.toModelType());
        }
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        final List<Student> modelStudents = new ArrayList<>(studentList);
        final LocalDate modelDate = date;
        return new Date(modelDate, modelStudents);
    }
}
