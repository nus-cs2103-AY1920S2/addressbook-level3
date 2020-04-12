package seedu.address.testutil;

import seedu.address.model.modelFinance.Finance;
import seedu.address.model.modelFinance.FinanceAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static seedu.address.logic.commands.CommandTestUtil.*;

/**
 * A utility class containing a list of {@code Finance} objects to be used in tests.
 */
public class TypicalFinance {

    public static final Finance FINANCE_ALICE = new FinanceBuilder().withName("Alice Pauline")
            .withFinanceType("m")
            .withDate("2020-01-01")
            .withAmount("999")
            .withCourseID("829")
            .withStudentID("33")
            .withTeacherID("21")
            .withTags("friends").build();
    public static final Finance FINANCE_BENSON = new FinanceBuilder().withName("Benson Meier")
            .withFinanceType("m")
            .withDate("2020-02-01")
            .withAmount("999")
            .withCourseID("829")
            .withStudentID("33")
            .withTeacherID("21")
            .withTags("owesMoney", "friends").build();
    public static final Finance CARL = new FinanceBuilder().withName("Carl Kurz").withFinanceType("m")
            .withDate("2020-03-01").withAmount("999").withCourseID("829")
            .withStudentID("33")
            .withTeacherID("21").build();
    public static final Finance DANIEL = new FinanceBuilder().withName("Daniel Meier").withFinanceType("m")
            .withDate("2020-04-01").withAmount("999").withCourseID("829")
            .withStudentID("33")
            .withTeacherID("21")
            .withTags("friends").build();
    public static final Finance ELLE = new FinanceBuilder().withName("Elle Meyer").withFinanceType("m")
            .withDate("2020-05-01").withAmount("999").withCourseID("829")
            .withStudentID("33")
            .withTeacherID("21").build();
    public static final Finance FIONA = new FinanceBuilder().withName("Fiona Kunz").withFinanceType("m")
            .withDate("2020-06-01").withAmount("999").withCourseID("829")
            .withStudentID("33")
            .withTeacherID("21").build();
    public static final Finance GEORGE = new FinanceBuilder().withName("George Best").withFinanceType("m")
            .withDate("2020-07-01").withAmount("999").withCourseID("829")
            .withStudentID("33")
            .withTeacherID("21").build();

    // Manually added
    public static final Finance HOON = new FinanceBuilder().withName("Hoon Meier").withFinanceType("m")
            .withDate("2020-08-01").withAmount("999").withCourseID("829")
            .withStudentID("33")
            .withTeacherID("21").build();
    public static final Finance IDA = new FinanceBuilder().withName("Ida Mueller").withFinanceType("m")
            .withDate("2020-09-01").withAmount("999").withCourseID("829")
            .withStudentID("33")
            .withTeacherID("21").build();

    // Manually added - Finance's details found in {@code CommandTestUtil}
    public static final Finance AMY = new FinanceBuilder().withName(VALID_NAME_AMY).withFinanceType(VALID_FINANCETYPE_AMY)
            .withDate(VALID_DATE_AMY).withAmount(VALID_AMOUNT_AMY).withTags(VALID_TAG_FRIEND).withCourseID(VALID_COURSEID_AMY)
            .withStudentID(VALID_STUDENTID_AMY)
            .withTeacherID(VALID_TEACHERID_AMY).build();
    public static final Finance BOB = new FinanceBuilder().withName(VALID_NAME_BOB).withFinanceType(VALID_FINANCETYPE_BOB)
            .withDate(VALID_DATE_BOB).withAmount(VALID_AMOUNT_BOB).withCourseID(VALID_COURSEID_BOB)
            .withStudentID(VALID_STUDENTID_BOB)
            .withTeacherID(VALID_TEACHERID_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();


    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalFinance() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical finances.
     */
    public static FinanceAddressBook getTypicalFinanceAddressBook() {
        FinanceAddressBook ab = new FinanceAddressBook();
        for (Finance finance : getTypicalFinances()) {
            ab.add(finance);
        }
        return ab;
    }

    public static List<Finance> getTypicalFinances() {
        return new ArrayList<>(Arrays.asList(FINANCE_ALICE, FINANCE_BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
