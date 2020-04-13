package seedu.address.model.facilitator;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.module.ModuleCode;

/**
 * Represents a Facilitator in Mod Manager.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Facilitator {

    // Identity fields
    private final Name name;

    // Data fields
    private final Phone phone;
    private final Email email;
    private final Office office;
    private final Set<ModuleCode> moduleCodes = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Facilitator(Name name, Phone phone, Email email, Office office, Set<ModuleCode> moduleCodes) {
        requireAllNonNull(name, phone, email, office, moduleCodes);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.office = office;
        this.moduleCodes.addAll(moduleCodes);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Office getOffice() {
        return office;
    }

    /**
     * Returns an immutable module code set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<ModuleCode> getModuleCodes() {
        return Collections.unmodifiableSet(moduleCodes);
    }

    /**
     * Returns true if both facilitators have the same name.
     * This defines a weaker notion of equality between two facilitators.
     */
    public boolean isSameFacilitator(Facilitator otherFacilitator) {
        if (otherFacilitator == this) {
            return true;
        }

        return otherFacilitator != null
                && otherFacilitator.getName().equals(getName());
    }

    /**
     * Returns true if both facilitators have the same identity and data fields.
     * This defines a stronger notion of equality between two facilitators.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Facilitator)) {
            return false;
        }

        Facilitator otherFacilitator = (Facilitator) other;
        return otherFacilitator.getName().equals(getName())
                && otherFacilitator.getPhone().equals(getPhone())
                && otherFacilitator.getEmail().equals(getEmail())
                && otherFacilitator.getOffice().equals(getOffice())
                && otherFacilitator.getModuleCodes().equals(getModuleCodes());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, office, moduleCodes);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName()).append("\n");
        if (getPhone().value != null) {
            builder.append("Phone: ").append(getPhone()).append("\n");
        }
        if (getEmail().value != null) {
            builder.append("Email: ").append(getEmail()).append("\n");
        }
        if (getOffice().value != null) {
            builder.append("Office: ").append(getOffice()).append("\n");
        }
        if (!getModuleCodes().isEmpty()) {
            builder.append("Module codes: ");
            getModuleCodes().forEach(moduleCode -> builder.append(moduleCode).append(" "));
        }
        return builder.toString();
    }

}
