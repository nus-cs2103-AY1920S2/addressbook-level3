package team.easytravel.logic.parser.fixedexpense;

import team.easytravel.commons.core.Messages;
import team.easytravel.logic.commands.fixedexpense.SortFixedExpenseCommand;
import team.easytravel.logic.parser.Parser;
import team.easytravel.logic.parser.ParserUtil;
import team.easytravel.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a SortFixedExpenseCommand.
 */
public class SortFixedExpenseCommandParser implements Parser<SortFixedExpenseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortFixedExpenseCommand
     * and returns an SortFixedExpenseCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortFixedExpenseCommand parse(String args) throws ParseException {
        try {
            if (args.length() < 1) { //The case where nothing is placed after sortexpense
                throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                        SortFixedExpenseCommand.MESSAGE_USAGE));
            }

            String[] sortIdentifiers = ParserUtil.parseSortArgumentString(args);
            String parseSortIdentifier = ParserUtil.parseSortIdentifier(sortIdentifiers[0].toLowerCase());
            String parseSortParameter = ParserUtil.parseSortExpParameters(sortIdentifiers[1]).toLowerCase();

            return new SortFixedExpenseCommand((parseSortIdentifier), parseSortParameter);
        } catch (ParseException pe) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    SortFixedExpenseCommand.MESSAGE_USAGE), pe);
        }
    }

}
