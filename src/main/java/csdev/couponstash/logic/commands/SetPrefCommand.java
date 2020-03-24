package csdev.couponstash.logic.commands;

import static java.util.Objects.requireNonNull;

import csdev.couponstash.model.Model;

/**
 * Edits the user preferences in Coupon Stash, for example
 * the money symbol/currency type used to display monetary
 * amounts of the Coupon's savings.
 */
public class SetPrefCommand extends Command {
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
