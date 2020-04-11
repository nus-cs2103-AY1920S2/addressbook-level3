package seedu.address.logic.commands.modulecommand;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalNusModules.getTypicalModuleBook;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ModuleBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.calender.Task;
import seedu.address.model.nusmodule.Grade;
import seedu.address.model.nusmodule.ModuleCode;
import seedu.address.testutil.TypicalNusModules;

class GradeCommandTest {
    private Model model = new ModelManager(new AddressBook(), new UserPrefs(),
            getTypicalModuleBook(), FXCollections.observableList(new ArrayList<Task>()));

    @Test
    public void execute_updateGradeForModuleWithGrade_successful() throws Exception {
        ModuleCode validModuleCode = TypicalNusModules.ST3131.getModuleCode();
        GradeCommand gradeCommand = new GradeCommand(validModuleCode, Grade.S);
        String expectedMessage = GradeCommand.MESSAGE_SUCCESS + " "
                + validModuleCode + " "
                + Grade.S.getText();

        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs(),
                new ModuleBook(model.getModuleBook()), FXCollections.observableList(new ArrayList<Task>()));
        expectedModel.gradeModule(validModuleCode, Grade.S);

        assertCommandSuccess(gradeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidModuleCode_throwsCommandException() {
        GradeCommand gradeCommand = new GradeCommand(new ModuleCode("CS2040"), Grade.A);

        assertThrows(CommandException.class, Messages.MESSAGE_NO_SUCH_MODULE, (
        ) -> gradeCommand.execute(model));
    }
}

