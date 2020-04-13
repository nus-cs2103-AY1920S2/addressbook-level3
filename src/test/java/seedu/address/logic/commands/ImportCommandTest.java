package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ImportCommandTest {
    private static final String TEST_LIFE_CSV = Paths.get("src", "test",
            "data", "CsvFilesTest", "life.csv").toString();
    private static final String TEST_GROUP_CSV = Paths.get("src", "test",
            "data", "CsvFilesTest", "group.csv").toString();
    private static final String TEST_EVENT_CSV = Paths.get("src", "test",
            "data", "CsvFilesTest", "event.csv").toString();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        ImportCommand firstImportCommand = new ImportCommand("testing.csv", "testing2.csv", "testing2.csv");
        ImportCommand secondImportCommand = new ImportCommand("testing2.csv", "testing.csv", "testing2.csv");

        // same object -> returns true
        assertTrue(firstImportCommand.equals(firstImportCommand));

        // same values -> returns true
        ImportCommand firstImportCommandCopy = new ImportCommand("testing.csv", "testing2.csv", "testing2.csv");
        assertTrue(firstImportCommand.equals(firstImportCommandCopy));

        // different types -> returns false
        assertFalse(firstImportCommand.equals(1));

        // null -> returns false
        assertFalse(firstImportCommand.equals(null));

        // different file -> returns false
        assertFalse(firstImportCommand.equals(secondImportCommand));
    }

}
