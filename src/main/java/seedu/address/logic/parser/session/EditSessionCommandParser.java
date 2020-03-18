package seedu.address.logic.parser.session;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MOD_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SESSION_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditSessionCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

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
        Index index = ParserUtil.parseIndex(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_STARTTIME, PREFIX_ENDTIME,
                PREFIX_DATE, PREFIX_RECUR, PREFIX_MOD_CODE, PREFIX_SESSION_TYPE, PREFIX_NOTES);

        LocalDate date = LocalDate.now(); // Arbitrary default value. Will be overwritten by EditSessionCommand
        EditSessionCommand.EditSessionDescriptor editSessionDescriptor = new EditSessionCommand.EditSessionDescriptor();

        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
            editSessionDescriptor.setIsDateChanged(true);
        }

        if (argMultimap.getValue(PREFIX_STARTTIME).isPresent()) {
            LocalTime startTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_STARTTIME).get());
            editSessionDescriptor.setStartTime(LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(),
                    startTime.getHour(), startTime.getMinute(), startTime.getSecond()));
        }

        if (argMultimap.getValue(PREFIX_ENDTIME).isPresent()) {
            LocalTime endTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_ENDTIME).get());
            editSessionDescriptor.setEndTime(LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(),
                    endTime.getHour(), endTime.getMinute(), endTime.getSecond()));
        }

        if (argMultimap.getValue(PREFIX_RECUR).isPresent()) {
            editSessionDescriptor.setIsRecurring(argMultimap.getValue(PREFIX_RECUR).isPresent());
        }

        if (argMultimap.getValue(PREFIX_MOD_CODE).isPresent()) {
            editSessionDescriptor.setModuleCode(ParserUtil.parseValue(argMultimap.getValue(PREFIX_MOD_CODE).get()));
        }

        if (argMultimap.getValue(PREFIX_SESSION_TYPE).isPresent()) {
            editSessionDescriptor.setSessionType(
                    ParserUtil.parseSessionType(argMultimap.getValue(PREFIX_SESSION_TYPE).get()));
        }

        if (argMultimap.getValue(PREFIX_NOTES).isPresent()) {
            editSessionDescriptor.setDescription(ParserUtil.parseValue(argMultimap.getValue(PREFIX_NOTES).get()));
        }

        return new EditSessionCommand(index, editSessionDescriptor);
    }
}
