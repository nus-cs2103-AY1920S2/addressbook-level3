//@@author Chuayijing

package tatracker.model.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import tatracker.model.ReadOnlyTaTracker;
import tatracker.model.TaTracker;
import tatracker.model.group.Group;
import tatracker.model.group.GroupType;
import tatracker.model.module.Module;
import tatracker.model.session.Session;
import tatracker.model.session.SessionType;
import tatracker.model.student.Email;
import tatracker.model.student.Matric;
import tatracker.model.student.Name;
import tatracker.model.student.Phone;
import tatracker.model.student.Rating;
import tatracker.model.student.Student;
import tatracker.model.tag.Tag;


/**
 * Contains utility methods for populating {@code TaTracker} with sample data.
 */
public class SampleDataUtil {
    public static Student[] getSampleStudents() {
        return new Student[]{
            new Student(new Matric("A0187945J"),
                        new Name("Alex Yeoh"),
                        new Phone("87438807"),
                        new Email("alexyeoh@example.com"),
                        new Rating(4),
                        getTagSet("friends")),
            new Student(new Matric("A0181137L"),
                        new Name("Bernice Yu"),
                        new Phone("99272758"),
                        new Email("berniceyu@example.com"),
                        new Rating(1),
                        getTagSet("colleagues", "friends")),
            new Student(new Matric("A0187565N"),
                        new Name("Charlotte Oliveiro"),
                        new Phone("93210283"),
                        new Email("charlotte@example.com"),
                        new Rating(5),
                        getTagSet("neighbours")),
            new Student(new Matric("A0186153P"),
                        new Name("David Li"),
                        new Phone("91031282"),
                        new Email("lidavid@example.com"),
                        new Rating(3),
                        getTagSet("family")),
            new Student(new Matric("A0180474R"),
                        new Name("Irfan Ibrahim"),
                        new Phone("92492021"),
                        new Email("irfan@example.com"),
                        new Rating(2),
                        getTagSet("classmates")),
            new Student(new Matric("A0187613T"),
                        new Name("Roy Balakrishnan"),
                        new Phone("92624417"),
                        new Email("royb@example.com"),
                        new Rating(4),
                        getTagSet("colleagues")),
            new Student(new Matric("A0195558H"),
                        new Name("Jeffry Lum"),
                        new Phone("65162727"),
                        new Email("Jeffry@u.nus.edu"),
                        new Rating(5),
                        getTagSet("tutors"))
        };
    }

    public static Group[] getSampleGroups() {
        return new Group[]{
            new Group("G06", GroupType.LAB),
            new Group("G01", GroupType.TUTORIAL),
            new Group("G02", GroupType.RECITATION),
            new Group("G03", GroupType.OTHER),
        };
    }

    public static String[] getSampleGroupsString() {
        return new String[]{
            "G06",
            "G01",
            "G02",
            "G03",
        };
    }


    public static Module[] getSampleModules() {
        return new Module[]{
            new Module("CS3243", "Introduction to AI"),
            new Module("CS2103T", "Software Engineering"),
        };
    }


    public static String[] getSampleModulesString() {
        return new String[]{
            "CS3243",
            "CS2103T",
        };
    }

    public static Session[] getSampleSessions() {
        return new Session[]{
            new Session(LocalDateTime.of(2020, 04, 30, 13, 00),
                        LocalDateTime.of(2020, 04, 30, 14, 30),
                        SessionType.CONSULTATION, 1,
                        "CS2103T",
                        "with Alice and Bob"),
            new Session(LocalDateTime.of(2020, 04, 10, 10, 00),
                        LocalDateTime.of(2020, 04, 10, 11, 00),
                        SessionType.LAB, 2,
                        "CS3243",
                        "prepare notes"),
            new Session(LocalDateTime.of(2020, 06, 26, 14, 00),
                        LocalDateTime.of(2020, 06, 26, 15, 00),
                        SessionType.TUTORIAL, 1,
                        "CS2103T",
                        "check work"),
            new Session(LocalDateTime.of(2020, 04, 29, 9, 30),
                        LocalDateTime.of(2020, 04, 29, 11, 00),
                        SessionType.GRADING, 0,
                        "CS2103T",
                        "grade group 1 first"),
            new Session(LocalDateTime.of(2020, 05, 26, 14, 00),
                        LocalDateTime.of(2020, 05, 26, 17, 00),
                        SessionType.GRADING, 0,
                        "CS3243",
                        "clarifications for Bob's question"),
            new Session(LocalDateTime.of(2020, 03, 26, 14, 00),
                        LocalDateTime.of(2020, 03, 26, 17, 00),
                        SessionType.CONSULTATION, 0,
                        "CS3243",
                        "clarifications for Alice's question"),
            new Session(LocalDateTime.of(2020, 03, 21, 9, 30),
                        LocalDateTime.of(2020, 03, 21, 11, 00),
                        SessionType.GRADING, 0,
                        "CS2103T",
                        "grade group 1 first"),
        };
    }

