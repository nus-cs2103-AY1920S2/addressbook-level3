package nasa.logic.parser.addcommandparser;

import nasa.logic.commands.addcommands.AddDeadlineCommand;

import nasa.logic.parser.ArgumentMultimap;
import nasa.logic.parser.ArgumentTokenizer;
import nasa.logic.parser.ParserUtil;
import nasa.logic.parser.exceptions.ParseException;

import nasa.model.activity.Date;
import nasa.model.activity.Deadline;
import nasa.model.activity.Name;
import nasa.model.activity.Note;
import nasa.model.module.ModuleCode;

import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;
import static nasa.logic.parser.CliSyntax.PREFIX_DATE;
import static nasa.logic.parser.CliSyntax.PREFIX_ACTIVITY_NAME;
import static nasa.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static nasa.logic.parser.CliSyntax.PREFIX_NOTE;

public class AddDeadlineCommandParser extends AddCommandParser {

    public AddDeadlineCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE, PREFIX_DATE, PREFIX_ACTIVITY_NAME, PREFIX_PRIORITY, PREFIX_NOTE);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE, PREFIX_DATE, PREFIX_ACTIVITY_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDeadlineCommand.MESSAGE_USAGE));
        }

        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        Name activityName = ParserUtil.parseActivityName(argMultimap.getValue(PREFIX_ACTIVITY_NAME).get());
        ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get());
        Note note = ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get());
        Deadline deadline = new Deadline(activityName, date, note);
        return new AddDeadlineCommand(deadline, moduleCode);
    }
}
