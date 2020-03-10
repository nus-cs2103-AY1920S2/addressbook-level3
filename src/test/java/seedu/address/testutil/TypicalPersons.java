package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.expenditure.Expenditure;

/**
 * A utility class containing a list of {@code Expenditure} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Expenditure ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withAmount(3.14)
            .withId("94351253")
            .withTags("friends").build();
    public static final Expenditure BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withAmount(3.14).withId("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Expenditure CARL = new PersonBuilder().withName("Carl Kurz").withId("95352563")
            .withAmount(3.14).withAddress("wall street").build();
    public static final Expenditure DANIEL = new PersonBuilder().withName("Daniel Meier").withId("87652533")
            .withAmount(3.14).withAddress("10th street").withTags("friends").build();
    public static final Expenditure ELLE = new PersonBuilder().withName("Elle Meyer").withId("9482224")
            .withAmount(3.14).withAddress("michegan ave").build();
    public static final Expenditure FIONA = new PersonBuilder().withName("Fiona Kunz").withId("9482427")
            .withAmount(3.14).withAddress("little tokyo").build();
    public static final Expenditure GEORGE = new PersonBuilder().withName("George Best").withId("9482442")
            .withAmount(3.14).withAddress("4th street").build();

    // Manually added
    public static final Expenditure HOON = new PersonBuilder().withName("Hoon Meier").withId("8482424")
            .withAmount(3.14).withAddress("little india").build();
    public static final Expenditure IDA = new PersonBuilder().withName("Ida Mueller").withId("8482131")
            .withAmount(3.14).withAddress("chicago ave").build();

    // Manually added - Expenditure's details found in {@code CommandTestUtil}
    public static final Expenditure AMY = new PersonBuilder().withName(VALID_NAME_AMY).withId(VALID_ID_AMY)
            .withAmount(VALID_AMOUNT_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Expenditure BOB = new PersonBuilder().withName(VALID_NAME_BOB).withId(VALID_ID_BOB)
            .withAmount(VALID_AMOUNT_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Expenditure expenditure : getTypicalPersons()) {
            ab.addPerson(expenditure);
        }
        return ab;
    }

    public static List<Expenditure> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
