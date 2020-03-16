package nasa.logic.parser.addcommandparser;

import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nasa.logic.parser.CliSyntax.PREFIX_ACTIVITY_NAME;
import static nasa.logic.parser.CliSyntax.PREFIX_DATE;
import static nasa.logic.parser.CliSyntax.PREFIX_END_DATE;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;
import static nasa.logic.parser.CliSyntax.PREFIX_NOTE;
import static nasa.logic.parser.CliSyntax.PREFIX_PRIORITY;

import nasa.logic.commands.addcommands.AddEventCommand;
import nasa.logic.parser.ArgumentMultimap;
import nasa.logic.parser.ArgumentTokenizer;
import static nasa.logic.parser.CliSyntax.PREFIX_START_DATE;
import nasa.logic.parser.ParserUtil;
import nasa.logic.parser.exceptions.ParseException;
import nasa.model.activity.Date;
import nasa.model.activity.Event;
import nasa.model.activity.Name;
import nasa.model.activity.Note;
import nasa.model.module.ModuleCode;

public class AddEventCommandParser extends AddCommandParser {

    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE, PREFIX_DATE, PREFIX_ACTIVITY_NAME, PREFIX_START_DATE,
                        PREFIX_END_DATE, PREFIX_PRIORITY, PREFIX_NOTE);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE, PREFIX_DATE, PREFIX_ACTIVITY_NAME,
                PREFIX_START_DATE, PREFIX_END_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        Name activityName = ParserUtil.parseActivityName(argMultimap.getValue(PREFIX_ACTIVITY_NAME).get());
        ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get());
        Note note = ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get());
        Date startDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_START_DATE).get());
        Date endDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_END_DATE).get());

        Event event = new Event(activityName, note, startDate, endDate);

        return new AddEventCommand(event, moduleCode);
    }
}
