package fithelper.logic.commands;

import static fithelper.testutil.AssertUtil.assertThrows;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import fithelper.logic.exceptions.CommandException;
import fithelper.model.FitHelper;
import fithelper.model.ModelStub;
import fithelper.model.ReadOnlyFitHelper;
import fithelper.model.entry.Entry;
import fithelper.testutil.EntryBuilder;

public class AddCommandTest {

    @Test
    public void constructornullPersonthrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void executeentryAcceptedByModeladdSuccessful() throws Exception {
        ModelStubAcceptingEntryAdded modelStub = new ModelStubAcceptingEntryAdded();
        Entry validEntry = new EntryBuilder().build();

        CommandResult commandResult = new AddCommand(validEntry).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validEntry), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validEntry), modelStub.entriesAdded);
    }

    @Test
    public void executeduplicateEntrythrowsCommandException() {
        Entry validEntry = new EntryBuilder().build();
        AddCommand addCommand = new AddCommand(validEntry);
        ModelStub modelStub = new ModelStubWithEntry(validEntry);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_ENTRY, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Entry food = new EntryBuilder().withName("Chicken Rice").build();
        Entry sports = new EntryBuilder().withName("Rock climbing").build();
        AddCommand addFoodCommand = new AddCommand(food);
        AddCommand addSportsCommand = new AddCommand(sports);

        // same object -> returns true
        assertTrue(addFoodCommand.equals(addFoodCommand));

        // same values -> returns true
        AddCommand addFoodCommandCopy = new AddCommand(food);
        assertTrue(addFoodCommand.equals(addFoodCommandCopy));

        // different types -> returns false
        assertFalse(addFoodCommand.equals(1));

        // different person -> returns false
        assertFalse(addFoodCommand.equals(addSportsCommand));
    }

    /**
     * A Model stub that contains a single entry.
     */
    private class ModelStubWithEntry extends ModelStub {
        private final Entry entry;

        ModelStubWithEntry(Entry entry) {
            requireNonNull(entry);
            this.entry = entry;
        }

        @Override
        public boolean hasEntry(Entry entry) {
            requireNonNull(entry);
            return this.entry.isSameEntry(entry);
        }
    }

    /**
     * A Model stub that always accept the entry being added.
     */
    private class ModelStubAcceptingEntryAdded extends ModelStub {
        private final ArrayList<Entry> entriesAdded = new ArrayList<Entry>();

        @Override
        public boolean hasEntry(Entry entry) {
            requireNonNull(entry);
            return entriesAdded.stream().anyMatch(entry::isSameEntry);
        }

        @Override
        public void addEntry(Entry entry) {
            requireNonNull(entry);
            entriesAdded.add(entry);
        }

        @Override
        public ReadOnlyFitHelper getFitHelper() {
            return new FitHelper();
        }

    }
}
