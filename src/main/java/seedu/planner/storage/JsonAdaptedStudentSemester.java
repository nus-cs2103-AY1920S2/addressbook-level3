package seedu.planner.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.planner.commons.exceptions.IllegalValueException;
import seedu.planner.model.time.DegreeYear;
import seedu.planner.model.time.SemesterYear;
import seedu.planner.model.time.StudentSemester;

/**
 * Jackson-friendly version of {@link StudentSemester}.
 */
public class JsonAdaptedStudentSemester {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "StudentSemester's %s field is missing!";

    private final JsonAdaptedSemesterYear semYear;
    private final int degreeYear;

    @JsonCreator
    public JsonAdaptedStudentSemester(@JsonProperty("semYear") JsonAdaptedSemesterYear semYear,
                                      @JsonProperty("degreeYear") int degreeYear) {
        this.semYear = semYear;
        this.degreeYear = degreeYear;
    }

    /**
     * Converts a given {@code StudentSemester} into this class for Jackson use.
     */
    public JsonAdaptedStudentSemester(StudentSemester source) {
        semYear = new JsonAdaptedSemesterYear(source.getSemesterYear());
        degreeYear = source.getDegreeYear();
    }

    /**
     * Converts this Jackson-friendly adapted StudentSemester object into the model's {@code StudentSemester} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted StudentSemester.
     */
    public StudentSemester toModelType() throws IllegalValueException {

        if (semYear == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    SemesterYear.class.getSimpleName()));
        }

        final SemesterYear modelSemYear = semYear.toModelType();

        try {
            final DegreeYear modelDegreeYear = new DegreeYear(degreeYear);
            return new StudentSemester(modelSemYear, modelDegreeYear);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalValueException(DegreeYear.MESSAGE_CONSTRAINTS);
        }
    }
}
