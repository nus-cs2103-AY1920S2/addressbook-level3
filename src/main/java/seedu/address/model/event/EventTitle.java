package seedu.address.model.event;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents an Event's title.
 * Guarantees: immutable; is valid as declared in {@Link #isValidTitle(String)}
 */
public class EventTitle {
    public static final String MESSAGE_CONSTRAINTS = "Event title cannot be empty!";

    /**
     * The first character of the description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    // Instance fields
    public final String eventTitle;

    /**
     * Constructs a {@code EventTitle}
     * @param eventTitle Title of the event.
     */
    public EventTitle(String eventTitle) {
        requireAllNonNull(eventTitle);
        checkArgument(isValidEventTitle(eventTitle), MESSAGE_CONSTRAINTS);
        this.eventTitle = eventTitle;
    }

    public static boolean isValidEventTitle(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.event.EventTitle // instanceof handles nulls
                && eventTitle.equals(((seedu.address.model.event.EventTitle) other).eventTitle)); // state check
    }

    @Override
    public String toString() {
        return eventTitle;
    }

}
