package seedu.address.logic.parser.people;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANSACTION_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.people.PeopleReturnedCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PeopleReturnedCommand object
 */
public class PeopleReturnedCommandParser implements Parser<PeopleReturnedCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PeopleReturnedCommand
     * and returns a PeopleReturnedCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public PeopleReturnedCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TRANSACTION_INDEX);

        Index personIndex;
        Index debtIndex;

        try {
            personIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
            if (argMultimap.getValue(PREFIX_TRANSACTION_INDEX).isPresent()) {
                debtIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_TRANSACTION_INDEX).get());
            } else {
                debtIndex = null;
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PeopleReturnedCommand.MESSAGE_USAGE), pe);
        }

        return new PeopleReturnedCommand(personIndex, debtIndex);
    }
}
