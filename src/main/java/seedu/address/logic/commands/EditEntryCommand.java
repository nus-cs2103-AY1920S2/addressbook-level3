package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entry.Entry;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing entry in the log book.
 */
public class EditEntryCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the entry identified "
            + "by the index number used in the displayed entry list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_ENTRYNAME + "NAME] "
            + "[" + PREFIX_ENTRYTIME + "Time] "
            + "[" + PREFIX_ENTRYLOCATION + "LOCATION] "
            + "[" + PREFIX_ENTRYCALORIE + "CALORIE]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ENTRYNAME + "swimming "
            + PREFIX_ENTRYTIME + "Wednesday 6 pm";

    public static final String MESSAGE_EDIT_ENTRY_SUCCESS = "Edited Entry: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ENTRY = "This entry already exists in the log book.";

    private final Index index;
    private final EditEntryDescriptor editEntryDescriptor;

    /**
     * @param index of the entry in the filtered entry list to edit
     * @param editEntryDescriptor details to edit the entry with
     */
    public EditEntryCommand(Index index, EditEntryDescriptor editEntryDescriptor) {
        requireNonNull(index);
        requireNonNull(editEntryDescriptor);

        this.index = index;
        this.editEntryDescriptor = new EditEntryDescriptor(editEntryDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Entry> lastShownList = model.getFilteredEntryList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
        }

        Entry entryToEdit = lastShownList.get(index.getZeroBased());
        Entry editedEntry = createEditedEntry(entryToEdit, editEntryDescriptor);

        if (!entryToEdit.isSameEntry(editedEntry) && model.hasEntry(editedEntry)) {
            throw new CommandException(MESSAGE_DUPLICATE_ENTRY);
        }

        model.setEntry(entryToEdit, editedEntry);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_ENTRY_SUCCESS, editedEntry));
    }

    /**
     * Creates and returns a {@code Entry} with the details of {@code EntryToEdit}
     * edited with {@code editEntryDescriptor}.
     */
    private static Entry createEditedEntry(Entry entryToEdit, EditEntryDescriptor editEntryDescriptor) {
        assert entryToEdit != null;

        String updatedName = editEntryDescriptor.getName().orElse(entryToEdit.getName());
        String updatedTime = editEntryDescriptor.getTime().orElse(entryToEdit.getTime());
        String updatedLocation = editEntryDescriptor.getLocation().orElse(entryToEdit.getLocation());
        String updatedCalorie = editEntryDescriptor.getCalorie().orElse(entryToEdit.getCalorie());
        Remark updatedRemark = editEntryDescriptor.getRemark().orElse(entryToEdit.getRemark());

        return new Entry(updatedName, updatedTime, updatedLocation, updatedCalorie, updatedRemark);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditEntryCommand)) {
            return false;
        }

        // state check
        EditEntryCommand e = (EditEntryCommand) other;
        return index.equals(e.index)
                && editEntryDescriptor.equals(e.editEntryDescriptor);
    }

    /**
     * Stores the details to edit the entry with. Each non-empty field value will replace the
     * corresponding field value of the entry.
     */
    public static class EditEntryDescriptor {
        private String name;
        private String time;
        private String location;
        private String calorie;
        private Remark remark;

        public EditEntryDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditEntryDescriptor(EditEntryDescriptor toCopy) {
            setName(toCopy.name);
            setTime(toCopy.time);
            setLocation(toCopy.location);
            setCalorie(toCopy.calorie);
            setRemark(toCopy.remark);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, time, location, calorie);
        }

        public void setName(String name) {
            this.name = name;
        }

        public Optional<String> getName() {
            return Optional.ofNullable(name);
        }

        public void setTime(String time) {
            this.time = time;
        }

        public Optional<String> getTime() {
            return Optional.ofNullable(time);
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public Optional<String> getLocation() {
            return Optional.ofNullable(location);
        }

        public void setCalorie(String calorie) {
            this.calorie = calorie;
        }

        public Optional<String> getCalorie() {
            return Optional.ofNullable(calorie);
        }

        public void setRemark(Remark remark) {
            this.remark = remark;
        }

        public Optional<Remark> getRemark() {
            return Optional.ofNullable(remark);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditEntryDescriptor)) {
                return false;
            }

            // state check
            EditEntryDescriptor e = (EditEntryDescriptor) other;

            return getName().equals(e.getName())
                    && getTime().equals(e.getTime())
                    && getLocation().equals(e.getLocation())
                    && getCalorie().equals(e.getCalorie())
                    && getRemark().equals(e.getRemark());
        }
    }
}

