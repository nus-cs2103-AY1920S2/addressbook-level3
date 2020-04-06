package seedu.address.testutil;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelProgress.Progress;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * A utility class to help with building Progress objects.
 */
public class ProgressBuilder {

    public static final String DEFAULT_AID = "80";
    public static final String DEFAULT_SID = "1";

    public static final String DEFAULT_ISDONE = "false";


    private CompositeID id;
    private Boolean isDone;

    public ProgressBuilder() throws CommandException {
        this.id = new CompositeID(new ID(DEFAULT_AID), new ID(DEFAULT_SID));
        this.isDone = Boolean.valueOf(DEFAULT_ISDONE);
    }

    /**
     * Initializes the ProgressBuilder with the data of {@code ProgressToCopy}.
     */
    public ProgressBuilder(Progress ProgressToCopy) {
        id = ProgressToCopy.getId();
        isDone = ProgressToCopy.getIsDone();
    }

    /**
     * Sets the {@code Name} of the {@code Assignment} that we are building.
     */
    public ProgressBuilder withCompositeID(CompositeID compID) {
        this.id = compID;
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Assignment} that we are building.
     */
    public ProgressBuilder withDone(Boolean isDone) {
        this.isDone = isDone;
        return this;
    }

    public Progress build() {
        return new Progress(id, isDone);
    }

}
