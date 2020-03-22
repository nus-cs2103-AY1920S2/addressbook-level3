package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.course.module.ModuleCode;
import seedu.address.model.profile.course.module.personal.Deadline;

/**
 * Deletes a profile identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the profile identified by the name used in the displayed profile list.\n"
            + "Parameters: "
            + "(" + PREFIX_NAME + "NAME) "
            + "(" + PREFIX_MODULE + "MODULE) "
            + "(" + PREFIX_TASK + "TASK) "
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS2103 "
            + PREFIX_TASK + "assignment";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Profile: %1$s";
    public static final String MESSAGE_DELETE_MODULE_SUCCESS = "Deleted Module: %1$s";

    //private final Index targetIndex;
    private final Name deleteName;
    private final ModuleCode deleteModuleCode;
    private final Deadline deleteDeadline;

    /*public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }*/

    /**
     * Creates a delete command to delete the profile with name {@code name}.
     */
    public DeleteCommand(Name name) {
        requireNonNull(name);
        this.deleteName = name;
        this.deleteModuleCode = null;
        this.deleteDeadline = null;
    }

    /**
     * Creates a delete command to delete the module with module code {@code moduleCode} in the current profile.
     */
    public DeleteCommand(ModuleCode moduleCode) {
        requireNonNull(moduleCode);
        this.deleteModuleCode = moduleCode;
        this.deleteName = null;
        this.deleteDeadline = null;
    }

    /**
     * Creates a delete command to delete the deadline with description {@code deadline}
     * of the module with module code {@code moduleCode} of the current profile.
     */
    public DeleteCommand(ModuleCode moduleCode, Deadline deadline) {
        requireNonNull(moduleCode);
        requireNonNull(deadline);
        this.deleteModuleCode = moduleCode;
        this.deleteDeadline = deadline;
        this.deleteName = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Deleting a user profile
        if (deleteName != null) {
            if (model.hasProfile(deleteName)) {
                Profile profileToDelete = model.getProfile(deleteName);
                model.deletePerson(profileToDelete);
                return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, profileToDelete));
            } else {
                throw new CommandException("Profile with name " + deleteName + " does not exist!");
            }
        } else if (deleteModuleCode != null) {
            Profile profile = model.getFirstProfile(); // To edit when dealing with multiple profiles
            if (!profile.hasModule(deleteModuleCode)) {
                throw new CommandException(
                        "User is currently not taking a module with module code " + deleteModuleCode.toString());
            }
            //Module module = profile.getModule(deleteModuleCode);
            if (deleteDeadline == null) { // Deleting a module
                profile.deleteModule(deleteModuleCode);
                return new CommandResult(String.format(MESSAGE_DELETE_MODULE_SUCCESS, deleteModuleCode));
            } /*else { // Deleting a deadline/task
                // TODO
            }*/
        }

        throw new CommandException("Please ensure that either a profile name or a module code has been entered");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this && other instanceof DeleteCommand) {
            return true;
        }
        DeleteCommand command = ((DeleteCommand) other);
        boolean sameName = (deleteName == null && command.deleteName == null)
                || ((deleteName != null) && this.deleteName.equals(command.deleteName));
        boolean sameModuleCode = (deleteModuleCode == null && command.deleteModuleCode == null)
                || ((deleteModuleCode != null) && this.deleteModuleCode.equals(command.deleteModuleCode));
        boolean sameDeadline = (deleteDeadline == null && command.deleteDeadline == null)
                || ((deleteDeadline != null) && this.deleteDeadline.equals(command.deleteDeadline));
        return sameName && sameModuleCode && sameDeadline;
    }

    /*@Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Profile> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Profile profileToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePerson(profileToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, profileToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }*/
}
