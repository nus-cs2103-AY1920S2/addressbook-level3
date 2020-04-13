//@@author Eclmist

package tatracker.logic.parser.session;

import static java.util.Objects.requireNonNull;
import static tatracker.commons.core.Messages.MESSAGE_NOT_EDITED;
import static tatracker.logic.parser.Prefixes.DATE;
import static tatracker.logic.parser.Prefixes.END_TIME;
import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.NOTES;
import static tatracker.logic.parser.Prefixes.RECUR;
import static tatracker.logic.parser.Prefixes.SESSION_TYPE;
import static tatracker.logic.parser.Prefixes.START_TIME;

import tatracker.commons.core.Messages;
import tatracker.commons.core.index.Index;
import tatracker.logic.commands.session.EditSessionCommand;
import tatracker.logic.commands.session.EditSessionCommand.EditSessionDescriptor;
import tatracker.logic.parser.ArgumentMultimap;
import tatracker.logic.parser.ArgumentTokenizer;
import tatracker.logic.parser.Parser;
import tatracker.logic.parser.ParserUtil;
import tatracker.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditSessionCommand object
 */
public class EditSessionCommandParser implements Parser<EditSessionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditSessionCommand
     * and returns an AddSessionCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditSessionCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, START_TIME, END_TIME,
                DATE, RECUR, MODULE, SESSION_TYPE, NOTES);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(Messages.getInvalidCommandMessage(EditSessionCommand.DETAILS.getUsage()), pe);
        }

        EditSessionDescriptor editSessionDescriptor = new EditSessionDescriptor();

        if (argMultimap.getValue(DATE).isPresent()) {
            editSessionDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(DATE).get()));
        }
        if (argMultimap.getValue(START_TIME).isPresent()) {
            editSessionDescriptor.setStartTime(ParserUtil.parseTime(argMultimap.getValue(START_TIME).get()));
        }
        if (argMultimap.getValue(END_TIME).isPresent()) {
            editSessionDescriptor.setEndTime(ParserUtil.parseTime(argMultimap.getValue(END_TIME).get()));
        }
        if (argMultimap.getValue(RECUR).isPresent()) {
            editSessionDescriptor.setRecurring(ParserUtil.parseNumWeeks(argMultimap.getValue(RECUR).get()));
        }
        if (argMultimap.getValue(MODULE).isPresent()) {
            editSessionDescriptor.setModuleCode(argMultimap.getValue(MODULE)
                    .map(String::trim)
                    .map(String::toUpperCase)
                    .get());
        }
        if (argMultimap.getValue(SESSION_TYPE).isPresent()) {
            editSessionDescriptor.setSessionType(
                    ParserUtil.parseSessionType(argMultimap.getValue(SESSION_TYPE).get()));
        }
        if (argMultimap.getValue(NOTES).isPresent()) {
            editSessionDescriptor.setDescription(argMultimap.getValue(NOTES).map(String::trim).get());
        }

        if (!editSessionDescriptor.isAnyFieldEdited()) {
            throw new ParseException(MESSAGE_NOT_EDITED);
        }

        return new EditSessionCommand(index, editSessionDescriptor);
    }
}
