package nasa.logic.parser.addcommandparser;

import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nasa.logic.parser.CliSyntax.PREFIX_ACTIVITY_NAME;
import static nasa.logic.parser.CliSyntax.PREFIX_DATE;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;
import static nasa.logic.parser.CliSyntax.PREFIX_NOTE;
import static nasa.logic.parser.CliSyntax.PREFIX_PRIORITY;

import nasa.logic.commands.addcommands.AddDeadlineCommand;
import nasa.logic.parser.ArgumentMultimap;
import nasa.logic.parser.ArgumentTokenizer;
import nasa.logic.parser.ParserUtil;
import nasa.logic.parser.exceptions.ParseException;
import nasa.model.activity.Date;
import nasa.model.activity.Deadline;
import nasa.model.activity.Name;
import nasa.model.activity.Note;
import nasa.model.activity.Priority;
import nasa.model.module.ModuleCode;

//@@author kester-ng
/**
 * Parses input arguments and creates an AddDeadlineCommand object.
 */
public class AddDeadlineCommandParser extends AddCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddDeadlineCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddDeadlineCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE, PREFIX_DATE,
                    PREFIX_ACTIVITY_NAME, PREFIX_PRIORITY, PREFIX_NOTE);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE,
            PREFIX_DATE, PREFIX_ACTIVITY_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        // compulsory fields
        Date dueDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        Name activityName = ParserUtil.parseActivityName(argMultimap.getValue(PREFIX_ACTIVITY_NAME).get());
        ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get());

        try {
            Deadline deadline = new Deadline(activityName, dueDate);

            // optional fields - must see if it exist, else create null
            Note note;
            if (arePrefixesPresent(argMultimap, PREFIX_NOTE)) {
                note = ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get());
                deadline.setNote(note);
            } else {
                note = null;
            }

            Priority priority = new Priority();
            if (arePrefixesPresent(argMultimap, PREFIX_PRIORITY)) {
                priority = ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get());
            }
            deadline.setPriority(priority);
            return new AddDeadlineCommand(deadline, moduleCode);
        } catch (IllegalArgumentException e) {
            throw new ParseException(Deadline.DATE_CONSTRAINTS);
        }
    }
}
