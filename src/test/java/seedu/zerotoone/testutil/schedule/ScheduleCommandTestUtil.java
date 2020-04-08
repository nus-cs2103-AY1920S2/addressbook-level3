package seedu.zerotoone.testutil.schedule;

import static seedu.zerotoone.logic.parser.CliSyntax.PREFIX_DATETIME;

import seedu.zerotoone.testutil.CommandTestUtil;

/**
 * Contains helper methods for testing commands.
 */
public class ScheduleCommandTestUtil extends CommandTestUtil {

    public static final String VALID_DATETIME = "2020-06-01 13:00";

    public static final String DATETIME_DESC = " " + PREFIX_DATETIME + VALID_DATETIME;

    public static final String INVALID_DATETIME_DESC = " " + PREFIX_DATETIME + "2020-06-01 1300";
}
