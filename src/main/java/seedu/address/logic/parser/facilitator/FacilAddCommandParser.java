package seedu.address.logic.parser.facilitator;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_TOO_MANY_ARGUMENTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OFFICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.facilitator.FacilAddCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.facilitator.Email;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.model.facilitator.Name;
import seedu.address.model.facilitator.Office;
import seedu.address.model.facilitator.Phone;
import seedu.address.model.module.ModuleCode;

/**
 * Parses input arguments and creates a new FacilAddCommand object.
 */
public class FacilAddCommandParser implements Parser<FacilAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FacilAddCommand
     * and returns a FacilAddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public FacilAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_OFFICE, PREFIX_MODULE_CODE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME) || !arePrefixesPresent(argMultimap, PREFIX_MODULE_CODE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FacilAddCommand.MESSAGE_USAGE));
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_PHONE) && !arePrefixesPresent(argMultimap, PREFIX_EMAIL)
                && !arePrefixesPresent(argMultimap, PREFIX_OFFICE)) {
            throw new ParseException(FacilAddCommand.MESSAGE_NOT_ADDED);
        }

        if (argMultimap.numOfValuesPresent(PREFIX_NAME) > 1) {
            throw new ParseException(String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_NAME));
        }
        if (argMultimap.numOfValuesPresent(PREFIX_PHONE) > 1) {
            throw new ParseException(String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_PHONE));
        }
        if (argMultimap.numOfValuesPresent(PREFIX_EMAIL) > 1) {
            throw new ParseException(String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_EMAIL));
        }
        if (argMultimap.numOfValuesPresent(PREFIX_OFFICE) > 1) {
            throw new ParseException(String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_OFFICE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME));
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE));
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL));
        Office office = ParserUtil.parseOffice(argMultimap.getValue(PREFIX_OFFICE));

        Set<ModuleCode> moduleCodeList = ParserUtil.parseModuleCodes(argMultimap.getAllValues(PREFIX_MODULE_CODE));

        Facilitator facilitator = new Facilitator(name, phone, email, office, moduleCodeList);

        return new FacilAddCommand(facilitator);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix) != null);
    }
}
