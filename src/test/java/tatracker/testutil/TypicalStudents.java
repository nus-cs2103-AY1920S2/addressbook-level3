package tatracker.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tatracker.logic.commands.CommandTestUtil;
import tatracker.model.TaTracker;
import tatracker.model.student.Student;

/**
 * A utility class containing a list of {@code Student} objects to be used in tests.
 */
public class TypicalStudents {

    public static final Student ALICE = new StudentBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com")
            .withMatric("A0193235J")
            .withPhone("94351253").withTags("friends").build();
    public static final Student BENSON = new StudentBuilder().withName("Benson Meier")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withMatric("A0188621K")
            .withTags("owesMoney", "friends").build();
    public static final Student CARL = new StudentBuilder().withName("Carl Kurz")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withMatric("A0190706L").build();
    public static final Student DANIEL = new StudentBuilder().withName("Daniel Meier")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withMatric("A0192154M")
            .withTags("friends").build();
    public static final Student ELLE = new StudentBuilder().withName("Elle Meyer")
            .withPhone("9482224")
            .withEmail("werner@example.com")
            .withMatric("A0190546N").build();
    public static final Student FIONA = new StudentBuilder().withName("Fiona Kunz")
            .withPhone("9482427")
            .withEmail("lydia@example.com")
            .withMatric("A0196074P").build();
    public static final Student GEORGE = new StudentBuilder().withName("George Best")
            .withPhone("9482442")
            .withEmail("anna@example.com")
            .withMatric("A0190289Q").build();

    // Manually added
    public static final Student HOON = new StudentBuilder().withName("Hoon Meier")
            .withPhone("8482424")
            .withEmail("stefan@example.com")
            .withMatric("A0191183R").build();
    public static final Student IDA = new StudentBuilder().withName("Ida Mueller")
            .withPhone("8482131")
            .withEmail("hans@example.com")
            .withMatric("A0188967T").build();

    // Manually added - Student's details found in {@code CommandTestUtil}
    public static final Student AMY = new StudentBuilder()
            .withName(CommandTestUtil.VALID_NAME_AMY)
            .withPhone(CommandTestUtil.VALID_PHONE_AMY)
            .withEmail(CommandTestUtil.VALID_EMAIL_AMY)
            .withMatric(CommandTestUtil.VALID_MATRIC_AMY)
            .withTags(CommandTestUtil.VALID_TAG_FRIEND).build();
    public static final Student BOB = new StudentBuilder()
            .withName(CommandTestUtil.VALID_NAME_BOB)
            .withPhone(CommandTestUtil.VALID_PHONE_BOB)
            .withEmail(CommandTestUtil.VALID_EMAIL_BOB)
            .withMatric(CommandTestUtil.VALID_MATRIC_BOB)
            .withTags(CommandTestUtil.VALID_TAG_HUSBAND, CommandTestUtil.VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalStudents() {} // prevents instantiation

    /**
     * Returns an {@code TaTracker} with all the typical students.
     */
    public static TaTracker getTypicalAddressBook() {
        TaTracker ab = new TaTracker();
        for (Student student : getTypicalPersons()) {
            ab.addStudent(student);
        }
        return ab;
    }

    public static List<Student> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
