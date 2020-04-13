package modulo.logic.commands;

import static modulo.commons.core.Messages.MESSAGE_DEADLINE_ADDED;
import static modulo.logic.commands.CommandTestUtil.VALID_NAME_DEADLINE_LECTURE;
import static modulo.logic.commands.CommandTestUtil.VALID_NAME_DEADLINE_LECTURE_STANDARD;
import static modulo.logic.commands.CommandTestUtil.VALID_NAME_DEADLINE_TUTORIAL;
import static modulo.logic.commands.CommandTestUtil.assertCommandFailure;
import static modulo.logic.commands.CommandTestUtil.assertCommandSuccess;
import static modulo.testutil.event.TypicalEvents.TUTORIAL_1;
import static modulo.testutil.event.TypicalEvents.TUTORIAL_2;
import static modulo.testutil.event.TypicalEvents.TUTORIAL_3;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import modulo.commons.core.Messages;
import modulo.model.Model;
import modulo.model.ModelManager;
import modulo.model.Name;
import modulo.model.UserPrefs;
import modulo.testutil.module.TypicalModules;


/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class AddDeadlineCommandTest {

    private final Model model = new ModelManager(TypicalModules.getTypicalModulo(), new UserPrefs());

    @Test
    public void executeParentEventNull_focusedDisplayableModule_throwsCommandException() {
        AddDeadlineCommand addDeadlineCommand = new AddDeadlineCommand(new Name(VALID_NAME_DEADLINE_TUTORIAL),
                null, true);
        Model expectedModel = new ModelManager(model.getModulo(), model.getUserPrefs());
        expectedModel.setFocusedDisplayable(expectedModel.getFilteredModuleList().get(0));

        assertCommandFailure(addDeadlineCommand, expectedModel, Messages.MESSAGE_CANNOT_ADD_DEADLINE_TO_MODULE);

        addDeadlineCommand = new AddDeadlineCommand(new Name(VALID_NAME_DEADLINE_TUTORIAL), null, false);

        assertCommandFailure(addDeadlineCommand, expectedModel, Messages.MESSAGE_CANNOT_ADD_DEADLINE_TO_MODULE);
    }

    @Test
    public void executeParentEventNull_noFocusedDisplayable_throwsCommandException() {
        AddDeadlineCommand addDeadlineCommand = new AddDeadlineCommand(new Name(VALID_NAME_DEADLINE_TUTORIAL),
                null, true);
        Model expectedModel = new ModelManager(model.getModulo(), model.getUserPrefs());
        expectedModel.unsetFocusedDisplayable();

        assertCommandFailure(addDeadlineCommand, expectedModel, Messages.MESSAGE_EVENT_DOES_NOT_EXIST);

        addDeadlineCommand = new AddDeadlineCommand(new Name(VALID_NAME_DEADLINE_TUTORIAL), null, false);

        assertCommandFailure(addDeadlineCommand, expectedModel, Messages.MESSAGE_EVENT_DOES_NOT_EXIST);
    }

    @Test
    public void executeParentEventNull_isRepeatedTrue_success() {
        model.setFocusedDisplayable(model.getFilteredEventList().get(0));
        Model expectedModel = new ModelManager(model.getModulo(), model.getUserPrefs());
        expectedModel.setFocusedDisplayable(expectedModel.getFilteredEventList().get(0));

        AddDeadlineCommand addDeadlineCommand = new AddDeadlineCommand(new Name(VALID_NAME_DEADLINE_LECTURE),
                null, false);

        CommandResult expectedResult = new CommandResult(String.format(MESSAGE_DEADLINE_ADDED,
                VALID_NAME_DEADLINE_LECTURE), false, false,
                true, true, null, null);

        assertCommandSuccess(addDeadlineCommand, model, expectedResult, expectedModel);
    }


    @Test
    public void executeParentEventNotNull_modelContainsNoSuchEvent_throwsCommandException() {
        Model expectedModel = new ModelManager(model.getModulo(), model.getUserPrefs());

        AddDeadlineCommand addDeadlineCommand = new AddDeadlineCommand(new Name(VALID_NAME_DEADLINE_LECTURE_STANDARD),
                TUTORIAL_3, false);
        assertCommandFailure(addDeadlineCommand, expectedModel, Messages.MESSAGE_EVENT_DOES_NOT_EXIST);

        addDeadlineCommand = new AddDeadlineCommand(new Name(VALID_NAME_DEADLINE_LECTURE_STANDARD),
                TUTORIAL_3, true);
        assertCommandFailure(addDeadlineCommand, expectedModel, Messages.MESSAGE_EVENT_DOES_NOT_EXIST);
    }


    @Test
    public void executeParentEventNotNull_isRepeatedFalse_success() {
        Model expectedModel = new ModelManager(model.getModulo(), model.getUserPrefs());
        expectedModel.setFocusedDisplayable(expectedModel.getFilteredEventList().get(0));

        AddDeadlineCommand addDeadlineCommand = new AddDeadlineCommand(new Name(VALID_NAME_DEADLINE_LECTURE),
                TUTORIAL_1, false);

        CommandResult expectedResult = new CommandResult(String.format(MESSAGE_DEADLINE_ADDED,
                VALID_NAME_DEADLINE_LECTURE), false, false,
                true, true, null, null);
        assertCommandSuccess(addDeadlineCommand, model, expectedResult, expectedModel);
    }

    @Test
    public void executeAllFieldsValid_isRepeatedTrue_success() {
        Model expectedModel = new ModelManager(model.getModulo(), model.getUserPrefs());

        AddDeadlineCommand addDeadlineCommand = new AddDeadlineCommand(new Name(VALID_NAME_DEADLINE_LECTURE),
                TUTORIAL_1, true);

        CommandResult expectedResult = new CommandResult(String.format(MESSAGE_DEADLINE_ADDED,
                VALID_NAME_DEADLINE_LECTURE),
                false, false, true, true, null, null);
        assertCommandSuccess(addDeadlineCommand, model, expectedResult, expectedModel);
    }

    @Test
    public void equals() {

        AddDeadlineCommand addDeadlineCommandTutorialOne = new AddDeadlineCommand(
                new Name(VALID_NAME_DEADLINE_TUTORIAL), TUTORIAL_1, false);
        AddDeadlineCommand addDeadlineCommandTutorialTwo = new AddDeadlineCommand(
                new Name(VALID_NAME_DEADLINE_TUTORIAL), TUTORIAL_2, false);
        AddDeadlineCommand addDeadlineCommandTutorialThree = new AddDeadlineCommand(
                new Name(VALID_NAME_DEADLINE_LECTURE), TUTORIAL_2, false);

        // same object -> returns true
        assertEquals(addDeadlineCommandTutorialOne, addDeadlineCommandTutorialOne);

        // same values -> returns true
        AddDeadlineCommand addDeadlineCommandCopy = new AddDeadlineCommand(addDeadlineCommandTutorialOne.get_name(),
                addDeadlineCommandTutorialOne.get_parentEvent(), addDeadlineCommandTutorialOne.get_isRepeated());
        assertEquals(addDeadlineCommandCopy, addDeadlineCommandTutorialOne);

        // different types -> returns false
        assertNotEquals(1, addDeadlineCommandTutorialOne);

        // null -> returns false
        assertNotEquals(addDeadlineCommandTutorialOne, null);

        // same kind of deadline despite different parentEvents -> return true
        assertEquals(addDeadlineCommandTutorialOne, addDeadlineCommandTutorialTwo);

        // different kind of deadline -> return false
        assertNotEquals(addDeadlineCommandTutorialThree, addDeadlineCommandTutorialTwo);
    }
}
