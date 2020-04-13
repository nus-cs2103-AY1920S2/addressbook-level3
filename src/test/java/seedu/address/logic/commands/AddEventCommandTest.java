package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.AddEventTypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
//import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
//import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
//import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
//import seedu.address.model.person.Person;
import seedu.address.model.person.Time;
//import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddEventCommand.
 */
public class AddEventCommandTest {

    /*    @Test //need fix
        public void execute_addEventUnfilteredList_success() {
            final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
            final String activity = "test";
            final String place = "anywhere";

            Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
            Person editedPerson = new PersonBuilder(
            model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                    .withName(firstPerson.getName().fullName).withTime("0h 30m").withActivityList("test")
                    .withPlaceList("anywhere").build();

            Event event = new Event(activity, place, 30, 0);
            event.setWithPerson(INDEX_FIRST_PERSON.getOneBased());
            AddEventCommand addEventCommand = new AddEventCommand(event);

            String expectedMessage = String.format(AddEventCommand.MESSAGE_SUCCESS, editedPerson);

            Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
            expectedModel.setPerson(firstPerson, editedPerson);
            expectedModel.addEvent(event);

            assertCommandSuccess(addEventCommand, model, expectedMessage, expectedModel);

            assertTrue(model.getFilteredEventList().equals(expectedModel.getFilteredEventList()));
        }*/

    /*    @Test need fix
        public void execute_addEventFilteredList_success() {
            final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
            final String activity = "test";
            final String place = "anywhere";

            showPersonAtIndex(model, INDEX_SECOND_PERSON);

            Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
            Person editedPerson = new PersonBuilder(
            model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                    .withName(firstPerson.getName().fullName).withTime("1h 1m").withActivityList("test")
                    .withPlaceList("anywhere").build();

            Event event = new Event(activity, place, 1, 1);
            event.setWithPerson(INDEX_FIRST_PERSON.getOneBased());
            AddEventCommand addEventCommand = new AddEventCommand(event);

            String expectedMessage = String.format(AddEventCommand.MESSAGE_SUCCESS, editedPerson);

            Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
            expectedModel.setPerson(firstPerson, editedPerson);

            assertCommandSuccess(addEventCommand, model, expectedMessage, expectedModel);
        }*/

    @Test
    public void execute_invalidTimeUnfilteredList_failure() {
        final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        final String activity = "test";
        final String place = "anywhere";
        final Time time = new Time(0, 0);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        Event event = new Event(activity, place, time);
        event.setWithPerson(outOfBoundIndex.getOneBased());
        AddEventCommand addEventCommand = new AddEventCommand(event);

        assertCommandFailure(addEventCommand, model, AddEventCommand.MESSAGE_INVALID_TIME);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        final String activity = "test";
        final String place = "anywhere";
        final Time time = new Time(30, 0);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        Event event = new Event(activity, place, time);
        event.setWithPerson(outOfBoundIndex.getOneBased());
        AddEventCommand addEventCommand = new AddEventCommand(event);

        assertCommandFailure(addEventCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        final String activity = "test";
        final String place = "anywhere";
        final Time time = new Time(30, 0);
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        Event event = new Event(activity, place, time);
        event.setWithPerson(outOfBoundIndex.getOneBased());
        AddEventCommand addEventCommand = new AddEventCommand(event);

        assertCommandFailure(addEventCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /*    @Test need fix
        public void execute_addEventOfMoreThan1HourUnfilteredList_success() {
            final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
            final String activity = "test";
            final String place = "anywhere";
            final Time time = new Time(1, 01);

            Person firstPerson = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
            Person editedPerson = new PersonBuilder(
            model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased()))
                    .withName(firstPerson.getName().fullName).withTime("1h 1m").withActivityList("test")
                    .withPlaceList("anywhere").build();

            Event event = new Event(activity, place, 1, 01);
            event.setWithPerson(INDEX_THIRD_PERSON.getOneBased());
            AddEventCommand addEventCommand = new AddEventCommand(event);

            Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
            expectedModel.setPerson(firstPerson, editedPerson);

            String expectedMessage = String.format(AddEventCommand.MESSAGE_SUCCESS, editedPerson);

            assertCommandSuccess(addEventCommand, model, expectedMessage, expectedModel);
        }*/

    @Test
    public void equals() {
        final String activity = "test";
        final String place = "anywhere";
        Event finalEvent = new Event(activity, place, new Time(30, 0));
        finalEvent.setWithPerson(1);
        final AddEventCommand standardCommand = new AddEventCommand(finalEvent);

        AddEventCommand commandWithSameValues = new AddEventCommand(finalEvent);

        //same values -> returns true
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        Event anotherEvent = new Event(activity, place, new Time(30, 0));
        anotherEvent.setWithPerson(2);
        assertFalse(standardCommand.equals(new AddEventCommand(anotherEvent)));

        // different place -> returns false
        Event thirdEvent = new Event(activity, "anywhere 2", new Time(30, 0));
        thirdEvent.setWithPerson(1);
        assertFalse(standardCommand.equals(new AddEventCommand(thirdEvent)));

        // different activity -> returns false
        Event fourthEvent = new Event("test 2", place, new Time(30, 0));
        fourthEvent.setWithPerson(1);
        assertFalse(standardCommand.equals(new AddEventCommand(fourthEvent)));

        // different time -> returns false
        Event fifthEvent = new Event(activity, place, new Time(45, 0));
        fifthEvent.setWithPerson(1);
        assertFalse(standardCommand.equals(new AddEventCommand(fifthEvent)));
    }
}
