package tatracker.logic.parser.session;

import static tatracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tatracker.logic.parser.CliSyntax.PREFIX_DATE;
import static tatracker.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static tatracker.logic.parser.CliSyntax.PREFIX_MODULE;
import static tatracker.logic.parser.CliSyntax.PREFIX_NOTES;
import static tatracker.logic.parser.CliSyntax.PREFIX_RECUR;
import static tatracker.logic.parser.CliSyntax.PREFIX_SESSION_TYPE;
import static tatracker.logic.parser.CliSyntax.PREFIX_STARTTIME;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_STARTTIME, PREFIX_ENDTIME,
                PREFIX_DATE, PREFIX_RECUR, PREFIX_MODULE, PREFIX_SESSION_TYPE, PREFIX_NOTES);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSessionCommand.MESSAGE_USAGE));
        }

        LocalDate date = LocalDate.now();
        Session sessionToAdd = new Session();

        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        }

        if (argMultimap.getValue(PREFIX_STARTTIME).isPresent()) {
            LocalTime startTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_STARTTIME).get());
            sessionToAdd.setStartDateTime(LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(),
                    startTime.getHour(), startTime.getMinute(), startTime.getSecond()));
        }

        if (argMultimap.getValue(PREFIX_ENDTIME).isPresent()) {
            LocalTime endTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_ENDTIME).get());
            sessionToAdd.setEndDateTime(LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(),
                    endTime.getHour(), endTime.getMinute(), endTime.getSecond()));
        }

        if (argMultimap.getValue(PREFIX_RECUR).isPresent()) {
            sessionToAdd.setRecurring(argMultimap.getValue(PREFIX_RECUR).isPresent());
        }

        if (argMultimap.getValue(PREFIX_MODULE).isPresent()) {
            sessionToAdd.setModuleCode(ParserUtil.parseValue(argMultimap.getValue(PREFIX_MODULE).get()));
        }

        if (argMultimap.getValue(PREFIX_SESSION_TYPE).isPresent()) {
            sessionToAdd.setType(
                    ParserUtil.parseSessionType(argMultimap.getValue(PREFIX_SESSION_TYPE).get()));
        }

        if (argMultimap.getValue(PREFIX_NOTES).isPresent()) {
            sessionToAdd.setDescription(ParserUtil.parseValue(argMultimap.getValue(PREFIX_NOTES).get()));
        }

        return new AddSessionCommand(sessionToAdd);
    }
}
