package team.easytravel.logic.parser.packinglist;

import java.util.Arrays;

import team.easytravel.commons.core.Messages;
import team.easytravel.logic.commands.packinglist.FindItemCommand;
import team.easytravel.logic.parser.Parser;
import team.easytravel.logic.parser.exceptions.ParseException;
import team.easytravel.model.listmanagers.packinglistitem.ItemContainsKeywordsPredicate;

/**
 * Finds and lists all items whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindItemCommandParser implements Parser<FindItemCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindItemCommand
     * and returns a FindItemCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindItemCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindItemCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindItemCommand(new ItemContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
