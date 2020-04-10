package team.easytravel.logic.commands.packinglisttest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.easytravel.testutil.Assert.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.logic.commands.packinglist.AddItemCommand;
import team.easytravel.model.listmanagers.packinglistitem.PackingListItem;
import team.easytravel.testutil.packinglist.PackingListItemBuilder;

public class AddItemCommandTest {
    @Test
    public void constructor_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddItemCommand(null));
    }

    @Test
    public void execute_itemAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingItemAdded modelStub = new ModelStubAcceptingItemAdded();
        PackingListItem validPackingListItem = new PackingListItemBuilder().build();

        CommandResult commandResult = new AddItemCommand(validPackingListItem).execute(modelStub);

        assertEquals(String.format(AddItemCommand.MESSAGE_SUCCESS, validPackingListItem),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPackingListItem), modelStub.packingListItemsAdded);
    }

    @Test
    public void execute_duplicatePackingListItem_throwsCommandException() {
        PackingListItem validPackingListItem = new PackingListItemBuilder().build();
        AddItemCommand addItemCommand = new AddItemCommand(validPackingListItem);
        ModelStubWithItem modelStubWithItem = new ModelStubWithItem(validPackingListItem);

        assertThrows(CommandException.class, ()-> addItemCommand.execute(modelStubWithItem));
    }

    @Test
    public void equals() {
        PackingListItem raincoat = new PackingListItemBuilder().withItemName("Raincoat").build();
        PackingListItem camera = new PackingListItemBuilder().withQuantity(1).build();
        AddItemCommand addRaincoatCommand = new AddItemCommand(raincoat);
        AddItemCommand addCameraCommand = new AddItemCommand(camera);

        // same object -> returns true
        assertTrue(addRaincoatCommand.equals(addRaincoatCommand));

        // same values -> returns true
        AddItemCommand addRaincoatCommandCopy = new AddItemCommand(raincoat);
        assertTrue(addRaincoatCommand.equals(addRaincoatCommandCopy));

        // different types -> returns false
        assertFalse(addRaincoatCommand.equals(1));

        // null -> returns false
        assertFalse(addRaincoatCommand.equals(null));

        // different PackingListItem -> returns false
        assertFalse(addRaincoatCommand.equals(addCameraCommand));
    }

    @Test
    void execute() {
    }

    @Test
    void testEquals() {
    }
}
