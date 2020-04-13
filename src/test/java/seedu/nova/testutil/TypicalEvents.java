package seedu.nova.testutil;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import seedu.nova.model.Schedule;
import seedu.nova.model.schedule.event.Consultation;
import seedu.nova.model.schedule.event.Event;
import seedu.nova.model.schedule.event.Lesson;
import seedu.nova.model.schedule.event.Meeting;
import seedu.nova.model.schedule.event.StudySession;


/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */

public class TypicalEvents {

    public static final Event MEETING = new Meeting("Project Meeting", "COM1-B108",
            LocalTime.of(14, 0), LocalTime.of(15, 0), LocalDate.of(2020, 3, 9));

    public static final Event CONSULTATION = new Consultation("Design Principles", "COM2-0203",
            LocalTime.of(16, 0), LocalTime.of(17, 0), LocalDate.of(2020, 3, 20));

    public static final Event STUDY_SESSION = new StudySession("UML Diagrams", "Home",
            LocalTime.of(11, 0), LocalTime.of(12, 0), LocalDate.of(2020, 3, 12));

    public static final Event LESSON = new Lesson("CS2103T Lecture", "I3 AUD",
            LocalTime.of(14, 0), LocalTime.of(16, 0), DayOfWeek.FRIDAY, LocalDate.of(2020, 3, 27), "Bring charger");

    public static final Event LESSON_WEEK1 = new Lesson("CS2103T Tutorial", "COM1 B1-03",
            LocalTime.of(10, 0), LocalTime.of(11, 0), DayOfWeek.FRIDAY, LocalDate.of(2020, 1, 17), "");

    public static final Event LESSON_WEEK2 = new Lesson("CS2103T Tutorial", "COM1 B1-03",
            LocalTime.of(10, 0), LocalTime.of(11, 0), DayOfWeek.FRIDAY, LocalDate.of(2020, 1, 24), "");

    public static final Event LESSON_WEEK3 = new Lesson("CS2103T Tutorial", "COM1 B1-03",
            LocalTime.of(10, 0), LocalTime.of(11, 0), DayOfWeek.FRIDAY, LocalDate.of(2020, 1, 31), "");

    public static final Event LESSON_WEEK4 = new Lesson("CS2103T Tutorial", "COM1 B1-03",
            LocalTime.of(10, 0), LocalTime.of(11, 0), DayOfWeek.FRIDAY, LocalDate.of(2020, 2, 7), "");


    public static final Event LESSON_WEEK5 = new Lesson("CS2103T Tutorial", "COM1 B1-03",
            LocalTime.of(10, 0), LocalTime.of(11, 0), DayOfWeek.FRIDAY, LocalDate.of(2020, 2, 14), "");

    public static final Event LESSON_WEEK6 = new Lesson("CS2103T Tutorial", "COM1 B1-03",
            LocalTime.of(10, 0), LocalTime.of(11, 0), DayOfWeek.FRIDAY, LocalDate.of(2020, 2, 21), "");

    public static final Event LESSON_WEEK7 = new Lesson("CS2103T Tutorial", "COM1 B1-03",
            LocalTime.of(10, 0), LocalTime.of(11, 0), DayOfWeek.FRIDAY, LocalDate.of(2020, 3, 6), "");

    public static final Event LESSON_WEEK8 = new Lesson("CS2103T Tutorial", "COM1 B1-03",
            LocalTime.of(10, 0), LocalTime.of(11, 0), DayOfWeek.FRIDAY, LocalDate.of(2020, 3, 13), "");

    public static final Event LESSON_WEEK9 = new Lesson("CS2103T Tutorial", "COM1 B1-03",
            LocalTime.of(10, 0), LocalTime.of(11, 0), DayOfWeek.FRIDAY, LocalDate.of(2020, 3, 20), "");

    public static final Event LESSON_WEEK10 = new Lesson("CS2103T Tutorial", "COM1 B1-03",
            LocalTime.of(10, 0), LocalTime.of(11, 0), DayOfWeek.FRIDAY, LocalDate.of(2020, 3, 27), "");

    public static final Event LESSON_WEEK11 = new Lesson("CS2103T Tutorial", "COM1 B1-03",
            LocalTime.of(10, 0), LocalTime.of(11, 0), DayOfWeek.FRIDAY, LocalDate.of(2020, 4, 3), "");

    public static final Event LESSON_WEEK12 = new Lesson("CS2103T Tutorial", "COM1 B1-03",
            LocalTime.of(10, 0), LocalTime.of(11, 0), DayOfWeek.FRIDAY, LocalDate.of(2020, 4, 10), "");

    public static final Event LESSON_WEEK13 = new Lesson("CS2103T Tutorial", "COM1 B1-03",
            LocalTime.of(10, 0), LocalTime.of(11, 0), DayOfWeek.FRIDAY, LocalDate.of(2020, 4, 17), "");

    public static Schedule getTypicalSchedule() {
        Schedule schedule = new Schedule(LocalDate.of(2020, 1, 13),
                LocalDate.of(2020, 5, 3));

        for (Event event: getTypicalEvents()) {
            schedule.addEvent(event);
        }

        return schedule;
    }

    public static List<Event> getTypicalEvents() {
        return new LinkedList<>(Arrays.asList(MEETING, CONSULTATION, STUDY_SESSION, LESSON_WEEK1, LESSON_WEEK2,
                LESSON_WEEK3, LESSON_WEEK4, LESSON_WEEK5, LESSON_WEEK6, LESSON_WEEK7, LESSON_WEEK8, LESSON_WEEK9,
                LESSON_WEEK10, LESSON_WEEK11, LESSON_WEEK12, LESSON_WEEK13));
    }




}
