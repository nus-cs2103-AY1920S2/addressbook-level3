package com.flashspeed.logic.commands.gamecommands;

import static java.util.Objects.requireNonNull;

import com.flashspeed.logic.commands.Command;
import com.flashspeed.logic.commands.CommandResult;
import com.flashspeed.logic.commands.exceptions.CommandException;
import com.flashspeed.model.Model;
import com.flashspeed.model.util.View;

/**
 * Represents the command that stops the current game session.
 */
public class StopCommand extends Command {
    public static final String COMMAND_WORD = "stop";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Stop the current game session.\n";
    public static final String MESSAGE_SUCCESS = "Session stopped!";
    public static final String MESSAGE_NOT_PLAY_MODE = "You have not started a new game session";
    /**
     * Creates an StopCommand
     */
    public StopCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getView() != View.PLAY) {
            throw new CommandException(MESSAGE_NOT_PLAY_MODE);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS), false, false, true, model.stop());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof StopCommand; // instanceof handles nulls
    }
}
