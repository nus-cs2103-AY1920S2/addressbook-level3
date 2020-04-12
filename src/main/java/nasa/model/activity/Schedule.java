package nasa.model.activity;

import static nasa.commons.util.AppUtil.checkArgument;

/**
 * Represents schedule class in Nasa book.
 * Allows user to regenerate activity automatically.
 */
public class Schedule {

    public static final String MESSAGE_CONSTRAINTS = "Schedule should be from 0 to 3 inclusive only.";

    /**
     * Valid integers that start from 0 to 3.
     */
    public static final String VALID_INTEGER_REGEX = "([0-3]\\d{0})";

    private int type;
    private Date dateToRepeat;
    private Date defaultDate;

    /**
     * Construct an empty Schedule
     */
    public Schedule() {
        type = 0;
        dateToRepeat = Date.now();
        defaultDate = Date.now();
    }

    /**
     * Construct a schedule from string.
     * @param input String
     */
    public Schedule(String input) {
        String[] in = input.split(",");
        type = Integer.parseInt(in[0]);
        dateToRepeat = new Date(in[1]);
        defaultDate = Date.acceptPastDate(in[2]);
    }

    /**
     * Initialise schedule with the default type of 0.
     * @param date Date
     */
    public Schedule(Date date) {
        this.dateToRepeat = date;
        this.defaultDate = date;
        type = 0;
    }

    /**
     * Initialise schedule with specific type.
     * @param date Date
     * @param type int
     */
    public Schedule(Date date, int type) {
        checkArgument(isValidSchedule(String.valueOf(type)), MESSAGE_CONSTRAINTS);
        this.dateToRepeat = date;
        this.defaultDate = date;
        this.type = type;
        init();
    }

    /**
     * Method to extend the date of a module activity.
     * @return boolean
     */
    public boolean update() {
        boolean hasUpdate = false;
        while (Date.now().isAfter(dateToRepeat) && type != 0) {
            init();
            hasUpdate = true;
        }
        return hasUpdate;
    }

    /**
     * Set scheduling.
     * @param type int
     */
    public void setType(int type, Date dueDate) {
        checkArgument(isValidSchedule(String.valueOf(type)), MESSAGE_CONSTRAINTS);
        this.type = type;
        dateToRepeat = dueDate;
        init();
    }

    /**
     * Stop scheduling.
     */
    public void cancel() {
        this.type = 0;
        dateToRepeat = defaultDate;
    }

    /**
     * Initialize schedules.
     */
    public void init() {
        switch (type) {
        case 0 :
            cancel();
            break;
        case 1 :
            runOnceAWeek();
            break;
        case 2 :
            runTwiceAWeek();
            break;
        case 3 :
            runMonthly();
            break;
        default:
            break;
        }
    }

    /**
     * Set default date
     */
    public void setDefaultDate(Date date) {
        this.defaultDate = date;
    }

    /**
     * Set date to repeat
     */
    public void setRepeatDate(Date date) {
        this.dateToRepeat = date;
    }

    /**
     * Get type of schedule.
     * @return int
     */
    public int getType() {
        return type;
    }

    /**
     * Get type
     */
    public String typeInString() {
        if (type == 0) {
            return "-";
        } else if (type == 1) {
            return "WEEKLY";
        } else if (type == 2) {
            return "TWICE WEEKLY";
        } else if (type == 3) {
            return "MONTHLY";
        } else {
            return "-";
        }
    }

    /**
     * Get next running date.
     * @return Date
     */
    public Date getRepeatDate() {
        return dateToRepeat;
    }

    /**
     * Set new date by refreshing it weekly.
     */
    private void runOnceAWeek() {
        dateToRepeat = dateToRepeat.addDaysToCurrDate(7);
    }

    /**
     * Set new date by refreshing it twice weekly.
     */
    private void runTwiceAWeek() {
        dateToRepeat = dateToRepeat.addDaysToCurrDate(14);
    }

    /**
     * Set new date by refreshing it monthly.
     */
    private void runMonthly() {
        dateToRepeat = dateToRepeat.addMonthsToCurrDate(1);
    }

    public Schedule getDeepCopy() {
        Schedule temp = new Schedule();
        temp.setType(type, dateToRepeat);
        temp.setDefaultDate(defaultDate);
        temp.setRepeatDate(dateToRepeat);
        return temp;
    }

    private static boolean isValidSchedule(String test) {
        return test.matches(VALID_INTEGER_REGEX);
    }

    @Override
    public String toString() {
        return String.format("%d,%s,%s", type, dateToRepeat, defaultDate);
    }
}
