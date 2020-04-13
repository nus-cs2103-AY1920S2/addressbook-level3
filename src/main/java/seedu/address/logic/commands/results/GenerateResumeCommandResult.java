package seedu.address.logic.commands.results;

/**
 * Represents the result of the GenerateResume command execution.
 */
public class GenerateResumeCommandResult extends CommandResult {

    /**
     * Constructs a {@code GenerateResumeCommandResult} with the specified {@code dataToUser}, {@code feedbackToUser},
     * and {@code displayType}.
     * @param dataToUser data to show user.
     * @param feedbackToUser feedback to user.
     * @param displayType the alias of the item type.
     */
    public GenerateResumeCommandResult(String dataToUser, String feedbackToUser, String displayType) {
        super(dataToUser, feedbackToUser, displayType);
        super.isShowPreview = false;
        super.isShowHelp = false;
        super.isExit = false;
    }
}
