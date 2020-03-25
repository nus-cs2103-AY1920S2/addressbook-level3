package com.notably.logic.commands;

import static java.util.Objects.requireNonNull;

import com.notably.commons.core.path.AbsolutePath;
import com.notably.model.Model;

/**
 * Represents a command that opens a path.
 */
public class OpenCommand extends Command {
    public static final String COMMAND_WORD = "open";
    private AbsolutePath path;

    public OpenCommand(AbsolutePath path) {
        this.path = path;
    }

    /**
     * Open block path.
     * @param notablyModel used to open path.
     */
    @Override
    public void execute(Model notablyModel) {
        requireNonNull(notablyModel);
        notablyModel.setCurrentlyOpenBlock(path);
    }

}
