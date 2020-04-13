package seedu.delino.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.delino.logic.commands.NearbyCommandUtil.getGeneralLocation;
import static seedu.delino.logic.commands.NearbyCommandUtil.isValidPostalSector;
import static seedu.delino.logic.parser.CliSyntax.FLAG_ORDER_BOOK;
import static seedu.delino.logic.parser.CliSyntax.FLAG_RETURN_BOOK;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.delino.commons.core.LogsCenter;
import seedu.delino.commons.core.index.Index;
import seedu.delino.logic.commands.exceptions.CommandException;
import seedu.delino.model.Model;
import seedu.delino.model.parcel.order.Order;
import seedu.delino.model.parcel.returnorder.ReturnOrder;

//@@author JeremyLoh

/**
 * Used to identify orders in the order book, return order book that belong to a given
 * postal sector or area.
 */
public class NearbyCommand extends Command {
    public static final String COMMAND_WORD = "nearby";
    public static final String NEWLINE = System.lineSeparator();

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": View all orders located at the same postal sector based on the displayed list."
            + NEWLINE + "Parameters: [FLAG] POSTAL_SECTOR or AREA"
            + NEWLINE + "An optional flag may be given to indicate the list to be searched for."
            + NEWLINE + "The flag can be either -o for orders for -r for return orders"
            + NEWLINE + "A postal sector is the first two digits of a six digit Singapore postal code"
            + NEWLINE + "An area is one of the following: Central, East, North-East, West, North"
            + NEWLINE + "Example: " + COMMAND_WORD + " -o 14"
            + NEWLINE + "Example: " + COMMAND_WORD + " -r central"
            + NEWLINE + "Example: " + COMMAND_WORD + " east";

    public static final String MESSAGE_SUCCESS_POSTAL_SECTOR = "Displayed all orders in postal sector."
            + NEWLINE + "General Location: %1$s";
    public static final String MESSAGE_SUCCESS_AREA = "Displayed all orders in area (%s)";
    public static final String MESSAGE_FAILURE_POSTAL_SECTOR = "Invalid postal sector given.";
    public static final String MESSAGE_FAILURE_AREA = "Invalid area given.";

    private static final Logger logger = LogsCenter.getLogger(NearbyCommand.class);
    private final String searchTerm;
    private boolean isOrderListSearch;
    private boolean isReturnListSearch;

    public NearbyCommand(String searchTerm) {
        logger.fine("Invoking NearbyCommand constructor");
        String validFlagRegex = "\\s*%s\\s+";
        String orderListRegex = String.format(validFlagRegex, FLAG_ORDER_BOOK.toString());
        String returnListRegex = String.format(validFlagRegex, FLAG_RETURN_BOOK.toString());
        this.isOrderListSearch = hasRegexInSearchTerm(orderListRegex, searchTerm);
        this.isReturnListSearch = hasRegexInSearchTerm(returnListRegex, searchTerm);

        if (this.isOrderListSearch) {
            this.searchTerm = searchTerm.replaceAll(orderListRegex, "");
            logger.fine("Search to be done on order list");
        } else if (this.isReturnListSearch) {
            this.searchTerm = searchTerm.replaceAll(returnListRegex, "");
            logger.fine("Search to be done on return order list");
        } else {
            this.isOrderListSearch = true;
            this.isReturnListSearch = true;
            this.searchTerm = searchTerm;
            logger.fine("Search to be done on both lists (order list and return order list)");
        }
        logger.fine("Exiting NearbyCommand constructor");
    }

    /**
     * Checks if the given {@code regex} is contained in the given {@code searchTerm}.
     *
     * @param regex      used for checking {@code searchTerm}
     * @param searchTerm String to search
     * @return boolean indicating whether the given {@code regex} was found
     */
    private boolean hasRegexInSearchTerm(String regex, String searchTerm) {
        logger.fine(String.format("Checking if given regex (%s) is a partial match in searchTerm (%s)",
                regex, searchTerm));
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(searchTerm);
        return m.find();
    }

    /**
     * Obtain a {@code Predicate<Order>} that will search based on the postal sector.
     *
     * @return {@code Predicate<Order>} used for filtering orders
     */
    private Predicate<Order> getPostalSectorPredicate() {
        logger.fine("Obtain predicate for order list based on postal sector");
        Index postalSector = Index.fromOneBased(Integer.parseInt(searchTerm));
        String location = getGeneralLocation(postalSector).get();
        List<String> matchingPostalSectors = NearbyCommandUtil.sameGeneralLocation(location);
        return order -> {
            String orderAddress = order.getAddress().toString();
            return matchingPostalSectors.stream()
                    .map(mps -> String.format(".*%s\\d{4}.*", mps))
                    .anyMatch(regex -> Pattern.matches(regex, orderAddress));
        };
    }

