package hirelah.logic.commands.interview;

import static hirelah.logic.util.CommandUtil.saveTranscript;

import hirelah.commons.exceptions.IllegalValueException;
import hirelah.logic.commands.Command;
import hirelah.logic.commands.CommandResult;
import hirelah.logic.commands.exceptions.CommandException;
import hirelah.model.Model;
import hirelah.model.hirelah.Attribute;
import hirelah.storage.Storage;

/**
 * Attempts to find the attribute specified by the user, and update the score of that attribute for
 * the currently interviewed interviewee.
 */
public class ScoreCommand extends Command {
    public static final String MESSAGE_SUCCESS = "Scored %.2f to %s";
    public static final String MESSAGE_SCORE_OUT_OF_BOUND = "The score provided: %s is out of range. The score "
            + "should ranges from 0 to 10, inclusive.";
    public static final String MESSAGE_FORMAT = ":set <attribute> <score>";
    public static final String MESSAGE_USAGE = MESSAGE_FORMAT
            + ": gives the interviewee this score to the given attribute.\n"
            + "Example: :set leadership 5.0";

    private String attributePrefix;
    private double score;

    public ScoreCommand(String attributePrefix, double score) {
        this.attributePrefix = attributePrefix;
        this.score = score;
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        Attribute attribute;

        if (score < 0 || score > 10) {
            throw new CommandException(String.format(MESSAGE_SCORE_OUT_OF_BOUND, score));
        }
        try {
            attribute = model.getAttributeList().find(attributePrefix);
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }
        model.getCurrentTranscript().setAttributeScore(attribute, this.score);
        saveTranscript(model, storage);
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.score, attribute));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScoreCommand // instanceof handles nulls
                && attributePrefix.equals(((ScoreCommand) other).attributePrefix)
                && score == ((ScoreCommand) other).score);
    }
}
