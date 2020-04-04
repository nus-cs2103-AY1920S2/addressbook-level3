package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOCUS_AREA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;

import java.util.stream.Stream;

import seedu.address.logic.commands.ShowCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.course.CourseName;
import seedu.address.model.profile.course.module.ModuleCode;

/**
 * Parses input arguments and creates a new ShowCommand object
 */
public class ShowCommandParser implements Parser<ShowCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ShowCommand
     * and returns an ShowCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_SEMESTER, PREFIX_MODULE,
                        PREFIX_FOCUS_AREA, PREFIX_COURSE_NAME);

        // Get Name
        if (arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            return new ShowCommand(name);
        }

        // Get Semester
        if (arePrefixesPresent(argMultimap, PREFIX_SEMESTER)) {
            int intSemester = ParserUtil.parseSemester(argMultimap.getValue(PREFIX_SEMESTER).get());
            return new ShowCommand(intSemester);
        }

        // Get Module
        if (arePrefixesPresent(argMultimap, PREFIX_MODULE)) {
            ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get());
            return new ShowCommand(moduleCode);
        }

        // Get Focus Area
        if (arePrefixesPresent(argMultimap, PREFIX_FOCUS_AREA)) {
            String focusArea = ParserUtil.parseFocusArea(argMultimap.getValue(PREFIX_FOCUS_AREA).get().toUpperCase());
            return new ShowCommand(focusArea); // returns String
        }

        // Get Course
        if (arePrefixesPresent(argMultimap, PREFIX_COURSE_NAME)) {
            CourseName courseName = ParserUtil.parseCourseName(
                    argMultimap.getValue(PREFIX_COURSE_NAME).get().toUpperCase());
            return new ShowCommand(courseName); // returns CourseName
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

