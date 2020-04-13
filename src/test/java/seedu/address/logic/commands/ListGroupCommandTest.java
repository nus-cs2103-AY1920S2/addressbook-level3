package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showGroupAtIndex;
import static seedu.address.testutil.TypicalGroups.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ListGroupCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void initialSetup() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        CommandResult expectedResult = new CommandResult(ListGroupCommand.MESSAGE_SUCCESS, ViewType.GROUPS);
        assertCommandSuccess(new ListGroupCommand(), model, expectedResult, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showGroupAtIndex(model, Index.fromOneBased(1));
        CommandResult expectedResult = new CommandResult(ListGroupCommand.MESSAGE_SUCCESS, ViewType.GROUPS);
        assertCommandSuccess(new ListGroupCommand(), model, expectedResult, expectedModel);
    }
}
