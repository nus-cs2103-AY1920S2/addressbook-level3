package fithelper.logic.parser;

import static fithelper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static fithelper.logic.parser.CliSyntax.PREFIX_ENTRYCALORIE;
import static fithelper.logic.parser.CliSyntax.PREFIX_ENTRYLOCATION;
import static fithelper.logic.parser.CliSyntax.PREFIX_ENTRYNAME;
import static fithelper.logic.parser.CliSyntax.PREFIX_ENTRYTIME;

import java.util.stream.Stream;

import fithelper.logic.commands.AddEntryCommand;
import fithelper.logic.parser.exceptions.ParseException;
import fithelper.model.entry.Entry;

/**
 * Parses input arguments and creates a new Add EntryCommand object
 */
public class AddEntryCommandParser implements Parser<AddEntryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEntryCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ENTRYNAME, PREFIX_ENTRYTIME,
                        PREFIX_ENTRYLOCATION, PREFIX_ENTRYCALORIE);

        if (!arePrefixesPresent(argMultimap, PREFIX_ENTRYNAME, PREFIX_ENTRYTIME, PREFIX_ENTRYLOCATION,
                PREFIX_ENTRYCALORIE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEntryCommand.MESSAGE_USAGE));
        }

        String name = ParserUtil.parseEntryName(argMultimap.getValue(PREFIX_ENTRYNAME).get());
        String time = ParserUtil.parseEntryTime(argMultimap.getValue(PREFIX_ENTRYTIME).get());
        String location = ParserUtil.parseEntryLocation(argMultimap.getValue(PREFIX_ENTRYLOCATION).get());
        String calorie = ParserUtil.parseEntryCalorie(argMultimap.getValue(PREFIX_ENTRYCALORIE).get());

        Entry entry = new Entry(name, time, location, calorie);

        return new AddEntryCommand(entry);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
