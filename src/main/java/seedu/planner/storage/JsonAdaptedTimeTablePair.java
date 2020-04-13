package seedu.planner.storage;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.util.Pair;
import seedu.planner.commons.exceptions.IllegalValueException;
import seedu.planner.model.student.TimeTable;
import seedu.planner.model.time.StudentSemester;

/**
 * This class is used since Pair of JsonAdaptedStudentSemester and JsonAdaptedTimeTable doesn't seem to be read
 * correctly by Jackson.
 */
public class JsonAdaptedTimeTablePair {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "StudentSemester's %s field is missing!";

    private final JsonAdaptedStudentSemester sem;
    private final JsonAdaptedTimeTable timeTable;

    @JsonCreator
    public JsonAdaptedTimeTablePair(@JsonProperty("sem") JsonAdaptedStudentSemester sem,
                                    @JsonProperty("timeTable") JsonAdaptedTimeTable timeTable) {
        this.sem = sem;
        this.timeTable = timeTable;
    }

    /**
     * Converts a given {@code TimeTableMap.Entry} into this class for Jackson use.
     */
    public JsonAdaptedTimeTablePair(Map.Entry<StudentSemester, TimeTable> source) {
        this.sem = new JsonAdaptedStudentSemester(source.getKey());
        this.timeTable = new JsonAdaptedTimeTable(source.getValue());
    }

    /**
     * Converts this Jackson-friendly adapted timetable entry into the model's
     * {@code Pair&lt;JsonAdaptedStudentSemester, JsonAdaptedTimeTable&gt;} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted timetable entry.
     */
    public Pair<StudentSemester, TimeTable> toModelType() throws IllegalValueException {
        if (sem == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StudentSemester.class.getSimpleName()));
        }

        if (timeTable == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TimeTable.class.getSimpleName()));
        }

        return new Pair<>(sem.toModelType(), timeTable.toModelType());
    }
}
