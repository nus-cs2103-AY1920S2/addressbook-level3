package seedu.address.testutil;

import seedu.address.model.lesson.DayAndTime;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonType;
import seedu.address.model.module.ModuleCode;

/**
 * A utility class to help with building Lesson objects.
 */
public class LessonBuilder {
    public static final String DEFAULT_MODULE_CODE = "CS2103T";
    public static final String DEFAULT_LESSON_TYPE = "LEC";
    public static final String DEFAULT_DAY_AND_TIME = "MONDAY 14:00 16:00";
    public static final String DEFAULT_VENUE = "i3-Aud";

    private ModuleCode moduleCode;
    private LessonType lessonType;
    private DayAndTime dayAndTime;
    private String venue;

    public LessonBuilder() {
        moduleCode = new ModuleCode(DEFAULT_MODULE_CODE);
        lessonType = LessonType.valueOf(DEFAULT_LESSON_TYPE);
        dayAndTime = new DayAndTime(DEFAULT_DAY_AND_TIME);
        venue = DEFAULT_VENUE;
    }

    /**
     * Sets the {@code ModuleCode} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withModuleCode(String moduleCode) {
        this.moduleCode = new ModuleCode(moduleCode);
        return this;
    }

    /**
     * Sets the {@code LessonType} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withLessonType(String type) {
        this.lessonType = LessonType.valueOf(type);
        return this;
    }

    /**
     * Sets the {@code DayAndTime} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withDayAndTime(String dayAndTime) {
        this.dayAndTime = new DayAndTime(dayAndTime);
        return this;
    }

    /**
     * Sets the {@code venue} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withVenue(String venue) {
        this.venue = venue;
        return this;
    }

    public Lesson build() {
        return new Lesson(moduleCode, lessonType, dayAndTime, venue);
    }


}
