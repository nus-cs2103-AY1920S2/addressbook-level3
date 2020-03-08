package fithelper.model.util;

import fithelper.model.FitHelper;
import fithelper.model.ReadOnlyFitHelper;
import fithelper.model.entry.Entry;


/**
 * Contains utility methods for populating {@code FitHelper} with sample data.
 */
public class SampleDataUtil {
    public static Entry[] getSampleEntries() {
        return new Entry[]{
                new Entry(new Name("Alex Yeoh"), new Email("alexyeoh@example.com"),
                        new Address("Blk 30 Geylang Street 29, #06-40"), new Remark("He is rich."),
                        getTagSet("friends")),
                new Entry(new Name("Bernice Yu"), new Email("berniceyu@example.com"),
                        new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Remark("He is rich."),
                        getTagSet("colleagues", "friends")),
                new Entry(new Name("Charlotte Oliveiro"), new Email("charlotte@example.com"),
                        new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Remark("A funny person."),
                        getTagSet("neighbours")),
                new Entry(new Name("David Li"), new Email("lidavid@example.com"),
                        new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Remark("He studies really hard."),
                        getTagSet("family")),
                new Entry(new Name("Irfan Ibrahim"), new Email("irfan@example.com"),
                        new Address("Blk 47 Tampines Street 20, #17-35"), new Remark("People person."),
                        getTagSet("classmates")),
                new Entry(new Name("Roy Balakrishnan"), new Email("royb@example.com"),
                        new Address("Blk 45 Aljunied Street 85, #11-31"), new Remark("Handsome boy."),
                        getTagSet("colleagues"))
        };
    }

    public static ReadOnlyFitHelper getSampleFitHelper() {
        FitHelper sampleAb = new FitHelper();
        for (Entry sampleEntry : getSampleEntries()) {
            sampleAb.addEntry(sampleEntry);
        }
        return sampleAb;
    }

}


