package team.easytravel.logic.commands.packinglist;

import static java.util.Objects.requireNonNull;
import static team.easytravel.commons.core.Messages.MESSAGE_ITEMS_LISTED_OVERVIEW;
import static team.easytravel.logic.commands.CommandResult.Action.SWITCH_TAB_PACKING_LIST;

import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.listmanagers.packinglistitem.ItemCategoryContainsKeywordsPredicate;
import team.easytravel.model.trip.TripManager;

/**
 * Finds and lists all items whose category contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindItemCategoryCommand extends Command {
    public static final String COMMAND_WORD = "finditemcategory";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all items whose category contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " swimming clothes essentials";

    private final ItemCategoryContainsKeywordsPredicate predicate;

    public FindItemCategoryCommand(ItemCategoryContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        model.updateFilteredPackingList(predicate);
        return new CommandResult(
                String.format(MESSAGE_ITEMS_LISTED_OVERVIEW, model.getFilteredPackingList().size(), "item(s)"),
                SWITCH_TAB_PACKING_LIST);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindItemCategoryCommand // instanceof handles nulls
                && predicate.equals(((FindItemCategoryCommand) other).predicate)); // state check
    }
}
