package seedu.address.testutil;

import seedu.address.model.admin.Admin;
import seedu.address.model.admin.Date;

/**
 * A utility class to help with building Admin objects.
 * Example usage: <br>
 *     {@code Admin ad = new AdminBuilder().withDate("Jan 26", "Feb 26").build();}
 */
public class AdminBuilder {

    private Admin admin;

    public AdminBuilder() {
        admin = new Admin();
    }

    public AdminBuilder(Admin admin) {
        this.admin = admin;
    }

    /**
     * Adds a new {@code Date} to the {@code Admin} that we are building.
     */
    public AdminBuilder withDate(Date date) {
        admin.addDate(date);
        return this;
    }

    public Admin build() {
        return admin;
    }
}
