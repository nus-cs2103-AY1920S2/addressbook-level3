package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.testutil.GroupBuilder;
import seedu.address.testutil.ModelStub;

public class AddGroupCommandTest {

    @Test
    public void constructorNullGroupThrowsNullGroupPointer() {
        assertThrows(NullPointerException.class, () -> new AddGroupCommand(null));
    }

    @Test
    public void executeGroupAcceptedByModelAddSuccessful() throws Exception {
        ModelStubAcceptingGroupAdded modelStub = new ModelStubAcceptingGroupAdded();
        Group validGroup = new GroupBuilder().build();
        validGroup.setMemberIDs(new ArrayList<>(Arrays.asList(1, 2, 3)));
        CommandResult commandResult = new AddGroupCommand(validGroup).execute(modelStub);

        assertEquals(String.format(AddGroupCommand.MESSAGE_SUCCESS, validGroup), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validGroup), modelStub.groupsAdded);

    }

    @Test
    public void executeDuplicateGroupThrowsCommandException() throws Exception {
        Group validGroup = new GroupBuilder().build();
        validGroup.setMemberIDs(new ArrayList<>(Arrays.asList(1, 2, 3)));
        AddGroupCommand addGroupCommand = new AddGroupCommand(validGroup);
        ModelStub modelStub = new ModelStubWithGroup(validGroup);

        assertThrows(CommandException.class, AddGroupCommand.MESSAGE_DUPLICATE_GROUP, (

        ) -> addGroupCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Group soc = new GroupBuilder().build();
        Group rc = new GroupBuilder().withName("RC Friends").build();
        AddGroupCommand addSoCCommand = new AddGroupCommand(soc);
        AddGroupCommand addRcCommand = new AddGroupCommand(rc);

        //same object so should be true
        assertTrue(addSoCCommand.equals(addSoCCommand));

        //same values so should be true
        AddGroupCommand addSoCCopy = new AddGroupCommand(soc);
        assertTrue(addSoCCopy.equals(addSoCCommand));

        //differeny types so should false
        assertFalse(("sample text").equals(addSoCCommand));

        //equality with null should be false
        assertFalse(addRcCommand.equals(null));

        // different groups should be false
        assertFalse(addRcCommand.equals(addSoCCommand));
    }

    private class ModelStubWithGroup extends ModelStub {
        private final Group group;

        ModelStubWithGroup(Group group) {
            requireNonNull(group);
            this.group = group;
        }

        @Override
        public boolean hasGroup(Group group) {
            requireNonNull(group);
            return this.group.equals(group);
        }
    }


    private class ModelStubAcceptingGroupAdded extends ModelStub {
        private final ArrayList<Group> groupsAdded = new ArrayList<>();

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            // persons list with sample persons
            UniquePersonList uniquePersonList = new UniquePersonList();
            uniquePersonList.add(ALICE);
            uniquePersonList.add(BENSON);
            uniquePersonList.add(CARL);
            FilteredList<Person> sampleList = new FilteredList<>(uniquePersonList.asUnmodifiableObservableList());
            return sampleList;
        }

        @Override
        public boolean hasGroup(Group toCheck) {
            requireNonNull(toCheck);
            return groupsAdded.stream().anyMatch(toCheck::equals);
        }

        @Override
        public void addGroup(Group toAdd) {
            requireNonNull(toAdd);
            groupsAdded.add(toAdd);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
