package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedAssignment.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAssignments.CS2103_TP;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.assignment.Deadline;
import seedu.address.model.assignment.Title;
import seedu.address.model.assignment.Workload;

public class JsonAdaptedAssignmentTest {
    private static final String INVALID_TITLE = " ";
    private static final String INVALID_DEADLINE = "2020-14-31 23:59";
    private static final String INVALID_WORKLOAD = "0.6";

    private static final String VALID_TITLE = CS2103_TP.getTitle().toString();
    private static final String VALID_DEADLINE = CS2103_TP.getDeadline().dateTime
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    private static final String VALID_WORKLOAD = CS2103_TP.getWorkload().toString();
    private static final String VALID_STATUS = CS2103_TP.getStatus().toString();

    @Test
    public void toModelType_validAssignmentDetails_returnsAssignment() throws Exception {
        JsonAdaptedAssignment assignment = new JsonAdaptedAssignment(CS2103_TP);
        assertEquals(CS2103_TP, assignment.toModelType());
    }

    @Test
    public void toModelType_invalidTitle_throwsIllegalValueException() {
        JsonAdaptedAssignment assignment =
                new JsonAdaptedAssignment(INVALID_TITLE, VALID_DEADLINE, VALID_WORKLOAD, VALID_STATUS);

        String expectedMessage = Title.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }

    @Test
    public void toModelType_nullTitle_throwsIllegalValueException() {
        JsonAdaptedAssignment assignment =
                new JsonAdaptedAssignment(null, VALID_DEADLINE, VALID_WORKLOAD, VALID_STATUS);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }

    @Test
    public void toModelType_invalidDeadline_throwsIllegalValueException() {
        JsonAdaptedAssignment assignment =
                new JsonAdaptedAssignment(VALID_TITLE, INVALID_DEADLINE, VALID_WORKLOAD, VALID_STATUS);

        String expectedMessage = Deadline.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }

    @Test
    public void toModelType_nullDeadline_throwsIllegalValueException() {
        JsonAdaptedAssignment assignment =
                new JsonAdaptedAssignment(VALID_TITLE, null, VALID_WORKLOAD, VALID_STATUS);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Deadline.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }

    @Test
    public void toModelType_invalidWorkload_throwsIllegalValueException() {
        JsonAdaptedAssignment assignment =
                new JsonAdaptedAssignment(VALID_TITLE, VALID_DEADLINE, INVALID_WORKLOAD, VALID_STATUS);

        String expectedMessage = Workload.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }

    @Test
    public void toModelType_nullWorkload_throwsIllegalValueException() {
        JsonAdaptedAssignment assignment =
                new JsonAdaptedAssignment(VALID_TITLE, VALID_DEADLINE, null, VALID_STATUS);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Workload.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }
}
