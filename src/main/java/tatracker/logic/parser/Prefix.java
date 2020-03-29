package tatracker.logic.parser;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A prefix that marks the beginning of an argument in an arguments string.
 * E.g. 't/' in 'add James t/ friend'.
 */
public class Prefix {
    public static final String DELIMITER = "/";

    private static final String OPTIONAL_FORMAT = "[%s]";
    private static final String MULTIPLE_FORMAT = "%s...";

    private final String prefix;

    private final String constraint;
    private final String usage;
    private final List<String> examples;

    public Prefix(String prefix) {
        this(prefix, "", "", "");
    }

    public Prefix(String prefix, String constraint, String usage, String ... examples) {
        this.prefix = prefix;
        this.constraint = constraint;
        this.usage = usage;
        this.examples = Arrays.asList(examples);
    }

    public String getPrefix() {
        return prefix;
    }

    public String getConstraint() {
        return constraint;
    }

    public String getExamples() {
        return examples.stream()
                .map(e -> prefix + e)
                .collect(Collectors.joining(" "));
    }

    public String getUsage() {
        return getMultipleUsage(prefix + usage);
    }

    public String getOptionalUsage() {
        return getMultipleUsage(String.format(OPTIONAL_FORMAT, prefix + usage));
    }

    private String getMultipleUsage(String usage) {
        if (examples.size() <= 1) {
            return usage;
        } else {
            return String.format(MULTIPLE_FORMAT, usage);
        }
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
        return otherPrefix.getPrefix().equals(getPrefix());
    }
}
