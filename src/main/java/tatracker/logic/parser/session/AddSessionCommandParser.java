package tatracker.logic.parser.session;

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

import tatracker.commons.core.Messages;
import tatracker.logic.commands.session.AddSessionCommand;
import tatracker.logic.parser.ArgumentMultimap;
import tatracker.logic.parser.ArgumentTokenizer;
import tatracker.logic.parser.Parser;
import tatracker.logic.parser.ParserUtil;
import tatracker.logic.parser.exceptions.ParseException;
import tatracker.model.session.Session;

/*
 * === BUGS ===
 * TODO: No error when end time is after start time.
 *
 * TODO: Sessions cannot have dates that are earlier than the current date.
 *        Earlier dates are replaced by the current date.
 */

/**
 * Parses input arguments and creates a new AddSessionCommand object
 */
public class AddSessionCommandParser implements Parser<AddSessionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddSessionCommand
     * and returns an AddSessionCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddSessionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, MODULE, START_TIME, END_TIME,
                DATE, RECUR, SESSION_TYPE, NOTES);

        if (!argMultimap.arePrefixesPresent(MODULE)
               || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(Messages.getInvalidCommandMessage(AddSessionCommand.DETAILS.getUsage()));
        }

        LocalDate date = LocalDate.now();
        Session sessionToAdd = new Session();


        if (argMultimap.getValue(MODULE).isPresent()) {
            sessionToAdd.setModuleCode(argMultimap.getValue(MODULE).map(String::trim).map(String::toUpperCase).get());
        }

        if (argMultimap.getValue(DATE).isPresent()) {
            date = ParserUtil.parseDate(argMultimap.getValue(DATE).get());
        }

        if (argMultimap.getValue(START_TIME).isPresent()) {
            LocalTime startTime = ParserUtil.parseTime(argMultimap.getValue(START_TIME).get());
            sessionToAdd.setStartDateTime(LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(),
                    startTime.getHour(), startTime.getMinute(), startTime.getSecond()));
        }

        if (argMultimap.getValue(END_TIME).isPresent()) {
            LocalTime endTime = ParserUtil.parseTime(argMultimap.getValue(END_TIME).get());
            sessionToAdd.setEndDateTime(LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(),
                    endTime.getHour(), endTime.getMinute(), endTime.getSecond()));
        } else {
            if (argMultimap.getValue(START_TIME).isPresent()) {
                LocalTime startTime = ParserUtil.parseTime(argMultimap.getValue(START_TIME).get());
                sessionToAdd.setEndDateTime(LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(),
                        startTime.getHour(), startTime.getMinute(), startTime.getSecond()));
            }
        }

        if (argMultimap.getValue(RECUR).isPresent()) {
            sessionToAdd.setRecurring(ParserUtil.parseNumWeeks(argMultimap.getValue(RECUR).get()));
        }

        if (argMultimap.getValue(SESSION_TYPE).isPresent()) {
            sessionToAdd.setType(
                    ParserUtil.parseSessionType(argMultimap.getValue(SESSION_TYPE).get()));
        }

        if (argMultimap.getValue(NOTES).isPresent()) {
            sessionToAdd.setDescription(argMultimap.getValue(NOTES).map(String::trim).get());
        }

        return new AddSessionCommand(sessionToAdd);
    }
}
