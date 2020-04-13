//@@author potatocombat

package tatracker.logic.parser;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class PrefixDetails {
    private static final String OPTIONAL_FORMAT = "[%s]";
    private static final String MULTIPLE_FORMAT = "%s...";

    private final Prefix prefix;

    private final String constraint;
    private final Function<String, Boolean> validator;

    private final List<String> examples;

    PrefixDetails(Prefix prefix, String constraint, Function<String, Boolean> validator,
                  String ... examples) {
        this.prefix = prefix;
        this.constraint = constraint;
        this.validator = validator;
        this.examples = List.of(examples);
    }

    PrefixDetails(Prefix prefix, String ... examples) {
        this(prefix, "", value -> true, examples);
    }

    public Prefix getPrefix() {
        return prefix;
    }

    public String getInfo() {
        return prefix.getInfo();
    }

    public String getConstraint() {
        return constraint;
    }

    public Function<String, Boolean> getValidator() {
        return validator;
    }

    public List<String> getExamples() {
        return examples;
    }

    public boolean isValidValue(String test) {
        return validator.apply(test);
    }

    public String getPrefixWithInfo() {
        return addMultiplePrefixInfo(prefix + getInfo());
    }

    public String getPrefixWithOptionalInfo() {
        return addMultiplePrefixInfo(String.format(OPTIONAL_FORMAT, prefix + getInfo()));
    }

    public String getPrefixWithExamples() {
        return examples.stream()
                .map(e -> prefix + e)
                .collect(Collectors.joining(" "));
    }

    /**
     * Adds an ellipsis for all prefixes that can be repeated.
     */
    private String addMultiplePrefixInfo(String usage) {
        if (examples.size() <= 1) {
            return usage;
        } else {
            return String.format(MULTIPLE_FORMAT, usage);
        }
    }
}
