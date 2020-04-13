//@@author Chuayijing

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
import tatracker.model.session.SessionType;

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

        // Module code is compulsory
        String moduleCode = argMultimap.getValue(MODULE).map(String::trim).map(String::toUpperCase).get();

        LocalDate date = LocalDate.now();
        if (argMultimap.getValue(DATE).isPresent()) {
            date = ParserUtil.parseDate(argMultimap.getValue(DATE).get());
        }

        //@@author potatocombat
        LocalTime startTime = LocalTime.now().withSecond(0).withNano(0);
        if (argMultimap.getValue(START_TIME).isPresent()) {
            startTime = ParserUtil.parseTime(argMultimap.getValue(START_TIME).get());
        }

        LocalTime endTime = startTime;
        if (argMultimap.getValue(END_TIME).isPresent()) {
            endTime = ParserUtil.parseTime(argMultimap.getValue(END_TIME).get());
        }

        //@@author chuayijing

        // Adjusting the start and end time to be on the specified date (today if not specified)
        LocalDateTime startDateTime = LocalDateTime.of(date, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(date, endTime);

        int recurringWeeks = Session.DEFAULT_RECURRING_WEEKS;
        if (argMultimap.getValue(RECUR).isPresent()) {
            recurringWeeks = ParserUtil.parseNumWeeks(argMultimap.getValue(RECUR).get());
        }

        SessionType sessionType = Session.DEFAULT_SESSION_TYPE;
        if (argMultimap.getValue(SESSION_TYPE).isPresent()) {
            sessionType = ParserUtil.parseSessionType(argMultimap.getValue(SESSION_TYPE).get());
        }

        String notes = Session.DEFAULT_DESCRIPTION;
        if (argMultimap.getValue(NOTES).isPresent()) {
            notes = argMultimap.getValue(NOTES).map(String::trim).get();
        }

        Session sessionToAdd = new Session(startDateTime, endDateTime, sessionType, recurringWeeks, moduleCode, notes);

        return new AddSessionCommand(sessionToAdd);
    }
}
