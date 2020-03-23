package com.notably.logic.commands;

import static java.util.Objects.requireNonNull;

import com.notably.commons.core.path.AbsolutePath;
import com.notably.commons.core.path.RelativePath;
import com.notably.commons.core.path.exceptions.InvalidPathException;
import com.notably.logic.commands.exceptions.CommandException;
import com.notably.model.Model;
import com.notably.model.block.Block;

/**
 * Represent a command used to add a new block.
 */
public class NewCommand extends Command {
    public static final String COMMAND_WORD = "new";
    private final Block toAdd;
    private RelativePath path;

    public NewCommand(Block block, RelativePath path) {
        requireNonNull(block);
        this.toAdd = block;
        this.path = path;
    }

    /**
     * Add a new block to the tree structure.
     * @param notablyModel used to access the tree structure.
     * @throws CommandException when block of the same Title is detected.
     */
    public void execute(Model notablyModel) throws CommandException {
        requireNonNull(notablyModel);
        try {
            AbsolutePath addPath = this.path.toAbsolutePath(notablyModel.getCurrentlyOpenPath());
            if (notablyModel.hasPath(addPath)) {
                throw new CommandException("Block with the same Title detected.");
            }
        } catch (InvalidPathException ex) {
            throw new CommandException(ex.getMessage());
        }

        notablyModel.addBlockToCurrentPath(toAdd);
    }
}
