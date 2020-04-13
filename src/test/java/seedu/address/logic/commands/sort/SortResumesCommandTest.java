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
import seedu.address.model.item.Resume;
import seedu.address.model.util.ItemUtil;
import seedu.address.testutil.TypicalResumeBook;

public class SortResumesCommandTest {
    private Model expectedModel;
    private Model model;

    @BeforeEach
    public void setUp() {
        expectedModel = new ModelManager(TypicalResumeBook.TYPICAL_WITHOUT_GOOGLE, new UserPrefs());
        model = new ModelManager(TypicalResumeBook.TYPICAL_WITHOUT_GOOGLE, new UserPrefs());
    }

    @Test
    public void execute_sortResumesByNameReverseFalse_success() {
        expectedModel.sortResumes(Comparator.comparing(Resume::getName));
        expectedModel.setResumeToDisplay();

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, Resume.class.getSimpleName());
        CommandResult commandResult =
                new SortCommandResult(expectedMessage, ItemUtil.RESUME_ALIAS);
        assertCommandSuccess(new SortResumesCommand("name", false), model, commandResult, expectedModel);
    }

    @Test
    public void execute_sortResumesByNameReverseTrue_success() {
        expectedModel.sortResumes(Comparator.comparing(Resume::getName).reversed());
        expectedModel.setResumeToDisplay();

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, Resume.class.getSimpleName());
        CommandResult commandResult =
                new SortCommandResult(expectedMessage, ItemUtil.RESUME_ALIAS);
        assertCommandSuccess(
                new SortResumesCommand("name", true), model, commandResult, expectedModel);
    }

}
