package seedu.address.logic.parser;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new HelpCommand object
 */
public class HelpCommandParser implements Parser<HelpCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the HelpCommand
     * and returns a HelpCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public HelpCommand parse(String args) throws ParseException {
        if (!doesInputHaveExtraParameters(args)) {
            HelpCommand helpCommand = new HelpCommand();
            helpCommand.setValidity(true);
            return helpCommand;
        } else {
            throw new ParseException(String.format(HelpCommand.SHOW_ADDITIONAL_PARAMETERS_MESSAGE + Messages.NEWLINE
                    + HelpCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Checks if the user inputs extra characters after the 'help' command word.
     * @param argument
     * @return true if user input includes extra parameters and false otherwise
     */
    private boolean doesInputHaveExtraParameters(String argument) {
        if (argument.equals("")) {
            return false;
        } else {
            return true;
        }
    }

}
