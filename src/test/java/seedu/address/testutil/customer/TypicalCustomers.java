package seedu.address.testutil.customer;

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

import seedu.address.model.InventorySystem;
import seedu.address.model.customer.Customer;

/**
 * A utility class containing a list of {@code Customer} objects to be used in tests.
 */
public class TypicalCustomers {

    public static final String ALICE_ID = "a4365691-ea10-47ad-b33b-fc038f1e5e81";
    public static final String BOB_ID = "a4365692-ea10-47ad-b33b-fc038f1e5e81";
    public static final String CARL_ID = "a4365693-ea10-47ad-b33b-fc038f1e5e81";
    public static final String DANIEL_ID = "a4365694-ea10-47ad-b33b-fc038f1e5e81";
    public static final String ELLE_ID = "a4365695-ea10-47ad-b33b-fc038f1e5e81";
    public static final String FIONA_ID = "a4365696-ea10-47ad-b33b-fc038f1e5e81";
    public static final String GEORGE_ID = "a4365697-ea10-47ad-b33b-fc038f1e5e81";
    public static final String HOON_ID = "a4365698-ea10-47ad-b33b-fc038f1e5e81";
    public static final String IDA_ID = "a4365699-ea10-47ad-b33b-fc038f1e5e81";
    public static final String AMY_ID = "a4365680-ea10-47ad-b33b-fc038f1e5e81";

    public static final Customer ALICE = new CustomerBuilder(ALICE_ID).withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Customer BENSON = new CustomerBuilder(BOB_ID).withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Customer CARL = new CustomerBuilder(CARL_ID).withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final Customer DANIEL = new CustomerBuilder(DANIEL_ID).withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends").build();
    public static final Customer ELLE = new CustomerBuilder(ELLE_ID).withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Customer FIONA = new CustomerBuilder(FIONA_ID).withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Customer GEORGE = new CustomerBuilder(GEORGE_ID).withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Customer HOON = new CustomerBuilder(HOON_ID).withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Customer IDA = new CustomerBuilder(IDA_ID).withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Customer's details found in {@code CommandTestUtil}
    public static final Customer AMY =
            new CustomerBuilder(AMY_ID).withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Customer BOB =
            new CustomerBuilder(BOB_ID).withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalCustomers() {} // prevents instantiation

    /**
     * Returns an {@code InventorySystem} with all the typical persons.
     */
    public static InventorySystem getTypicalInventorySystem() {
        InventorySystem ab = new InventorySystem();
        for (Customer customer : getTypicalPersons()) {
            ab.addPerson(customer);
        }
        return ab;
    }

    public static List<Customer> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
