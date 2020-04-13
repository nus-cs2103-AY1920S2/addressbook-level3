package seedu.eylah.expensesplitter.testutil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.eylah.expensesplitter.model.PersonAmountBook;
import seedu.eylah.expensesplitter.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    // -------------- PersonAmountBook ------------------------------------------------------
    public static final Person ALICE = new PersonBuilder().withName("alice seah")
            .withAmount(new BigDecimal("3.50")).build();

    public static final Person BOB = new PersonBuilder().withName("bob tan")
        .withAmount(new BigDecimal("4.50")).build();

    public static final Person CARL = new PersonBuilder().withName("carl lim")
        .withAmount(new BigDecimal("5.50")).build();

    public static final Person DANIEL = new PersonBuilder().withName("daniel lee")
        .withAmount(new BigDecimal("6.50")).build();

    public static final Person ELLE = new PersonBuilder().withName("elle seah")
        .withAmount(new BigDecimal("7.50")).build();

    public static final Person FIONA = new PersonBuilder().withName("fiona kwok")
        .withAmount(new BigDecimal("8.50")).build();

    public static final Person GEORGE = new PersonBuilder().withName("george peet")
        .withAmount(new BigDecimal("9.50")).build();


    // Manually added
    public static final Person HOON = new PersonBuilder().withName("hoon seah")
        .withAmount(new BigDecimal("10.50")).build();

    public static final Person IDA = new PersonBuilder().withName("ida seah")
        .withAmount(new BigDecimal("11.60")).build();

    // -------------- Receipt ------------------------------------------------------

    public static final Person ANNA = new PersonBuilder().withName("anna")
            .withAmount(new BigDecimal("8.33")).build();

    public static final Person BRANDON = new PersonBuilder().withName("brandon")
            .withAmount(new BigDecimal("8.33")).build();

    public static final Person CHARLIE = new PersonBuilder().withName("charlie")
            .withAmount(new BigDecimal("8.33")).build();

    public static final Person DARREN = new PersonBuilder().withName("darren").withAmount(new BigDecimal("9.95"))
        .build();

    public static final Person ELYSHA = new PersonBuilder().withName("elysha").withAmount(new BigDecimal("9.95"))
        .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    public static PersonAmountBook getTypicalPersonAmountBook() {
        PersonAmountBook pABook = new PersonAmountBook();
        for (Person person : getTypicalPersons()) {
            pABook.addPerson(person);
        }
        return pABook;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BOB, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static ArrayList<Person> getTypicalPersonsArrayList() {
        return new ArrayList<>(Arrays.asList(ANNA, BRANDON, CHARLIE));
    }

    public static ArrayList<Person> getTypicalPersonsArrayListV2() {
        return new ArrayList<>(Arrays.asList(DARREN, ELYSHA));
    }

    public static String getTypicalPersonAmountBookToString() {
        PersonAmountBook pABook = new PersonAmountBook();
        for (Person person : getTypicalPersons()) {
            pABook.addPerson(person);
        }
        return pABook.toString();
    }
}
