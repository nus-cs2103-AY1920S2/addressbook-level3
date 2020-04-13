package seedu.address.logic.commands.edit;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.item.field.Time;
import seedu.address.model.tag.Tag;

/**
 * Stores the details to edit the note with. Each non-empty field value will replace the
 * corresponding field value of the note.
 */
public class EditNoteDescriptor extends EditItemDescriptor {
    private Time time;
    private Set<Tag> tags;

    public EditNoteDescriptor() {}

    /**
     * Copy constructor.
     * A defensive copy of {@code tags} is used internally.
     */
    public EditNoteDescriptor(EditNoteDescriptor toCopy) {
        setName(toCopy.name);
        setTime(toCopy.time);
        setTags(toCopy.tags);
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Optional<Time> getTime() {
        return Optional.ofNullable(time);
    }

    /**
     * Sets {@code tags} to this object's {@code tags}.
     * A defensive copy of {@code tags} is used internally.
     */
    public void setTags(Set<Tag> tags) {
        this.tags = (tags != null) ? new HashSet<>(tags) : null;
    }

    /**
     * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code tags} is null.
     */
    public Optional<Set<Tag>> getTags() {
        return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
    }

    /**
     * Returns true if at least one field is edited.
     */
    @Override
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(name, time, tags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditNoteDescriptor)) {
            return false;
        }

        // state check
        EditNoteDescriptor e = (EditNoteDescriptor) other;

        return getName().equals(e.getName())
                && getTime().equals(e.getTime())
                && getTags().equals(e.getTags());
    }
}
