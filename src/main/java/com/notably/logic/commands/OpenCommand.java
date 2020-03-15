package com.notably.logic.commands;

import com.notably.logic.commands.exceptions.CommandException;
import com.notably.model.BlockManager;
import com.notably.model.block.Path;

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
