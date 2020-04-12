package nasa.logic.commands;

import java.util.HashMap;

import nasa.logic.commands.addcommands.AddDeadlineCommand;
import nasa.logic.commands.addcommands.AddEventCommand;
import nasa.logic.commands.module.AddModuleCommand;
import nasa.logic.commands.module.DeleteModuleCommand;
import nasa.logic.commands.module.EditModuleCommand;

/**
 * Dictionary of commands and their message usage.
 */
public class CommandHint {
    private static HashMap<String, String> commandList = new HashMap<>() {{
            put(AddDeadlineCommand.COMMAND_WORD, AddDeadlineCommand.MESSAGE_USAGE);
            put(AddEventCommand.COMMAND_WORD, AddEventCommand.MESSAGE_USAGE);
            put(AddModuleCommand.COMMAND_WORD, AddModuleCommand.MESSAGE_USAGE);
            put(DeleteModuleCommand.COMMAND_WORD, DeleteModuleCommand.MESSAGE_USAGE);
            put(EditModuleCommand.COMMAND_WORD, EditModuleCommand.MESSAGE_USAGE);
            put(ClearCommand.COMMAND_WORD, "Clears NASA book");
            put(ContinueCommand.COMMAND_WORD, ContinueCommand.MESSAGE_USAGE);
            put(DeleteDeadlineCommand.COMMAND_WORD, DeleteDeadlineCommand.MESSAGE_USAGE);
            put(DeleteEventCommand.COMMAND_WORD, DeleteEventCommand.MESSAGE_USAGE);
            put(DoneCommand.COMMAND_WORD, DoneCommand.MESSAGE_USAGE);
            put(EditDeadlineCommand.COMMAND_WORD, EditDeadlineCommand.MESSAGE_USAGE);
            put(EditEventCommand.COMMAND_WORD, EditEventCommand.MESSAGE_USAGE);
            put(ExportCalendarCommand.COMMAND_WORD, ExportCalendarCommand.MESSAGE_USAGE);
            put(ExportQrCommand.COMMAND_WORD, ExportQrCommand.MESSAGE_USAGE);
            put(FindCommand.COMMAND_WORD, FindCommand.MESSAGE_USAGE);
            put(HelpCommand.COMMAND_WORD, HelpCommand.MESSAGE_USAGE);
            put(ListCommand.COMMAND_WORD, ListCommand.MESSAGE_USAGE);
            put(QuoteCommand.COMMAND_WORD, QuoteCommand.MESSAGE_USAGE);
            put(RedoCommand.COMMAND_WORD, RedoCommand.MESSAGE_USAGE);
            put(RepeatDeadlineCommand.COMMAND_WORD, RepeatDeadlineCommand.MESSAGE_USAGE);
            put(RepeatEventCommand.COMMAND_WORD, RepeatEventCommand.MESSAGE_USAGE);
            put(SortCommand.COMMAND_WORD, SortCommand.MESSAGE_USAGE);
            put(UndoCommand.COMMAND_WORD, UndoCommand.MESSAGE_USAGE);
        }};


    public static HashMap<String, String> getCommandList() {
        return commandList;
    }
}
