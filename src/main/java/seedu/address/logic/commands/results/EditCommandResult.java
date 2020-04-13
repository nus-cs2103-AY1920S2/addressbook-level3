package seedu.address.logic.commands.results;

/**
 * Represents the result of the Edit command execution.
 */
public class EditCommandResult extends CommandResult {

    /**
     * Constructs an {@code EditCommandResult} with the specified {@code dataToUser}, {@code feedbackToUser}, and
     * {@code displayType}.
     * @param dataToUser data to show user.
     * @param feedbackToUser feedback to user.
     * @param displayType the alias of the item type.
     */
    public EditCommandResult(String dataToUser, String feedbackToUser, String displayType) {
        super(dataToUser, feedbackToUser, displayType);
        super.isShowPreview = false;
        super.isShowHelp = false;
        super.isExit = false;
    }
}
