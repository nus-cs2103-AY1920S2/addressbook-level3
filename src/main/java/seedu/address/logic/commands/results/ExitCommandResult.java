package seedu.address.logic.commands.results;

/**
 * Represents the result of the Exit command execution.
 */
public class ExitCommandResult extends CommandResult {

    /**
     * Constructs an {@code ExitCommandResult} with the specified {@code dataToUser}, {@code feedbackToUser}, and
     * {@code displayType}.
     * @param dataToUser data to show user.
     * @param feedbackToUser feedback to user.
     * @param displayType the alias of the item type.
     */
    public ExitCommandResult(String dataToUser, String feedbackToUser, String displayType) {
        super(dataToUser, feedbackToUser, displayType);
        super.isShowPreview = false;
        super.isShowHelp = false;
        super.isExit = true;
    }
}
