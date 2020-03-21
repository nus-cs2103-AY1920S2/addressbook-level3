package com.notably.logic.commands;

import com.notably.commons.core.path.RelativePath;
import com.notably.logic.commands.exceptions.CommandException;
import com.notably.model.BlockManager;

/**
 * Represents a command that opens a path.
 */
public class OpenCommand extends Command {
    public static final String COMMAND_WORD = "open";
    private final RelativePath path;

    public OpenCommand(RelativePath path) {
        this.path = path;
    }

    /**
     * Open block path
     * @param notablyModel used to open path.
     * @throws CommandException if there is an invalid path error.
     */
    public void execute(BlockManager notablyModel) throws CommandException {
        //TODO: Change relative path to Absolute after BlockManager Impl is merged
        notablyModel.openBlock(path);
    }

}
