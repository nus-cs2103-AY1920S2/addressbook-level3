package com.notably.logic.commands;

import static java.util.Objects.requireNonNull;

import com.notably.logic.commands.exceptions.CommandException;
import com.notably.model.BlockManager;
import com.notably.model.block.Block;
import com.notably.model.block.Path;

public class NewCommand extends Command {
    public static final String COMMAND_WORD = "new";
    private final Block toAdd;
    public boolean jump;

    public NewCommand(Block block, Boolean jump) {
        requireNonNull(block);
        this.toAdd = block;
        this.jump = jump;
    }
    public void execute(BlockManager notablyModel) throws CommandException {
        if (notablyModel.hasBlock(toAdd)) {
            throw new CommandException("Block with the same Title detected.");
        }
        notablyModel.addBlock(toAdd);
        if (this.jump) {
            notablyModel.openBlock(new Path(toAdd.getTitle()));
        }
    }
}
