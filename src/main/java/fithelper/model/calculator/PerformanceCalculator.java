package fithelper.model.calculator;

import static fithelper.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;

import fithelper.model.entry.Entry;

/**
 * Represents a performance calculator.
 */
public class PerformanceCalculator {

    private List<Entry> entries;
    private int doneCounter;
    private int undoneCounter;
    private double performance;

    public PerformanceCalculator(List<Entry> entries) {
        requireAllNonNull(entries);
        this.entries = entries;
        this.doneCounter = countDoneEntries(entries);
        this.undoneCounter = countUndoneEntries(entries);
        if (doneCounter + undoneCounter == 0) {
            this.performance = 0.0;
        } else {
            this.performance = doneCounter / (doneCounter + undoneCounter);
        }
        assert doneCounter + undoneCounter == entries.size();
    }

    public void setEntries(List<Entry> entries) {
        requireAllNonNull(entries);
        this.entries = entries;
        this.doneCounter = countDoneEntries(entries);
        this.undoneCounter = countUndoneEntries(entries);
        this.performance = doneCounter / (doneCounter + undoneCounter);
        assert doneCounter + undoneCounter == entries.size();
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public int getDoneCounter() {
        return doneCounter;
    }

    public int getUndoneCounter() {
        return undoneCounter;
    }

    public double getPerformance() {
        return performance;
    }

    /**
     * Counter the done entries in the list.
     * @param entries a list of entries
     * @return the number of done entries
     */
    public int countDoneEntries(List<Entry> entries) {
        int temp = 0;
        for (Entry entry : entries) {
            if (entry.isDone()) {
                temp++;
            }
        }
        return temp;
    }

    /**
     * Counter the undone entries in the list.
     * @param entries a list of entries
     * @return the number of undone entries
     */
    public int countUndoneEntries(List<Entry> entries) {
        int temp = 0;
        for (Entry entry : entries) {
            if (!entry.isDone()) {
                temp++;
            }
        }
        return temp;
    }

    /**
     * Returns true if both calculators have the same entries.
     * This defines a stronger notion of equality between two calculators.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CalorieCalculator)) {
            return false;
        }

        PerformanceCalculator otherPerformanceCalculator = (PerformanceCalculator) other;
        return otherPerformanceCalculator.getEntries().equals(getEntries());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(entries);
    }

}
