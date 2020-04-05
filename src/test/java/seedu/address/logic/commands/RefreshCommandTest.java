package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.testutil.TypicalAssessments.getTypicalAcademics;
import static seedu.address.testutil.TypicalDates.getTypicalAdmin;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalNotes.getTypicalNotes;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;
import static seedu.address.testutil.event.TypicalEvents.getTypicalEventHistory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RefreshCommand.
 */
public class RefreshCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {

        model = new ModelManager(getTypicalAddressBook(), getTypicalAcademics(), getTypicalAdmin(), getTypicalNotes(),
                new UserPrefs(), getTypicalEventHistory());
        expectedModel = new ModelManager(model.getAddressBook(), model.getAcademics(), model.getAdmin(),
                model.getNotesManager(), new UserPrefs(), getTypicalEventHistory());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new RefreshCommand(), model, RefreshCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);
        assertCommandSuccess(new RefreshCommand(), model, RefreshCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
