package seedu.address.logic.commands.edit;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.item.Project;
import seedu.address.model.item.field.Name;
import seedu.address.model.item.field.Time;
import seedu.address.model.item.field.Website;
import seedu.address.model.tag.Tag;

/**
 * Edits a Project Item in the address book.
 */
public class EditProjectCommand extends EditCommand {
    private static final String EXAMPLE = "Example: "
            + COMMAND_WORD + " 1 "
            + PREFIX_ITEM + " proj "
            + PREFIX_NAME + " LaundryBot "
            + PREFIX_TIME + " 02-2022";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.\n" + EXAMPLE;
    private static final String MESSAGE_EDIT_PROJECT_SUCCESS = "Edited Project: %1$s";

    private EditProjectDescriptor editProjectDescriptor;

    /**
     * @param index                of the project in the filtered project list to edit
     * @param editProjectDescriptor details to edit the project with
     */
    public EditProjectCommand(Index index, EditProjectDescriptor editProjectDescriptor) {
        super(index);
        this.editProjectDescriptor = editProjectDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (index.getZeroBased() >= model.getProjectSize()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INDEX);
        }

        Project toEdit = model.getProjectByIndex(index);

        Project editProject = createEditedProject(toEdit, editProjectDescriptor);

        model.setProject(toEdit, editProject);
        model.setProjectToDisplay();
        model.commitResumeBook();
        return new CommandResult(editProject.toString(), String.format(MESSAGE_EDIT_PROJECT_SUCCESS, editProject));
    }

    /**
     * Creates the edited Project item from the project to be edited and the descriptor.
     * @param toEdit Project item to be edited
     * @param editProjectDescriptor Descriptor parsed from input of user
     * @return Edited Project item.
     */
    private static Project createEditedProject(
            Project toEdit, EditProjectDescriptor editProjectDescriptor) {
        Name updatedName = editProjectDescriptor.getName().orElse(toEdit.getName());
        Time updatedTime = editProjectDescriptor.getTime().orElse(toEdit.getTime());
        Website updatedWebsite = editProjectDescriptor.getWebsite().orElse(toEdit.getWebsite());
        String updatedDesc = editProjectDescriptor.getDescription().orElse(toEdit.getDescription());
        Set<Tag> updatedTags = editProjectDescriptor.getTags().orElse(toEdit.getTags());
        int id = toEdit.getId();
        return new Project(updatedName, updatedTime, updatedWebsite, updatedDesc, updatedTags, id);
    }
}
