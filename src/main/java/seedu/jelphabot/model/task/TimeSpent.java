// @@author Clouddoggo

package seedu.jelphabot.model.task;

import static java.util.Objects.requireNonNull;

import java.time.Duration;

/**
 * Represents amount of time spent on a Task.
 */
public class TimeSpent {
    private Duration duration;

    // Empty constructor needed to pass test cases.
    public TimeSpent() {

    }

    public TimeSpent(Duration duration) {
        requireNonNull(duration);
        this.duration = duration;
    }

    public Duration getDuration() {
        return this.duration;
    }

    public void addTime(TimeSpent timeSpent) {
        this.duration = this.duration.plus(timeSpent.duration);
    }

    @Override
    public String toString() {
        return String.format("%d h %d m %d s", duration.toHoursPart(), duration.toMinutesPart(),
            duration.toSecondsPart());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                   || (other instanceof TimeSpent // instanceof handles nulls
                           && duration.equals(((TimeSpent) other).duration)); // state check
    }

    @Override
    public int hashCode() {
        return duration.hashCode();
    }
}
