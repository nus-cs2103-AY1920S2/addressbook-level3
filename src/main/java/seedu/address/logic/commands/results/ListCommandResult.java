package seedu.address.logic.commands.results;

/**
 * Represents the result of the List command execution.
 */
public class ListCommandResult extends CommandResult {

    /**
     * Constructs a {@code ListCommandResult} with the specified {@code dataToUser}, {@code feedbackToUser}, and
     * {@code displayType}.
     * @param dataToUser data to show user.
     * @param feedbackToUser feedback to user.
     * @param displayType the alias of the item type.
     */
    public ListCommandResult(String dataToUser, String feedbackToUser, String displayType) {
        super(dataToUser, feedbackToUser, displayType);
        super.isShowPreview = false;
        super.isShowHelp = false;
        super.isExit = false;
    }
}
