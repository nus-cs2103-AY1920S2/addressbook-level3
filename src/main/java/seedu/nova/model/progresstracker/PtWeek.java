package seedu.nova.model.progresstracker;

/**
 * Represents a week
 */
public class PtWeek {
    public static final String MESSAGE_CONSTRAINTS = "Week must be a number and should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "\\d+";

    private int weekNum;
    private PtTaskList taskList;

    /**
     * Creates PtWeek object
     * @param num week number
     */
    public PtWeek(int num) {
        weekNum = num;
        taskList = new PtTaskList();
    }

    public int getWeekNum() {
        return weekNum;
    }

    public PtTaskList getTaskList() {
        return taskList;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidWeek(String test) {
        if (test.matches(VALIDATION_REGEX) && Integer.valueOf(test) > 0 && Integer.valueOf(test) <= 13) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return Integer.toString(weekNum);
    }
}
