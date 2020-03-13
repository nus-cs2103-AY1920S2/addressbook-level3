package csdev.couponstash.logic.commands;

import csdev.couponstash.model.Model;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("Hello from undo");
    }
}
