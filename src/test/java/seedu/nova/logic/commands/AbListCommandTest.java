package seedu.nova.logic.commands;

import static seedu.nova.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.nova.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.nova.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.nova.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import seedu.nova.logic.commands.abcommands.AbListCommand;
import seedu.nova.model.Model;
import seedu.nova.model.ModelManager;
import seedu.nova.model.Nova;
import seedu.nova.model.UserPrefs;
import seedu.nova.model.VersionedAddressBook;


/**
 * Contains integration tests (interaction with the Model) and unit tests for AbListCommand.
 */
public class AbListCommandTest {

    private Model model;
    private Model expectedModel;
    private final Nova nova = new Nova();
    private final VersionedAddressBook ab = new VersionedAddressBook(getTypicalAddressBook());

    @BeforeEach
    public void setUp() {
        nova.setAddressBookNova(ab);
        model = new ModelManager(nova, new UserPrefs());
        expectedModel = new ModelManager(nova, new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        String expectedMessage = "Listed all contacts. Remarks will only appear with list c\\classmate "
                + "or list c\\teammate command.\n\n"
                + "1. Alice Pauline, Phone: 94351253, Email: alice@example.com, Category: classmate\n"
                + "2. Benson Meier, Phone: 98765432, Email: johnd@example.com, Category: classmate\n"
                + "3. Carl Kurz, Phone: 95352563, Email: heinz@example.com, Category: classmate\n"
                + "4. Daniel Meier, Phone: 87652533, Email: cornelia@example.com, Category: classmate\n"
                + "5. Elle Meyer, Phone: 9482224, Email: werner@example.com, Category: classmate\n"
                + "6. Fiona Kunz, Phone: 9482427, Email: lydia@example.com, Category: classmate\n"
                + "7. George Best, Phone: 9482442, Email: anna@example.com, Category: teammate\n";
        assertCommandSuccess(new AbListCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        String expectedMessage = "Listed all contacts. Remarks will only appear with list c\\classmate "
                + "or list c\\teammate command.\n\n"
                + "1. Alice Pauline, Phone: 94351253, Email: alice@example.com, Category: classmate\n"
                + "2. Benson Meier, Phone: 98765432, Email: johnd@example.com, Category: classmate\n"
                + "3. Carl Kurz, Phone: 95352563, Email: heinz@example.com, Category: classmate\n"
                + "4. Daniel Meier, Phone: 87652533, Email: cornelia@example.com, Category: classmate\n"
                + "5. Elle Meyer, Phone: 9482224, Email: werner@example.com, Category: classmate\n"
                + "6. Fiona Kunz, Phone: 9482427, Email: lydia@example.com, Category: classmate\n"
                + "7. George Best, Phone: 9482442, Email: anna@example.com, Category: teammate\n";
        assertCommandSuccess(new AbListCommand(), model, expectedMessage, expectedModel);
    }
}
