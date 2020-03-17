package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.TypicalOrders.getTypicalReturnOrderBook;

import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;

/**
 * Contains integration tests (interactions with the Model) and unit test for
 * {@code NearbyCommand}.
 */
class NearbyCommandTest {
    private Model model = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());
    private Model expectedModel;
    private Index invalidPostalSector;
    private Predicate<Order> ordersInJurong;

    @BeforeEach
    void setUp() {
        invalidPostalSector = Index.fromOneBased(4000);
        ordersInJurong = order -> {
            String[] postalSectors = new String[]{"S60", "S61", "S62", "S63", "S64"};
            String orderAddress = order.getAddress().toString();
            for (String sector : postalSectors) {
                if (orderAddress.contains(sector)) {
                    return true;
                }
            }
            return false;
        };
    }

    @Test
    void execute_validPostalSectorUnfilteredList_success() {
        Index postalSector = Index.fromOneBased(64);
        Optional<String> location = NearbyCommandUtil.getGeneralLocation(postalSector);
        if (location.isEmpty()) {
            fail("Given postal sector is not valid");
        }

        NearbyCommand nearbyCommand = new NearbyCommand(postalSector);
        expectedModel = new ModelManager(model.getOrderBook(), model.getReturnOrderBook(), new UserPrefs());
        expectedModel.updateFilteredOrderList(ordersInJurong);
        String expectedMessage = String.format(NearbyCommand.MESSAGE_SUCCESS,
                location.get());

        assertCommandSuccess(nearbyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_validPostalSectorNoMatchingOrderUnfilteredList_success() {
        Index postalSector = Index.fromOneBased(7);
        Optional<String> location = NearbyCommandUtil.getGeneralLocation(postalSector);
        if (location.isEmpty()) {
            fail("Given postal sector is not valid");
        }

        String expectedMessage = String.format(NearbyCommand.MESSAGE_SUCCESS,
                location.get());
        NearbyCommand nearbyCommand = new NearbyCommand(postalSector);
        expectedModel = new ModelManager(model.getOrderBook(), model.getReturnOrderBook(), new UserPrefs());
        showNoOrder(expectedModel);

        assertCommandSuccess(nearbyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_invalidPostalSectorUnfilteredList_throwsCommandException() {
        NearbyCommand nearbyCommand = new NearbyCommand(invalidPostalSector);
        assertCommandFailure(nearbyCommand, model, NearbyCommand.MESSAGE_FAILURE);
    }

    /**
     * Updates {@code model}'s filtered list to show no orders.
     *
     * @param model used for changing filtered list
     */
    private void showNoOrder(Model model) {
        model.updateFilteredOrderList(p -> false);
        assertTrue(model.getFilteredOrderList().isEmpty());
    }
}
