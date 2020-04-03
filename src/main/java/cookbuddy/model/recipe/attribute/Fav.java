package cookbuddy.model.recipe.attribute;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Recipe's favourite status in the recipe book.
 * Guarantees: mutable.
 */
public class Fav {

    public static final String MESSAGE_CONSTRAINTS =
            "Favourites can only be true or false!";


    private boolean favStatus;

    /**
     * Constructs a {@code Fav status}.
     *
     * @param status true or false.
     */
    public Fav(boolean status) {
        requireNonNull(status);
        this.favStatus = status;
    }

    public void fav() {
        this.favStatus = true;
    }

    public void unFav() {
        this.favStatus = false;
    }

    public boolean getfavStatus() {
        return favStatus;
    }


    @Override
    public String toString() {
        if (favStatus == true) {
            return "\u2665";
        } else {
            return "\u2661";
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Fav // instanceof handles nulls
                    && favStatus == (((Fav) other).favStatus)); // state check
    }

    @Override
    public int hashCode() {
        return String.valueOf(favStatus).hashCode();
    }

}
