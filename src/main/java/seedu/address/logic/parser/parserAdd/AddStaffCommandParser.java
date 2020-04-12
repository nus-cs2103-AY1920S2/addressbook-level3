package seedu.address.logic.parser.parserAdd;

import seedu.address.logic.commands.commandAdd.AddCommand;
import seedu.address.logic.commands.commandAdd.AddStaffCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.modelObjectTags.*;
import seedu.address.model.modelStaff.Staff;
import seedu.address.model.tag.Tag;

import java.util.Set;
import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddStaffCommandParser extends AddCommandParser {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand and returns an
     * AddCommand object for execution.
     *
     * @return
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer
                        .tokenize(args, PREFIX_NAME, PREFIX_LEVEL, PREFIX_GENDER, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_SALARY, PREFIX_ADDRESS,
                                PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_LEVEL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStaffCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Gender gender = ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get());
        Staff.Level level = ParserUtil.parseLevel(argMultimap.getValue(PREFIX_LEVEL).get());
        Phone phone = new Phone("Unknown");
        Email email = new Email("Unknown");
        Salary salary = new Salary("0");
        Address address = new Address("Unknown");
        if (arePrefixesPresent(argMultimap, PREFIX_PHONE)) {
            phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        }
        if (arePrefixesPresent(argMultimap, PREFIX_EMAIL)) {
            email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        }
        if (arePrefixesPresent(argMultimap, PREFIX_SALARY)) {
            salary = ParserUtil.parseSalary(argMultimap.getValue(PREFIX_SALARY).get());
        }
        if (arePrefixesPresent(argMultimap, PREFIX_ADDRESS)) {
            address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        }
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Staff teacher = new Staff(name, gender, level, phone, email, salary, address, tagList);

        return new AddStaffCommand(teacher);
    }
}
