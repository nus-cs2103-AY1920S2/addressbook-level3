package com.notably.logic.commands;

import static java.util.Objects.requireNonNull;

import com.notably.commons.core.path.AbsolutePath;
import com.notably.commons.core.path.Path;
import com.notably.commons.core.path.RelativePath;
import com.notably.commons.core.path.exceptions.InvalidPathException;
import com.notably.logic.commands.exceptions.CommandException;
import com.notably.model.Model;

/**
 * Represents a command that opens a path.
 */
public class OpenCommand extends Command {
    public static final String COMMAND_WORD = "open";
    private Path path;

    public OpenCommand(Path path) {
        this.path = path;
    }

    /**
     * Open block path
     * @param notablyModel used to open path.
     * @throws CommandException if there is an invalid path error.
     */
    public void execute(Model notablyModel) throws CommandException {
        requireNonNull(notablyModel);
        if (this.path instanceof RelativePath) {
            RelativePath toConvert = (RelativePath) this.path;
            try {
                this.path = toConvert.toAbsolutePath(notablyModel.getCurrentlyOpenPath());
            } catch (InvalidPathException ex) {
                throw new CommandException(ex.getMessage());
            }
        }
        notablyModel.setCurrentlyOpenBlock((AbsolutePath) path);
    }

}