    /**
     * Randomise the groupings and students.
     *
     * @return student 6 and student 7 from {@code getSampleStudents};
     */
    public static Student[] module1Group1Students() {
        ArrayList<Student> sList = new ArrayList<>();
        for (int i = 5; i < getSampleStudents().length; i++) {
            sList.add(getSampleStudents()[i]);
        }
        Student[] s = sList.toArray(new Student[sList.size()]);
        return s;
    }

    /**
     * Randomise the groupings and students.
     *
     * @return students 1 to 5 from {@code getSampleStudents};
     */
    public static Student[] module1Group2Students() {
        ArrayList<Student> sList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            sList.add(getSampleStudents()[i]);
        }
        Student[] s = sList.toArray(new Student[sList.size()]);
        return s;
    }

    /**
     * Randomise the groupings and students.
     *
     * @return students 1 to 3 from {@code getSampleStudents};
     */
    public static Student[] module2Group1Students() {
        ArrayList<Student> sList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            sList.add(getSampleStudents()[i]);
        }
        Student[] s = sList.toArray(new Student[sList.size()]);
        return s;
    }

    /**
     * Randomise the groupings and students.
     *
     * @return students 4 to 7 from {@code getSampleStudents};
     */
    public static Student[] module2Group2Students() {
        ArrayList<Student> sList = new ArrayList<>();
        for (int i = 3; i < getSampleStudents().length; i++) {
            sList.add(getSampleStudents()[i]);
        }
        Student[] s = sList.toArray(new Student[sList.size()]);
        return s;
    }

    /**
     * each module consists of 2 groups, each group consists of students.
     * @return
     */
    public static ReadOnlyTaTracker getSampleTaTracker() {
        TaTracker sampleAb = new TaTracker();
        sampleAb.addModule(getSampleModules()[0]);
        sampleAb.addGroup(getSampleGroups()[0], getSampleModules()[0]);
        for (int i = 0; i < module1Group1Students().length; i++) {
            sampleAb.addStudent(module1Group1Students()[i],
                    getSampleGroupsString()[0], getSampleModulesString()[0]);
        }
        sampleAb.addGroup(getSampleGroups()[1], getSampleModules()[0]);
        for (int i = 0; i < module1Group2Students().length; i++) {
            sampleAb.addStudent(module1Group2Students()[i],
                    getSampleGroupsString()[1], getSampleModulesString()[0]);
        }

        sampleAb.addModule(getSampleModules()[1]);
        sampleAb.addGroup(getSampleGroups()[3], getSampleModules()[1]);
        for (int i = 0; i < module2Group2Students().length; i++) {
            sampleAb.addStudent(module2Group2Students()[i],
                    getSampleGroupsString()[3], getSampleModulesString()[1]);
        }
        sampleAb.addGroup(getSampleGroups()[2], getSampleModules()[1]);
        for (int i = 0; i < module2Group1Students().length; i++) {
            sampleAb.addStudent(module2Group1Students()[i],
                    getSampleGroupsString()[2], getSampleModulesString()[1]);
        }
        for (int i = 0; i < 5; i++) {
            sampleAb.addSession(getSampleSessions()[i]);
        }
        for (int i = 5; i < getSampleSessions().length; i++) {
            sampleAb.addDoneSession(getSampleSessions()[i]);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */

    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}

