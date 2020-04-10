package team.easytravel.logic.commands.fixedexpense;

import static java.util.Objects.requireNonNull;
import static team.easytravel.commons.util.CollectionUtil.requireAllNonNull;
import static team.easytravel.logic.commands.CommandResult.Action.SWITCH_TAB_FIXED_EXPENSE;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_CURRENCY;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;

import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.listmanagers.fixedexpense.Amount;
import team.easytravel.model.listmanagers.fixedexpense.FixedExpense;
import team.easytravel.model.trip.TripManager;

/**
 * Adds a Fixed Expense to the Fixed Expense manager.
 */
public class AddFixedExpenseCommand extends Command {

    public static final String COMMAND_WORD = "addexpense";

    public static final String MESSAGE_DUPLICATE_EXPENSE = "This expense already exists in the address book";
    public static final String MESSAGE_EXCEED_BUDGET = "Take note, you have exceeded your budget!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a fixed expense to the fixed expense list"
            + "Parameters: "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_CURRENCY + "CURRENCY "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_CATEGORY + "CATEGORY...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_AMOUNT + "1500 "
            + PREFIX_CURRENCY + "sgd "
            + PREFIX_DESCRIPTION + "Plane Tickets "
            + PREFIX_CATEGORY + "transport";

    public static final String MESSAGE_SUCCESS = "New Fixed Expense added: %1$s";
    private FixedExpense toAdd;
    private boolean isOverseasAmount;

    /**
     * Creates an AddFixedExpenseCommand to the specified {@code FixedExpense}
     */
    public AddFixedExpenseCommand(FixedExpense toAdd, boolean isOverseasAmount) {
        requireAllNonNull(toAdd, isOverseasAmount);
        this.toAdd = toAdd;
        this.isOverseasAmount = isOverseasAmount;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        if (model.hasFixedExpense(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EXPENSE);
        }

        if (isOverseasAmount) {
            double exchangeRate = model.getExchangeRate();
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            Double amountInSgd = Double.parseDouble(toAdd.getAmount().value) * exchangeRate;
            // JPY -> SGD
            toAdd = new FixedExpense(new Amount(decimalFormat.format(amountInSgd)),
                    toAdd.getDescription(),
                    toAdd.getFixedExpenseCategory());
            model.addFixedExpense(toAdd);
        } else {
            model.addFixedExpense(toAdd);
        }

        int remainingBudget = (int) (model.getBudget() - model.getTotalExpense());


        if (remainingBudget < 1) {
            FixedExpense highestExpense = Collections.max(model.getFilteredFixedExpenseList(),
                    Comparator.comparingDouble(x -> Double.parseDouble(x.getAmount().value)));

            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd + "\n"
                    + MESSAGE_EXCEED_BUDGET + "\n Currently, your Highest expense is "
                    + highestExpense), SWITCH_TAB_FIXED_EXPENSE);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd + "\n"
                + "Your budget left is now " + remainingBudget), SWITCH_TAB_FIXED_EXPENSE);
    }
}