    /**
     * Obtain a {@code Predicate<ReturnOrder>} that will search based on postal sector.
     *
     * @return {@code Predicate<ReturnOrder>} used for filtering orders
     */
    private Predicate<ReturnOrder> getPostalSectorReturnPredicate() {
        logger.fine("Obtain predicate for return order list based on postal sector");
        Index postalSector = Index.fromOneBased(Integer.parseInt(searchTerm));
        String location = getGeneralLocation(postalSector).get();
        List<String> matchingPostalSectors = NearbyCommandUtil.sameGeneralLocation(location);
        return returnOrder -> {
            String returnOrderAddress = returnOrder.getAddress().toString();
            return matchingPostalSectors.stream()
                    .map(mps -> String.format(".*%s\\d{4}.*", mps))
                    .anyMatch(regex -> Pattern.matches(regex, returnOrderAddress));
        };
    }

    /**
     * Obtain a {@code Predicate<Order>} that will search based on area.
     *
     * @return {@code Predicate<Order>} used for filtering orders
     */
    private Predicate<Order> getAreaPredicate() {
        logger.fine("Obtain predicate for order list based on area");
        List<String> areaRegex = DistrictInfo.sameAreaRegex(searchTerm);
        return order -> {
            String orderAddress = order.getAddress().toString();
            return areaRegex.stream()
                    .anyMatch(regex -> Pattern.matches(regex, orderAddress));
        };
    }

    /**
     * Obtain a {@code Predicate<ReturnOrder>} that will search based on area.
     *
     * @return {@code Predicate<ReturnOrder>} used for filtering return orders
     */
    private Predicate<ReturnOrder> getAreaReturnPredicate() {
        logger.fine("Obtain predicate for return order list based on area");
        List<String> areaRegex = DistrictInfo.sameAreaRegex(searchTerm);
        return returnOrder -> {
            String returnOrderAddress = returnOrder.getAddress().toString();
            return areaRegex.stream()
                    .anyMatch(regex -> Pattern.matches(regex, returnOrderAddress));
        };
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.fine("In NearbyCommand execute method");
        if (isPostalSectorSearch()) {
            return getPostalSectorCommandResult(model);
        }

        if (DistrictInfo.isValidArea(searchTerm)) {
            return getAreaCommandResult(model);
        } else {
            logger.info("Invalid area given");
            throw new CommandException(String.format("%s" + NEWLINE + "%s",
                    MESSAGE_FAILURE_AREA,
                    MESSAGE_USAGE));
        }
    }

    /**
     * Obtain the command result for area search.
     */
    private CommandResult getAreaCommandResult(Model model) {
        showArea(model);
        return new CommandResult(String.format(MESSAGE_SUCCESS_AREA, searchTerm));
    }

    /**
     * Obtain the command result for postal sector search.
     */
    private CommandResult getPostalSectorCommandResult(Model model) throws CommandException {
        Index postalSector = Index.fromOneBased(Integer.parseInt(searchTerm));
        if (isValidPostalSector(postalSector)) {
            Optional<String> generalLocation = getGeneralLocation(postalSector);
            if (generalLocation.isEmpty()) {
                throw new CommandException(String.format("%s" + NEWLINE + "%s",
                        MESSAGE_FAILURE_POSTAL_SECTOR,
                        MESSAGE_USAGE));
            }
            showPostalSectors(model);
            return new CommandResult(String.format(MESSAGE_SUCCESS_POSTAL_SECTOR,
                    generalLocation.get()));
        } else {
            throw new CommandException(String.format("%s" + NEWLINE + "%s",
                    MESSAGE_FAILURE_POSTAL_SECTOR,
                    MESSAGE_USAGE));
        }
    }

    /**
     * Shows all orders at an area in the given {@code model}.
     */
    private void showArea(Model model) {
        if (isOrderListSearch) {
            Predicate<Order> orderPredicate = getAreaPredicate();
            model.updateFilteredOrderList(orderPredicate);
        }
        if (isReturnListSearch) {
            Predicate<ReturnOrder> returnOrderPredicate = getAreaReturnPredicate();
            model.updateFilteredReturnOrderList(returnOrderPredicate);
        }
    }

    /**
     * Show all postal sectors in the given {@code model}.
     */
    private void showPostalSectors(Model model) {
        if (isOrderListSearch) {
            Predicate<Order> orderPredicate = getPostalSectorPredicate();
            model.updateFilteredOrderList(orderPredicate);
        }
        if (isReturnListSearch) {
            Predicate<ReturnOrder> returnOrderPredicate = getPostalSectorReturnPredicate();
            model.updateFilteredReturnOrderList(returnOrderPredicate);
        }
    }

    /**
     * Checks if given {@code searchTerm} is a postal sector.
     *
     * @return boolean of whether {@code searchTerm} is a postal sector
     */
    private boolean isPostalSectorSearch() {
        try {
            Integer.parseInt(searchTerm);
            logger.fine("Successfully parse argument for postal sector");
            return true;
        } catch (NumberFormatException nfe) {
            logger.info("Unable to parse argument given for postal sector");
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NearbyCommand) // instanceof handles nulls
                && searchTerm.equals(((NearbyCommand) other).searchTerm); // state check
    }
}
