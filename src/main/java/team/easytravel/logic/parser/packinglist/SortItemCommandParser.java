package team.easytravel.logic.parser.packinglist;

import static team.easytravel.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ITEM_CRITERIA;

import team.easytravel.logic.commands.packinglist.SortItemCommand;
import team.easytravel.logic.parser.ArgumentMultimap;
import team.easytravel.logic.parser.ArgumentTokenizer;
import team.easytravel.logic.parser.Parser;
import team.easytravel.logic.parser.ParserUtil;
import team.easytravel.logic.parser.exceptions.ParseException;

/**
 * The type Sort item parser.
 */
public class SortItemCommandParser implements Parser<SortItemCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortItemCommand
     * and returns an SortItemCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortItemCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ITEM_CRITERIA);
        String parseSortItemIdentifier;
        String parseSortItemParameter;

        try {
            parseSortItemIdentifier = ParserUtil.parseSortItemIdentifier(argMultimap.getPreamble());
            if (argMultimap.getValue(PREFIX_ITEM_CRITERIA).isPresent()) {
                parseSortItemParameter = ParserUtil.parseSortItemParameters(
                        argMultimap.getValue(PREFIX_ITEM_CRITERIA).get());
            } else {
                throw new ParseException("Please indicate a sorting criteria!");
            }
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortItemCommand.MESSAGE_USAGE), pe);
        }

        return new SortItemCommand(parseSortItemIdentifier, parseSortItemParameter);
    }
}
