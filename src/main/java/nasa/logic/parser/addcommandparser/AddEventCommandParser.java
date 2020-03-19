package nasa.logic.parser.addcommandparser;

import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nasa.logic.parser.CliSyntax.PREFIX_ACTIVITY_NAME;
import static nasa.logic.parser.CliSyntax.PREFIX_END_DATE;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;
import static nasa.logic.parser.CliSyntax.PREFIX_NOTE;
import static nasa.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static nasa.logic.parser.CliSyntax.PREFIX_START_DATE;

import nasa.logic.commands.addcommands.AddEventCommand;
import nasa.logic.parser.ArgumentMultimap;
import nasa.logic.parser.ArgumentTokenizer;
import nasa.logic.parser.ParserUtil;
import nasa.logic.parser.exceptions.ParseException;
import nasa.model.activity.Date;
import nasa.model.activity.Event;
import nasa.model.activity.Note;
import nasa.model.activity.Priority;
import nasa.model.module.ModuleCode;

/**
 * Parses input arguments and creates an AddEventCommand object.
 */
public class AddEventCommandParser extends AddCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddEventCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE, PREFIX_ACTIVITY_NAME, PREFIX_START_DATE,
                        PREFIX_END_DATE, PREFIX_PRIORITY, PREFIX_NOTE);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE, PREFIX_ACTIVITY_NAME,
                PREFIX_START_DATE, PREFIX_END_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        Name activityName = ParserUtil.parseActivityName(argMultimap.getValue(PREFIX_ACTIVITY_NAME).get());
        ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get());
        Date startDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_START_DATE).get());
        Date endDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_END_DATE).get());

        // optional fields - must see if it exist, else create null
        Note note;
        if (arePrefixesPresent(argMultimap, PREFIX_NOTE)) {
            note = ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get());
        } else {
            note = null;
        }

        Priority priority;
        if (arePrefixesPresent(argMultimap, PREFIX_PRIORITY)) {
            priority = ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get());
        } else {
            priority = null;
        }

        Event event = new Event(activityName, note, priority, startDate, endDate);
        return new AddEventCommand(event, moduleCode);
    }
}
