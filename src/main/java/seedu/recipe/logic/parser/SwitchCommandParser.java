package seedu.recipe.logic.parser;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.logic.commands.SwitchCommand;
import seedu.recipe.logic.parser.exceptions.ParseException;
import seedu.recipe.ui.tab.Tab;

/**
 * Parses input arguments and creates a new SwitchCommand object.
 */
public class SwitchCommandParser implements Parser<SwitchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SwitchCommand
     * and returns a SwitchCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public SwitchCommand parse(String args) throws ParseException {
        String specifiedTab = args.trim().toLowerCase();
        if (specifiedTab.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SwitchCommand.MESSAGE_USAGE));
        }

        if (specifiedTab.equals("recipes")) {
            return new SwitchCommand(Tab.RECIPES);
        } else if (specifiedTab.equals("planning")) {
            return new SwitchCommand(Tab.PLANNING);
        } else if (specifiedTab.equals("goals")) {
            return new SwitchCommand(Tab.GOALS);
        } else if (specifiedTab.equals("achievements")) {
            return new SwitchCommand(Tab.ACHIEVEMENTS);
        } else {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SwitchCommand.MESSAGE_USAGE));
        }
    }
}
