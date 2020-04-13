//@@author fatin99

package tatracker.logic.commands.commons;

import static tatracker.logic.parser.Prefixes.RATE;

import java.util.List;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.logic.commands.CommandWords;
import tatracker.model.Model;


/**
 * Switches between tabs
 */
public class SetRateCommand extends Command {

    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.SET_RATE,
            "Sets the pay rate to a specified integer greater than zero.",
            List.of(RATE),
            List.of(),
            RATE
    );

    public static final String MESSAGE_SET_RATE = "Set pay rate at $%s per hour.";

    private final int rate;

    public SetRateCommand(int rate) {
        this.rate = rate;
    }


    @Override
    public CommandResult execute(Model model) {
        model.setRate(rate);
        return new CommandResult(String.format(MESSAGE_SET_RATE, rate), Action.GOTO_CLAIMS);
    }
}
