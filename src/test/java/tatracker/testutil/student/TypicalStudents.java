package tatracker.testutil.student;

import static tatracker.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static tatracker.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_MATRIC_AMY;
import static tatracker.logic.commands.CommandTestUtil.VALID_MATRIC_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static tatracker.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static tatracker.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_RATING_AMY;
import static tatracker.logic.commands.CommandTestUtil.VALID_RATING_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static tatracker.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tatracker.model.group.Group;
import tatracker.model.group.GroupType;
import tatracker.model.module.Module;
import tatracker.model.student.Matric;
import tatracker.model.student.Student;

/**
 * A utility class containing a list of {@code Student} objects to be used in tests.
 */
public class TypicalStudents {

    public static final Module CS3243 = new Module("CS3243", "Introduction to AI");
    public static final Module CS2030 = new Module("CS2030", "Programming Methodology II");
    public static final Group G06 = new Group("G06", GroupType.LAB);
    public static final Group T04 = new Group("T04", GroupType.TUTORIAL);
    public static final Matric MATRIC_ALICE = new Matric("A0193235J");
    public static final Matric MATRIC_BENSON = new Matric("A0188621K");
    public static final Matric MATRIC_CARL = new Matric("A0190706L");

    public static final Student ALICE = new StudentBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com")
            //.withMatric("A0193235J")
            .withMatric(MATRIC_ALICE.value)
            .withPhone("94351253")
            .withRating(1)
            .withTags("friends")
            .build();
    public static final Student BENSON = new StudentBuilder().withName("Benson Meier")
            .withEmail("johnd@example.com").withPhone("98765432")
            //.withMatric("A0188621K")
            .withMatric(MATRIC_BENSON.value)
            .withTags("owesMoney", "friends")
            .withRating(2)
            .build();
    public static final Student CARL = new StudentBuilder().withName("Carl Kurz")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            //.withMatric("A0190706L")
            .withMatric(MATRIC_CARL.value)
            .withRating(3)
            .build();
    public static final Student DANIEL = new StudentBuilder().withName("Daniel Meier")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withMatric("A0192154M")
            .withTags("friends")
            .withRating(4)
            .build();
    public static final Student ELLE = new StudentBuilder().withName("Elle Meyer")
            .withPhone("9482224")
            .withEmail("werner@example.com")
            .withMatric("A0190546N")
            .withRating(5)
            .build();
    public static final Student FIONA = new StudentBuilder().withName("Fiona Kunz")
            .withPhone("9482427")
            .withEmail("lydia@example.com")
            .withMatric("A0196074P")
            .withRating(1)
            .build();
    public static final Student GEORGE = new StudentBuilder().withName("George Best")
            .withPhone("9482442")
            .withEmail("anna@example.com")
            .withMatric("A0190289Q")
            .withRating(2)
            .build();

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
            .withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withMatric(VALID_MATRIC_AMY)
            .withTags(VALID_TAG_FRIEND)
            .withRating(VALID_RATING_AMY)
            .build();
    public static final Student BOB = new StudentBuilder()
            .withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withMatric(VALID_MATRIC_BOB)
            .withRating(VALID_RATING_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalStudents() {} // prevents instantiation

    public static List<Student> getTypicalStudents() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
