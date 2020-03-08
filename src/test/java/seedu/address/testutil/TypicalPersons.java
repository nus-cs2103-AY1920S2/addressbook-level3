package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/** A utility class containing a list of {@code Person} objects to be used in tests. */
public class TypicalPersons {

    public static final Person ALICE =
            new PersonBuilder()
                    .withName("Alice Pauline")
                    .withDescription("123, Jurong West Ave 6, #08-111")
                    .withEmail("alice@example.com")
                    .withPriority("94351253")
                    .withTags("friends")
                    .build();
    public static final Person BENSON =
            new PersonBuilder()
                    .withName("Benson Meier")
                    .withDescription("311, Clementi Ave 2, #02-25")
                    .withEmail("johnd@example.com")
                    .withPriority("98765432")
                    .withTags("owesMoney", "friends")
                    .build();
    public static final Person CARL =
            new PersonBuilder()
                    .withName("Carl Kurz")
                    .withPriority("95352563")
                    .withEmail("heinz@example.com")
                    .withDescription("wall street")
                    .build();
    public static final Person DANIEL =
            new PersonBuilder()
                    .withName("Daniel Meier")
                    .withPriority("87652533")
                    .withEmail("cornelia@example.com")
                    .withDescription("10th street")
                    .withTags("friends")
                    .build();
    public static final Person ELLE =
            new PersonBuilder()
                    .withName("Elle Meyer")
                    .withPriority("9482224")
                    .withEmail("werner@example.com")
                    .withDescription("michegan ave")
                    .build();
    public static final Person FIONA =
            new PersonBuilder()
                    .withName("Fiona Kunz")
                    .withPriority("9482427")
                    .withEmail("lydia@example.com")
                    .withDescription("little tokyo")
                    .build();
    public static final Person GEORGE =
            new PersonBuilder()
                    .withName("George Best")
                    .withPriority("9482442")
                    .withEmail("anna@example.com")
                    .withDescription("4th street")
                    .build();

    // Manually added
    public static final Person HOON =
            new PersonBuilder()
                    .withName("Hoon Meier")
                    .withPriority("8482424")
                    .withEmail("stefan@example.com")
                    .withDescription("little india")
                    .build();
    public static final Person IDA =
            new PersonBuilder()
                    .withName("Ida Mueller")
                    .withPriority("8482131")
                    .withEmail("hans@example.com")
                    .withDescription("chicago ave")
                    .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY =
            new PersonBuilder()
                    .withName(VALID_NAME_AMY)
                    .withPriority(VALID_PHONE_AMY)
                    .withEmail(VALID_EMAIL_AMY)
                    .withDescription(VALID_ADDRESS_AMY)
                    .withTags(VALID_TAG_FRIEND)
                    .build();
    public static final Person BOB =
            new PersonBuilder()
                    .withName(VALID_NAME_BOB)
                    .withPriority(VALID_PHONE_BOB)
                    .withEmail(VALID_EMAIL_BOB)
                    .withDescription(VALID_ADDRESS_BOB)
                    .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
                    .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /** Returns an {@code AddressBook} with all the typical persons. */
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
