package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelCourse.CourseAddressBook;
import seedu.address.model.modelCourse.ReadOnlyCourseAddressBook;
import seedu.address.model.modelFinance.Finance;
import seedu.address.model.modelFinance.FinanceAddressBook;
import seedu.address.model.modelFinance.ReadOnlyFinanceAddressBook;
import seedu.address.model.modelStudent.ReadOnlyStudentAddressBook;
import seedu.address.model.modelStudent.Student;
import seedu.address.model.modelStudent.StudentAddressBook;
import seedu.address.model.modelTeacher.ReadOnlyTeacherAddressBook;
import seedu.address.model.modelTeacher.TeacherAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Amount;
import seedu.address.model.person.AssignedCourse;
import seedu.address.model.person.Email;
import seedu.address.model.person.ID;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Salary;
import seedu.address.model.modelTeacher.Teacher;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    public static Teacher[] getSampleTeachers() {
        return new Teacher[] {
            new Teacher(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"), new Salary("1000"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Teacher(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"), new Salary("1000"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Teacher(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"), new Salary("1000"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Teacher(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"), new Salary("1000"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Teacher(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"), new Salary("1000"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Teacher(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"), new Salary("1000"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyTeacherAddressBook getSampleTeacherAddressBook() {
        TeacherAddressBook sampleAb = new TeacherAddressBook();
        for (Teacher sampleTeacher : getSampleTeachers()) {
            sampleAb.addTeacher(sampleTeacher);
        }
        return sampleAb;
    }

    public static Student[] getSampleStudents() {
        return new Student[] {
            new Student(new Name("Alex Yeoh"), new AssignedCourse("abc"),
                getTagSet("friends")),
            new Student(new Name("Bernice Yu"), new AssignedCourse("abc"),
                getTagSet("colleagues", "friends")),
            new Student(new Name("Charlotte Oliveiro"), new AssignedCourse("abc"),
                getTagSet("neighbours")),
            new Student(new Name("David Li"), new AssignedCourse("abc"),
                getTagSet("family")),
            new Student(new Name("Irfan Ibrahim"), new AssignedCourse("abc"),
                getTagSet("classmates")),
            new Student(new Name("Roy Balakrishnan"), new AssignedCourse("abc"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyStudentAddressBook getSampleStudentAddressBook() {
        StudentAddressBook sampleAb = new StudentAddressBook();
        for (Student sampleStudent : getSampleStudents()) {
            sampleAb.addStudent(sampleStudent);
        }
        return sampleAb;
    }

    public static Finance[] getSampleFinances() {
        return new Finance[] {
            new Finance(new Name("Alex Yeoh"), new Amount("1000"),
                getTagSet("friends")),
            new Finance(new Name("Bernice Yu"), new Amount("1000"),
                getTagSet("colleagues", "friends")),
            new Finance(new Name("Charlotte Oliveiro"), new Amount("1000"),
                getTagSet("neighbours")),
            new Finance(new Name("David Li"), new Amount("1000"),
                getTagSet("family")),
            new Finance(new Name("Irfan Ibrahim"), new Amount("1000"),
                getTagSet("classmates")),
            new Finance(new Name("Roy Balakrishnan"), new Amount("1000"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyFinanceAddressBook getSampleFinanceAddressBook() {
        FinanceAddressBook sampleAb = new FinanceAddressBook();
        for (Finance sampleFinance : getSampleFinances()) {
            sampleAb.addFinance(sampleFinance);
        }
        return sampleAb;
    }

    public static Course[] getSampleCourses() {
        return new Course[] {
            new Course(new Name("Alex Yeoh"), new ID("1000"),
                getTagSet("friends")),
            new Course(new Name("Bernice Yu"), new ID("1000"),
                getTagSet("colleagues", "friends")),
            new Course(new Name("Charlotte Oliveiro"), new ID("1000"),
                getTagSet("neighbours")),
            new Course(new Name("David Li"), new ID("1000"),
                getTagSet("family")),
            new Course(new Name("Irfan Ibrahim"), new ID("1000"),
                getTagSet("classmates")),
            new Course(new Name("Roy Balakrishnan"), new ID("1000"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyCourseAddressBook getSampleCourseAddressBook() {
        CourseAddressBook sampleAb = new CourseAddressBook();
        for (Course sampleCourse : getSampleCourses()) {
            sampleAb.addCourse(sampleCourse);
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
