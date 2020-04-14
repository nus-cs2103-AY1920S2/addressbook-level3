package fithelper.testutil;

import fithelper.model.FitHelper;
import fithelper.model.entry.Entry;

/**
 * A utility class to help with building FitHelper objects.
 * Example usage: <br>
 *     {@code FitHelper fh = new FitHelperBuilder().withEntry("Food", "Sports").build();}
 */
public class FitHelperBuilder {

    private FitHelper fitHelper;

    public FitHelperBuilder() {
        fitHelper = new FitHelper();
    }

    public FitHelperBuilder(FitHelper fitHelper) {
        this.fitHelper = fitHelper;
    }

    /**
     * Adds a new {@code entry} to the {@code FitHelper} that we are building.
     */
    public FitHelperBuilder withEntry(Entry entry) {
        fitHelper.addEntry(entry);
        return this;
    }

    public FitHelper build() {
        return fitHelper;
    }
}
