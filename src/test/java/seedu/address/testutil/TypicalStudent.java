package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SALARY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SALARY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import seedu.address.model.modelStudent.Student;
import seedu.address.model.modelStudent.StudentAddressBook;

/**
 * A utility class containing a list of {@code Student} objects to be used in tests.
 */
public class TypicalStudent {

    public static final Student STUDENT_ALICE = new StudentBuilder().withName("Alice Pauline")
            .withCourse("Java Programming")
            .withTags("friends").build();
    public static final Student STUDENT_BENSON = new StudentBuilder().withName("Benson Meier")
            .withCourse("Java Programming")
            .withTags("owesMoney", "friends").build();
    public static final Student CARL = new StudentBuilder().withName("Carl Kurz").withCourse("Java Programming").build();
    public static final Student DANIEL = new StudentBuilder().withName("Daniel Meier").withCourse("Java Programming")
            .withTags("friends").build();
    public static final Student ELLE = new StudentBuilder().withName("Elle Meyer").withCourse("Java Programming").build();
    public static final Student FIONA = new StudentBuilder().withName("Fiona Kunz").withCourse("Java Programming").build();
    public static final Student GEORGE = new StudentBuilder().withName("George Best").withCourse("Java Programming").build();

    // Manually added
    public static final Student HOON = new StudentBuilder().withName("Hoon Meier").withCourse("Java Programming").build();
    public static final Student IDA = new StudentBuilder().withName("Ida Mueller").withCourse("Java Programming").build();

    // Manually added - Student's details found in {@code CommandTestUtil}
    public static final Student AMY = new StudentBuilder().withName(VALID_NAME_AMY).withCourse(VALID_COURSE_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Student BOB = new StudentBuilder().withName(VALID_NAME_BOB).withCourse(VALID_COURSE_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();


    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalStudent() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical students.
     */
    public static StudentAddressBook getTypicalStudentAddressBook() {
        StudentAddressBook ab = new StudentAddressBook();
        for (Student student : getTypicalStudents()) {
            ab.addStudent(student);
        }
        return ab;
    }

    public static List<Student> getTypicalStudents() {
        return new ArrayList<>(Arrays.asList(STUDENT_ALICE, STUDENT_BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
