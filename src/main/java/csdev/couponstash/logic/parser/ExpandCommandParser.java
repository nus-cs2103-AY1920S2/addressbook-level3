package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static java.util.Objects.requireNonNull;

import csdev.couponstash.commons.core.index.Index;
import csdev.couponstash.logic.commands.ExpandCommand;
import csdev.couponstash.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExpandCommand object
 */
public class ExpandCommandParser implements Parser<ExpandCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ShareCommand
     * and returns an ShareCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ExpandCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(
                            pe.getMessage() + "\n\n" + MESSAGE_INVALID_COMMAND_FORMAT,
                            ExpandCommand.MESSAGE_USAGE
                    ),
                    pe
            );
        }

        return new ExpandCommand(index);
    }
}
