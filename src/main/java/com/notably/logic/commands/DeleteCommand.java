package com.notably.logic.commands;

import static java.util.Objects.requireNonNull;

import com.notably.commons.core.path.AbsolutePath;
import com.notably.commons.core.path.Path;
import com.notably.commons.core.path.RelativePath;
import com.notably.commons.core.path.exceptions.InvalidPathException;
import com.notably.logic.commands.exceptions.CommandException;
import com.notably.model.Model;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";
    private Path targetPath;

    public DeleteCommand(Path targetPath) {
        this.targetPath = targetPath;
    }

    @Override
    public void execute(Model notablyModel) throws CommandException {
        requireNonNull(notablyModel);
        if (this.targetPath instanceof RelativePath) {
            RelativePath toConvert = (RelativePath) this.targetPath;
            try {
                this.targetPath = toConvert.toAbsolutePath(notablyModel.getCurrentlyOpenPath());
            } catch (InvalidPathException ex) {
                throw new CommandException(ex.getMessage());
            }
        }
        notablyModel.removeBlock((AbsolutePath) this.targetPath);
    }



}
