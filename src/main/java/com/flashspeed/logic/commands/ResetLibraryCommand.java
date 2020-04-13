package com.flashspeed.logic.commands;

import static java.util.Objects.requireNonNull;

import com.flashspeed.logic.commands.exceptions.CommandException;
import com.flashspeed.model.Library;
import com.flashspeed.model.Model;
import com.flashspeed.model.util.View;

/**
 * Clears the library.
 */
public class ResetLibraryCommand extends Command {

    public static final String COMMAND_WORD = "reset";
    public static final String MESSAGE_SUCCESS = "Library has been cleared!";
    public static final String MESSAGE_NOT_IN_VIEW_MODE = "Cannot reset library in the play view";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (model.getView() == View.PLAY) {
            throw new CommandException(MESSAGE_NOT_IN_VIEW_MODE);
        }

        requireNonNull(model);
        model.setLibrary(new Library());
        model.returnToLibrary();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
