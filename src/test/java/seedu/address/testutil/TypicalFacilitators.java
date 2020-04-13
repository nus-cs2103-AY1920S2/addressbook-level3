package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OFFICE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OFFICE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.facilitator.Facilitator;

/**
 * A utility class containing a list of {@code Facilitator} objects to be used in tests.
 */
public class TypicalFacilitators {

    public static final Facilitator ALICE = new FacilitatorBuilder().withName("Alice Pauline")
            .withOffice("COM2-08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withModuleCodes("CS2103T").build();
    public static final Facilitator BENSON = new FacilitatorBuilder().withName("Benson Meier")
            .withOffice("COM3-02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withModuleCodes("CS2101", "CS2103T").build();
    public static final Facilitator CARL = new FacilitatorBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withOffice("AS6-06-11").withModuleCodes("ES2660").build();
    public static final Facilitator DANIEL = new FacilitatorBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withOffice("AS4-06-13").withModuleCodes("CS2101").build();
    public static final Facilitator ELLE = new FacilitatorBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withOffice("S16-03-03").withModuleCodes("ST2334").build();
    public static final Facilitator FIONA = new FacilitatorBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withOffice("S12-02-03").withModuleCodes("MA1521").build();
    public static final Facilitator GEORGE = new FacilitatorBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withOffice("TP-SR4").withModuleCodes("GEQ1000").build();

    // Manually added
    public static final Facilitator HOON = new FacilitatorBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withOffice("UT-SR12").build();
    public static final Facilitator IDA = new FacilitatorBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withOffice("COM1-01-03").build();

    // Manually added - Facilitator's details found in {@code CommandTestUtil}
    public static final Facilitator AMY = new FacilitatorBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withOffice(VALID_OFFICE_AMY).withModuleCodes(VALID_MODULE_CODE_CS2101).build();
    public static final Facilitator BOB = new FacilitatorBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withOffice(VALID_OFFICE_BOB)
            .withModuleCodes(VALID_MODULE_CODE_CS2103T, VALID_MODULE_CODE_CS2101).build();

    public static final Facilitator BENSON_NULL_PHONE = new FacilitatorBuilder().withName("Benson Meier")
            .withOffice("COM3-02-25").withEmail("johnd@example.com")
            .withModuleCodes("CS2101", "CS2103T").build();
    public static final Facilitator BENSON_NULL_EMAIL = new FacilitatorBuilder().withName("Benson Meier")
            .withOffice("COM3-02-25").withPhone("98765432")
            .withModuleCodes("CS2101", "CS2103T").build();
    public static final Facilitator BENSON_NULL_OFFICE = new FacilitatorBuilder().withName("Benson Meier")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withModuleCodes("CS2101", "CS2103T").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalFacilitators() {} // prevents instantiation

    public static List<Facilitator> getTypicalFacilitators() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
