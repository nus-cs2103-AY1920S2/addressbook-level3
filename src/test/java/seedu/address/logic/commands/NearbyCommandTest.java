package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.FLAG_ORDER_BOOK;
import static seedu.address.logic.parser.CliSyntax.FLAG_RETURN_BOOK;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.TypicalReturnOrders.getTypicalReturnOrderBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.parcel.order.Order;
import seedu.address.model.parcel.returnorder.ReturnOrder;

/**
 * Contains integration tests (interactions with the Model) and unit test for
 * {@code NearbyCommand}.
 */
class NearbyCommandTest {
    private Model model = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());
    private Model expectedModel;
    private String invalidPostalSector;
    private String invalidArea;
    private Predicate<Order> ordersInJurong;
    private Predicate<ReturnOrder> returnOrdersInJurong;
    private Predicate<Order> eastArea;
    private Predicate<ReturnOrder> eastAreaReturn;

    @BeforeEach
    void setUp() {
        invalidPostalSector = "4000";
        invalidArea = "La La Land";
        ordersInJurong = order -> {
            String[] postalSectors = new String[]{"S60", "S61", "S62", "S63", "S64"};
            String orderAddress = order.getAddress().toString();
            return Arrays.stream(postalSectors)
                    .anyMatch(orderAddress::contains);
        };
        returnOrdersInJurong = returnOrder -> {
            String[] postalSectors = new String[]{"S60", "S61", "S62", "S63", "S64"};
            String returnOrderAddress = returnOrder.getAddress().toString();
            return Arrays.stream(postalSectors)
                    .anyMatch(returnOrderAddress::contains);
        };

        List<String> areaRegex = new ArrayList<>(Arrays.asList(
                ".*S16\\d{4}.*", ".*S17\\d{4}.*", ".*S18\\d{4}.*"));
        eastArea = order -> {
            String orderAddress = order.getAddress().toString();
            return areaRegex.stream()
                    .anyMatch(regex -> Pattern.matches(regex, orderAddress));
        };

        eastAreaReturn = returnOrder -> {
            String returnOrderAddress = returnOrder.getAddress().toString();
            return areaRegex.stream()
                    .anyMatch(regex -> Pattern.matches(regex, returnOrderAddress));
        };
    }

    @Test
    void execute_validAreaUnfilteredOrderList_success() {
        String area = "east";
        String searchTerm = FLAG_ORDER_BOOK + " east";
        NearbyCommand nearbyCommand = new NearbyCommand(searchTerm);
        expectedModel = new ModelManager(model.getOrderBook(), model.getReturnOrderBook(), new UserPrefs());
        expectedModel.updateFilteredOrderList(eastArea);
        String expectedMessage = String.format(NearbyCommand.MESSAGE_SUCCESS_AREA, area);
        assertCommandSuccess(nearbyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_validAreaUnfilteredReturnList_success() {
        String area = "east";
        String searchTerm = FLAG_RETURN_BOOK + " " + area;
        NearbyCommand nearbyCommand = new NearbyCommand(searchTerm);
        expectedModel = new ModelManager(model.getOrderBook(), model.getReturnOrderBook(), new UserPrefs());
        expectedModel.updateFilteredReturnOrderList(eastAreaReturn);
        String expectedMessage = String.format(NearbyCommand.MESSAGE_SUCCESS_AREA, area);
        assertCommandSuccess(nearbyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_validAreaUnfilteredBothLists_success() {
        String searchTerm = "east";
        NearbyCommand nearbyCommand = new NearbyCommand(searchTerm);
        expectedModel = new ModelManager(model.getOrderBook(), model.getReturnOrderBook(), new UserPrefs());
        expectedModel.updateFilteredReturnOrderList(eastAreaReturn);
        expectedModel.updateFilteredOrderList(eastArea);
        String expectedMessage = String.format(NearbyCommand.MESSAGE_SUCCESS_AREA, searchTerm);
        assertCommandSuccess(nearbyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_validAreaNoMatchingOrderFilteredOrderList_success() {
        String area = "east";
        String searchTerm = FLAG_ORDER_BOOK + " " + area;
        String expectedMessage = String.format(NearbyCommand.MESSAGE_SUCCESS_AREA, area);
        NearbyCommand nearbyCommand = new NearbyCommand(searchTerm);
        expectedModel = new ModelManager(model.getOrderBook(), model.getReturnOrderBook(), new UserPrefs());
        showNoOrder(expectedModel);
        assertCommandSuccess(nearbyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_validAreaNoMatchingReturnFilteredReturnList_success() {
        String area = "east";
        String searchTerm = FLAG_RETURN_BOOK + " " + area;
        String expectedMessage = String.format(NearbyCommand.MESSAGE_SUCCESS_AREA, area);
        NearbyCommand nearbyCommand = new NearbyCommand(searchTerm);
        expectedModel = new ModelManager(model.getOrderBook(), model.getReturnOrderBook(), new UserPrefs());
        showNoReturn(expectedModel);
        assertCommandSuccess(nearbyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_validPostalSectorUnfilteredOrderList_success() {
        String input = FLAG_ORDER_BOOK + " 64";
        Index postalSector = Index.fromOneBased(64);
        Optional<String> location = NearbyCommandUtil.getGeneralLocation(postalSector);
        if (location.isEmpty()) {
            fail("Given postal sector is not valid");
        }

        NearbyCommand nearbyCommand = new NearbyCommand(input);
        expectedModel = new ModelManager(model.getOrderBook(), model.getReturnOrderBook(), new UserPrefs());
        expectedModel.updateFilteredOrderList(ordersInJurong);
        String expectedMessage = String.format(NearbyCommand.MESSAGE_SUCCESS_POSTAL_SECTOR,
                location.get());

        assertCommandSuccess(nearbyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_validPostalSectorUnfilteredReturnList_success() {
        String input = FLAG_RETURN_BOOK + " 64";
        Index postalSector = Index.fromOneBased(64);
        Optional<String> location = NearbyCommandUtil.getGeneralLocation(postalSector);
        if (location.isEmpty()) {
            fail("Given postal sector is not valid");
        }

        NearbyCommand nearbyCommand = new NearbyCommand(input);
        expectedModel = new ModelManager(model.getOrderBook(), model.getReturnOrderBook(), new UserPrefs());
        expectedModel.updateFilteredReturnOrderList(returnOrdersInJurong);
        String expectedMessage = String.format(NearbyCommand.MESSAGE_SUCCESS_POSTAL_SECTOR,
                location.get());
        assertCommandSuccess(nearbyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_validPostalSectorUnfilteredBothLists_success() {
        String input = "64";
        Index postalSector = Index.fromOneBased(64);
        Optional<String> location = NearbyCommandUtil.getGeneralLocation(postalSector);
        if (location.isEmpty()) {
            fail("Given postal sector is not valid");
        }

        NearbyCommand nearbyCommand = new NearbyCommand(input);
        expectedModel = new ModelManager(model.getOrderBook(), model.getReturnOrderBook(), new UserPrefs());
        expectedModel.updateFilteredReturnOrderList(returnOrdersInJurong);
        expectedModel.updateFilteredOrderList(ordersInJurong);
        String expectedMessage = String.format(NearbyCommand.MESSAGE_SUCCESS_POSTAL_SECTOR,
                location.get());
        assertCommandSuccess(nearbyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_validPostalSectorNoMatchingOrderFilteredOrderList_success() {
        String input = FLAG_ORDER_BOOK + " 7";
        Index postalSector = Index.fromOneBased(7);
        Optional<String> location = NearbyCommandUtil.getGeneralLocation(postalSector);
        if (location.isEmpty()) {
            fail("Given postal sector is not valid");
        }

        String expectedMessage = String.format(NearbyCommand.MESSAGE_SUCCESS_POSTAL_SECTOR,
                location.get());
        NearbyCommand nearbyCommand = new NearbyCommand(input);
        expectedModel = new ModelManager(model.getOrderBook(), model.getReturnOrderBook(), new UserPrefs());
        showNoOrder(expectedModel);
        assertCommandSuccess(nearbyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_validPostalSectorNoMatchingReturnFilteredReturnList_success() {
        String input = FLAG_RETURN_BOOK + " 7";
        Index postalSector = Index.fromOneBased(7);
        Optional<String> location = NearbyCommandUtil.getGeneralLocation(postalSector);
        if (location.isEmpty()) {
            fail("Given postal sector is not valid");
        }

        String expectedMessage = String.format(NearbyCommand.MESSAGE_SUCCESS_POSTAL_SECTOR,
                location.get());
        NearbyCommand nearbyCommand = new NearbyCommand(input);
        expectedModel = new ModelManager(model.getOrderBook(), model.getReturnOrderBook(), new UserPrefs());
        showNoReturn(expectedModel);
        assertCommandSuccess(nearbyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_validPostalSectorNoMatchingOrderFilteredBothLists_success() {
        String input = "7";
        Index postalSector = Index.fromOneBased(7);
        Optional<String> location = NearbyCommandUtil.getGeneralLocation(postalSector);
        if (location.isEmpty()) {
            fail("Given postal sector is not valid");
        }

        String expectedMessage = String.format(NearbyCommand.MESSAGE_SUCCESS_POSTAL_SECTOR,
                location.get());
        NearbyCommand nearbyCommand = new NearbyCommand(input);
        expectedModel = new ModelManager(model.getOrderBook(), model.getReturnOrderBook(), new UserPrefs());
        showNoOrder(expectedModel);
        showNoReturn(expectedModel);
        assertCommandSuccess(nearbyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_invalidPostalSectorUnfilteredList_throwsCommandException() {
        NearbyCommand nearbyCommand = new NearbyCommand(invalidPostalSector);
        assertCommandFailure(nearbyCommand, model, NearbyCommand.MESSAGE_FAILURE_POSTAL_SECTOR);
    }

    @Test
    void execute_invalidAreaUnfilteredList_throwsCommandException() {
        NearbyCommand nearbyCommand = new NearbyCommand(invalidArea);
        assertCommandFailure(nearbyCommand, model, NearbyCommand.MESSAGE_FAILURE_AREA);
    }

    /**
     * Updates {@code model}'s filtered order list to show no orders.
     *
     * @param model used for changing filtered order list
     */
    private void showNoOrder(Model model) {
        model.updateFilteredOrderList(p -> false);
        assertTrue(model.getFilteredOrderList().isEmpty());
    }

    /**
     * Updates {@code model}'s filtered return list to show no return orders.
     *
     * @param model used for changing filtered return list
     */
    private void showNoReturn(Model model) {
        model.updateFilteredReturnOrderList(p -> false);
        assertTrue(model.getFilteredReturnOrderList().isEmpty());
    }
}
