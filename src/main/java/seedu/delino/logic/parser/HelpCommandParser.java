package seedu.delino.logic.parser;

import java.util.logging.Logger;

import seedu.delino.commons.core.Messages;
import seedu.delino.logic.commands.HelpCommand;
import seedu.delino.logic.parser.exceptions.ParseException;

//@@author cherweijie
/**
 * Parses input arguments and creates a new HelpCommand object
 */
public class HelpCommandParser implements Parser<HelpCommand> {
    private static final Logger logger = Logger.getLogger(HelpCommandParser.class.getName());

    /**
     * Parses the given {@code String} of arguments in the context of the HelpCommand
     * and returns a HelpCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public HelpCommand parse(String args) throws ParseException {
        if (doesInputHaveExtraParameters(args)) {
            logger.info("Help command user input contains extra unnecessary parameters.");
            throw new ParseException(String.format(HelpCommand.SHOW_ADDITIONAL_PARAMETERS_MESSAGE + Messages.NEWLINE
                    + HelpCommand.MESSAGE_USAGE));
        }
        logger.fine("Successful help command parsed.");
        HelpCommand helpCommand = new HelpCommand();
        helpCommand.setValidity(true);
        return helpCommand;
    }

    /**
     * Checks if the user inputs extra characters after the 'help' command word.
     * @param argument
     * @return true if user input includes extra parameters and false otherwise
     */
    private boolean doesInputHaveExtraParameters(String argument) {
        logger.fine("check if the help input has extra parameters.");
        return !argument.equals("");
    }

}
