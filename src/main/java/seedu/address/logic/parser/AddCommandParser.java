package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.profile.course.module.ModuleCode;
import seedu.address.model.profile.course.module.exceptions.DateTimeException;

//@@author joycelynteo

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException, DateTimeException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE, PREFIX_YEAR, PREFIX_GRADE,
                        PREFIX_TASK, PREFIX_DEADLINE);

        // To check if Module argument exists since it is compulsory
        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        // Create module object
        Collection<String> strModuleCodes = argMultimap.getAllValues(PREFIX_MODULE)
                .stream()
                .map(x->x.trim().toUpperCase())
                .collect(Collectors.toList());
        Set<ModuleCode> moduleCodes = ParserUtil.parseModuleCodes(strModuleCodes);

        String grade = null;
        String task = null;
        String deadlineString = null;
        LocalDate date = null;
        LocalTime time = null;
        int intSemester = 0;

        if (arePrefixesPresent(argMultimap, PREFIX_YEAR)) {
            intSemester = ParserUtil.parseYear(argMultimap.getValue(PREFIX_YEAR).get()).getSemester();
        }
        if (arePrefixesPresent(argMultimap, PREFIX_GRADE)) {
            grade = ParserUtil.parseGrade(argMultimap.getValue(PREFIX_GRADE).get().toUpperCase());
        }
        if (arePrefixesPresent(argMultimap, PREFIX_TASK, PREFIX_DEADLINE)) {
            task = argMultimap.getValue(PREFIX_TASK).get();
            String[] datetime = ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_DEADLINE).get());
            String dateString = datetime[0];
            String timeString = datetime[1];

            date = LocalDate.parse(dateString);
            time = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));
        }
        if (arePrefixesPresent(argMultimap, PREFIX_TASK)) {
            task = argMultimap.getValue(PREFIX_TASK).get().toLowerCase();
        }

        return new AddCommand(moduleCodes, intSemester, grade, task, date, time);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
