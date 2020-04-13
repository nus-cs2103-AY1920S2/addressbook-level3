package seedu.address.logic.parser.consults;

import static seedu.address.commons.core.Messages.MESSAGE_CONSULT_BEGIN_TIME_BEFORE_END_TIME;
import static seedu.address.commons.core.Messages.MESSAGE_CONSULT_DIFFERENT_DATE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_REPEATED_PREFIXES;
import static seedu.address.commons.util.ConsultUtil.checkSameDate;
import static seedu.address.commons.util.ConsultUtil.checkStartEndDateTime;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONSULT_BEGIN_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONSULT_END_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;

import java.time.LocalDateTime;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.consults.AddConsultCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Location;
import seedu.address.model.event.consult.Consult;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddConsultCommandParser implements Parser<AddConsultCommand> {

    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Parses the given {@code String} of arguments in the context of the AddConsultCommand
     * and returns an AddConsultCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddConsultCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STUDENT, PREFIX_CONSULT_BEGIN_DATE_TIME,
                        PREFIX_CONSULT_END_DATE_TIME, PREFIX_PLACE);

        if (!arePrefixesPresent(argMultimap, PREFIX_STUDENT, PREFIX_CONSULT_BEGIN_DATE_TIME,
                PREFIX_CONSULT_END_DATE_TIME, PREFIX_PLACE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddConsultCommand.MESSAGE_USAGE));
        }

        if (!arePrefixesUnique(argMultimap, PREFIX_STUDENT, PREFIX_CONSULT_BEGIN_DATE_TIME,
                PREFIX_CONSULT_END_DATE_TIME, PREFIX_PLACE)) {
            throw new ParseException(String.format(MESSAGE_REPEATED_PREFIXES,
                    AddConsultCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_STUDENT).get());
        LocalDateTime beginDateTime = ParserUtil.parseDateTime(argMultimap.getValue(
                PREFIX_CONSULT_BEGIN_DATE_TIME).get());
        LocalDateTime endDateTime = ParserUtil.parseDateTime(argMultimap.getValue(
                PREFIX_CONSULT_END_DATE_TIME).get());
        Location location = ParserUtil.parsePlace(argMultimap.getValue(
                PREFIX_PLACE).get());

        logger.fine("Index: " + index.toString());
        logger.fine("Begin Date Time: " + beginDateTime.toString());
        logger.fine("End Date Time: " + endDateTime.toString());
        logger.fine("Location: " + location.toString());


        if (!checkStartEndDateTime(beginDateTime, endDateTime)) {
            throw new ParseException(MESSAGE_CONSULT_BEGIN_TIME_BEFORE_END_TIME);
        }

        if (!checkSameDate(beginDateTime, endDateTime)) {
            throw new ParseException(MESSAGE_CONSULT_DIFFERENT_DATE);
        }

        Consult consult = new Consult(beginDateTime, endDateTime, location);

        return new AddConsultCommand(index, consult);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if at least one of the prefixes is repeated in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesUnique(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).filter(argumentMultimap::isRepeated).count() == 0;
    }
}
