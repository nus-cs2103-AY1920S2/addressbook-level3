package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.commands.ViewGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewCommand object.
 */
public class ViewGroupCommandParser implements Parser<ViewGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand.
     *
     * @return A ViewCommand object for execution.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public ViewGroupCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_GROUP);
        Index index;
        String parameter;

        if (argMultimap.getValue(PREFIX_GROUP).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewGroupCommand.MESSAGE_USAGE));
        }
        index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_GROUP).get());

        if (!argMultimap.getPreamble().isEmpty()) {
            parameter = argMultimap.getPreamble();
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewGroupCommand.MESSAGE_USAGE));
        }

        if (!parameter.equals(ViewCommand.KEYWORD_PLACE) && !parameter.equals(ViewCommand.KEYWORD_ACTIVITY)) {
            throw new ParseException(String.format(ViewGroupCommand.MESSAGE_INVALID_PARAMETER,
                    ViewGroupCommand.MESSAGE_USAGE));
        }

        return new ViewGroupCommand(index, parameter, ViewGroupCommand.TYPE_PERSON);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
