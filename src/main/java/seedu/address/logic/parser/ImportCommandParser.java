package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIFE;

import java.util.stream.Stream;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ImportCommand object
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand and returns a
     * ImportCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ImportCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_LIFE, PREFIX_GROUP, PREFIX_EVENT);

        if ((!arePrefixesPresent(argMultimap, PREFIX_LIFE, PREFIX_GROUP, PREFIX_EVENT))
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }

        try {
            String lifePath = "";
            String groupPath = "";
            String eventPath = "";

            if (argMultimap.getValue(PREFIX_LIFE).isPresent()) {
                lifePath = ParserUtil.parsePath(argMultimap.getValue(PREFIX_LIFE).get());
            }
            if (argMultimap.getValue(PREFIX_GROUP).isPresent()) {
                groupPath = ParserUtil.parsePath(argMultimap.getValue(PREFIX_GROUP).get());
            }

            if (argMultimap.getValue(PREFIX_EVENT).isPresent()) {
                eventPath = ParserUtil.parsePath(argMultimap.getValue(PREFIX_EVENT).get());
            }

            return new ImportCommand(lifePath, groupPath, eventPath);

        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(ParserUtil.MESSAGE_INVALID_PATH + "\n" + ImportCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
