package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.client.Address;
import seedu.address.model.client.Birthday;
import seedu.address.model.client.Client;
import seedu.address.model.client.CurrentWeight;
import seedu.address.model.client.Email;
import seedu.address.model.client.Gender;
import seedu.address.model.client.Height;
import seedu.address.model.client.Name;
import seedu.address.model.client.PersonalBest;
import seedu.address.model.client.Phone;
import seedu.address.model.client.Remark;
import seedu.address.model.client.Sport;
import seedu.address.model.client.TargetWeight;
import seedu.address.model.exercise.UniqueExerciseList;
import seedu.address.model.schedule.ScheduleList;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Client[] getSampleClients() {
        return new Client[] {
            new Client(new Name("Alex Yeoh"), new Gender("Male"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"), new Address("Blk 69 Geylang Street 29, #06-40"),
                    getTagSet("Paleo"), new Birthday("25-12-1997"), new CurrentWeight("70"),
                    new TargetWeight("75"), new Height("170"), new Remark("Diet may not be good"),
                    getSportSet("Sumo Wrestling"), new UniqueExerciseList(), new PersonalBest(), new ScheduleList()),
            new Client(new Name("Bernice Yu"), new Gender("Female"), new Phone("99272758"),
                    new Email("berniceyu@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("Normal"), new Birthday("29-02-2000"), new CurrentWeight("55.8"),
                    new TargetWeight("50"), new Height("160"), new Remark(""), getSportSet("Hockey"),
                    new UniqueExerciseList(), new PersonalBest(), new ScheduleList()),
            new Client(new Name("Irfan Ibrahim"), new Gender("Male"), new Phone("92492021"),
                    new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
                    getTagSet("Halal"), new Birthday("12-08-1975"), new CurrentWeight("70"),
                    new TargetWeight("75"), new Height("180"), new Remark("Requires protein"),
                    getSportSet("Soccer"), new UniqueExerciseList(), new PersonalBest(), new ScheduleList()),
            new Client(new Name("Roy Balakrishnan"), new Gender("Male"), new Phone("92624417"),
                    new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getTagSet("Vegan"), new Birthday("05-12-1990"), new CurrentWeight("67.4"),
                    new TargetWeight("75"), new Height("174"), new Remark("History of past injuries"),
                    getSportSet("Belly Dance"), new UniqueExerciseList(), new PersonalBest(), new ScheduleList()),
            new Client(new Name("Amy Ang"), new Gender("Female"), new Phone("97227128"),
                    new Email("amyyyy@example.com"), new Address("Blk 90 Sunset Way, #20-18"),
                    getTagSet("Normal"), new Birthday("12-05-1970"), new CurrentWeight("61.2"),
                    new TargetWeight("60"), new Height("165"), new Remark("History of past injuries"),
                    getSportSet("Fencing"), new UniqueExerciseList(), new PersonalBest(), new ScheduleList()) };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Client sampleClient : getSampleClients()) {
            sampleAb.addClient(sampleClient);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings).map(Tag::new).collect(Collectors.toSet());
    }

    /**
     * Returns a sport set containing the list of strings given.
     */
    public static Set<Sport> getSportSet(String... strings) {
        return Arrays.stream(strings)
                .map(Sport::new)
                .collect(Collectors.toSet());
    }

}
