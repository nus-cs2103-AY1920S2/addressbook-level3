package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GetCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.PersonExistPredicate;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new GetCommand object
 */
public class GetCommandParser implements Parser<GetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GetCommand
     * and returns a GetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GetCommand parse(String args, Model model) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            PersonExistPredicate personExistPredicate = new PersonExistPredicate(index);
            return new GetCommand(personExistPredicate);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GetCommand.MESSAGE_USAGE), pe);
        }
    }

}
