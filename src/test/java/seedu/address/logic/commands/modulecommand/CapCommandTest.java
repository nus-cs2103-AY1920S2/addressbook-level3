package seedu.address.logic.commands.modulecommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalNusModules.getTypicalModuleBook;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.calender.Task;

class CapCommandTest {

    private Model model = new ModelManager(new AddressBook(), new UserPrefs(),
            getTypicalModuleBook(), FXCollections.observableList(new ArrayList<Task>()));

    @Test
    public void execute_noGradeStatedForSomeModule_successful() {
        CommandResult commandResult = new CapCommand().execute(model);

        assertEquals(CapCommand.MESSAGE_SUCCESS + "\nCurrent CAP: " + "0.0",
                commandResult.getFeedbackToUser());
    }

}
