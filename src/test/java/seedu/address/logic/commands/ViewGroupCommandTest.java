package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showGroupAtIndex;
import static seedu.address.testutil.TypicalGroups.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ViewGroupCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        ViewGroupCommand places = new ViewGroupCommand(INDEX_FIRST_PERSON, "places", 1);
        ViewGroupCommand activities = new ViewGroupCommand(INDEX_FIRST_PERSON, "activities", 1);

        //same object -> returns true
        assertTrue(places.equals(places));
        assertTrue(activities.equals(activities));

        //same values -> returns true
        ViewGroupCommand placesCopy = new ViewGroupCommand(INDEX_FIRST_PERSON, "places", 1);
        assertTrue(places.equals(placesCopy));

        //different types -> returns false
        assertFalse(places.equals(new ClearCommand()));

        //null -> returns false
        assertFalse(places.equals(null));

        //different index
        ViewGroupCommand places2 = new ViewGroupCommand(INDEX_SECOND_PERSON, "places", 1);
        assertFalse(places.equals(places2));

        //different parameters
        assertFalse(places.equals(activities));
    }

    @Test
    public void execute_invalidIndex_failure() {
        showGroupAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGroupList().size() + 1);

        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getGroupList().size());

        ViewGroupCommand command = new ViewGroupCommand(outOfBoundIndex, "places", 1);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidParameter_failure() {
        ViewGroupCommand command = new ViewGroupCommand(INDEX_FIRST_PERSON, "test", 1);

        assertCommandFailure(command, model, String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                ViewGroupCommand.MESSAGE_USAGE));
    }

    //@Test
    //public void execute_places_success() {
    //Group group = model.getFilteredGroupList().get(INDEX_FIRST_PERSON.getZeroBased());
    //ViewCommand_group command = new ViewCommand_group(INDEX_FIRST_PERSON, "places", 1);
    //String expectedMessage = ViewCommand_group.MESSAGE_PLACE + group.getName() + ".";
    //expectedModel.showGroupPlaceList(group);
    //assertCommandSuccess(command, model, expectedMessage, expectedModel);
    //}
}
