package tatracker.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class PrefixDictionary {
    private static final Map<Prefix, PrefixEntry> PREFIXES = Arrays
            .stream(PrefixEntry.values())
            .collect(Collectors.toUnmodifiableMap(PrefixEntry::getPrefix, entry -> entry));

    private final Map<String, PrefixEntry> dictionary;

    private final List<Prefix> parameters;
    private final List<Prefix> optionals;

    public PrefixDictionary(List<Prefix> parameters) {
        this(parameters, List.of());
    }

    public PrefixDictionary(List<Prefix> parameters, List<Prefix> optionals) {
        requireNonNull(parameters);
        requireNonNull(optionals);

        this.parameters = List.copyOf(parameters);
        this.optionals = List.copyOf(optionals);
        this.dictionary = createPrefixDictionary(this.parameters, this.optionals);
    }

    /**
     * Creates a new Prefix dictionary for syntax matching. If there are duplicate prefixes,
     * the first inserted prefix is kept, and the rest are skipped.
     */
    public static Map<String, PrefixEntry> createPrefixDictionary(List<Prefix> parameters, List<Prefix> optionals) {
        List<Prefix> prefixes = new ArrayList<>();
        prefixes.addAll(parameters);
        prefixes.addAll(optionals);

        return prefixes.stream()
                .collect(Collectors.toUnmodifiableMap(Prefix::getPrefix, PREFIXES::get, (first, second) -> first));
    }

    public List<Prefix> getParameters() {
        return parameters;
    }

    public List<Prefix> getOptionals() {
        return optionals;
    }

    /**
     * Returns the matching CommandEntry.
     */
    public PrefixEntry getPrefixEntry(String prefix) {
        requireNonNull(prefix);
        return dictionary.get(prefix);
    }

    // public static PrefixEntry getPrefixEntry(Prefix prefix) {
    //     requireNonNull(prefix);
    //     return PREFIXES.get(prefix);
    // }

    public String getPrefixesWithInfo() {
        return getPrefixesWithInfo(parameters, optionals);
    }

    public static String getPrefixesWithInfo(List<Prefix> parameters) {
        return getPrefixesWithInfo(parameters, List.of());
    }

    public static String getPrefixesWithInfo(List<Prefix> parameters, List<Prefix> optionals) {
        String params = formatPrefixes(parameters, p -> PREFIXES.get(p).getPrefixWithInfo());
        String opts = formatPrefixes(optionals, p -> PREFIXES.get(p).getPrefixWithOptionalInfo());

        if (params.isEmpty()) {
            return opts;
        } else {
            return params + " " + opts;
        }
    }

    public String getPrefixesWithExamples() {
        String params = formatPrefixes(parameters, p -> PREFIXES.get(p).getPrefixWithExamples());
        String opts = formatPrefixes(optionals, p -> PREFIXES.get(p).getPrefixWithExamples());

        if (params.isEmpty()) {
            return opts;
        } else {
            return params + " " + opts;
        }
    }

    public static String getPrefixesWithExamples(Prefix ... prefixes) {
        return formatPrefixes(Arrays.asList(prefixes), p -> PREFIXES.get(p).getPrefixWithExamples());
    }

    private static String formatPrefixes(List<Prefix> prefixes, Function<Prefix, String> mapper) {
        return prefixes.stream()
                .map(mapper)
                .collect(Collectors.joining(" "));
    }
}
