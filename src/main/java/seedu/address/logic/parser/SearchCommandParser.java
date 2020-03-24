package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * <h1>SearchCommandParserClass</h1>
 * Simple Parser for Search Command
 */
public class SearchCommandParser implements Parser<SearchCommand> {
    /**
     * Search Command Parser
     * @param args String of Module Code
     * @return Search Command Object
     * @throws ParseException Incase you type wrong
     */
    public SearchCommand parse(String args) throws ParseException {
        if (args.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.SYNTAX_FAILURE));
        }

        return new SearchCommand(args.trim());
    }
}
