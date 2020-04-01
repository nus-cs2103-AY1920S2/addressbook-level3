package nasa.logic.parser.addcommandparser;

import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nasa.logic.parser.CliSyntax.PREFIX_ACTIVITY_NAME;
import static nasa.logic.parser.CliSyntax.PREFIX_END_DATE;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;
import static nasa.logic.parser.CliSyntax.PREFIX_NOTE;
import static nasa.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static nasa.logic.parser.CliSyntax.PREFIX_START_DATE;

import nasa.logic.commands.addcommands.AddLessonCommand;
import nasa.logic.parser.ArgumentMultimap;
import nasa.logic.parser.ArgumentTokenizer;
import nasa.logic.parser.ParserUtil;
import nasa.logic.parser.exceptions.ParseException;
import nasa.model.activity.Date;
import nasa.model.activity.Lesson;
import nasa.model.activity.Name;
import nasa.model.activity.Note;
import nasa.model.activity.Priority;
import nasa.model.module.ModuleCode;

/**
 * Parses input arguments and creates an AddLessonCommand object.
 */
public class AddLessonCommandParser extends AddCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddLessonCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddLessonCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE, PREFIX_ACTIVITY_NAME, PREFIX_START_DATE,
                        PREFIX_END_DATE, PREFIX_PRIORITY, PREFIX_NOTE);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE, PREFIX_ACTIVITY_NAME,
                PREFIX_START_DATE, PREFIX_END_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));
        }

        Name activityName = ParserUtil.parseActivityName(argMultimap.getValue(PREFIX_ACTIVITY_NAME).get());
        ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get());

        Date startDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_START_DATE).get());
        Date endDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_END_DATE).get());

        try {
            Lesson lesson = new Lesson(activityName, startDate, endDate);

            // optional fields - must see if it exist, else create null
            Note note;
            if (arePrefixesPresent(argMultimap, PREFIX_NOTE)) {
                note = ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get());
                lesson.setNote(note);
            } else {
                note = null;
            }

            Priority priority = new Priority();
            if (arePrefixesPresent(argMultimap, PREFIX_PRIORITY)) {
                priority = ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get());
            }
            lesson.setPriority(priority);
            return new AddLessonCommand(lesson, moduleCode);
        } catch (IllegalArgumentException e) {
            // if the start date is > end date or end date is already in the past
            throw new ParseException(Lesson.INVALID_LESSON);
        }
    }
}
