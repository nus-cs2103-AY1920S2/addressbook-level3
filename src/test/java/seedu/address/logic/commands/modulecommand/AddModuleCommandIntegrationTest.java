package seedu.address.logic.commands.modulecommand;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalNusModules.getTypicalModuleBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.calender.Task;
import seedu.address.model.nusmodule.NusModule;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddModuleCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(),
                getTypicalModuleBook(), FXCollections.observableList(new ArrayList<Task>()));
    }


    @Test
    public void execute_duplicateModule_throwsCommandException() {
        NusModule moduleInList = model.getModuleBook().getModulesTakenList().get(0);
        assertCommandFailure(new AddModuleCommand(moduleInList), model, AddModuleCommand.MESSAGE_DUPLICATE_NUS_MODULE);
    }

}
