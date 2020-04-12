package nasa.logic.parser;

import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;
import static nasa.logic.parser.CliSyntax.PREFIX_REPEAT;

import java.util.stream.Stream;

import nasa.commons.core.index.Index;
import nasa.logic.commands.RepeatDeadlineCommand;
import nasa.logic.commands.RepeatEventCommand;
import nasa.logic.parser.exceptions.ParseException;
import nasa.model.module.ModuleCode;

/**
 * Parser for repeat command.
 */
public class RepeatEventCommandParser implements Parser<RepeatEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RepeatCommand
     * and returns an RepeatCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RepeatEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE,
                        PREFIX_REPEAT);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RepeatEventCommand.MESSAGE_USAGE), pe);
        }

        Prefix[] prefixes = {PREFIX_MODULE, PREFIX_REPEAT};

        boolean arePrefixesPresent = Stream.of(prefixes).allMatch(prefix ->
                argMultimap.getValue(prefix).isPresent());

        if (!(arePrefixesPresent)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RepeatDeadlineCommand.MESSAGE_USAGE));
        }

        // compulsory fields
        ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get());
        Index type = ParserUtil.parseZeroIndex(argMultimap.getValue(PREFIX_REPEAT).get());

        return new RepeatEventCommand(moduleCode, index, type);
    }
}
