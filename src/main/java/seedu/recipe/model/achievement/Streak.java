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
     * Updates Streak every time App is opened
     */
    public void updateStreak() {
        if (records.size() == 0) { //make sure both gets updated during redo as well
            currStreak = 0;
            highStreak = 0;
        } else {
            int currStreakTemp = 0; //temp value to cal currSteak
            int currHighScore = 0; //current high score of each iteration
            int finalHigh = 0; //logs max score so far
            boolean stillStreak = true;
            int index = records.size() - 1;
            Date currDate = this.date; //currStreak
            while (index >= 0) {
                if (stillStreak) {
                    long days = date.noOfDaysBetween(records.get(index).getDate(), currDate);
                    if (days == 1 || currStreakTemp == 0) { // 1 day or not updated yet; increase streak
                        currStreakTemp++;
                    } else if (days > 1) { //end iteration of current streak
                        stillStreak = false;
                        currHighScore = 1; //start new streak
                    } //if day <0 continue streak without streak increase
                    finalHigh = currStreakTemp;
                    currDate = records.get(index).getDate();
                    index--;
                } else { // cal for high streak
                    long days = date.noOfDaysBetween(records.get(index).getDate(), currDate);
                    if (days <= 1) { //curr streak continues
                        if (days == 1) {
                            currHighScore++;
                        } else {
                            if (currHighScore == 0) { //score not updated for the day yet
                                currHighScore++;
                            }
                        }
                    } else { //days > 1
                        currHighScore = 0; //reset currentHighScore
                    }
                    if (currHighScore > finalHigh) { //replace high score
                        finalHigh = currHighScore;
                    }
                    currDate = records.get(index).getDate();
                    index--;
                }
            }
            currStreak = currStreakTemp;
            highStreak = finalHigh;
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
