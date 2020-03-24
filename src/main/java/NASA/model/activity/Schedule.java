package nasa.model.activity;

import nasa.model.module.Module;

/**
 * Represents schedule class in Nasa book.
 * Allows user to regenerate activity automatically.
 */
public class Schedule {

    private int type;
    private Module module;
    private Activity activity;
    private Date date;

    Schedule() {}

    Schedule(Module module, Activity activity, int type) {
        this.module = module;
        this.activity = activity;
        this.type = type;
    }

    /**
     * Update all activities if it expired.
     */
    public void run() {
        if (type == 1) {

        }
    }

    /**
     * Set new date by refreshing it weekly.
     * @param activity
     */
    private Date runWeekly(Activity activity) {
        Date startDate = activity.getDate();
        startDate.addDaysToCurrDate(7);
        return startDate;
    }

    /**
     * Set new date by refreshing it monthly.
     * @param activity
     */
    private Date runMonthly(Activity activity) {
        Date startDate = activity.getDate();
        int dayOfMonth = startDate.getDate().getDayOfMonth();
        int year = startDate.getDate().getYear();
        String startOfNextMonth = "01-" + dayOfMonth + "-" + year;
        Date newMonth = new Date(startOfNextMonth);
        return newMonth;
    }

}
