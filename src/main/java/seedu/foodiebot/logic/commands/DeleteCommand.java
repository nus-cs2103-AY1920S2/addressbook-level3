package seedu.foodiebot.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.foodiebot.logic.parser.ParserContext.SUGGESTED_CONTEXT_MESSAGE;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

import seedu.foodiebot.commons.core.LogsCenter;
import seedu.foodiebot.commons.core.Messages;
import seedu.foodiebot.commons.core.index.Index;
import seedu.foodiebot.logic.commands.exceptions.CommandException;
import seedu.foodiebot.logic.parser.ParserContext;
import seedu.foodiebot.model.Model;
import seedu.foodiebot.model.budget.Budget;
import seedu.foodiebot.model.transaction.PurchasedFood;

/** Deletes a purchased food using its displayed index from the transactions list. */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD
                    + ": Deletes the purchased food in the transactions list.\n"
                    + "Parameters: INDEX (must be a positive integer)\n"
                    + "Example: "
                    + COMMAND_WORD
                    + " 1";

    public static final String MESSAGE_SUCCESS = "Successfully deleted %s purchased on %s";
    private static final Logger logger = LogsCenter.getLogger(DeleteCommand.class);

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.loadFilteredTransactionsList();

        if (!ParserContext.getCurrentContext().equals(ParserContext.TRANSACTIONS_CONTEXT)) {
            throw new CommandException(ParserContext.INVALID_CONTEXT_MESSAGE
                    + ParserContext.getCurrentContext()
                    + SUGGESTED_CONTEXT_MESSAGE
                    + ParserContext.TRANSACTIONS_CONTEXT);
        }

        List<PurchasedFood> purchasedFoodList = model.getFilteredTransactionsList();
        if (targetIndex.getZeroBased() < 0 || targetIndex.getZeroBased() >= purchasedFoodList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        PurchasedFood foodToDelete = purchasedFoodList.get(targetIndex.getZeroBased());
        String foodName = foodToDelete.getName();
        LocalDate purchaseDate = foodToDelete.getDateAdded();
        model.removePurchasedFood(foodToDelete);

        if (model.getBudget().get().getTotalBudget() != 0) {
            Budget savedBudget = model.getFoodieBot().getBudget();

            if (savedBudget.getRemainingBudget() + foodToDelete.getPrice() > savedBudget.getTotalBudget()) {

                model.setBudget(new Budget(savedBudget.getTotalBudget(), savedBudget.getTotalBudget(),
                        savedBudget.getDuration(), savedBudget.getDateTimeOfCreation(), savedBudget.getCycleRange()));

            } else {
                savedBudget.addToRemainingBudget(foodToDelete.getPrice());
                model.setBudget(savedBudget);
            }
        }

        return new CommandResult(COMMAND_WORD, String.format(MESSAGE_SUCCESS, foodName, purchaseDate));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                        && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }

    @Override
    public boolean needToSaveCommand() {
        return true;
    }
}
