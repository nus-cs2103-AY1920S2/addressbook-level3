package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODULE_CODE_CS9000;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY_AND_TIME_MONDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_TYPE_REC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_TYPE_TUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_GEQ1000;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_HOME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_PARK;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.LessonBuilder;

public class LessonTest {

    @Test
    public void equals() {
        Lesson lesson = new LessonBuilder().build();
        Lesson anotherLesson = new LessonBuilder().withModuleCode(VALID_MODULE_CODE_GEQ1000)
                .withLessonType(VALID_LESSON_TYPE_REC)
                .withDayAndTime(VALID_DAY_AND_TIME_MONDAY)
                .withVenue(VALID_VENUE_HOME).build();

        // same object should return true
        assertTrue(lesson.equals(lesson));

        // same values should return true
        Lesson lessonCopy = new LessonBuilder().build();
        assertTrue(lesson.equals(lessonCopy));

        // null should return false
        assertFalse(lesson.equals(null));

        // different type should return false
        assertFalse(lesson.equals(1));

        // different lesson should return false
        assertFalse(lesson.equals(anotherLesson));

        // different module code should return false
        anotherLesson = new LessonBuilder().withModuleCode(INVALID_MODULE_CODE_CS9000).build();
        assertFalse(lesson.equals(anotherLesson));

        // different type should return false
        anotherLesson = new LessonBuilder().withLessonType(VALID_LESSON_TYPE_TUT).build();
        assertFalse(lesson.equals(anotherLesson));

        // different day and time should return false
        anotherLesson = new LessonBuilder().withDayAndTime(VALID_DAY_AND_TIME_MONDAY).build();
        assertFalse(lesson.equals(anotherLesson));

        // different venue should return false
        anotherLesson = new LessonBuilder().withVenue(VALID_VENUE_PARK).build();
        assertFalse(lesson.equals(anotherLesson));
    }
}
