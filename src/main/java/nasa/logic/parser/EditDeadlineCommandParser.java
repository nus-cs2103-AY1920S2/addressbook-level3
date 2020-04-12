package nasa.logic.parser;

import static java.util.Objects.requireNonNull;
import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nasa.logic.parser.CliSyntax.PREFIX_ACTIVITY_NAME;
import static nasa.logic.parser.CliSyntax.PREFIX_DATE;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;
import static nasa.logic.parser.CliSyntax.PREFIX_NOTE;
import static nasa.logic.parser.CliSyntax.PREFIX_PRIORITY;

import java.util.NoSuchElementException;

import nasa.commons.core.index.Index;
import nasa.logic.commands.EditDeadlineCommand;
import nasa.logic.parser.exceptions.ParseException;
import nasa.model.module.ModuleCode;

/* @@author don-tay */
/**
 * Parses input arguments and creates a new EditDeadlineCommand object.
 * Format: edit-d INDEX
 */
public class EditDeadlineCommandParser implements Parser<EditDeadlineCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditDeadlineCommand
     * and returns an EditDeadlineCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditDeadlineCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE, PREFIX_DATE, PREFIX_NOTE, PREFIX_PRIORITY,
                        PREFIX_ACTIVITY_NAME);

        Index index;
        ModuleCode moduleCode;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            moduleCode = ParserUtil.parseModuleCode(argMultimap.getFirstValue(PREFIX_MODULE).get());

        } catch (ParseException pe) {
            throw new ParseException(EditDeadlineCommand.MESSAGE_USAGE, pe);
        } catch (NoSuchElementException ne) { // case when no module code is provided
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, ne);
        }

        EditDeadlineCommand.EditDeadlineDescriptor editDeadlineDescriptor =
                new EditDeadlineCommand.EditDeadlineDescriptor();

        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editDeadlineDescriptor.setDueDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_ACTIVITY_NAME).isPresent()) {
            editDeadlineDescriptor.setName(ParserUtil.parseActivityName(
                    argMultimap.getValue(PREFIX_ACTIVITY_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_NOTE).isPresent()) {
            editDeadlineDescriptor.setNote(ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get()));
        }
        if (argMultimap.getValue(PREFIX_PRIORITY).isPresent()) {
            editDeadlineDescriptor.setPriority(ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get()));
        }

        if (!editDeadlineDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditDeadlineCommand.MESSAGE_NOT_EDITED);
        }

        return new EditDeadlineCommand(index, moduleCode, editDeadlineDescriptor);
    }
}
