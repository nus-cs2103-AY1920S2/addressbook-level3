package seedu.address.testutil;

import seedu.address.model.activity.Activity;

/**
 * Contains examples of activities use for testing.
 */
public class TypicalActivities {

    public static final Activity DEADLINE = new ActivityBuilder().withName("deadline")
            .withDate("2020-03-19")
            .withNote("Do homework").build();

    private TypicalActivities() {} // prevents instantiation
}
