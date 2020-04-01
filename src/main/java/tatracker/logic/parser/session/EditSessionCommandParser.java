package tatracker.logic.parser.session;

import static java.util.Objects.requireNonNull;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tatracker.logic.parser.Prefixes.DATE;
import static tatracker.logic.parser.Prefixes.END_TIME;
import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.NOTES;
import static tatracker.logic.parser.Prefixes.RECUR;
import static tatracker.logic.parser.Prefixes.SESSION_TYPE;
import static tatracker.logic.parser.Prefixes.START_TIME;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import tatracker.commons.core.index.Index;
import tatracker.logic.commands.session.EditSessionCommand;
import tatracker.logic.parser.ArgumentMultimap;
import tatracker.logic.parser.ArgumentTokenizer;
import tatracker.logic.parser.Parser;
import tatracker.logic.parser.ParserUtil;
import tatracker.logic.parser.exceptions.ParseException;

/*
 * === BUGS ===
 * TODO: No error when end time is after start time.
 *
 * TODO: Sessions cannot have dates that are earlier than the current date.
 *        Earlier dates are replaced by the current date.
 */

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
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditSessionCommand.DETAILS.getUsage()),
                    pe);
        }

        EditSessionCommand.EditSessionDescriptor editSessionDescriptor = new EditSessionCommand.EditSessionDescriptor();

        LocalDate date = LocalDate.now(); // Arbitrary default value. Will be overwritten by EditSessionCommand
        if (argMultimap.getValue(DATE).isPresent()) {
            date = ParserUtil.parseDate(argMultimap.getValue(DATE).get());
            editSessionDescriptor.setIsDateChanged(true);
        }

        if (argMultimap.getValue(START_TIME).isPresent()) {
            LocalTime startTime = ParserUtil.parseTime(argMultimap.getValue(START_TIME).get());
            editSessionDescriptor.setStartTime(LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(),
                    startTime.getHour(), startTime.getMinute(), startTime.getSecond()));
        }

        if (argMultimap.getValue(END_TIME).isPresent()) {
            LocalTime endTime = ParserUtil.parseTime(argMultimap.getValue(END_TIME).get());
            editSessionDescriptor.setEndTime(LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(),
                    endTime.getHour(), endTime.getMinute(), endTime.getSecond()));
        }

        if (argMultimap.getValue(RECUR).isPresent()) {
            editSessionDescriptor.setRecurring(ParserUtil.parseNumWeeks(argMultimap.getValue(RECUR).get()));
        }

        if (argMultimap.getValue(MODULE).isPresent()) {
            editSessionDescriptor.setModuleCode(ParserUtil.parseValue(argMultimap.getValue(MODULE).get()));
        }

        if (argMultimap.getValue(SESSION_TYPE).isPresent()) {
            editSessionDescriptor.setSessionType(
                    ParserUtil.parseSessionType(argMultimap.getValue(SESSION_TYPE).get()));
        }

        if (argMultimap.getValue(NOTES).isPresent()) {
            editSessionDescriptor.setDescription(ParserUtil.parseValue(argMultimap.getValue(NOTES).get()));
        }

        // TODO: Check if editing should be allowed if there are no fields
        if (!editSessionDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditSessionCommand.MESSAGE_NOT_EDITED);
        }

        return new EditSessionCommand(index, editSessionDescriptor);
    }
}
