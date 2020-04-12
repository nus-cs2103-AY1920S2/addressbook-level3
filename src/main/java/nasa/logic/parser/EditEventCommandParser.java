package nasa.logic.parser;

import static java.util.Objects.requireNonNull;
import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nasa.logic.parser.CliSyntax.PREFIX_ACTIVITY_NAME;
import static nasa.logic.parser.CliSyntax.PREFIX_END_DATE;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;
import static nasa.logic.parser.CliSyntax.PREFIX_NOTE;
import static nasa.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static nasa.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.util.NoSuchElementException;

import nasa.commons.core.index.Index;
import nasa.logic.commands.EditEventCommand;
import nasa.logic.parser.exceptions.ParseException;
import nasa.model.module.ModuleCode;

/* @@author don-tay */
/**
 * Parses input arguments and creates a new EditEventCommand object
 */
public class EditEventCommandParser implements Parser<EditEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditEventCommand
     * and returns an EditEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE, PREFIX_START_DATE, PREFIX_END_DATE, PREFIX_NOTE,
                        PREFIX_PRIORITY, PREFIX_ACTIVITY_NAME);

        Index index;
        ModuleCode moduleCode;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            moduleCode = ParserUtil.parseModuleCode(argMultimap.getFirstValue(PREFIX_MODULE).get());

        } catch (ParseException pe) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        } catch (NoSuchElementException ne) { // case when no module code is provided
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        EditEventCommand.EditEventDescriptor editEventDescriptor =
                new EditEventCommand.EditEventDescriptor();

        if (argMultimap.getValue(PREFIX_START_DATE).isPresent()) {
            editEventDescriptor.setStartDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_START_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_END_DATE).isPresent()) {
            editEventDescriptor.setEndDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_END_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_ACTIVITY_NAME).isPresent()) {
            editEventDescriptor.setName(ParserUtil.parseActivityName(
                    argMultimap.getValue(PREFIX_ACTIVITY_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_NOTE).isPresent()) {
            editEventDescriptor.setNote(ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get()));
        }
        if (argMultimap.getValue(PREFIX_PRIORITY).isPresent()) {
            editEventDescriptor.setPriority(ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get()));
        }

        if (!editEventDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditEventCommand.MESSAGE_NOT_EDITED);
        }

        return new EditEventCommand(index, moduleCode, editEventDescriptor);
    }
}
