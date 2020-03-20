package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private Model model = new ModelManager(getTypicalOrderBook(), new UserPrefs());
    private Model expectedModel;
    private String invalidPostalSector;
    private String invalidArea;
    private Predicate<Order> ordersInJurong;
    private Predicate<Order> eastArea;

    @BeforeEach
    void setUp() {
        invalidPostalSector = "4000";
        invalidArea = "La La Land";
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

        List<String> areaRegex = new ArrayList<>(Arrays.asList(
                ".*S16\\d{4}.*", ".*S17\\d{4}.*", ".*S18\\d{4}.*"));
        eastArea = order -> {
            String orderAddress = order.getAddress().toString();
            return areaRegex.stream().anyMatch(regex -> {
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(orderAddress);
                return m.matches();
            });
        };
    }

    @Test
    void execute_validAreaUnfilteredList_success() {
        String searchTerm = "east";
        NearbyCommand nearbyCommand = new NearbyCommand(searchTerm);
        expectedModel = new ModelManager(model.getOrderBook(), new UserPrefs());
        expectedModel.updateFilteredOrderList(eastArea);
        String expectedMessage = String.format(NearbyCommand.MESSAGE_SUCCESS_AREA, searchTerm);
        assertCommandSuccess(nearbyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_validAreaNoMatchingOrderFilteredList_success() {
        String searchTerm = "east";
        String expectedMessage = String.format(NearbyCommand.MESSAGE_SUCCESS_AREA, searchTerm);
        NearbyCommand nearbyCommand = new NearbyCommand(searchTerm);
        expectedModel = new ModelManager(model.getOrderBook(), new UserPrefs());
        showNoOrder(expectedModel);

        assertCommandSuccess(nearbyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_validPostalSectorUnfilteredList_success() {
        String input = "64";
        Index postalSector = Index.fromOneBased(64);
        Optional<String> location = NearbyCommandUtil.getGeneralLocation(postalSector);
        if (location.isEmpty()) {
            fail("Given postal sector is not valid");
        }

        NearbyCommand nearbyCommand = new NearbyCommand(input);
        expectedModel = new ModelManager(model.getOrderBook(), new UserPrefs());
        expectedModel.updateFilteredOrderList(ordersInJurong);
        String expectedMessage = String.format(NearbyCommand.MESSAGE_SUCCESS_POSTAL_SECTOR,
                location.get());

        assertCommandSuccess(nearbyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_validPostalSectorNoMatchingOrderFilteredList_success() {
        String input = "7";
        Index postalSector = Index.fromOneBased(7);
        Optional<String> location = NearbyCommandUtil.getGeneralLocation(postalSector);
        if (location.isEmpty()) {
            fail("Given postal sector is not valid");
        }

        String expectedMessage = String.format(NearbyCommand.MESSAGE_SUCCESS_POSTAL_SECTOR,
                location.get());
        NearbyCommand nearbyCommand = new NearbyCommand(input);
        expectedModel = new ModelManager(model.getOrderBook(), new UserPrefs());
        showNoOrder(expectedModel);

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
     * Updates {@code model}'s filtered list to show no orders.
     *
     * @param model used for changing filtered list
     */
    private void showNoOrder(Model model) {
        model.updateFilteredOrderList(p -> false);
        assertTrue(model.getFilteredOrderList().isEmpty());
    }
}
