package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_GITHUB_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.edit.EditUserCommand.MESSAGE_EDIT_PERSON_SUCCESS;
import static seedu.address.logic.commands.edit.EditUserCommand.MESSAGE_FROM_TO_MISORDER;
import static seedu.address.logic.commands.edit.EditUserCommand.USER_NOT_EDITED;
import static seedu.address.testutil.TypicalPerson.AMY;
import static seedu.address.testutil.TypicalPerson.BOB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.edit.EditUserCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.results.EditUserCommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.EditUserDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.ResumeBookBuilder;
import seedu.address.testutil.TypicalResumeBook;

public class EditUserCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(
                new ResumeBookBuilder(TypicalResumeBook.TYPICAL_WITHOUT_GOOGLE)
                        .withPerson(new PersonBuilder(AMY).build()).build(),
                new UserPrefs());
        expectedModel = new ModelManager(
                new ResumeBookBuilder(TypicalResumeBook.TYPICAL_WITHOUT_GOOGLE)
                        .withPerson(new PersonBuilder(AMY).build()).build(),
                new UserPrefs());
    }

    @Test
    public void execute_editMultipleFields_success() {
        expectedModel.setUser(new PersonBuilder(BOB).build());
        EditUserCommand editUserCommand = new EditUserCommand(new EditUserDescriptorBuilder(BOB).build());
        assertCommandSuccess(editUserCommand, model,
                new EditUserCommandResult(BOB.toString(), MESSAGE_EDIT_PERSON_SUCCESS, expectedModel.getDisplayType()),
                expectedModel);
    }

    @Test
    public void execute_editSingleFieldName_success() {
        expectedModel.setUser(new PersonBuilder(AMY).withName(VALID_NAME_BOB).build());
        EditUserCommand editUserCommand = new EditUserCommand(
                new EditUserDescriptorBuilder().withName(VALID_NAME_BOB).build());
        assertCommandSuccess(editUserCommand, model,
                new EditUserCommandResult(
                        new PersonBuilder(AMY).withName(VALID_NAME_BOB).build().toString(),
                        MESSAGE_EDIT_PERSON_SUCCESS,
                        expectedModel.getDisplayType()),
                expectedModel);
    }

    @Test
    public void execute_editSingleFieldGithub_success() {
        expectedModel.setUser(new PersonBuilder(AMY).withGithub(VALID_GITHUB_BOB).build());
        EditUserCommand editUserCommand = new EditUserCommand(
                new EditUserDescriptorBuilder().withGithub(VALID_GITHUB_BOB).build());
        assertCommandSuccess(editUserCommand, model,
                new EditUserCommandResult(
                        new PersonBuilder(AMY).withGithub(VALID_GITHUB_BOB).build().toString(),
                        MESSAGE_EDIT_PERSON_SUCCESS,
                        expectedModel.getDisplayType()),
                expectedModel);
    }

    @Test
    public void execute_editNoFields_failure() {
        EditUserCommand editUserCommand = new EditUserCommand(new EditUserDescriptorBuilder().build());
        assertCommandFailure(editUserCommand,
                model,
                new CommandException(USER_NOT_EDITED));
    }

    @Test
    public void execute_fromFieldLaterThanTo_failure() {
        EditUserCommand editUserCommand = new EditUserCommand(
                new EditUserDescriptorBuilder().withFrom("10-2030").build());
        assertCommandFailure(editUserCommand,
                model,
                new CommandException(MESSAGE_FROM_TO_MISORDER));
    }
}
