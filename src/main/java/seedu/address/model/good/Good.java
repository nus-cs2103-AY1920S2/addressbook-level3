package seedu.address.model.good;

import java.util.Objects;

public class Good {
    private final String name;

    public static final String MESSAGE_CONSTRAINTS =
            "Dummy constraint message.";


    public Good(String good) {
        this.name = good;
    }

    /**
     * Returns true if a given string is a valid good name.
     */
    public static boolean isValidGoodName(String test) {
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Good good1 = (Good) o;
        return name.equals(good1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
