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
import nasa.logic.commands.EditActivityCommand;
import nasa.logic.parser.exceptions.ParseException;
import nasa.model.module.ModuleCode;

/**
 * Parses input arguments and creates a new EditActivityCommand object
 */
public class EditActivityCommandParser implements Parser<EditActivityCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditActivityCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditActivityCommand parse(String args) throws ParseException {
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
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditActivityCommand.MESSAGE_USAGE),
                    pe);
        } catch (NoSuchElementException ne) { // case when no module code is provided
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditActivityCommand.MESSAGE_USAGE),
                    ne);
        }

        EditActivityCommand.EditActivityDescriptor editActivityDescriptor =
                new EditActivityCommand.EditActivityDescriptor();
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editActivityDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_ACTIVITY_NAME).isPresent()) {
            editActivityDescriptor.setName(ParserUtil.parseActivityName(
                    argMultimap.getValue(PREFIX_ACTIVITY_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_NOTE).isPresent()) {
            editActivityDescriptor.setNote(ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get()));
        }
        if (argMultimap.getValue(PREFIX_PRIORITY).isPresent()) {
            editActivityDescriptor.setPriority(ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get()));
        }

        if (!editActivityDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditActivityCommand.MESSAGE_NOT_EDITED);
        }

        return new EditActivityCommand(index, moduleCode, editActivityDescriptor);
    }
}
