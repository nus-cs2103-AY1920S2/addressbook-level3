package nasa.model.activity;

/**
 * Represents schedule class in Nasa book.
 * Allows user to regenerate activity automatically.
 */
public class Schedule {

    private int type;
    private Date date;
    private Date defaultDate;

    /**
     * Construct a schedule from string.
     */
    public Schedule(String input) {
        String[] in = input.split(",");
        type = Integer.parseInt(in[0]);
        date = new Date(in[1]);
        defaultDate = new Date(in[2]);
    }

    /**
     * Initialise schedule with the default type of 0.
     * @param date
     */
    public Schedule(Date date) {
        this.date = date;
        this.defaultDate = date;
        type = 0;
    }

    /**
     * Initialise schedule with specific type.
     * @param date
     * @param type
     */
    public Schedule(Date date, int type) {
        this.date = date;
        this.defaultDate = date;
        this.type = type;
        init(type);
    }

    /**
     * Method to extend the date of a module activity.
     */
    public boolean update() {
        boolean hasUpdate = false;
        while (Date.now().isAfter(date) && type != 0) {
            init(type);
            hasUpdate = true;
        }
        return hasUpdate;
    }

    /**
     * Set scheduling.
     */
    public void setType(int type) {
        this.type = type;
        date = defaultDate;
        update();
    }

    /**
     * Stop scheduling.
     */
    public void cancel() {
        this.type = 0;
        date = defaultDate;
    }

    /**
     * Initialise schedules.
     */
    public void init(int type) {
        switch (type) {
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
     * Get next running date.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Set new date by refreshing it weekly.
     */
    private void runOnceAWeek() {
        date = date.addDaysToCurrDate(7);
    }

    /**
     * Set new date by refreshing it twice weekly.
     */
    private void runTwiceAWeek() {
        date = date.addDaysToCurrDate(14);
    }

    /**
     * Set new date by refreshing it monthly.
     */
    private void runMonthly() {
        date = date.addMonthsToCurrDate(1);
    }

    @Override
    public String toString() {
        return String.format("%d,%s,%s", type, date, defaultDate);
    }

}
