package com.notably.logic.commands;

import com.notably.commons.core.path.Path;
import com.notably.logic.commands.exceptions.CommandException;
import com.notably.model.BlockManager;

/**
 * Represents a command that opens a path.
 */
public class OpenCommand extends Command {
    public static final String COMMAND_WORD = "open";

    private final Path path;
    public OpenCommand(Path path) {
        this.path = path;
    }
    public void execute(BlockManager notablyModel) throws CommandException {
        notablyModel.openBlock(path);
    }

}
