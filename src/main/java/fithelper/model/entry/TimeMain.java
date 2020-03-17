package fithelper.model.entry;

import fithelper.commons.exceptions.IllegalValueException;

/**
 * This main class is for testing and debugging only.
 */
public class TimeMain {

    /**
     * For debugging.
     * @param args user input.
     */
    public static void main(String[] args) {
        String timeStr = "2020-03-31 06:15";
        Time tryTime = new Time(timeStr);
        System.out.println(tryTime.time);
        System.out.println(tryTime.date);
        System.out.println(tryTime.value);
        System.out.println(tryTime.isValidTime(tryTime.value));
        System.out.println(new Time(tryTime.value));
    }
}
