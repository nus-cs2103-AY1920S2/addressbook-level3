//package team.easytravel.logic.commands.packinglisttest;
//
//import static team.easytravel.logic.commands.CommandTestUtil.assertPackingListItemCommandSuccess;
//import static team.easytravel.testutil.TypicalIndexes.INDEX_FIRST;
//
//import org.junit.jupiter.api.Test;
//
//import team.easytravel.logic.commands.packinglist.DeleteItemCommand;
//import team.easytravel.model.Model;
//import team.easytravel.model.ModelManager;
//import team.easytravel.model.listmanagers.AccommodationBookingManager;
//import team.easytravel.model.listmanagers.ActivityManager;
//import team.easytravel.model.listmanagers.FixedExpenseManager;
//import team.easytravel.model.listmanagers.PackingListManager;
//import team.easytravel.model.listmanagers.TransportBookingManager;
//import team.easytravel.model.listmanagers.UserPrefs;
//import team.easytravel.model.listmanagers.packinglistitem.PackingListItem;
//import team.easytravel.model.trip.TripManager;
//
//
///**
// * The type Delete item command test.
// */
//public class DeleteItemCommandTest {
//    private Model model = new ModelManager(new TransportBookingManager(), new FixedExpenseManager(),
//        new PackingListManager(), new ActivityManager(), new AccommodationBookingManager(),
//        new TripManager(), new UserPrefs());
//
//    /**
//     * Execute valid index unfiltered list success.
//     */
//    @Test
//    public void execute_validIndexUnfilteredList_success() {
//        PackingListItem itemToDelete = model.getFilteredPackingList().get(INDEX_FIRST.getZeroBased());
//        DeleteItemCommand deleteCommand = new DeleteItemCommand(INDEX_FIRST);
//
//        String expectedMessage = String.format(DeleteItemCommand.MESSAGE_DELETE_ITEM_SUCCESS,
//                itemToDelete);
//
//        ModelManager expectedModel = new ModelManager(model.getTransportBookingManager(),
//                model.getFixedExpenseManager(), model.getPackingListManager(), model.getActivityManager(),
//                model.getAccommodationBookingManager(), model.getTripManager(), new UserPrefs());
//
//        expectedModel.deletePackingListItem(itemToDelete);
//
//        assertPackingListItemCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
//    }
//}
