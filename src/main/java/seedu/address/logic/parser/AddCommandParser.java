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
import seedu.address.model.profile.course.module.PersonalModule;
import seedu.address.model.profile.course.module.SemesterData;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     * @return
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE, PREFIX_SEMESTER, PREFIX_GRADE,
                        PREFIX_TASK, PREFIX_DEADLINE);

        // To check if Module argument exists since it is compulsory
        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE, PREFIX_SEMESTER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        // Add tasks to tasklist
        if (arePrefixesPresent(argMultimap, PREFIX_TASK)) {
            String task = argMultimap.getValue(PREFIX_MODULE).get();
            //add task to tasklist
            if (arePrefixesPresent(argMultimap, PREFIX_DEADLINE)) {
                String deadline = argMultimap.getValue(PREFIX_DEADLINE).get();
                //add deadline to task
            }
        }

        // Add module to list in Personal object within Module Object
        SemesterData semester = ParserUtil.parseSemesterData(argMultimap.getValue(PREFIX_SEMESTER).get());

        String grade = null;
        if (arePrefixesPresent(argMultimap, PREFIX_GRADE)) {
            grade = argMultimap.getValue(PREFIX_GRADE).get();
        }

        PersonalModule personalModule = new PersonalModule(semester, grade);

        return new AddCommand(personalModule);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
