package com.notably.logic.commands;

import static java.util.Objects.requireNonNull;

import com.notably.logic.commands.exceptions.CommandException;
import com.notably.model.Model;
import com.notably.model.block.Body;
import com.notably.model.block.exceptions.CannotModifyRootException;

/**
 * Represent a command that edits the block's body.
 */
public class EditCommand extends Command {
    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_SHORTHAND = "e";
    private final Body body;

    public EditCommand(Body body) {
        requireNonNull(body);
        this.body = body;
    }

    /**
     * Edit the Block body of the current directory.
     * @param notablyModel used to access the tree structure.
     * @throws CommandException
     */
    public void execute(Model notablyModel) throws CommandException {
        requireNonNull(notablyModel);
        try {
            notablyModel.updateCurrentlyOpenBlockBody(body);
        } catch (CannotModifyRootException ex) {
            throw new CommandException(ex.getMessage());
        }
    }
}

