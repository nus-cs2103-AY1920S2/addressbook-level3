package seedu.address.logic.commands.modulecommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalNusModules.getTypicalModuleBook;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.calender.Task;
import seedu.address.model.nusmodule.Grade;
import seedu.address.model.nusmodule.ModuleCode;
import seedu.address.testutil.TypicalNusModules;

class GradeCommandTest {
    private Model model = new ModelManager(new AddressBook(), new UserPrefs(),
            getTypicalModuleBook(), FXCollections.observableList(new ArrayList<Task>()));

    @Test
    public void execute_updateGradeForModuleWithoutGrade_successful() throws Exception {
        ModuleCode validModuleCode = TypicalNusModules.CS2030.getModuleCode();
        GradeCommand gradeCommand = new GradeCommand(validModuleCode, Grade.A);
        CommandResult commandResult = gradeCommand.execute(model);
        assertEquals(GradeCommand.MESSAGE_SUCCESS + " "
                + validModuleCode + " "
                + Grade.A.getText(),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidModuleCode_throwsCommandException() {
        GradeCommand gradeCommand = new GradeCommand(new ModuleCode("CS2040"), Grade.A);

        assertThrows(CommandException.class, GradeCommand.MESSAGE_NO_SUCH_MODULE, (
        ) -> gradeCommand.execute(model));
    }
}

