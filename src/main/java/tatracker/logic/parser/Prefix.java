//@@author potatocombat

package tatracker.logic.parser;

/**
 * A prefix that marks the beginning of an argument in an arguments string.
 * E.g. 't/' in 'add James t/ friend'.
 */
public class Prefix {

    private static final String NO_INFO = "";

    private final String prefix;
    private final String info;

    public Prefix(String prefix) {
        this(prefix, NO_INFO);
    }

    public Prefix(String prefix, String info) {
        this.prefix = prefix;
        this.info = info;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getInfo() {
        return info;
    }

    public String toString() {
        return getPrefix();
    }

    @Override
    public int hashCode() {
        return prefix == null ? 0 : prefix.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Prefix)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Prefix otherPrefix = (Prefix) obj;
        return otherPrefix.getPrefix().equals(getPrefix())
                && otherPrefix.getInfo().equals(getInfo());
    }
}
