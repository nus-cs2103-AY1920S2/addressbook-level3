package seedu.planner.logic.parser.lessons;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_LESSON;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_STUDENT_SEM;

import java.util.List;
import java.util.stream.Stream;

import seedu.planner.commons.util.LessonDataImporterUtil;
import seedu.planner.logic.commands.lessons.LessonAddCommand;
import seedu.planner.logic.parser.ArgumentMultimap;
import seedu.planner.logic.parser.ArgumentTokenizer;
import seedu.planner.logic.parser.Parser;
import seedu.planner.logic.parser.ParserUtil;
import seedu.planner.logic.parser.Prefix;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.module.Lesson;
import seedu.planner.model.module.ModuleCode;
import seedu.planner.model.time.Semester;

/**
 * Class to parse input for {@code LessonaDDCommand}.
 */
class LessonAddCommandParser implements Parser<LessonAddCommand> {

    public static final String MESSAGE_INVALID_SEMESTER = "Semester is not valid: %1$s";

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeclareMajorCommand
     * and returns a DeclareMajorCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public LessonAddCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(" " + args, PREFIX_MODULE, PREFIX_LESSON,
                PREFIX_STUDENT_SEM);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE, PREFIX_LESSON, PREFIX_STUDENT_SEM)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LessonAddCommand.MESSAGE_USAGE));
        }
        ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get());
        String lessonNumber = ParserUtil.parseLessonNumber(argMultimap.getValue(PREFIX_LESSON).get());
        Semester sem = ParserUtil.parseSemester(argMultimap.getValue(PREFIX_STUDENT_SEM).get());
        int semNo;
        switch (sem.getFullName()) {
        case "Semester 1":
            semNo = 1;
            break;
        case "Semester 2":
            semNo = 2;
            break;
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_SEMESTER, sem.getFullName()));
        }
        LessonDataImporterUtil lessonDataImporter = new LessonDataImporterUtil();
        List<Lesson> lessons = LessonDataImporterUtil.run(moduleCode.toString(), semNo);
        Lesson selectedLesson = new Lesson();
        for (int i = 0; i < lessons.size(); i++) {
            if (lessons.get(i).getClassNo().equals(lessonNumber)) {
                selectedLesson = lessons.get(i);
                break;
            }
        }
        return new LessonAddCommand(selectedLesson);
    }

}
