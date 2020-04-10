package team.easytravel.logic.parser.packinglist;

import java.util.Arrays;

import team.easytravel.commons.core.Messages;
import team.easytravel.logic.commands.packinglist.FindItemCategoryCommand;
import team.easytravel.logic.parser.Parser;
import team.easytravel.logic.parser.exceptions.ParseException;
import team.easytravel.model.listmanagers.packinglistitem.ItemCategoryContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindItemCategoryCommand object
 */
public class FindItemCategoryCommandParser implements Parser<FindItemCategoryCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindItemCategoryCommand
     * and returns a FindItemCategoryCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindItemCategoryCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindItemCategoryCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindItemCategoryCommand(new ItemCategoryContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
