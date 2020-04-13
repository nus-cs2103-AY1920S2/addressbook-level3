package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.lesson.exceptions.DuplicateLessonException;
import seedu.address.model.lesson.exceptions.LessonNotFoundException;
import seedu.address.testutil.LessonBuilder;

public class LessonListTest {

    private final LessonList lessonList = new LessonList();

    @Test
    public void contains_nullLesson_throwsException() {
        assertThrows(NullPointerException.class, () -> lessonList.contains(null));
    }

    @Test
    public void contains_lessonNotInList_returnsFalse() {
        assertFalse(lessonList.contains(new LessonBuilder().build()));
    }

    @Test
    public void contains_lessonInList_returnsTrue() {
        Lesson lesson = new LessonBuilder().build();
        lessonList.addLesson(lesson);
        assertTrue(lessonList.contains(lesson));
    }

    @Test
    public void add_nullLesson_throwsException() {
        assertThrows(NullPointerException.class, () -> lessonList.addLesson(null));
    }

    @Test
    public void add_duplicateLesson_throwsException() {
        Lesson lesson = new LessonBuilder().build();
        lessonList.addLesson(lesson);
        assertThrows(DuplicateLessonException.class, () -> lessonList.addLesson(lesson));
    }

    @Test
    public void setLesson_nullTargetLesson_throwsException() {
        Lesson lesson = new LessonBuilder().build();
        lessonList.addLesson(lesson);
        assertThrows(NullPointerException.class, () -> lessonList.setLesson(null, lesson));
    }

    @Test
    public void setLesson_nullEditedLesson_throwsException() {
        Lesson lesson = new LessonBuilder().build();
        lessonList.addLesson(lesson);
        assertThrows(NullPointerException.class, () -> lessonList.setLesson(lesson, null));
    }

    @Test
    public void setLesson_targetLessonNotInList_throwsException() {
        Lesson lesson = new LessonBuilder().build();
        assertThrows(LessonNotFoundException.class, () -> lessonList.setLesson(lesson, lesson));
    }

    @Test
    public void setLesson_targetAndEditedSameLesson_success() {
        Lesson lesson = new LessonBuilder().build();
        lessonList.addLesson(lesson);
        LessonList expectedLessonList = new LessonList();
        expectedLessonList.addLesson(lesson);
        assertEquals(lessonList, expectedLessonList);
    }

    @Test
    public void remove_nullLesson_throwsException() {
        assertThrows(NullPointerException.class, () -> lessonList.deleteLesson(null));
    }

    @Test
    public void remove_lessonDoesNotExist_throwsException() {
        assertThrows(LessonNotFoundException.class, () -> lessonList.deleteLesson(new LessonBuilder().build()));
    }

    @Test
    public void remove_existingLesson_success() {
        Lesson lesson = new LessonBuilder().build();
        lessonList.addLesson(lesson);
        LessonList expectedLessonList = new LessonList();
        lessonList.deleteLesson(lesson);
        assertEquals(expectedLessonList, lessonList);
    }

    @Test
    public void setLessons_nullLessonList_throwsException() {
        assertThrows(NullPointerException.class, () -> lessonList.setLessons((List<Lesson>) null));
    }

    @Test
    public void setLessons_lessonList_replacesList() {
        Lesson lesson = new LessonBuilder().build();
        lessonList.addLesson(lesson);
        Lesson anotherLesson = new LessonBuilder().withModuleCode("GEQ1000").build();
        LessonList expectedLessonList = new LessonList();
        expectedLessonList.addLesson(anotherLesson);
        lessonList.setLessons(expectedLessonList.getLessonList());
        assertEquals(lessonList, expectedLessonList);
    }

    @Test
    public void setLessons_null_throwsException() {
        assertThrows(NullPointerException.class, () -> lessonList.setLessons(null));
    }

    @Test
    public void setLessons_listWithDuplicateLessons_throwsException() {
        Lesson lesson = new LessonBuilder().build();
        List<Lesson> anotherList = new ArrayList<>();
        anotherList.add(lesson);
        anotherList.add(lesson);
        assertThrows(DuplicateLessonException.class, () -> lessonList.setLessons(anotherList));
    }

    @Test
    public void getLessonList_emptyList_success() {
        assertEquals(lessonList.getLessonList(), new ArrayList<>());
    }

    @Test
    public void getLessonList_nonEmptyList_success() {
        Lesson lesson = new LessonBuilder().build();
        lessonList.addLesson(lesson);
        List<Lesson> anotherList = new ArrayList<>();
        anotherList.add(lesson);
        assertEquals(lessonList.getLessonList(), anotherList);
    }
}
