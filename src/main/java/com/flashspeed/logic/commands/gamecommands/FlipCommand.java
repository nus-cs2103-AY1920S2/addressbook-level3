package com.flashspeed.logic.commands.gamecommands;

import static java.util.Objects.requireNonNull;

import com.flashspeed.logic.commands.Command;
import com.flashspeed.logic.commands.CommandResult;
import com.flashspeed.logic.commands.exceptions.CommandException;
import com.flashspeed.model.Model;
import com.flashspeed.model.deck.card.BackFace;
import com.flashspeed.model.util.View;

/**
 * Represents the command that informs the model manager to fiip the current flashcard.
 */
public class FlipCommand extends Command {
    public static final String COMMAND_WORD = "flip";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Flips the card to see the back face.\n";
    public static final String MESSAGE_SUCCESS = "Did you get your answer right?";
    public static final String MESSAGE_NOT_PLAY_MODE = "Cannot flip card in non-play view";
    public static final String MESSAGE_ALREADY_FLIPPED = "Card already flipped!";
    private BackFace backFace;
    /**
     * Creates an FlipCommand.
     */
    public FlipCommand() {
        this.backFace = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getView() != View.PLAY) {
            throw new CommandException(MESSAGE_NOT_PLAY_MODE);
        }
        BackFace backFace = model.flip();
        if (backFace == null) {
            throw new CommandException(MESSAGE_ALREADY_FLIPPED);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FlipCommand); // instanceof handles nulls
    }
}
