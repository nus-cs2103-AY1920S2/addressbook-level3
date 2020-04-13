package seedu.nova.logic.commands;

import static seedu.nova.logic.commands.CommandTestUtil.assertCommandSuccess;

import static seedu.nova.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.nova.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.nova.logic.commands.abcommands.AbRemarkCommand;
import seedu.nova.model.Model;
import seedu.nova.model.ModelManager;
import seedu.nova.model.Nova;
import seedu.nova.model.UserPrefs;
import seedu.nova.model.VersionedAddressBook;
import seedu.nova.model.person.Person;
import seedu.nova.model.person.Remark;
import seedu.nova.testutil.PersonBuilder;

class AbRemarkCommandTest {

    private static final String REMARK_STUB = "Some remark";

    private final Nova nova = new Nova();
    private final VersionedAddressBook ab = new VersionedAddressBook(getTypicalAddressBook());

    @Test
    void execute_addRemarkUnfilteredList_success() {
        nova.setAddressBookNova(ab);
        Model model = new ModelManager(nova, new UserPrefs());

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withRemark(REMARK_STUB).build();

        AbRemarkCommand abRemarkCommand = new AbRemarkCommand(INDEX_FIRST_PERSON,
                new Remark(editedPerson.getRemark().value));

        String expectedMessage = String.format(AbRemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);
        expectedMessage = expectedMessage + "\nRemark: " + REMARK_STUB + "\n\n"
                + "Note: Please only use remark command after using list, list c\\classmate, list c\\teammate or "
                + "find command. You may wish to undo if you accidentally used remark command on the wrong person.";

        Model expectedModel = new ModelManager(model.getNova(), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(abRemarkCommand, model, expectedMessage, expectedModel);
    }

}
