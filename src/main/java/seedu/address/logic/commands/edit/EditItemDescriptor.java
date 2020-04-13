package seedu.address.logic.commands.edit;

import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.item.field.Name;

/**
 * Stores the details to edit the item with. Each non-empty field value will replace the
 * corresponding field value of the item.
 */
public class EditItemDescriptor {
    protected Name name;

    public EditItemDescriptor() {}

    /**
     * Copy constructor.
     */
    public EditItemDescriptor(EditItemDescriptor toCopy) {
        setName(toCopy.name);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(name);
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Optional<Name> getName() {
        return Optional.ofNullable(name);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditItemDescriptor)) {
            return false;
        }

        // state check
        EditItemDescriptor e = (EditItemDescriptor) other;

        return getName().equals(e.getName());
    }
}
