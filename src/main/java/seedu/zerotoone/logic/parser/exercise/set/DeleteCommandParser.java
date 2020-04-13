package seedu.zerotoone.logic.parser.exercise.set;

import static java.util.Objects.requireNonNull;
import static seedu.zerotoone.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import seedu.zerotoone.commons.core.LogsCenter;
import seedu.zerotoone.commons.core.index.Index;
import seedu.zerotoone.logic.commands.exercise.set.DeleteCommand;
import seedu.zerotoone.logic.parser.Parser;
import seedu.zerotoone.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        logger.fine("Parsing: " + args);

        requireNonNull(args);
        String[] splitArgs = args.trim().split("\\s+");
        if (splitArgs.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        Index exerciseId = SetParserUtil.parseIndex(splitArgs[0]);
        Index setId = SetParserUtil.parseIndex(splitArgs[1]);
        return new DeleteCommand(exerciseId, setId);
    }
}
