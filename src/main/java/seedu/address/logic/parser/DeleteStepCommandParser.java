package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteStepCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteStepCommandParser implements Parser<DeleteStepCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteStepCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String[] argsArray = args.trim().split("\\s");

        Index index;
        try {
            index = ParserUtil.parseIndex(argsArray[0]);
            Integer[] stepNumbers = Arrays.stream(ParserUtil.parseMultipleIndex(argsArray[1])).map(Index::getOneBased).toArray(Integer[]::new);
        } catch (ParseException pe) {
            throw new ParseException(ParserUtil.MESSAGE_INVALID_INDEX);
        }

        // Index[] index = ParserUtil.parseMultipleIndex(args);
        return new DeleteStepCommand(index, 4);
    }

}
