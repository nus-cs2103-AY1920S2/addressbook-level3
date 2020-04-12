package seedu.address.testutil;

import seedu.address.model.modelStaff.Staff;
import seedu.address.model.modelStaff.StaffAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static seedu.address.logic.commands.CommandTestUtil.*;

/**
 * A utility class containing a list of {@code Teacher} objects to be used in tests.
 */
public class TypicalTeacher {

    public static final Staff TEACHER_ALICE = new TeacherBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withSalary("1000")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Staff TEACHER_BENSON = new TeacherBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withSalary("1000")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Staff CARL = new TeacherBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withSalary("1000").withAddress("wall street").build();
    public static final Staff DANIEL = new TeacherBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withSalary("1000").withAddress("10th street").withTags("friends").build();
    public static final Staff ELLE = new TeacherBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withSalary("1000").withAddress("michegan ave").build();
    public static final Staff FIONA = new TeacherBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withSalary("1000").withAddress("little tokyo").build();
    public static final Staff GEORGE = new TeacherBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withSalary("1000").withAddress("4th street").build();

    // Manually added Twins same everything
    public static final Staff DEFAULT_STAFF_1 = new TeacherBuilder().build();
    public static final Staff DEFAULT_STAFF_2 = new TeacherBuilder().build();
    public static final Staff TWINS_A1 = new TeacherBuilder().withName("Andy").withPhone("8482424")
            .withEmail("andy@example.com").withSalary("2000").withAddress("chicago").build();
    public static final Staff TWINS_A2 = new TeacherBuilder().withName("Andy").withPhone("8482424")
            .withEmail("andy@example.com").withSalary("2000").withAddress("chicago").build();
    // Manually added Twins same everything except gender
    public static final Staff TWINS_B1 = new TeacherBuilder().withName("Ben").withID("16100").
            withLevel("admin").withGender("f").withPhone("8482424").withEmail("ben@example.com").
            withSalary("1000").withAddress("mexico").build();
    public static final Staff TWINS_B2 = new TeacherBuilder().withName("Ben").withID("16100").
            withLevel("admin").withGender("m").withPhone("8482424").withEmail("ben@example.com").
            withSalary("1000").withAddress("mexico").build();
    // Manually added Twins same everything except level
    public static final Staff TWINS_C1 = new TeacherBuilder().withName("Christina").withID("16100").
            withLevel("teacher").withGender("f").withPhone("8482424").withEmail("christina@example.com").
            withSalary("1000").withAddress("mexico").build();
    public static final Staff TWINS_C2 = new TeacherBuilder().withName("Christina").withID("16100").
            withLevel("admin").withGender("f").withPhone("8482424").withEmail("christina@example.com").
            withSalary("1000").withAddress("mexico").build();
    // Manually added Twins same everything except id
    public static final Staff TWINS_D1 = new TeacherBuilder().withName("Daniel").withID("16100").
            withLevel("admin").withGender("m").withPhone("8482424").withEmail("daniel@example.com").
            withSalary("1000").withAddress("mexico").build();
    public static final Staff TWINS_D2 = new TeacherBuilder().withName("Daniel").withID("17100").
            withLevel("admin").withGender("m").withPhone("8482424").withEmail("daniel@example.com").
            withSalary("1000").withAddress("mexico").build();

    // Manually added - Staff's details found in {@code CommandTestUtil}
    public static final Staff AMY = new TeacherBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withSalary(VALID_SALARY_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Staff BOB = new TeacherBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withSalary(VALID_SALARY_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    private TypicalTeacher() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical teachers.
     */
    public static StaffAddressBook getTypicalTeacherAddressBook() {
        StaffAddressBook ab = new StaffAddressBook();
        for (Staff teacher : getTypicalTeachers()) {
            ab.add(teacher);
        }
        return ab;
    }

    public static List<Staff> getTypicalTeachers() {
        return new ArrayList<>(Arrays.asList(TEACHER_ALICE, TEACHER_BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
