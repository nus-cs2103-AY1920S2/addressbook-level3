package seedu.jelphabot.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jelphabot.storage.JsonAdaptedTask.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.jelphabot.testutil.Assert.assertThrows;
import static seedu.jelphabot.testutil.TypicalTasks.BOOK_REPORT;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.commons.exceptions.IllegalValueException;
import seedu.jelphabot.model.task.DateTime;
import seedu.jelphabot.model.task.Description;
import seedu.jelphabot.model.task.ModuleCode;
import seedu.jelphabot.model.task.Priority;
import seedu.jelphabot.model.task.Status;
import seedu.jelphabot.model.task.TimeSpent;

// no test for status and TimeSpent needed because they are not user-dependent
public class JsonAdaptedTaskTest {
    private static final String INVALID_DESCRIPTION = "∫3L L1M";
    private static final String INVALID_MODULE_CODE = "2103T";
    private static final String INVALID_TAG = "#rabbit!!";
    private static final String INVALID_DATETIME = "03-19/1999 00:00";

    private static final String VALID_DESCRIPTION = BOOK_REPORT.getDescription().toString();
    private static final String VALID_MODULE_CODE = BOOK_REPORT.getModuleCode().toString();
    private static final String VALID_DATETIME = BOOK_REPORT.getDateTime().toString();
    private static final String VALID_DONETIME = BOOK_REPORT.getDoneTime().toString();
    private static final Priority VALID_PRIORITY = BOOK_REPORT.getPriority();
    private static final Status VALID_STATUS = BOOK_REPORT.getStatus();
    private static final List<JsonAdaptedTag> VALID_TAGS = BOOK_REPORT.getTags().stream()
                                                               .map(JsonAdaptedTag::new)
                                                               .collect(Collectors.toList());
    private static final TimeSpent VALID_TIME_SPENT = new TimeSpent(Duration.ofHours(2));

    @Test
    public void toModelType_validTaskDetails_returnsTask() throws Exception {
        JsonAdaptedTask task = new JsonAdaptedTask(BOOK_REPORT);
        assertEquals(BOOK_REPORT, task.toModelType());
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedTask task =
            new JsonAdaptedTask(INVALID_DESCRIPTION, VALID_STATUS, VALID_DATETIME, VALID_DONETIME, VALID_MODULE_CODE,
                VALID_PRIORITY, VALID_TAGS, VALID_TIME_SPENT
            );
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(
            null,
            VALID_STATUS,
            VALID_DATETIME,
            VALID_DONETIME,
            VALID_MODULE_CODE,
            VALID_PRIORITY,
            VALID_TAGS,
            VALID_TIME_SPENT
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidModuleCode_throwsIllegalValueException() {
        JsonAdaptedTask task =
            new JsonAdaptedTask(VALID_DESCRIPTION, VALID_STATUS, VALID_DATETIME, VALID_DONETIME,
                INVALID_MODULE_CODE, VALID_PRIORITY, VALID_TAGS, VALID_TIME_SPENT
            );
        String expectedMessage = ModuleCode.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullModuleCode_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(
            VALID_DESCRIPTION,
            VALID_STATUS,
            VALID_DATETIME,
            VALID_DONETIME,
            null,
            VALID_PRIORITY,
            VALID_TAGS, VALID_TIME_SPENT
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ModuleCode.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidDateTime_throwsIllegalValueException() {
        JsonAdaptedTask task =
            new JsonAdaptedTask(VALID_DESCRIPTION, VALID_STATUS, INVALID_DATETIME, VALID_DONETIME,
                VALID_MODULE_CODE, VALID_PRIORITY, VALID_TAGS, VALID_TIME_SPENT
            );
        String expectedMessage = DateTime.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullDateTime_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(
            VALID_DESCRIPTION,
            VALID_STATUS,
            null,
            VALID_DONETIME,
            VALID_MODULE_CODE,
            VALID_PRIORITY,
            VALID_TAGS, VALID_TIME_SPENT
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DateTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedTask task =
            new JsonAdaptedTask(VALID_DESCRIPTION, VALID_STATUS, VALID_DATETIME, VALID_DONETIME,
                VALID_MODULE_CODE, VALID_PRIORITY, invalidTags, VALID_TIME_SPENT
            );
        assertThrows(IllegalValueException.class, task::toModelType);
    }

    //@@author eedenong
    @Test
    public void toModelType_nullDoneTime_throwsNullPointerException() {
        JsonAdaptedTask task = new JsonAdaptedTask(
            VALID_DESCRIPTION,
            VALID_STATUS,
            VALID_DATETIME,
            null,
            VALID_MODULE_CODE,
            VALID_PRIORITY,
            VALID_TAGS,
            VALID_TIME_SPENT
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, LocalDateTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }
}


