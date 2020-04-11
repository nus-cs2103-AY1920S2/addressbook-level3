package tatracker.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tatracker.storage.JsonAdaptedStudent.MISSING_FIELD_MESSAGE_FORMAT;
import static tatracker.testutil.Assert.assertThrows;
import static tatracker.testutil.student.TypicalStudents.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import tatracker.commons.exceptions.IllegalValueException;
import tatracker.model.student.Email;
import tatracker.model.student.Matric;
import tatracker.model.student.Name;
import tatracker.model.student.Phone;
import tatracker.model.student.Rating;

public class JsonAdaptedStudentTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_MATRIC = "@0187945J";
    private static final int INVALID_RATING_LOWER = 0;
    private static final int INVALID_RATING_UPPER = 6;
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_MATRIC = BENSON.getMatric().toString();
    private static final int VALID_RATING = BENSON.getRating().value;
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validStudentDetails_returnsStudent() throws Exception {
        JsonAdaptedStudent student = new JsonAdaptedStudent(BENSON);
        assertEquals(BENSON, student.toModelType());
    }

    @Test
    public void toModelType_invalidMatric_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(INVALID_MATRIC, VALID_NAME,
                VALID_PHONE, VALID_EMAIL, VALID_RATING, VALID_TAGS);
        String expectedMessage = Matric.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullMatric_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(null, VALID_NAME,
                VALID_PHONE, VALID_EMAIL, VALID_RATING, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Matric.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_MATRIC, INVALID_NAME,
                        VALID_PHONE, VALID_EMAIL, VALID_RATING, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_MATRIC, null,
                VALID_PHONE, VALID_EMAIL, VALID_RATING, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_MATRIC, VALID_NAME,
                        INVALID_PHONE, VALID_EMAIL, VALID_RATING, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_MATRIC, VALID_NAME,
                null, VALID_EMAIL, VALID_RATING, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_MATRIC, VALID_NAME,
                        VALID_PHONE, INVALID_EMAIL, VALID_RATING, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_MATRIC, VALID_NAME,
                VALID_PHONE, null, VALID_RATING, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidRating_throwsIllegalValueException() {
        String expectedMessage = Rating.MESSAGE_CONSTRAINTS;

        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_MATRIC, VALID_NAME,
                VALID_PHONE, VALID_EMAIL, INVALID_RATING_LOWER, VALID_TAGS);
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);

        student = new JsonAdaptedStudent(VALID_MATRIC, VALID_NAME,
                VALID_PHONE, VALID_EMAIL, INVALID_RATING_UPPER, VALID_TAGS);
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_MATRIC, VALID_NAME,
                        VALID_PHONE, VALID_EMAIL, VALID_RATING, invalidTags);
        assertThrows(IllegalValueException.class, student::toModelType);
    }
}
