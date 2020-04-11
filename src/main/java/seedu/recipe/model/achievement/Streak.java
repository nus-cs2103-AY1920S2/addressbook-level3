package seedu.recipe.model.achievement;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.recipe.model.Date;
import seedu.recipe.model.cooked.Record;

/**
 * Streak helps user track how often they're eating healthy
 */
public class Streak {

    private int currStreak;
    private int highStreak = 0;
    private Date date; // current date
    private ObservableList<Record> records;

    public Streak(ObservableList<Record> records, Date date) {
        requireNonNull(date);
        this.currStreak = 0;
        this.date = date; //most recent date update
        this.records = records;
    }

    /**
     * Updates Streak every time App is openend
     */
    public void updateStreak() {
        System.out.println(records.size());
        if (records.size() == 0) {
            currStreak = 0;
        } else {
            boolean stillStreak = true;
            int index = records.size() - 1;
            Date currDate = this.date;
            while (stillStreak && index >= 0) {
                long days = date.noOfDaysBetween(records.get(index).getDate(), currDate);
                if (days <= 1) {
                    currDate = records.get(index).getDate();
                    index--;
                    if (days == 1) {
                        currStreak++;
                    }
                } else {
                    stillStreak = false;
                }
            }
        }
    }

    @Override
    public String toString() {
        return Integer.toString(this.currStreak);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Streak // instanceof handles nulls
                && currStreak == (((Streak) other).currStreak)) // state check
                && highStreak == (((Streak) other).highStreak);
    }

    @Override
    public int hashCode() {
        return Integer.toString(currStreak).hashCode();
    }

    public int getCurrStreak() {
        return currStreak;
    }

    public int getHighStreak() {
        return highStreak;
    }
}
