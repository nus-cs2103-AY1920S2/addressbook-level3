package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.taskcommand.listcommand.ListModuleTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.nusmodule.ModuleCode;

/**
 * Parses input arguments and creates a new ListModuleTaskCommand object
 */
public class ListModuleTaskCommandParser implements Parser<ListModuleTaskCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ListModuleTaskCommand
     * and returns a ListModuleTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListModuleTaskCommand parse(String args) throws ParseException {
        try {
            ModuleCode moduleCode = ParserUtil.parseModuleCode(args);
            return new ListModuleTaskCommand(moduleCode);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListModuleTaskCommand.MESSAGE_USAGE), pe);
        }
    }
}
