package seedu.address.logic.parser;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.SwitchCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.tab.Tab;

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
        } else {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SwitchCommand.MESSAGE_USAGE));
        }
    }
}
