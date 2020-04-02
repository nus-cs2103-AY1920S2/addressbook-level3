package csdev.couponstash.logic.commands;

import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_MONEY_SYMBOL;
import static java.util.Objects.requireNonNull;

import csdev.couponstash.model.Model;

/**
 * Edits the user preferences in Coupon Stash, for example
 * the money symbol/currency type used to display monetary
 * amounts of the Coupon's savings.
 */
public class SetPrefCommand extends Command {
    public static final String COMMAND_WORD = "setpref";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the user preferences for Coupon Stash\n\n"
            + "Parameters: " + PREFIX_MONEY_SYMBOL + " NEW_MONEY_SYMBOL\n\n"
            + "Example: setpref " + PREFIX_MONEY_SYMBOL + " RM";
    public static final String MESSAGE_MISSING_VALUES = COMMAND_WORD
            + " requires at least one value to be set!\n\n";

    private static final String MESSAGE_SUCCESS = "Money symbol changed from %s to ";

    private final String moneySymbol;

    public SetPrefCommand(String newMoneySymbol) {
        this.moneySymbol = newMoneySymbol;
    }

    @Override
    public CommandResult execute(Model model, String commandText) {
        requireNonNull(model);
        String oldSymbol = model.setMoneySymbol(this.moneySymbol);

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, oldSymbol) + this.moneySymbol + "!");
    }
}
