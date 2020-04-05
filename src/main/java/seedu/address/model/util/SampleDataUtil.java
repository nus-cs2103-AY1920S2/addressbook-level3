package seedu.address.model.util;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.academics.Academics;
import seedu.address.model.academics.Assessment;
import seedu.address.model.academics.Exam;
import seedu.address.model.academics.Homework;
import seedu.address.model.academics.ReadOnlyAcademics;
import seedu.address.model.admin.Admin;
import seedu.address.model.admin.Date;
import seedu.address.model.admin.ReadOnlyAdmin;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventHistory;
import seedu.address.model.event.ReadOnlyEvents;
import seedu.address.model.event.RecurrenceType;
import seedu.address.model.notes.Notes;
import seedu.address.model.notes.NotesManager;
import seedu.address.model.notes.ReadOnlyNotes;

import seedu.address.model.student.Address;
import seedu.address.model.student.Attendance;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.NextOfKin;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Remark;
import seedu.address.model.student.Student;
import seedu.address.model.student.Temperature;

import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final Remark EMPTY_REMARK = new Remark("");

    public static Student[] getSampleStudents() {
        return new Student[] {
            new Student(new Name("Simon Lam"), new Phone("87438807"), new Email("simonlam@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), new Temperature("36.5"),
                    new Attendance("Sick"), new NextOfKin("Joseph Lam - father - 99999999"),
                    EMPTY_REMARK,
                    getTagSet("Sheares")),
            new Student(new Name("Gerren Seow"), new Phone("99272758"), new Email("gerrenseow@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Temperature("37.0"),
                    new Attendance("Present"), new NextOfKin("Joseph Seow - father - 99999999"),
                    EMPTY_REMARK,
                    getTagSet("Temasek")),
            new Student(new Name("Lee Hui Ting"), new Phone("93210283"), new Email("huiting@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Temperature("36.9"),
                    new Attendance("Present"), new NextOfKin("Joseph Lee - father - 99999999"),
                    EMPTY_REMARK,
                    getTagSet("CAPT")),
            new Student(new Name("Gary Syndromes"), new Phone("91031282"), new Email("leegary@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Temperature("36.4"),
                    new Attendance("Present"), new NextOfKin("Joseph Syndromes - father - 99999999"),
                    EMPTY_REMARK,
                    getTagSet("Home")),
            new Student(new Name("Freddy Zhang"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new Temperature("37.4"),
                    new Attendance("Present"), new NextOfKin("Joseph Zhang - father - 99999999"),
                    EMPTY_REMARK,
                    getTagSet("classmates"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Student sampleStudent : getSampleStudents()) {
            sampleAb.addStudent(sampleStudent);
        }
        return sampleAb;
    }

    public static ReadOnlyEvents getSampleEvents() {
        String eventName = "Sample event";
        String colorCode = "group01";
        String uniqueIdentifier = "teapethelper";
        LocalDateTime startDateTime = LocalDateTime.now();
        LocalDateTime endDateTime = LocalDateTime.now().plusHours(2);
        Event event = new Event(eventName, startDateTime, endDateTime, colorCode, uniqueIdentifier,
                RecurrenceType.NONE);
        ArrayList<Event> events = new ArrayList<>();
        events.add(event);
        EventHistory sampleEventHistory = new EventHistory(events);
        return sampleEventHistory;
    }

    public static Assessment[] getSampleAssessments() {
        return new Assessment[] {
            new Homework("Math Differentiation Homework", "2020-03-23"),
            new Homework("Science Plant and Species Scrapbook", "2020-03-23"),
            new Homework("Science Experiment", "2020-03-25"),
            new Exam("English Spelling Test", "2020-05-25"),
            new Exam("Chinese Final Exam", "2020-05-25")
        };
    }

    public static ReadOnlyAcademics getSampleAcademics() {
        Academics sampleAcademics = new Academics();
        for (Assessment sampleAssessment : getSampleAssessments()) {
            sampleAssessment.setSampleSubmissions(Arrays.asList(getSampleStudents()),
                    sampleAssessment.getDescription());
            sampleAcademics.addAssessment(sampleAssessment);
        }
        return sampleAcademics;
    }


    public static Notes[] getSampleNotes() {
        return new Notes[] {
            new Notes("Simon Lam", "He was disruptive in class today, "
                    + "and did not complete his homework as usual.", "LOW", "29/03/2020 22:31"),
            new Notes("Gerren Seow", "Reminder to print the testimonial as requested.",
                    "MEDIUM", "29/03/2020 22:40"),
            new Notes("Lee Hui Ting", "Did not take her temperature for the third day in a row. "
                    + "Inform parents.", "HIGH", "29/03/2020 22:40")
        };
    }

    public static ReadOnlyNotes getSampleNotesManager() {
        NotesManager sampleNotesManager = new NotesManager();
        sampleNotesManager.setNotes(Arrays.asList(getSampleNotes()));
        return sampleNotesManager;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    public static Date[] getSampleDates() {
        return new Date[] {new Date(LocalDate.now(), Arrays.asList(getSampleStudents()))};
    }

    public static ReadOnlyAdmin getSampleAdmin() {
        Admin sampleAd = new Admin();
        for (Date sampleDate : getSampleDates()) {
            sampleAd.addDate(sampleDate);
        }
        return sampleAd;
    }
}
