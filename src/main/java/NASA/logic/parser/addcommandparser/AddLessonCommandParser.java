package NASA.logic.parser.addcommandparser;

import NASA.logic.commands.addcommands.AddLessonCommand;
import NASA.logic.parser.ArgumentMultimap;
import NASA.logic.parser.ArgumentTokenizer;
import NASA.logic.parser.ParserUtil;
import NASA.logic.parser.exceptions.ParseException;
import NASA.model.activity.Date;
import NASA.model.activity.Lesson;
import NASA.model.activity.Name;
import NASA.model.activity.Note;
import NASA.model.module.ModuleCode;

import static NASA.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static NASA.logic.parser.CliSyntax.PREFIX_MODULE;
import static NASA.logic.parser.CliSyntax.PREFIX_DATE;
import static NASA.logic.parser.CliSyntax.PREFIX_ACTIVITY_NAME;
import static NASA.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static NASA.logic.parser.CliSyntax.PREFIX_NOTE;

public class AddLessonCommandParser extends AddCommandParser {

    public AddLessonCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE, PREFIX_DATE, PREFIX_ACTIVITY_NAME, PREFIX_PRIORITY, PREFIX_NOTE);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE, PREFIX_DATE, PREFIX_ACTIVITY_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));
        }

        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        Name activityName = ParserUtil.parseActivityName(argMultimap.getValue(PREFIX_ACTIVITY_NAME).get());
        ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get());
        Note note = ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get());
        Lesson lesson = new Lesson(activityName, date, note);

        return new AddLessonCommand(lesson, moduleCode);
    }
}
