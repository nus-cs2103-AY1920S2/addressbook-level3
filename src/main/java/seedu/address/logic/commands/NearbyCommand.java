package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.NearbyCommandUtil.getGeneralLocation;
import static seedu.address.logic.commands.NearbyCommandUtil.isValidPostalSector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.Order;

/**
 * Used to identify orders in the order book that belong to a given postal sector or area.
 */
public class NearbyCommand extends Command {
    public static final String COMMAND_WORD = "nearby";
    public static final String NEWLINE = System.lineSeparator();

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": View all orders located at the same postal sector based on the displayed order list."
            + NEWLINE + "Parameters: POSTAL_SECTOR or Area"
            + NEWLINE + "A postal sector is the first two digits of a six digit Singapore postal code"
            + NEWLINE + "An area is one of the following: Central, East, North-East, West, North"
            + NEWLINE + "Example: " + COMMAND_WORD + " 14"
            + NEWLINE + "Example: " + COMMAND_WORD + " central";

    public static final String MESSAGE_SUCCESS_POSTAL_SECTOR = "Displayed all orders in postal sector."
            + NEWLINE + "General Location: %1$s";
    public static final String MESSAGE_SUCCESS_AREA = "Displayed all orders in area (%s)";
    public static final String MESSAGE_FAILURE_POSTAL_SECTOR = "Invalid postal sector given.";
    public static final String MESSAGE_FAILURE_AREA = "Invalid area given.";

    private final String searchTerm;
    private final List<String> validAreas = new ArrayList<>(Arrays.asList(
            "CENTRAL",
            "EAST",
            "NORTH-EAST",
            "WEST",
            "NORTH"));

    public NearbyCommand(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    /**
     * Obtain a {@code Predicate<Order>} that will search based on the postal sector.
     *
     * @return {@code Predicate<Order>} used for filtering orders
     */
    private Predicate<Order> getPostalSectorPredicate() {
        Index postalSector = Index.fromOneBased(Integer.parseInt(searchTerm));
        String location = getGeneralLocation(postalSector).get();
        List<String> matchingPostalSectors = NearbyCommandUtil.sameGeneralLocation(location);
        return order -> {
            String orderAddress = order.getAddress().toString();
            return matchingPostalSectors.stream()
                    .map(mps -> String.format(".*%s\\d{4}.*", mps))
                    .anyMatch(regex -> {
                        Pattern p = Pattern.compile(regex);
                        Matcher m = p.matcher(orderAddress);
                        return m.matches();
                    });
        };
    }

    /**
     * Obtain a {@code Predicate<Order>} that will search based on area.
     *
     * @return {@code Predicate<Order>} used for filtering orders
     */
    private Predicate<Order> getAreaPredicate() {
        List<String> areaRegex = NearbyCommandUtil.sameAreaRegex(searchTerm);
        return order -> {
            String orderAddress = order.getAddress().toString();
            return areaRegex.stream().anyMatch(regex -> {
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(orderAddress);
                return m.matches();
            });
        };
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (isPostalSectorSearch()) {
            Index postalSector = Index.fromOneBased(Integer.parseInt(searchTerm));
            if (isValidPostalSector(postalSector)) {
                return showPostalSectors(model, postalSector);
            }
            throw new CommandException(MESSAGE_FAILURE_POSTAL_SECTOR);
        }

        if (isValidAreaSearch()) {
            Predicate<Order> orderPredicate = getAreaPredicate();
            model.updateFilteredOrderList(orderPredicate);
            return new CommandResult(String.format(MESSAGE_SUCCESS_AREA, searchTerm));
        } else {
            throw new CommandException(MESSAGE_FAILURE_AREA);
        }
    }

    /**
     * Show all postal sectors in the given {@code model}.
     */
    private CommandResult showPostalSectors(Model model, Index postalSector) throws CommandException {
        Predicate<Order> orderPredicate = getPostalSectorPredicate();
        model.updateFilteredOrderList(orderPredicate);

        Optional<String> generalLocation = getGeneralLocation(postalSector);
        if (generalLocation.isEmpty()) {
            throw new CommandException(MESSAGE_FAILURE_POSTAL_SECTOR);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS_POSTAL_SECTOR,
                generalLocation.get()));
    }

    /**
     * Checks if given {@code searchTerm} is a postal sector.
     *
     * @return boolean of whether {@code searchTerm} is a postal sector
     */
    private boolean isPostalSectorSearch() {
        try {
            Integer.parseInt(searchTerm);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Checks if given {@code searchTerm} is a valid area.
     *
     * @return boolean indicating area validity of {@code searchTerm}
     */
    private boolean isValidAreaSearch() {
        return validAreas.stream().anyMatch(s -> s.contains(searchTerm.toUpperCase()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NearbyCommand) // instanceof handles nulls
                && searchTerm.equals(((NearbyCommand) other).searchTerm); // state check
    }
}
