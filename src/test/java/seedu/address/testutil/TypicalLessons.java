package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.lesson.Lesson;

/**
 * A utility class containing a list of {@code Lesson} objects to be used in tests.
 */
public class TypicalLessons {
    public static final Lesson CS2103T = new LessonBuilder().build();
    public static final Lesson GEQ1000 = new LessonBuilder().withModuleCode("GEQ1000").withLessonType("LAB")
            .withDayAndTime("WEDNESDAY 09:00 10:00").build();

    private TypicalLessons() {}

    public static List<Lesson> getTypicalLessons() {
        return new ArrayList<>(Arrays.asList(CS2103T, GEQ1000));
    }
}
