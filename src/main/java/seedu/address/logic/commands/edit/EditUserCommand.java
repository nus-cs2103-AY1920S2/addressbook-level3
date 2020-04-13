package seedu.address.logic.commands.edit;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GITHUB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNIVERSITY;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.logic.commands.results.EditUserCommandResult;
import seedu.address.model.Model;
import seedu.address.model.item.Person;
import seedu.address.model.item.field.Cap;
import seedu.address.model.item.field.Description;
import seedu.address.model.item.field.DisplayPicture;
import seedu.address.model.item.field.Email;
import seedu.address.model.item.field.Github;
import seedu.address.model.item.field.Major;
import seedu.address.model.item.field.Name;
import seedu.address.model.item.field.Phone;
import seedu.address.model.item.field.Time;
import seedu.address.model.item.field.University;

/**
 * Edits user profile information.
 */
public class EditUserCommand extends Command {

    public static final String COMMAND_WORD = "me";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited User Profile!";

    public static final String MESSAGE_NO_ARGS = "You did not specify any arguments!";

    public static final String MESSAGE_FROM_TO_MISORDER = "\'from\' cannot be later than \'to\' field. ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the user profile in the ResuMe application. "
            + "Format: " + COMMAND_WORD + " "
            + PREFIX_DP + " DISPLAY_PICTURE "
            + PREFIX_NAME + " NAME "
            + PREFIX_DESCRIPTION + " DESCRIPTION "
            + PREFIX_PHONE + " PHONE "
            + PREFIX_EMAIL + " EMAIL "
            + PREFIX_GITHUB + " GITHUB "
            + PREFIX_UNIVERSITY + " UNIVERSITY "
            + PREFIX_MAJOR + " MAJOR "
            + PREFIX_FROM + " FROM "
            + PREFIX_TO + " TO "
            + PREFIX_CAP + " CURRENT_CAP MAX_CAP\n"
            + "Example: \n" + COMMAND_WORD + " "
            + PREFIX_DP + " /Users/nhamquochung/Desktop/test.png "
            + PREFIX_NAME + " HUNG "
            + PREFIX_DESCRIPTION + " Technology enthusiast. "
            + PREFIX_PHONE + " 91648888 "
            + PREFIX_EMAIL + " nhamhung.gttn@gmail.com "
            + PREFIX_GITHUB + " nhamhung "
            + PREFIX_UNIVERSITY + " National University of Singapore "
            + PREFIX_MAJOR + " Computer Science "
            + PREFIX_FROM + " 08-2018 "
            + PREFIX_TO + " 05-2022 "
            + PREFIX_CAP + " 4.0 5.0";

    public static final String USER_NOT_EDITED = "You are not making any changes to your personal details.";

    private EditUserDescriptor editUserDescriptor;

    /**
     * @param editUserDescriptor details to edit the person with.
     */
    public EditUserCommand(EditUserDescriptor editUserDescriptor) {
        this.editUserDescriptor = editUserDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person userToEdit = model.getUser();

        Person editedUser = createEditedUser(userToEdit, editUserDescriptor);

        if (editedUser.getFrom().compareTo(editedUser.getTo()) > 0) {
            throw new CommandException(MESSAGE_FROM_TO_MISORDER);
        }

        if (editedUser.equals(userToEdit)) {
            throw new CommandException(USER_NOT_EDITED);
        }

        model.setUser(editedUser);

        model.commitResumeBook();

        return new EditUserCommandResult(editedUser.toString(),
                MESSAGE_EDIT_PERSON_SUCCESS, model.getDisplayType());
    }

    /**
     * Creates the user after edited.
     * @param toEdit
     * @param editUserDescriptor
     * @return
     */
    private static Person createEditedUser(Person toEdit, EditUserDescriptor editUserDescriptor) {
        DisplayPicture displayPicture = editUserDescriptor.getDisplayPicture().orElse(toEdit.getDisplayPicture());
        Name name = editUserDescriptor.getName().orElse(toEdit.getName());
        Description description = editUserDescriptor.getDescription().orElse(toEdit.getDescription());
        Phone phone = editUserDescriptor.getPhone().orElse(toEdit.getPhone());
        Email email = editUserDescriptor.getEmail().orElse(toEdit.getEmail());
        Github github = editUserDescriptor.getGithub().orElse(toEdit.getGithub());
        University university = editUserDescriptor.getUniversity().orElse(toEdit.getUniversity());
        Major major = editUserDescriptor.getMajor().orElse(toEdit.getMajor());
        Time from = editUserDescriptor.getFrom().orElse(toEdit.getFrom());
        Time to = editUserDescriptor.getTo().orElse(toEdit.getTo());
        Cap cap = editUserDescriptor.getCap().orElse(toEdit.getCap());

        return new Person(displayPicture, name, description, phone, email, github, university, major, from, to, cap);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditUserCommand // instanceof handles nulls
                && editUserDescriptor.equals(((EditUserCommand) other).editUserDescriptor));
    }
}
