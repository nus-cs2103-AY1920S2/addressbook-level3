package seedu.address.logic.commands.results;

/**
 * Represents the result of the ResumePreview command execution.
 */
public class ResumePreviewCommandResult extends CommandResult {

    /**
     * Constructs a {@code ResumePreviewCommandResult} with the specified {@code dataToUser}, {@code feedbackToUser},
     * and {@code displayType}.
     * @param dataToUser data to show user.
     * @param feedbackToUser feedback to user.
     * @param displayType the alias of the item type.
     */
    public ResumePreviewCommandResult(String dataToUser, String feedbackToUser, String displayType) {
        super(dataToUser, feedbackToUser, displayType);
        super.isShowPreview = true;
        super.isShowHelp = false;
        super.isExit = false;
    }
}
