package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ResumePreviewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ResumePreviewCommand object.
 */
public class ResumePreviewCommandParser implements Parser<ResumePreviewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ResumePreviewCommand
     * and returns ResumePreviewCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    @Override
    public ResumePreviewCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput);

        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ResumePreviewCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());

        return new ResumePreviewCommand(index);
    }
}
