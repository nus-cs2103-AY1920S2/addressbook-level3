package seedu.address.logic.commands.sort;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.results.CommandResult;
import seedu.address.logic.commands.results.SortCommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.item.Project;
import seedu.address.model.util.ItemUtil;
import seedu.address.testutil.TypicalResumeBook;

public class SortProjectsCommandTest {
    private Model expectedModel;
    private Model model;

    @BeforeEach
    public void setUp() {
        expectedModel = new ModelManager(TypicalResumeBook.TYPICAL_WITHOUT_GOOGLE, new UserPrefs());
        model = new ModelManager(TypicalResumeBook.TYPICAL_WITHOUT_GOOGLE, new UserPrefs());
    }

    @Test
    public void execute_sortProjectsByNameReverseFalse_success() {
        expectedModel.sortProjects(Comparator.comparing(Project::getName));
        expectedModel.setProjectToDisplay();

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, Project.class.getSimpleName());
        CommandResult commandResult =
                new SortCommandResult(expectedMessage, ItemUtil.PROJECT_ALIAS);
        assertCommandSuccess(new SortProjectsCommand("name", false), model, commandResult, expectedModel);
    }

    @Test
    public void execute_sortProjectsByTimeReverseTrue_success() {
        expectedModel.sortProjects(Comparator.comparing(Project::getTime).reversed());
        expectedModel.setProjectToDisplay();

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, Project.class.getSimpleName());
        CommandResult commandResult =
                new SortCommandResult(expectedMessage, ItemUtil.PROJECT_ALIAS);
        assertCommandSuccess(
                new SortProjectsCommand("time", true), model, commandResult, expectedModel);
    }

}
