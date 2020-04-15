package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INDEX_NOT_INTEGER;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_NO_LINE_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINE_NUMBER;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteInfoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code DeleteInfoCommand} object
 */
public class DeleteInfoCommandParser implements Parser<DeleteInfoCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code RemarkCommand}
     * and returns a {@code RemarkCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteInfoCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_LINE_NUMBER);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteInfoCommand.MESSAGE_USAGE), ive);
        }
        if (!argMultimap.getValue(PREFIX_LINE_NUMBER).isPresent() || argMultimap
                .getAllValues(PREFIX_LINE_NUMBER).get(0).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_NO_LINE_NUMBER, DeleteInfoCommand.MESSAGE_USAGE));
        }

        ArrayList<Integer> line;
        try {
            line = ParserUtil.parseLines(argMultimap.getAllValues(PREFIX_LINE_NUMBER));
        } catch (NumberFormatException nfe) {
            throw new ParseException(String.format(MESSAGE_INDEX_NOT_INTEGER,
                    DeleteInfoCommand.MESSAGE_USAGE), nfe);
        }

        return new DeleteInfoCommand(index, line);
    }

}
