package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalGroups;
import seedu.address.testutil.TypicalPersons;

public class ViewCommandTest {

    private Model personModel = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
    private Model groupModel = new ModelManager(TypicalGroups.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        ViewCommand firstViewCommand = new ViewCommand(INDEX_FIRST_PERSON, "places", InsightType.PERSON);
        ViewCommand secondViewCommand = new ViewCommand(INDEX_SECOND_PERSON, "places", InsightType.PERSON);
        ViewCommand thirdViewCommand = new ViewCommand(INDEX_FIRST_PERSON, "recent", InsightType.PERSON);
        ViewCommand fourthViewCommand = new ViewCommand(INDEX_FIRST_PERSON, "places", InsightType.GROUP);

        // same object -> returns true
        assertTrue(firstViewCommand.equals(firstViewCommand));

        // same values -> returns true
        ViewCommand firstViewCommandCopy = new ViewCommand(INDEX_FIRST_PERSON, "places", InsightType.PERSON);
        assertTrue(firstViewCommand.equals(firstViewCommandCopy));

        // different types -> returns false
        assertFalse(firstViewCommand.equals(new ClearCommand()));

        // null -> returns false
        assertFalse(firstViewCommand.equals(null));

        // different index parameter -> returns false
        assertFalse(firstViewCommand.equals(secondViewCommand));

        // different parameter parameter -> returns false
        assertFalse(firstViewCommand.equals(thirdViewCommand));

        //different insight type parameter -> returns false
        assertFalse(firstViewCommand.equals(fourthViewCommand));
    }

    @Test
    public void execute_invalidPersonIndex_failure() {
        showPersonAtIndex(personModel, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = Index.fromOneBased(personModel.getFilteredPersonList().size() + 1);
        assertTrue(outOfBoundIndex.getZeroBased() < personModel.getAddressBook().getPersonList().size());
        ViewCommand viewCommand = new ViewCommand(outOfBoundIndex, "places", InsightType.PERSON);
        assertCommandFailure(viewCommand, personModel, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidGroupIndex_failure() {
        Index outOfBoundIndex = INDEX_FIRST_PERSON;
        ViewCommand viewCommand = new ViewCommand(outOfBoundIndex, "recent", InsightType.GROUP);
        assertCommandFailure(viewCommand, personModel, Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
    }

    @Test
    public void execute_viewAll_success() {
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_PERSON, "all", InsightType.ALL);
        CommandResult result = new CommandResult(ViewCommand.MESSAGE_ALL_EVENTS, ViewType.ALL);
        assertCommandSuccess(viewCommand, personModel, result, personModel);
    }

    @Test
    public void execute_viewAllRecent_success() {
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_PERSON, "recent", InsightType.ALL);
        CommandResult result = new CommandResult(ViewCommand.MESSAGE_RECENT_ALL, ViewType.RECENT);
        assertCommandSuccess(viewCommand, personModel, result, personModel);
    }

    @Test
    public void execute_viewTime_success() {
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_PERSON, "time", InsightType.ALL);
        CommandResult result = new CommandResult(ViewCommand.MESSAGE_ALL_TIME, ViewType.TIME);
        assertCommandSuccess(viewCommand, personModel, result, personModel);
    }

    @Test
    public void execute_viewPersonPlaces_success() {
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_PERSON, "places", InsightType.PERSON);
        String expectedMessage = ViewCommand.MESSAGE_PLACE + "Alice Pauline.";
        CommandResult result = new CommandResult(expectedMessage, ViewType.PLACES);
        assertCommandSuccess(viewCommand, personModel, result, personModel);
    }

    @Test
    public void execute_viewPersonActivities_success() {
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_PERSON, "activities", InsightType.PERSON);
        String expectedMessage = ViewCommand.MESSAGE_ACTIVITY + "Alice Pauline.";
        CommandResult result = new CommandResult(expectedMessage, ViewType.ACTIVITIES);
        assertCommandSuccess(viewCommand, personModel, result, personModel);
    }

    @Test
    public void execute_viewPersonRecent_success() {
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_PERSON, "recent", InsightType.PERSON);
        String expectedMessage = ViewCommand.MESSAGE_RECENT_PERSON + "Alice Pauline.";
        CommandResult result = new CommandResult(expectedMessage, ViewType.RECENT);
        assertCommandSuccess(viewCommand, personModel, result, personModel);
    }

    @Test
    public void execute_viewGroupPlaces_success() {
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_PERSON, "places", InsightType.GROUP);
        String expectedMessage = ViewCommand.MESSAGE_PLACE + "SoC Friends.";
        CommandResult result = new CommandResult(expectedMessage, ViewType.PLACES);
        assertCommandSuccess(viewCommand, groupModel, result, groupModel);
    }

    @Test
    public void execute_viewGroupActivities_success() {
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_PERSON, "activities", InsightType.GROUP);
        String expectedMessage = ViewCommand.MESSAGE_ACTIVITY + "SoC Friends.";
        CommandResult result = new CommandResult(expectedMessage, ViewType.ACTIVITIES);
        assertCommandSuccess(viewCommand, groupModel, result, groupModel);
    }

    @Test
    public void execute_viewGroupRecent_success() {
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_PERSON, "recent", InsightType.GROUP);
        String expectedMessage = ViewCommand.MESSAGE_RECENT_PERSON + "SoC Friends.";
        CommandResult result = new CommandResult(expectedMessage, ViewType.RECENT);
        assertCommandSuccess(viewCommand, groupModel, result, groupModel);
    }
}
