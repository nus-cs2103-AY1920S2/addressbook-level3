package seedu.address.model.util;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.course.CourseName;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Profile[] getSamplePersons() throws ParseException {
        return new Profile[] {
            new Profile(new Name("Alex Yeoh"), new CourseName("Computer Science"), 1,
                    null),
            new Profile(new Name("Bernice Yu"), new CourseName("Business Analytics"), 1,
                    null),
            new Profile(new Name("Charlotte Oliveiro"), new CourseName("Information Security"), 1,
                    null),
            new Profile(new Name("David Li"), new CourseName("Information Systems"), 1,
                    null),
            new Profile(new Name("Irfan Ibrahim"), new CourseName("Computer Science"), 1,
                    null),
            new Profile(new Name("Roy Balakrishnan"), new CourseName("Business Analytics"), 1,
                    null)
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() throws ParseException {
        AddressBook sampleAb = new AddressBook();
        for (Profile sampleProfile : getSamplePersons()) {
            sampleAb.addPerson(sampleProfile);
        }
        return sampleAb;
    }

}
