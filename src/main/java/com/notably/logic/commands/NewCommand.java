package com.notably.logic.commands;

import static java.util.Objects.requireNonNull;

import com.notably.logic.commands.exceptions.CommandException;
import com.notably.model.BlockManager;
import com.notably.model.block.Block;

/**
 * Represent a command used to add a new block.
 */
public class NewCommand extends Command {
    public static final String COMMAND_WORD = "new";
    private final Block toAdd;

    public NewCommand(Block block) {
        requireNonNull(block);
        this.toAdd = block;
    }

    /**
     * Add a new block to the tree structure.
     * @param notablyModel used to access the tree structure.
     * @throws CommandException when block of the same Title is detected.
     */
    public void execute(BlockManager notablyModel) throws CommandException {
        if (notablyModel.hasBlock(toAdd)) {
            throw new CommandException("Block with the same Title detected.");
        }
        notablyModel.addBlock(toAdd);
    }
}
