package seedu.address.logic.commands.edit;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.logic.commands.results.EditCommandResult;
import seedu.address.model.Model;
import seedu.address.model.item.Resume;
import seedu.address.model.item.exceptions.DuplicateItemException;
import seedu.address.model.item.field.Name;
import seedu.address.model.tag.Tag;

/**
 * Edits a Resume Item in the resume book.
 */
public class EditResumeCommand extends EditCommand {

    public static final String MESSAGE_EDIT_RESUME_SUCCESS = "Edited Resume: %1$s";
    public static final String MESSAGE_SAME_RESUME = "You are not making any changes to this resume.";

    private static final String FIELDS = "Format: " + COMMAND_WORD
            + " INDEX "
            + PREFIX_ITEM + " res "
            + "[" + PREFIX_NAME + " RESUME_NAME] "
            + "[" + PREFIX_TAG + " TAG]....\n";
    private static final String EXAMPLE = "Example: "
            + COMMAND_WORD + " 1 "
            + PREFIX_ITEM + " res "
            + PREFIX_NAME + " Resume 1 "
            + PREFIX_TAG + " frontend ";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.\n" + FIELDS + EXAMPLE;

    private EditResumeDescriptor editResumeDescriptor;
    /**
     * @param index                of the resume in the filtered resume list to edit
     * @param editResumeDescriptor details to edit the resume with
     */
    public EditResumeCommand(Index index, EditResumeDescriptor editResumeDescriptor) {
        super(index);
        this.editResumeDescriptor = editResumeDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (index.getZeroBased() >= model.getResumeSize()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INDEX);
        }

        Resume toEdit = model.getResumeByIndex(index);

        Resume editedResume = createEditedResume(toEdit, editResumeDescriptor);

        if (editedResume.equals(toEdit)) {
            throw new CommandException(MESSAGE_SAME_RESUME);
        }

        try {
            model.setResume(toEdit, editedResume);
            model.setResumeToDisplay();
            model.commitResumeBook();
        } catch (DuplicateItemException e) {
            throw new CommandException(MESSAGE_DUPLICATE_ITEM);
        }

        return new EditCommandResult(editedResume.toString(),
                String.format(MESSAGE_EDIT_RESUME_SUCCESS, editedResume.getName().fullName),
                model.getDisplayType());
    }

    /**
     * Creates the edited Resume item from the resume to be edited and the descriptor.
     * @param toEdit resume item to be edited
     * @param editResumeDescriptor Descriptor parsed from input of user
     * @return Edited Resume item.
     */
    public static Resume createEditedResume(Resume toEdit, EditResumeDescriptor editResumeDescriptor) {
        Name updatedName = editResumeDescriptor.getName().orElse(toEdit.getName());
        Set<Tag> updatedTags = editResumeDescriptor.getTags().orElse(toEdit.getTags());
        int id = toEdit.getId();
        Resume editedResume = new Resume(updatedName, id, updatedTags);
        editedResume.setInternshipIds(toEdit.getInternshipIds());
        editedResume.setProjectIds(toEdit.getProjectIds());
        editedResume.setSkillIds(toEdit.getSkillIds());
        return editedResume;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditResumeCommand // instanceof handles nulls
                && this.index.equals(((EditResumeCommand) other).index)
                && this.editResumeDescriptor.equals(((EditResumeCommand) other).editResumeDescriptor));
    }

}
