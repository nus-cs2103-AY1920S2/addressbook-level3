package seedu.eylah.diettracker.model;

import seedu.eylah.diettracker.model.self.Height;
import seedu.eylah.diettracker.model.self.Self;
import seedu.eylah.diettracker.model.self.Weight;

/**
 * Unmodifiable view of a Diet Tracker user.
 */
public interface ReadOnlyMyself {

    /**
     * Returns an unmodifiable view of the Diet Tracker user.
     */
    Height getHeight();
    Weight getWeight();
    Mode getMode();
    Self getSelf();
}
