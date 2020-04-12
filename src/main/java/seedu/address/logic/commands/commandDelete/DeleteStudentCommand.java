package seedu.address.logic.commands.commandDelete;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.Constants;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandAdd.AddStudentCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.modelObjectTags.ID;
import seedu.address.model.modelStudent.Student;

import static java.util.Objects.requireNonNull;

/**
 * Deletes a student identified using it's displayed index from the address book.
 */
public class DeleteStudentCommand extends DeleteCommand {

    public static final String COMMAND_WORD = "delete-student";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the student identified by the ID number used in the displayed student list.\n"
            + "Parameters: ID (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 16100";

    public static final String MESSAGE_DELETE_STUDENT_SUCCESS = "Deleted Student: %1$s";

    private ID targetID;
    private Index targetIndex;
    private Student toDelete;

    public DeleteStudentCommand(ID targetID) {
        this.targetID = targetID;
    }

    public DeleteStudentCommand(Student toDelete) {
        this.toDelete = toDelete;
    }

    @Override
    protected void preprocessUndoableCommand(Model model) throws CommandException {
        if (this.toDelete == null) {
            if (!ID.isValidId(targetID.toString())) {
                throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_ID);
            }
            if (!model.has(targetID, Constants.ENTITY_TYPE.STUDENT)) {
                throw new CommandException(Messages.MESSAGE_NOTFOUND_STUDENT_DISPLAYED_ID);
            }
            this.toDelete = (Student) model.get(targetID, Constants.ENTITY_TYPE.STUDENT);
        }
        if (this.targetID == null) {
            this.targetID = toDelete.getId();
        }
        if (this.targetIndex == null) {
            this.targetIndex = model.getIndex(this.toDelete);
        }
    }

    @Override
    protected void generateOppositeCommand() {
        oppositeCommand = new AddStudentCommand(toDelete.clone(), targetIndex.getZeroBased());
    }

    @Override
    public CommandResult executeUndoableCommand(Model model) throws CommandException {
        requireNonNull(model);
        model.delete(this.toDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_STUDENT_SUCCESS, this.toDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteStudentCommand // instanceof handles nulls
                && targetID.equals(((DeleteStudentCommand) other).targetID)); // state check
    }
}
