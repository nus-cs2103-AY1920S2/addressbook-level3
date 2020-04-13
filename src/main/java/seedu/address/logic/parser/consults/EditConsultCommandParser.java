package seedu.address.logic.parser.consults;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_REPEATED_PREFIXES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONSULT_BEGIN_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONSULT_END_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLACE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.consults.EditConsultCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditStudentCommand object
 */
public class EditConsultCommandParser implements Parser<EditConsultCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditConsultCommand
     * and returns an EditConsultCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditConsultCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CONSULT_BEGIN_DATE_TIME, PREFIX_CONSULT_END_DATE_TIME,
                        PREFIX_PLACE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditConsultCommand.MESSAGE_USAGE), pe);
        }

        EditConsultCommand.EditConsultDescriptor editConsultDescriptor = new EditConsultCommand.EditConsultDescriptor();

        if (!arePrefixesUnique(argMultimap, PREFIX_CONSULT_BEGIN_DATE_TIME, PREFIX_CONSULT_END_DATE_TIME,
                PREFIX_PLACE)) {
            throw new ParseException(String.format(MESSAGE_REPEATED_PREFIXES,
                    EditConsultCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_CONSULT_BEGIN_DATE_TIME).isPresent()) {
            editConsultDescriptor.setBeginDateTime(
                    ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_CONSULT_BEGIN_DATE_TIME).get()));
        }
        if (argMultimap.getValue(PREFIX_CONSULT_END_DATE_TIME).isPresent()) {
            editConsultDescriptor.setEndDateTime(
                    ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_CONSULT_END_DATE_TIME).get()));
        }
        if (argMultimap.getValue(PREFIX_PLACE).isPresent()) {
            editConsultDescriptor.setLocation(ParserUtil.parsePlace(argMultimap.getValue(PREFIX_PLACE).get()));
        }

        if (!editConsultDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditConsultCommand.MESSAGE_NOT_EDITED);
        }

        return new EditConsultCommand(index, editConsultDescriptor);
    }

    /**
     * Returns true if at least one of the prefixes is repeated in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesUnique(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).filter(argumentMultimap::isRepeated).count() == 0;
    }
}
