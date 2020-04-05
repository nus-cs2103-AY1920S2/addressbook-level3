package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.profile.course.module.ModuleCode;
import seedu.address.model.profile.course.module.exceptions.DateTimeException;

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
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE, PREFIX_SEMESTER, PREFIX_GRADE,
                        PREFIX_TASK, PREFIX_DEADLINE);

        // To check if Module argument exists since it is compulsory
        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        // Create module object
        String moduleCodeString = argMultimap.getValue(PREFIX_MODULE).get().trim().toUpperCase();
        ModuleCode moduleCode = ParserUtil.parseModuleCode(moduleCodeString);

        String grade = null;
        String task = null;
        String deadlineString = null;
        int intSemester = 0;

        if (arePrefixesPresent(argMultimap, PREFIX_SEMESTER)) {
            intSemester = ParserUtil.parseSemester(argMultimap.getValue(PREFIX_SEMESTER).get()).getSemester();
        }
        if (arePrefixesPresent(argMultimap, PREFIX_GRADE)) {
            grade = ParserUtil.parseGrade(argMultimap.getValue(PREFIX_GRADE).get().toUpperCase());
        }
        if (arePrefixesPresent(argMultimap, PREFIX_TASK, PREFIX_DEADLINE)) {
            task = argMultimap.getValue(PREFIX_TASK).get();
            deadlineString = ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_DEADLINE).get());
        }
        if (arePrefixesPresent(argMultimap, PREFIX_TASK)) {
            task = argMultimap.getValue(PREFIX_TASK).get();
        }

        return new AddCommand(moduleCode, intSemester, grade, task, deadlineString);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
