package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.MajorCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.nusmodule.Major;

/**
 * Parses input arguments and creates a new MajorCommand object
 */
public class MajorCommandParser implements Parser<MajorCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public MajorCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MajorCommand.MESSAGE_USAGE));
        }

        Major major = ParserUtil.parseMajor(trimmedArgs);

        return new MajorCommand(major);
    }
}
