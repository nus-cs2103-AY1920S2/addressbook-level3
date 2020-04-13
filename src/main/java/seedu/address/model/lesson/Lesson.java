package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalTime;
import java.util.Optional;

import seedu.address.model.lesson.exceptions.InvalidLessonTypeException;
import seedu.address.model.lesson.exceptions.InvalidTimeRangeException;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.util.DailySchedulable;

/**
 * Represents a Lesson in Mod Manager.
 */
public class Lesson implements Comparable<Lesson>, DailySchedulable {

    public static final String MESSAGE_CONSTRAINTS_VENUE =
            "Venue cannot be an empty string";

    private ModuleCode moduleCode;
    private LessonType type;
    private DayAndTime dayAndTime;
    private String venue; // optional

    public Lesson(ModuleCode moduleCode, LessonType type, DayAndTime dayAndTime,
                  String venue) throws InvalidTimeRangeException {
        requireAllNonNull(moduleCode, type, dayAndTime);
        this.moduleCode = moduleCode;
        this.type = type;
        this.dayAndTime = dayAndTime;
        this.venue = venue;
    }

    public ModuleCode getModuleCode() {
        return moduleCode;
    }

    public LessonType getType() {
        return type;
    }

    public String getVenue() {
        return venue;
    }

    public DayAndTime getDayAndTime() {
        return dayAndTime;
    }

    public boolean doesVenueExist() {
        return getVenue() != null;
    }

    /**
     * Checks if the venue of the lessons are the same.
     *
     * @param otherLesson The other lesson to compare with.
     * @return True if lessons are at the same venue and false otherwise.
     */
    private boolean isSameVenue(Lesson otherLesson) {
        boolean isSameVenue = false;
        if (otherLesson.doesVenueExist() && doesVenueExist()) {
            isSameVenue = otherLesson.getVenue().equals(getVenue());
        } else if (!otherLesson.doesVenueExist() && !doesVenueExist()) {
            isSameVenue = true;
        }
        return isSameVenue;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Lesson)) {
            return false;
        }

        Lesson otherLesson = (Lesson) other;

        return otherLesson.getModuleCode().equals(getModuleCode())
                && otherLesson.getType().equals(getType())
                && otherLesson.getDayAndTime().equals(getDayAndTime())
                && isSameVenue(otherLesson);
    }

    /**
     * Compares the instance of lesson to {@code lesson}.
     */
    public int compareTo(Lesson lesson) {
        return this.dayAndTime.compareTo(lesson.dayAndTime);
    }

    /**
     * Checks if type is a valid lesson type.
     * @return True if it is valid and false otherwise.
     */
    public static boolean isValidType(String type) {
        requireNonNull(type);
        for (LessonType t : LessonType.values()) {
            if (t.name().equals(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Converts string to a lesson type.
     */
    public static LessonType convertStringToLessonType(String type) {
        requireNonNull(type);
        for (LessonType t : LessonType.values()) {
            if (t.name().equals(type)) {
                return t;
            }
        }
        throw new InvalidLessonTypeException();
    }

    @Override
    public String toString() {
        if (doesVenueExist()) {
            return getModuleCode() + " " + getType() + " " + getDayAndTime()
                    + " at " + venue;
        }
        return getModuleCode() + " " + getType() + " " + getDayAndTime();
    }

    @Override
    public Optional<LocalTime> getComparableTime() {
        return Optional.of(getDayAndTime().getStartTime());
    }
}

