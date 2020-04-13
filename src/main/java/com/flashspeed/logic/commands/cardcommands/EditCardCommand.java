package com.flashspeed.logic.commands.cardcommands;

import static java.util.Objects.requireNonNull;

import com.flashspeed.commons.core.Messages;
import com.flashspeed.commons.core.index.Index;
import com.flashspeed.logic.commands.Command;
import com.flashspeed.logic.commands.CommandResult;
import com.flashspeed.logic.commands.exceptions.CommandException;
import com.flashspeed.model.Model;
import com.flashspeed.model.deck.card.BackFace;
import com.flashspeed.model.deck.card.Card;
import com.flashspeed.model.deck.card.FrontFace;
import com.flashspeed.model.util.View;

/**
 * Edits a card in the current deck.
 */
public class EditCardCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits a card in the current deck.\n"
            + "Either FRONT or BACK can be omitted.\n"
            + "Parameters: \n"
            + "INDEX (must be a positive integer) FRONT:BACK\n"
            + "or "
            + "INDEX (must be a positive integer) :BACK\n"
            + "or "
            + "INDEX (must be a positive integer) FRONT:\n"
            + "Example 1: " + COMMAND_WORD + " "
            + "1 "
            + "ありがとう:thanks\n"
            + "Example 2: " + COMMAND_WORD + " "
            + "1 "
            + "ありがとう:\n"
            + "Example 3: " + COMMAND_WORD + " "
            + "1 "
            + ":thanks";

    public static final String MESSAGE_SUCCESS = "Card edited: %1$s";
    public static final String MESSAGE_NOT_IN_VIEW_MODE = "Cannot edit card in the play view";

    private final Index targetIndex;
    private final FrontFace front;
    private final BackFace back;
    private Card editedCard;

    public EditCardCommand(Index targetIndex, FrontFace front, BackFace back) {
        this.targetIndex = targetIndex;
        this.front = front;
        this.back = back;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getView() == View.PLAY) {
            throw new CommandException(MESSAGE_NOT_IN_VIEW_MODE);
        }

        if (model.getView() != View.DECK) {
            throw new CommandException(Messages.MESSAGE_NOT_IN_DECK_VIEW);
        }
        if (targetIndex.getZeroBased() >= model.getCurrentDeck().getSize()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
        }

        Card oldCard = model.getCard(targetIndex);

        if (front.toString().isBlank()) {
            FrontFace newFront = new FrontFace(oldCard.getFrontFace().toString());
            editedCard = new Card(newFront, back);
        } else if (back.toString().isBlank()) {
            BackFace newBack = new BackFace(oldCard.getBackFace().toString());
            editedCard = new Card(front, newBack);
        } else {
            editedCard = new Card(front, back);
        }

        model.replaceCard(oldCard, editedCard);
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedCard));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditCardCommand // instanceof handles nulls
                && targetIndex.equals(((EditCardCommand) other).targetIndex)); // state check
    }
}
