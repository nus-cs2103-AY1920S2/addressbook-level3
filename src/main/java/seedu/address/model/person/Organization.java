package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's Organization in the Address Book.
 * Guarantees: immutable, is always valid.
 */
public class Organization {

    // Instance variables
    public final String organization;

    /**
     * Constructs a {@code Organization}.
     *
     * @param organization A valid organization name (Non-null).
     */
    public Organization(String organization) {
        requireNonNull(organization);
        this.organization = organization;
    }

    @Override
    public String toString() {
        if (this.organization.isEmpty()) {
            return "";
        }

        return this.organization;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Organization // instanceof handles nulls
                && organization.equals(((Organization) other).organization)); // state check
    }

    @Override
    public int hashCode() {
        return organization.hashCode();
    }
}
