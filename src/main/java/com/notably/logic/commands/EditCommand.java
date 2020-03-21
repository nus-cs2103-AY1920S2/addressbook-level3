package com.notably.logic.commands;

import static java.util.Objects.requireNonNull;

import com.notably.logic.commands.exceptions.CommandException;
import com.notably.model.BlockManager;
import com.notably.model.block.Block;

public class EditCommand {
    public static final String COMMAND_WORD = "edit";
    private final path toReplace;

    public EditCommand(Block block,) {
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
