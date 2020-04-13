package seedu.nova.logic.parser.abparsers;

import static seedu.nova.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Set;
import java.util.stream.Stream;

import seedu.nova.logic.commands.abcommands.AbAddCommand;
import seedu.nova.logic.parser.ArgumentMultimap;
import seedu.nova.logic.parser.ArgumentTokenizer;
import seedu.nova.logic.parser.Parser;
import seedu.nova.logic.parser.ParserUtil;
import seedu.nova.logic.parser.Prefix;
import seedu.nova.logic.parser.exceptions.ParseException;
import seedu.nova.model.category.Category;
import seedu.nova.model.person.Email;
import seedu.nova.model.person.Name;
import seedu.nova.model.person.Person;
import seedu.nova.model.person.Phone;
import seedu.nova.model.person.Remark;

/**
 * Parses input arguments and creates a new AbAddCommand object
 */
public class AbAddCommandParser implements Parser<AbAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AbAddCommand
     * and returns an AbAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AbAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_CATEGORY);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_CATEGORY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AbAddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Set<Category> categoryList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_CATEGORY));
        if (ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_CATEGORY)).size() == 1) {
            categoryList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_CATEGORY));
        } else if (argMultimap.getAllValues(PREFIX_CATEGORY).size() > 1) {
            throw new ParseException("Please only provide 1 category");
        }

        Remark remark = new Remark("");

        Person person = new Person(name, phone, email, categoryList, remark);

        return new AbAddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
