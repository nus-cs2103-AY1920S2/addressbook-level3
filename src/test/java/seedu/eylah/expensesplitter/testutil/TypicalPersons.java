package seedu.eylah.expensesplitter.testutil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.eylah.expensesplitter.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Seah")
            .withAmount(new BigDecimal("3.50")).build();

    public static final Person BOB = new PersonBuilder().withName("Bob Tan")
        .withAmount(new BigDecimal("4.50")).build();

    public static final Person CARL = new PersonBuilder().withName("Carl Lim")
        .withAmount(new BigDecimal("5.50")).build();

    public static final Person DANIEL = new PersonBuilder().withName("Daniel Lee")
        .withAmount(new BigDecimal("6.50")).build();

    public static final Person ELLE = new PersonBuilder().withName("Elle Seah")
        .withAmount(new BigDecimal("7.50")).build();

    public static final Person FIONA = new PersonBuilder().withName("Fiona Kwok")
        .withAmount(new BigDecimal("8.50")).build();

    public static final Person GEORGE = new PersonBuilder().withName("Geroge Peet")
        .withAmount(new BigDecimal("9.50")).build();


    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Seah")
        .withAmount(new BigDecimal("10.50")).build();

    public static final Person IDA = new PersonBuilder().withName("IDA Seah")
        .withAmount(new BigDecimal("11.50")).build();


    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BOB, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
