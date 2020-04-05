package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAssessments.getTypicalAcademics;
import static seedu.address.testutil.TypicalDates.getTypicalAdmin;
import static seedu.address.testutil.TypicalNotes.getTypicalNotes;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;
import static seedu.address.testutil.event.TypicalEvents.getTypicalEventHistory;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalAcademics(), getTypicalAdmin(),
                getTypicalNotes(), new UserPrefs(), getTypicalEventHistory());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalAcademics(), getTypicalAdmin(),
                getTypicalNotes(), new UserPrefs(), getTypicalEventHistory());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
