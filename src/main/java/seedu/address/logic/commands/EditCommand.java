package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CURRENT_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPEC;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.course.module.Module;

/**
 * Edits Profile or Module specified by user.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits Profile or Module specified by user.\n"
            + "Parameters to edit Profile: "
            + PREFIX_NAME + "NAME "
            + "(" + PREFIX_COURSE_NAME + "COURSE) "
            + "(" + PREFIX_CURRENT_SEMESTER + "CURRENT_SEMESTER) "
            + "(" + PREFIX_SPEC + "SPECIALISATION) "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John "
            + "(" + PREFIX_COURSE_NAME + "Computer Science) "
            + "(" + PREFIX_CURRENT_SEMESTER + "4) "
            + "(" + PREFIX_SPEC + "Theory and ALgorithm) "
            + "Parameters to edit Module: "
            + PREFIX_MODULE + "MODULE "
            + "(" + PREFIX_SEMESTER + "SEMESTER) "
            + "(" + PREFIX_GRADE + "GRADE) "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS2103 "
            + "(" + PREFIX_SEMESTER + "4) "
            + "(" + PREFIX_GRADE + "A+) ";

    public static final String MESSAGE_EDIT_PROFILE_SUCCESS = "Edited Profile: " + Profile.getStaticName();
    public static final String MESSAGE_EDIT_MODULE_SUCCESS = "Edited Module: %1$s";

    private boolean toEditProfile = false;
    private Module toEditModule = null;

    public EditCommand() {
        toEditProfile = true;
    }

    public EditCommand(Module toEditModule) {
        this.toEditModule = toEditModule;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (toEditProfile) {
            return new CommandResult(String.format(MESSAGE_EDIT_PROFILE_SUCCESS, toEditProfile));
        } else if (toEditModule != null) {
            return new CommandResult(String.format(MESSAGE_EDIT_MODULE_SUCCESS, toEditModule));
        } else {
            throw new CommandException("Error: Edit Command cannot be executed");
        }
    }
}
