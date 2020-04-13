package com.flashspeed.logic.commands.gamecommands;

import static java.util.Objects.requireNonNull;

import com.flashspeed.logic.commands.Command;
import com.flashspeed.logic.commands.CommandResult;
import com.flashspeed.logic.commands.exceptions.CommandException;
import com.flashspeed.model.Model;
import com.flashspeed.model.deck.card.Card;
import com.flashspeed.model.util.View;

/**
 * Represents the command that indicates that the user answered the question incorrectly.
 */
public class AnswerNoCommand extends Command {
    public static final String COMMAND_WORD = "no";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Answer no if you have the wrong answer.\n";
    public static final String MESSAGE_SUCCESS = "Oops, you got it wrong!";
    public static final String MESSAGE_NOT_PLAY_MODE = "Cannot answer card in non-play view";
    public static final String MESSAGE_NOT_FLIPPED = "Card has not flipped yet!";
    public static final String MESSAGE_END_GAME = "Session completed!";

    /**
     * Creates a AnswerNoCommand
     */
    public AnswerNoCommand() { }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getView() != View.PLAY) {
            throw new CommandException(MESSAGE_NOT_PLAY_MODE);
        }
        if (!model.getGame().isFlipped()) {
            throw new CommandException(MESSAGE_NOT_FLIPPED);
        }
        Card nextCard = model.answerNo();
        if (nextCard == null) {
            return new CommandResult(String.format(MESSAGE_END_GAME), false, false, true, model.stop());
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AnswerNoCommand); // instanceof handles nulls

    }
}
