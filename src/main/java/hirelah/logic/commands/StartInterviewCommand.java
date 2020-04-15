package hirelah.logic.commands;

import static hirelah.logic.util.CommandUtil.saveInterviewees;
import static hirelah.logic.util.CommandUtil.saveTranscript;

import hirelah.commons.util.ModelUtil;
import hirelah.logic.commands.exceptions.CommandException;
import hirelah.model.Model;
import hirelah.model.hirelah.Interviewee;
import hirelah.model.hirelah.exceptions.IllegalActionException;
import hirelah.storage.Storage;

/**
 * Changes the app to the interview phase to interview the given interviewee. Fails if no interviewee can
 * be identified via the String given, or the interviewee is already interviewed.
 */
public class StartInterviewCommand extends Command {
    public static final String MESSAGE_SUCCESS = "Interview with %s started!";
    public static final boolean DESIRED_MODEL_FINALIZED_STATE = true;

    private String identifier;

    public StartInterviewCommand(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        ModelUtil.validateFinalisation(model, DESIRED_MODEL_FINALIZED_STATE);
        try {
            Interviewee interviewee = model.getIntervieweeList().getInterviewee(identifier);
            model.startInterview(interviewee);
            saveTranscript(model, storage);
            saveInterviewees(model, storage);
            return new ToggleCommandResult(String.format(MESSAGE_SUCCESS, interviewee), ToggleView.TRANSCRIPT);
        } catch (IllegalActionException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartInterviewCommand // instanceof handles nulls
                && identifier.equals(((StartInterviewCommand) other).identifier));
    }
}
