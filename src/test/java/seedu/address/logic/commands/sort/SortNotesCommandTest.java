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
import seedu.address.model.item.Note;
import seedu.address.testutil.TypicalResumeBook;

public class SortNotesCommandTest {
    private Model expectedModel;
    private Model model;

    @BeforeEach
    public void setUp() {
        expectedModel = new ModelManager(TypicalResumeBook.TYPICAL_WITHOUT_GOOGLE, new UserPrefs());
        model = new ModelManager(TypicalResumeBook.TYPICAL_WITHOUT_GOOGLE, new UserPrefs());
    }

    @Test
    public void execute_sortNotesByNameReverseTrue_success() {
        expectedModel.sortNotes(Comparator.comparing(Note::getName).reversed());

        // Do note (pun not intended) that we do not check display type here since Note is not a list box class

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, Note.class.getSimpleName());
        CommandResult commandResult =
                new SortCommandResult(expectedMessage, "");
        assertCommandSuccess(new SortNotesCommand("name", true), model, commandResult, expectedModel);
    }

    @Test
    public void execute_sortNotesByTimeReverseFalse_success() {
        expectedModel.sortNotes(Comparator.comparing(Note::getTime));

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, Note.class.getSimpleName());
        CommandResult commandResult =
                new SortCommandResult(expectedMessage, "");
        assertCommandSuccess(
                new SortNotesCommand("time", false), model, commandResult, expectedModel);
    }
}
