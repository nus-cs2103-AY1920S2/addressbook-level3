package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.lesson.exceptions.DuplicateLessonException;
import seedu.address.model.lesson.exceptions.InvalidTimeRangeException;
import seedu.address.model.lesson.exceptions.LessonNotFoundException;
import seedu.address.model.module.ModuleCode;

/**
 * A list of lessons.
 */
public class LessonList {

    private List<Lesson> lessons;

    public LessonList() {
        this.lessons = new ArrayList<>();
    }

    /**
     * Adds a lesson to the list of lessons.
     * @param lesson The lesson to be added.
     */
    public void addLesson(Lesson lesson) {
        requireNonNull(lesson);
        if (contains(lesson)) {
            throw new DuplicateLessonException();
        }

        if (!isTimeSlotFree(lesson, Optional.empty())) {
            throw new InvalidTimeRangeException();
        }

        lessons.add(lesson);
    }

    /**
     * Checks if time slot is free for the lesson.
     * @param lessonToBeAdded The lesson to check with.
     * @return True if time slot is free and false otherwise.
     */
    public boolean isTimeSlotFree(Lesson lessonToBeAdded, Optional<Lesson> lessonToExclude) {
        LocalTime targetStartTime = lessonToBeAdded.getStartTime();
        LocalTime targetEndTime = lessonToBeAdded.getEndTime();
        System.out.println(lessons);
        for (Lesson lesson : lessons) {
            if (!lessonToExclude.isEmpty() && lesson.equals(lessonToExclude.get())) {
                continue;
            }
            LocalTime lessonStartTime = lesson.getStartTime();
            LocalTime lessonEndTime = lesson.getEndTime();
            if ((lessonToBeAdded.getDay().equals(lesson.getDay()))
                    && (targetStartTime.compareTo(lessonEndTime) < 0 && lessonStartTime.compareTo(targetEndTime) < 0)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Edits an existing lesson.
     * @param target Lesson to be changed.
     * @param edited Lesson with the new details.
     */
    public void setLesson(Lesson target, Lesson edited) {
        requireAllNonNull(target, edited);
        if (!contains(target)) {
            throw new LessonNotFoundException();
        } else if (contains(edited)) {
            throw new DuplicateLessonException();
        } else {
            int index = lessons.indexOf(target);
            lessons.set(index, edited);
        }
    }

    public void setLessons(List<Lesson> replacement) {
        requireNonNull(replacement);
        Set<Lesson> set = new HashSet<>();
        for (Lesson l : replacement) {
            if (!set.add(l)) {
                throw new DuplicateLessonException();
            }
        }
        lessons = replacement;
    }

    public List<Lesson> getLessonList() {
        return lessons;
    }

    /**
     * Deletes a lesson from the list of lessons.
     * @param lesson The lesson to be deleted.
     */
    public void deleteLesson(Lesson lesson) {
        requireNonNull(lesson);
        if (lessons.contains(lesson)) {
            lessons.remove(lesson);
        } else {
            throw new LessonNotFoundException();
        }

    }

    /**
     * Delete lessons with {@code moduleCode}.
     * @param moduleCode The targeted module code.
     */
    public void deleteLessonsFromModule(ModuleCode moduleCode) {
        requireNonNull(moduleCode);
        List<Lesson> lessonsToDelete = findLessonByModuleCode(moduleCode);
        for (Lesson lesson : lessonsToDelete) {
            lessons.remove(lesson);
        }
    }

    /**
     * Finds a list of lessons by day.
     * @param day The day of the lessons.
     * @return The list of lessons.
     */
    public List<Lesson> findLessonsByDay(DayOfWeek day) {
        requireNonNull(day);
        List<Lesson> lessonsOnDate = new ArrayList<>();
        for (Lesson lesson : lessons) {
            if (lesson.getDay().equals(day)) {
                lessonsOnDate.add(lesson);
            }
        }
        return lessonsOnDate;
    }

    /**
     * Finds next lesson that is going to happen.
     * @return The next lesson happening.
     */
    public Lesson findNextLesson() {
        Collections.sort(lessons);
        LocalDate curDate = LocalDate.now();
        DayOfWeek curDay = curDate.getDayOfWeek();
        LocalTime curTime = LocalTime.now();
        for (Lesson lesson : lessons) {
            if (lesson.getDay().compareTo(curDay) == 0 && lesson.getStartTime().compareTo(curTime) >= 0) {
                return lesson;
            } else if (lesson.getDay().compareTo(curDay) > 0) {
                return lesson;
            }
        }
        return null;
    }

    /**
     * Finds lesson by module code.
     * @param moduleCode The module code of the lessons.
     * @return The list of lessons with the particular module code.
     */
    public List<Lesson> findLessonByModuleCode(ModuleCode moduleCode) {
        requireNonNull(moduleCode);
        List<Lesson> lessonWithCode = new ArrayList<>();
        for (Lesson lesson : lessons) {
            if (lesson.getModuleCode().equals(moduleCode)) {
                lessonWithCode.add(lesson);
            }
        }
        return lessonWithCode;
    }

    /**
     * Checks if lesson list contains a lesson.
     * @param lesson lesson to be checked.
     * @return True is lesson is in the list and false otherwise.
     */
    public boolean contains(Lesson lesson) {
        requireNonNull(lesson);
        for (Lesson l : lessons) {
            if (lesson.equals(l)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Replaces the module code {@code target} in the list with {@code editedModuleCode}.
     * {@code target} must exist in the list.
     * The module code identity of {@code editedModuleCode} must not be the same as another existing module code
     * in the list.
     */
    public void setModuleCode(ModuleCode target, ModuleCode editedModuleCode) {
        requireAllNonNull(target, editedModuleCode);
        List<Lesson> lessonsToEdit = new ArrayList<>();
        for (Lesson lesson : lessons) {
            if (lesson.getModuleCode().equals(target)) {
                lessonsToEdit.add(lesson);
            }
        }
        for (Lesson lesson : lessonsToEdit) {
            Lesson editedLesson = new Lesson(editedModuleCode, lesson.getType(), lesson.getDay(), lesson.getStartTime(),
                    lesson.getEndTime(), lesson.getVenue());
            setLesson(lesson, editedLesson);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof LessonList) {
            LessonList otherList = (LessonList) other;
            if (lessons.size() != otherList.getLessonList().size()) {
                return false;
            } else {
                for (int i = 0; i < lessons.size(); i++) {
                    if (!lessons.get(i).equals(otherList.getLessonList().get(i))) {
                        return false;
                    }
                }
                return true;
            }
        } else {
            return false;
        }
    }
}
