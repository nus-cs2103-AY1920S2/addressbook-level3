package cookbuddy.model.recipe.attribute;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Recipe's favourite status in the recipe book.
 * Guarantees: mutable.
 */
public class Done {

    public static final String MESSAGE_CONSTRAINTS =
            "A recipe can only be done or not!";


    private boolean doneStatus;

    /**
     * Constructs a {@code Fav status}.
     *
     * @param status true or false.
     */
    public Done(boolean status) {
        requireNonNull(status);
        this.doneStatus = status;
    }

    public void attempt() {
        this.doneStatus = true;
    }

    public void unAttempt() {
        this.doneStatus = false;
    }

    public boolean getDoneStatus() {
        return doneStatus;
    }


    @Override
    public String toString() {
        if (doneStatus == true) {
            return "Yes";
        } else {
            return "No";
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Done // instanceof handles nulls
                    && doneStatus == (((Done) other).doneStatus)); // state check
    }

    @Override
    public int hashCode() {
        return String.valueOf(doneStatus).hashCode();
    }

}
