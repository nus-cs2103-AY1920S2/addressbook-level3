package nasa.logic.parser;

import static java.util.Objects.requireNonNull;
import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nasa.logic.parser.CliSyntax.*;

import nasa.commons.core.index.Index;
import nasa.logic.commands.EditActivityCommand;
import nasa.logic.parser.exceptions.ParseException;
import nasa.model.module.Module;

/**
 * Parses input arguments and creates a new EditActivityCommand object
 */
public class EditActivityCommandParser implements Parser<EditActivityCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditActivityCommand
     * and returns an EditCommand object for execution.
     * @throws nasa.logic.parser.exceptions.ParseException if the user input does not conform the expected format
     */
    //TODO add implementation
    public EditActivityCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE, PREFIX_DATE, PREFIX_NOTE, PREFIX_PRIORITY, PREFIX_ACTIVITY_NAME);

        Index index;
        try {
            // TODO: de-conflict input format for edit command before changing how its parsed
             index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditActivityCommand.MESSAGE_USAGE),
                    pe);
        }

        EditActivityCommand.EditActivityDescriptor editActivityDescriptor = new EditActivityCommand.EditActivityDescriptor();
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editActivityDescriptor.setDate(nasa.logic.parser.ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_ACTIVITY_NAME).isPresent()) {
            editActivityDescriptor.setName(nasa.logic.parser.ParserUtil.parseActivityName(argMultimap.getValue(PREFIX_ACTIVITY_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_NOTE).isPresent()) {
            editActivityDescriptor.setNote(nasa.logic.parser.ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get()));
        }
        if (argMultimap.getValue(PREFIX_PRIORITY).isPresent()) {
            editActivityDescriptor.setPriority(nasa.logic.parser.ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get()));
        }

        if (!editActivityDescriptor.isAnyFieldEdited()) {
            throw new nasa.logic.parser.exceptions.ParseException(EditActivityCommand.MESSAGE_NOT_EDITED);
        }

        // TODO: get module by moduleCode
        Module module = new Module(
                nasa.logic.parser.ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get()),
                    nasa.logic.parser.ParserUtil.parseModuleName(argMultimap.getValue(PREFIX_MODULE_NAME).get()));
        // stub
        // Module module = ParserUtil.parseModule(argMultimap.getValue(PREFIX_MODULE).get()); // stub
        return new EditActivityCommand(index, module, editActivityDescriptor); // stub
    }
}
