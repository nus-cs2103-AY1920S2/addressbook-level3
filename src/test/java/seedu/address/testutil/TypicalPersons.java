package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import seedu.address.model.AddressBook;
import seedu.address.model.profile.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline").withCourse("Computer Science").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier").withCourse("Computer Science").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withCourse("Computer Science").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withCourse("Computer Science").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withCourse("Computer Science").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withCourse("Computer Science").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withCourse("Computer Science").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withCourse("Computer Science").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withCourse("Computer Science").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withCourse(VALID_COURSE_CS).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withCourse(VALID_COURSE_CS).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
