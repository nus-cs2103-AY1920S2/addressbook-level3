package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_EMPTY_PROFILE_LIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.CourseManager;
import seedu.address.model.ModuleManager;
import seedu.address.model.ProfileList;
import seedu.address.model.ProfileManager;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.course.module.ModuleCode;
import seedu.address.model.profile.course.module.personal.Deadline;
import seedu.address.model.profile.exceptions.DeadlineNotFoundException;

//@@author chanckben

/**
 * Deletes a profile identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the profile, module, task or grade identified by the respective parameters.\n"
            + "Parameters: "
            + "(" + PREFIX_NAME + "NAME) "
            + "(" + PREFIX_MODULE + "MODULE) "
            + "(" + PREFIX_TASK + "TASK) "
            + "(" + PREFIX_GRADE + "GRADE) "
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS2103 "
            + PREFIX_TASK + "assignment";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Profile: %1$s";
    public static final String MESSAGE_DELETE_MODULE_SUCCESS = "Deleted Module: %1$s";
    public static final String MESSAGE_DELETE_DEADLINE_SUCCESS = "Deleted Deadline: %1$s";
    public static final String MESSAGE_DELETE_GRADE_SUCCESS = "Deleted grade of module %1$s";
    public static final String MESSAGE_DELETE_DEADLINE_FAILURE = "Unable to delete task: %1$s";
    public static final String MESSAGE_DELETE_PROFILE_FAILURE = "Profile with name %1$s does not exist!";
    public static final String MESSAGE_DELETE_GRADE_FAILURE = "Module with module code %1$s has no grade";

    public static final String MESSAGE_NOT_TAKING_MODULE =
            "User is currently not taking a module with module code %1$s";

    private final Name deleteName;
    private final Set<ModuleCode> deleteModuleCodes;
    private final ArrayList<Deadline> deleteDeadlines;
    private final String deleteGrade;

    /**
     * Creates a delete command to delete the profile with name {@code name}.
     */
    public DeleteCommand(Name name) {
        requireNonNull(name);
        this.deleteName = name;
        this.deleteModuleCodes = null;
        this.deleteDeadlines = null;
        this.deleteGrade = null;
    }

    /**
     * Creates a delete command to delete the module with module code {@code moduleCode} in the current profile.
     */
    public DeleteCommand(Set<ModuleCode> moduleCodes) {
        requireNonNull(moduleCodes);
        this.deleteModuleCodes = moduleCodes;
        this.deleteName = null;
        this.deleteDeadlines = null;
        this.deleteGrade = null;
    }

    /**
     * Creates a delete command to delete the deadline with description {@code deadline}
     * of the module with module code {@code moduleCode} of the current profile.
     */
    public DeleteCommand(Set<ModuleCode> moduleCodes, ArrayList deadlines) {
        requireNonNull(moduleCodes);
        requireNonNull(deadlines);
        this.deleteModuleCodes = moduleCodes;
        this.deleteDeadlines = deadlines;
        this.deleteName = null;
        this.deleteGrade = null;
    }

    /**
     * Creates a delete command to delete the grade of the module
     * with module code {@code moduleCode} of the current profile.
     */
    public DeleteCommand(Set<ModuleCode> moduleCodes, String grade) {
        requireNonNull(moduleCodes);
        requireNonNull(grade);
        this.deleteModuleCodes = moduleCodes;
        this.deleteGrade = grade;
        this.deleteName = null;
        this.deleteDeadlines = null;
    }

    @Override
    public CommandResult execute(ProfileManager profileManager, CourseManager courseManager,
                                 ModuleManager moduleManager) throws CommandException {
        requireNonNull(profileManager);
        requireNonNull(courseManager);
        requireNonNull(moduleManager);

        // Deleting a user profile
        if (deleteName != null) {
            if (profileManager.hasProfile(deleteName)) {
                Profile profileToDelete = profileManager.getProfile(deleteName);
                profileManager.deleteProfile(profileToDelete);
                profileManager.setProfileList(new ProfileList());
                profileManager.clearDeadlineList();
                profileManager.setDisplayedView((Profile) null);
                return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, profileToDelete), true);
            } else {
                throw new CommandException(String.format(MESSAGE_DELETE_PROFILE_FAILURE, deleteName));
            }
        } else if (deleteModuleCodes != null) {
            if (!profileManager.hasOneProfile()) {
                throw new CommandException(MESSAGE_EMPTY_PROFILE_LIST);
            }

            // Case of multiple module codes: Execute DeleteCommand multiple times
            if (deleteModuleCodes.size() > 1) {
                String message = "";
                for (ModuleCode moduleCode: deleteModuleCodes) {
                    DeleteCommand deleteCommand;
                    if (deleteGrade != null) {
                        message = MESSAGE_DELETE_GRADE_SUCCESS;
                        deleteCommand = new DeleteCommand(Set.of(moduleCode), deleteGrade);
                    } else {
                        message = MESSAGE_DELETE_MODULE_SUCCESS;
                        deleteCommand = new DeleteCommand(Set.of(moduleCode));
                    }
                    deleteCommand.execute(profileManager, courseManager, moduleManager);
                }
                return new CommandResult(String.format(message, deleteModuleCodes), true);
            }

            ModuleCode deleteModuleCode = deleteModuleCodes.iterator().next();
            Profile profile = profileManager.getFirstProfile(); // To edit when dealing with multiple profiles
            if (!profile.hasModule(deleteModuleCode)) {
                throw new CommandException(String.format(MESSAGE_NOT_TAKING_MODULE, deleteModuleCode.toString()));
            }

            // Deleting a deadline/task
            if (deleteDeadlines != null) {
                try {
                    // Check if all deadlines exist first
                    for (Deadline deadline : deleteDeadlines) {
                        if (!profile.getModule(deleteModuleCode).hasDeadline(deadline)) {
                            throw new DeadlineNotFoundException();
                        }
                    }

                    for (Deadline deadline : deleteDeadlines) {
                        profile.getModule(deleteModuleCode).deleteDeadline(deadline);
                        profileManager.deleteDeadline(deadline); //delete from observablelist
                    }

                } catch (ParseException e) {
                    throw new CommandException(String.format(MESSAGE_NOT_TAKING_MODULE, deleteModuleCode.toString()));
                } catch (DeadlineNotFoundException e) {
                    throw new CommandException(String.format(MESSAGE_DELETE_DEADLINE_FAILURE, deleteDeadlines));
                }
                return new CommandResult(String.format(MESSAGE_DELETE_DEADLINE_SUCCESS, deleteDeadlines), false);
            }

            // Delete grade
            if (deleteGrade != null) {
                try {
                    profile.getModule(deleteModuleCode).deleteGrade();
                    profileManager.setDisplayedView(profile);
                    profile.updateCap();
                } catch (ParseException e) {
                    throw new CommandException(String.format(MESSAGE_NOT_TAKING_MODULE, deleteModuleCode.toString()));
                } catch (NoSuchElementException e) {
                    throw new CommandException(String.format(MESSAGE_DELETE_GRADE_FAILURE, deleteModuleCode));
                }
                return new CommandResult(String.format(MESSAGE_DELETE_GRADE_SUCCESS, deleteModuleCode), true);
            }

            // Deleting a module
            try {
                profile.deleteModule(deleteModuleCode);
                profileManager.deleteModuleDeadlines(deleteModuleCode);
                profile.updateCap();
                profileManager.setDisplayedView(profile);

            } catch (ParseException e) {
                throw new CommandException(String.format(MESSAGE_NOT_TAKING_MODULE, deleteModuleCode.toString()));
            }
            return new CommandResult(String.format(MESSAGE_DELETE_MODULE_SUCCESS, deleteModuleCode), true);
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
        boolean sameModuleCode = (deleteModuleCodes == null && command.deleteModuleCodes == null)
                || ((deleteModuleCodes != null) && this.deleteModuleCodes.equals(command.deleteModuleCodes));
        boolean sameDeadline = (deleteDeadlines == null && command.deleteDeadlines == null)
                || ((deleteDeadlines != null) && this.deleteDeadlines.equals(command.deleteDeadlines));
        boolean sameGrade = (deleteGrade == null && command.deleteGrade == null)
                || ((deleteGrade != null) && this.deleteGrade.equals(command.deleteGrade));
        return sameName && sameModuleCode && sameDeadline && sameGrade;
    }
}
