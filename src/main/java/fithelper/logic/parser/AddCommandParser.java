package fithelper.logic.parser;

import static fithelper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static fithelper.logic.parser.CliSyntax.PREFIX_NAME;
import static fithelper.logic.parser.CliSyntax.PREFIX_TYPE;
import static fithelper.logic.parser.CliSyntax.PREFIX_TIME;
import static fithelper.logic.parser.CliSyntax.PREFIX_LOCATION;
import static fithelper.logic.parser.CliSyntax.PREFIX_CALORIE;
import static fithelper.logic.parser.CliSyntax.PREFIX_STATUS;
import static fithelper.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.stream.Stream;

import fithelper.logic.commands.AddCommand;
import fithelper.logic.parser.exceptions.ParseException;

import fithelper.model.entry.Name;
import fithelper.model.entry.Type;
import fithelper.model.entry.Time;
import fithelper.model.entry.Location;
import fithelper.model.entry.Calorie;
import fithelper.model.entry.Status;
import fithelper.model.entry.Remark;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TYPE, PREFIX_NAME, PREFIX_TIME, PREFIX_LOCATION, PREFIX_CALORIE, PREFIX_REMARK);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_REMARK, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Remark remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Person person = new Person(name, email, address, remark, tagList);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
