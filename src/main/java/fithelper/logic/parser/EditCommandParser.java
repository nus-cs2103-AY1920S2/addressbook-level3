package fithelper.logic.parser;

import static fithelper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_CALORIE;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_LOCATION;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_NAME;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_REMARK;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_STATUS;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_TIME;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_TYPE;

import static java.util.Objects.requireNonNull;

import java.util.stream.Stream;

import fithelper.commons.core.index.Index;
import fithelper.logic.commands.EditCommand;
import fithelper.logic.commands.EditCommand.EditEntryDescriptor;
import fithelper.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TYPE, PREFIX_NAME, PREFIX_TIME, PREFIX_LOCATION,
                        PREFIX_CALORIE, PREFIX_STATUS, PREFIX_REMARK);

        if (!arePrefixesPresent(argMultimap, PREFIX_TYPE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditEntryDescriptor editEntryDescriptor = new EditEntryDescriptor();
        editEntryDescriptor.setType(ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get()));
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editEntryDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_TIME).isPresent()) {
            editEntryDescriptor.setTime(ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get()));
        }
        if (argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
            editEntryDescriptor.setLocation(ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get()));
        }
        if (argMultimap.getValue(PREFIX_CALORIE).isPresent()) {
            editEntryDescriptor.setCalorie(ParserUtil.parseCalorie(argMultimap.getValue(PREFIX_CALORIE).get()));
        }
        if (argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            editEntryDescriptor.setRemark(ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).get()));
        }


        if (!editEntryDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editEntryDescriptor);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
