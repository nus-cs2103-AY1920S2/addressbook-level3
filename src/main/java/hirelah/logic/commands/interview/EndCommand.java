package hirelah.logic.commands.interview;

import static hirelah.logic.util.CommandUtil.saveTranscript;

import hirelah.logic.commands.Command;
import hirelah.logic.commands.CommandResult;
import hirelah.logic.commands.ToggleCommandResult;
import hirelah.logic.commands.ToggleView;
import hirelah.logic.commands.exceptions.CommandException;
import hirelah.model.Model;
import hirelah.model.hirelah.Attribute;
import hirelah.model.hirelah.Transcript;
import hirelah.storage.Storage;

/**
 * Ends the current interview and returns to the Interviewee page. Will throw an error if the interviewee still
 * has missing attributes without a score.
 */
public class EndCommand extends Command {
    public static final String MESSAGE_SUCCESS = "Ended interview with %s.";
    public static final String MESSAGE_SCORELESS_ATTRIBUTES =
            "Unable to finish interview with some attributes not scored yet!";

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        Transcript transcript = model.getCurrentTranscript();
        for (Attribute attribute : model.getAttributeList()) {
            if (!transcript.isAttributeScored(attribute)) {
                throw new CommandException(MESSAGE_SCORELESS_ATTRIBUTES);
            }
        }
        transcript.complete();
        CommandResult result = new ToggleCommandResult(
                String.format(MESSAGE_SUCCESS, model.getCurrentInterviewee()),
                ToggleView.CLOSE_TRANSCRIPT);
        saveTranscript(model, storage);
        model.endInterview();
        return result;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndCommand);
    }
}
