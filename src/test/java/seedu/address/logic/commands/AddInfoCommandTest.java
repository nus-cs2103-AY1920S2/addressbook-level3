package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.EventSchedule;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.RestaurantBook;
import seedu.address.model.Scheduler;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.testutil.PersonBuilder;

class AddInfoCommandTest {

    private static final String REMARK_STUB = "Some remark";

    private Model model = new ModelManager(getTypicalAddressBook(),
            new RestaurantBook(),
            new Scheduler(),
            new EventSchedule(),
            new UserPrefs());

    @Test
    public void execute_addInfoUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withRemark(REMARK_STUB).build();
        ArrayList<Remark> remarks = new ArrayList<>();
        remarks.add(new Remark(REMARK_STUB));

        AddInfoCommand addInfoCommand = new AddInfoCommand(INDEX_FIRST_PERSON, remarks);

        String expectedMessage = String.format(AddInfoCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        Model expectedModel =
                new ModelManager(new AddressBook(model.getAddressBook()),
                        new RestaurantBook(),
                        new Scheduler(),
                        new EventSchedule(),
                        new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(addInfoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        ArrayList<Remark> remarks = new ArrayList<>();
        remarks.add(new Remark(REMARK_STUB));

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withRemark(REMARK_STUB).build();
        AddInfoCommand addInfoCommand = new AddInfoCommand(INDEX_FIRST_PERSON, remarks);

        String expectedMessage = String.format(AddInfoCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        Model expectedModel =
                new ModelManager(new AddressBook(model.getAddressBook()),
                        new RestaurantBook(),
                        new Scheduler(),
                        new EventSchedule(),
                        new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(addInfoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ArrayList<Remark> remarks = new ArrayList<>();
        remarks.add(new Remark(VALID_REMARK_BOB));
        AddInfoCommand remarkCommand = new AddInfoCommand(outOfBoundIndex, remarks);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list

        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonsList().size());

        ArrayList<Remark> remarks = new ArrayList<>();
        remarks.add(new Remark(VALID_REMARK_BOB));
        AddInfoCommand addInfoCommand = new AddInfoCommand(outOfBoundIndex, remarks);


        assertCommandFailure(addInfoCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ArrayList<Remark> remarks = new ArrayList<>();
        remarks.add(new Remark(VALID_REMARK_AMY));
        ArrayList<Remark> remarkArrayList = new ArrayList<>();
        remarkArrayList.add(new Remark("NO"));
        final AddInfoCommand standardCommand = new AddInfoCommand(INDEX_FIRST_PERSON, remarks);

        // same values -> returns true
        AddInfoCommand commandWithSameValues = new AddInfoCommand(INDEX_FIRST_PERSON, remarks);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddInfoCommand(INDEX_SECOND_PERSON, remarks)));

        // different remark -> returns false
        assertFalse(standardCommand.equals(new AddInfoCommand(INDEX_FIRST_PERSON, remarkArrayList)));
    }
}
