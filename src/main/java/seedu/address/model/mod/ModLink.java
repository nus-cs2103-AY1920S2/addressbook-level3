package seedu.address.model.mod;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Represents a Module's link to a web resource in TAble.
 * Guarantees: immutable; is valid as declared in {@link #isValidModLink(String)}
 */

public class ModLink {

    public static final String MESSAGE_CONSTRAINTS = "Module links should follow a URL link as specified in Java.";

    public final String modLink;

    /**
     * Constructs a {@code ModLink}.
     *
     * @param modLink A valid module code.
     */
    public ModLink(String modLink) {
        requireNonNull(modLink);
        checkArgument(isValidModLink(modLink), MESSAGE_CONSTRAINTS);
        this.modLink = modLink;
    }

    /**
     * Returns true if a given string is a valid tutorial name.
     */
    public static boolean isValidModLink(String test) {
        try {
            URL url = new URL(test);
        } catch (MalformedURLException e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return modLink;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.mod.ModLink // instanceof handles nulls
                && modLink.equals(((seedu.address.model.mod.ModLink) other).modLink)); // state check
    }

    @Override
    public int hashCode() {
        return modLink.hashCode();
    }
}
