package cookbuddy.model.recipe.attribute;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Recipe's favourite status in the recipe book.
 * Guarantees: mutable.
 */
public class Fav {

    public static final String MESSAGE_CONSTRAINTS =
            "Favourites can only be true or false!";

    /*
     * The first character of the name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */

    public boolean isFav;

    /**
     * Constructs a {@code Fav status}.
     *
     * @param status true or false.
     */
    public Fav(boolean status) {
        requireNonNull(status);
        this.isFav = status;
    }

    public void fav() {
        this.isFav = true;
    }

   /* public void unFav() {
        this.isFav = false;
    }*/





    @Override
    public String toString() {
        if(isFav == true) {
            return "\u2665";
        } else {
            return "\u2661";
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Fav // instanceof handles nulls
                    && isFav == (((Fav) other).isFav)); // state check
    }

    @Override
    public int hashCode() {
        return String.valueOf(isFav).hashCode();
    }

}
