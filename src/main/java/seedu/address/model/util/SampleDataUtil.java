package seedu.address.model.util;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.course.Course;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Profile[] getSamplePersons() {
        return new Profile[] {
            new Profile(new Name("Alex Yeoh"), new Course("Computer Science"), "1",
                    null),
            new Profile(new Name("Bernice Yu"), new Course("Business Analytics"), "1",
                    null),
            new Profile(new Name("Charlotte Oliveiro"), new Course("Information Security"), "1",
                    null),
            new Profile(new Name("David Li"), new Course("Information Systems"), "1",
                    null),
            new Profile(new Name("Irfan Ibrahim"), new Course("Computer Science"), "1",
                    null),
            new Profile(new Name("Roy Balakrishnan"), new Course("Business Analytics"), "1",
                    null)
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Profile sampleProfile : getSamplePersons()) {
            sampleAb.addPerson(sampleProfile);
        }
        return sampleAb;
    }

}
