package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.NearbyCommandUtil.getGeneralLocation;
import static seedu.address.logic.commands.NearbyCommandUtil.isValidPostalSector;

import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.Order;

/**
 * Used to identify orders in the order book that belong to a given postal sector.
 */
public class NearbyCommand extends Command {
    public static final String COMMAND_WORD = "nearby";
    public static final String NEWLINE = System.lineSeparator();

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": View all orders located at the same postal sector based on the displayed order list."
            + NEWLINE + "Parameters: POSTAL_SECTOR (must be a valid postal sector)"
            + NEWLINE + "A postal sector is the first two digits of a six digit Singapore postal code"
            + NEWLINE + "Example: " + COMMAND_WORD + " 14";

    public static final String MESSAGE_SUCCESS = "Displayed all orders in postal sector."
            + NEWLINE + "General Location: %1$s";
    public static final String MESSAGE_FAILURE = "Invalid postal sector given.";

    private final Index postalSector;

    public NearbyCommand(Index postalSector) {
        this.postalSector = postalSector;
    }

    /**
     * Obtain a {@code Predicate<Order>} that will search based on the postal sector.
     *
     * @return {@code Predicate<Order>} used for filtering orders
     */
    private Predicate<Order> getPredicate() {
        String sector = "S" + postalSector.getOneBased();
        return order -> order.getAddress().toString().contains(sector);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (isValidPostalSector(postalSector)) {
            Predicate<Order> orderPredicate = getPredicate();
            model.updateFilteredOrderList(orderPredicate);

            Optional<String> generalLocation = getGeneralLocation(postalSector);
            if (generalLocation.isEmpty()) {
                throw new CommandException(MESSAGE_FAILURE);
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS,
                    generalLocation.get()));
        } else {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NearbyCommand) // instanceof handles nulls
                && postalSector.equals(((NearbyCommand) other).postalSector); // state check
    }
}
