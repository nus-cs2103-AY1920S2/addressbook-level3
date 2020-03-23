package com.notably.logic.commands;

import static java.util.Objects.requireNonNull;

import com.notably.commons.core.path.Path;
import com.notably.logic.commands.exceptions.CommandException;
import com.notably.model.Model;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";
    private final Path targetPath;

    public DeleteCommand(Path targetPath) {
        this.targetPath = targetPath;
    }

    @Override
    public void execute(Model model) throws CommandException {
        requireNonNull(model);
        model.deleteBlock(this.targetPath);
    }



}
