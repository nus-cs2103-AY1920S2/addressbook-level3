package com.notably.logic.commands;

import static java.util.Objects.requireNonNull;

import com.notably.commons.path.AbsolutePath;
import com.notably.logic.commands.exceptions.CommandException;
import com.notably.model.Model;

/**
 * Represent a command that edits the block's body.
 */
public class EditCommand extends Command {
    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_SHORTHAND = "e";

    private static final String ERROR_ROOT_MODIFICATION = "Editing the root block is forbidden.";
    private static final AbsolutePath ROOT = AbsolutePath.fromString("/");

    /**
     * Edit the Block body of the current directory.
     * @param notablyModel used to access the tree structure.
     */
    public void execute(Model notablyModel) throws CommandException {
        requireNonNull(notablyModel);
        if (notablyModel.getCurrentlyOpenPath().equals(ROOT)) {
            throw new CommandException(ERROR_ROOT_MODIFICATION);
        }
        notablyModel.setBlockEditable(true);
    }
}
