package fithelper.logic.commands;

import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_CALORIE;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_DURATION;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_INDEX;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_LOCATION;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_NAME;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_REMARK;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_STATUS;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_TIME;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_TYPE;
import static fithelper.model.Model.PREDICATE_SHOW_ALL_ENTRIES;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.commons.core.Messages;
import fithelper.commons.core.index.Index;
import fithelper.commons.util.CollectionUtil;
import fithelper.logic.exceptions.CommandException;
import fithelper.model.Model;
import fithelper.model.entry.Calorie;
import fithelper.model.entry.Duration;
import fithelper.model.entry.Entry;
import fithelper.model.entry.Location;
import fithelper.model.entry.Name;
import fithelper.model.entry.Remark;
import fithelper.model.entry.Status;
import fithelper.model.entry.Time;
import fithelper.model.entry.Type;

/**
 * Edits the details of an existing entry in the location book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the entry identified "
            + "by the index number used in the displayed entry list. \n"
            + "Parameters: "
            + PREFIX_TYPE + "TYPE "
            + PREFIX_INDEX + "INDEX (one-based) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_TIME + "TIME] "
            + "[" + PREFIX_LOCATION + "LOCATION] "
            + "[" + PREFIX_CALORIE + "CALORIE] "
            + "[" + PREFIX_STATUS + "STATUS] "
            + "[" + PREFIX_REMARK + "REMARK]"
            + "[" + PREFIX_DURATION + "DURATION]...\n";

    public static final String MESSAGE_EDIT_ENTRY_SUCCESS = "Edited Entry: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ENTRY = "This entry already exists in the location book.";
    public static final String MESSAGE_TIME_CLASHES = "Maximum one entry can have time clashes";

    private static final String MESSAGE_COMMIT = "Edit an entry";

    private static final Logger logger = LogsCenter.getLogger(EditCommand.class);

    private final Index index;
    private final EditEntryDescriptor editEntryDescriptor;

    /**
     * @param index of the entry in the filtered entry list to edit
     * @param editEntryDescriptor details to edit the entry with
     */
    public EditCommand(Index index, EditEntryDescriptor editEntryDescriptor) {
        requireNonNull(index);
        requireNonNull(editEntryDescriptor);

        this.index = index;
        this.editEntryDescriptor = new EditEntryDescriptor(editEntryDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Entry> lastShownList;
        if (editEntryDescriptor.getType().equals(new Type("food"))) {
            lastShownList = model.getFilteredFoodEntryList();
        } else {
            lastShownList = model.getFilteredSportsEntryList();
        }

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
        }

        Entry entryToEdit = lastShownList.get(index.getZeroBased());
        Entry editedEntry = createEditedEntry(entryToEdit, editEntryDescriptor);

        if (entryToEdit.isSameEntry(editedEntry) && model.hasEntry(editedEntry)) {
            throw new CommandException(MESSAGE_DUPLICATE_ENTRY);
        }

        if (model.hasTimeClashes(entryToEdit, editedEntry)) {
            throw new CommandException(MESSAGE_TIME_CLASHES);
        }

        model.setEntry(entryToEdit, editedEntry);
        model.setVevent(entryToEdit, editedEntry);
        model.updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRIES);
        //model.updateFil

        model.commit(MESSAGE_COMMIT);
        logger.info(String.format("Edited a new entry [%s]", editEntryDescriptor.toString()));

        return new CommandResult(String.format(MESSAGE_EDIT_ENTRY_SUCCESS, editedEntry));
    }

    /**
     * Creates and returns a {@code Entry} with the details of {@code entryToEdit}
     * edited with {@code editEntryDescriptor}.
     */
    private static Entry createEditedEntry(Entry entryToEdit, EditEntryDescriptor editEntryDescriptor) {
        assert entryToEdit != null;

        Type updatedType = editEntryDescriptor.getType();
        Name updatedName = editEntryDescriptor.getName().orElse(entryToEdit.getName());
        Time updatedTime = editEntryDescriptor.getTime().orElse(entryToEdit.getTime());
        Location updatedLocation = editEntryDescriptor.getLocation().orElse(entryToEdit.getLocation());
        Calorie updatedCalorie = editEntryDescriptor.getCalorie().orElse(entryToEdit.getCalorie());
        Status updatedStatus = editEntryDescriptor.getStatus().orElse(entryToEdit.getStatus());
        Remark updatedRemark = editEntryDescriptor.getRemark().orElse(entryToEdit.getRemark());
        Duration updatedDuration = editEntryDescriptor.getDuration().orElse(entryToEdit.getDuration());

        return new Entry(updatedType, updatedName, updatedTime, updatedLocation, updatedCalorie, updatedStatus,
                updatedRemark, updatedDuration);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editEntryDescriptor.equals(e.editEntryDescriptor);
    }

    /**
     * Stores the details to edit the entry with. Each non-empty field value will replace the
     * corresponding field value of the entry.
     */
    public static class EditEntryDescriptor {
        private Type type;
        private Name name;
        private Time time;
        private Location location;
        private Calorie calorie;
        private Status status;
        private Remark remark;
        private Duration duration;

        public EditEntryDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code status} is used internally.
         */
        public EditEntryDescriptor(EditEntryDescriptor toCopy) {
            setType(toCopy.type);
            setName(toCopy.name);
            setTime(toCopy.time);
            setLocation(toCopy.location);
            setCalorie(toCopy.calorie);
            setStatus(toCopy.status);
            setRemark(toCopy.remark);
            setDuration(toCopy.duration);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, time, location, calorie, status, remark, duration);
        }

        public void setType(Type type) {
            this.type = type;
        }

        public Type getType() {
            return this.type;
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setTime(Time time) {
            this.time = time;
        }

        public Optional<Time> getTime() {
            return Optional.ofNullable(time);
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Optional<Location> getLocation() {
            return Optional.ofNullable(location);
        }

        public void setCalorie(Calorie calorie) {
            this.calorie = calorie;
        }

        public Optional<Calorie> getCalorie() {
            return Optional.ofNullable(calorie);
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Optional<Status> getStatus() {
            return Optional.ofNullable(status);
        }

        public void setDuration(Duration duration) {
            this.duration = duration;
        }

        public Optional<Duration> getDuration() {
            return Optional.ofNullable(duration);
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
                    && getType().equals(e.getType())
                    && getTime().equals(e.getTime())
                    && getLocation().equals(e.getLocation())
                    && getStatus().equals(e.getStatus())
                    && getCalorie().equals(e.getCalorie())
                    && getRemark().equals(e.getRemark());
        }
    }
}
