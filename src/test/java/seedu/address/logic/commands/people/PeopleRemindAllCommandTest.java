package seedu.address.logic.commands.people;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.people.PeopleRemindAllCommand.MESSAGE_REMINDALL_SUCCESS_EMAIL;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalUser.getTypicalUserData;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;

public class PeopleRemindAllCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_emptyUserData_failure() {
        PeopleRemindAllCommand peopleRemindallCommand = new PeopleRemindAllCommand();

        assertCommandFailure(peopleRemindallCommand, model, Messages.MESSAGE_EMPTY_USER_DATA);
    }

    @Test
    public void execute_unfiltered_success() {
        model.setUserData(getTypicalUserData());
        List<Person> lastShownList = model.getFilteredPersonList();

        String expectedMessage = "";
        for (Person p: lastShownList) {
            if (!p.getLoans().getTotal().isZero()) {
                expectedMessage += String.format(PeopleRemindAllCommand.MESSAGE_REMINDALL_SUCCESS,
                        p.getName(), p.getLoans().getTotal());
            }
        }
        expectedMessage += MESSAGE_REMINDALL_SUCCESS_EMAIL;

        assertCommandSuccess(new PeopleRemindAllCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filtered_success() {
        model.setUserData(getTypicalUserData());

        showPersonAtIndex(model, INDEX_SECOND);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        List<Person> lastShownList = model.getFilteredPersonList();

        String expectedMessage = "";
        for (Person p: lastShownList) {
            if (!p.getLoans().getTotal().isZero()) {
                expectedMessage += String.format(PeopleRemindAllCommand.MESSAGE_REMINDALL_SUCCESS,
                        p.getName(), p.getLoans().getTotal());
            }
        }
        expectedMessage += MESSAGE_REMINDALL_SUCCESS_EMAIL;

        assertCommandSuccess(new PeopleRemindAllCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allPeopleWithZeroLoan_failure() {
        model.setUserData(getTypicalUserData());
        Person alice = new PersonBuilder(ALICE).withLoans().build();
        Person bob = new PersonBuilder(BOB).withLoans().build();
        Person carl = new PersonBuilder(CARL).withLoans().build();
        AddressBook ab = new AddressBookBuilder()
                .withPerson(alice)
                .withPerson(bob)
                .withPerson(carl).build();
        model.setAddressBook(ab);

        String expectedMessage = "No one owes you money :(";

        assertCommandFailure(new PeopleRemindAllCommand(), model, expectedMessage);
    }
}
