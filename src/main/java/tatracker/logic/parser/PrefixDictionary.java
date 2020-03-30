package tatracker.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

        List<Prefix> prefixes = new ArrayList<>();
        prefixes.addAll(parameters);
        prefixes.addAll(optionals);

        this.dictionary = createPrefixDictionary(prefixes, List.of());
    }

    public List<Prefix> getParameters() {
        return parameters;
    }

    public List<Prefix> getOptionals() {
        return optionals;
    }

    /**
     * Creates a new Prefix dictionary for syntax matching.
     */
    public static Map<String, PrefixEntry> createPrefixDictionary(List<Prefix> parameters, List<Prefix> optionals) {
        if (!arePrefixesUnique(parameters) && !arePrefixesUnique(optionals)) {
            throw new IllegalArgumentException("Some prefix values are the same. This would result in duplicate keys");
        }

        List<Prefix> prefixes = new ArrayList<>();
        prefixes.addAll(parameters);
        prefixes.addAll(optionals);

        return prefixes.stream()
                .collect(Collectors.toUnmodifiableMap(Prefix::getPrefix, PrefixDictionary::getPrefixEntry));
    }

    /**
     * Returns true if the list of prefixes have unique values.
     * For example, "n/" could mean Student name or Module description, so it is not unique.
     */
    private static boolean arePrefixesUnique(List<Prefix> prefixes) {
        Set<String> uniquePrefixes = new HashSet<>();
        for (Prefix prefix : prefixes) {
            String value = prefix.toString();
            if (uniquePrefixes.contains(value)) {
                return false;
            }
            uniquePrefixes.add(value);
        }
        return true;
    }

    public PrefixEntry getPrefix(String value) {
        requireNonNull(value);
        return dictionary.get(value);
    }

    /**
     * Returns the matching CommandEntry.
     */
    public static PrefixEntry getPrefixEntry(Prefix prefix) {
        requireNonNull(prefix);
        return PREFIXES.get(prefix);
    }

    // public String getPrefixesWithInfo() {
    //     String params = formatPrefixes(parameters, p -> getPrefixEntry(p).getPrefixWithInfo());
    //     String opts = formatPrefixes(optionals, p -> getPrefixEntry(p).getPrefixWithOptionalInfo());
    //
    //     if (params.isEmpty()) {
    //         return opts;
    //     } else {
    //         return params + " " + opts;
    //     }
    // }
    //
    // public String getPrefixesWithExamples() {
    //     String params = formatPrefixes(parameters, p -> getPrefixEntry(p).getPrefixWithExamples());
    //     String opts = formatPrefixes(optionals, p -> getPrefixEntry(p).getPrefixWithExamples());
    //
    //     if (params.isEmpty()) {
    //         return opts;
    //     } else {
    //         return params + " " + opts;
    //     }
    // }

    public static String getPrefixesWithInfo(List<Prefix> parameters) {
        return getPrefixesWithInfo(parameters, List.of());
    }

    public static String getPrefixesWithInfo(List<Prefix> parameters, List<Prefix> optionals) {
        String params = formatPrefixes(parameters, p -> getPrefixEntry(p).getPrefixWithInfo());
        String opts = formatPrefixes(optionals, p -> getPrefixEntry(p).getPrefixWithOptionalInfo());

        if (params.isEmpty()) {
            return opts;
        } else {
            return params + " " + opts;
        }
    }

    public static String getPrefixesWithExamples(Prefix ... prefixes) {
        return formatPrefixes(Arrays.asList(prefixes), p -> getPrefixEntry(p).getPrefixWithExamples());
    }

    private static String formatPrefixes(List<Prefix> prefixes, Function<Prefix, String> mapper) {
        return prefixes.stream()
                .map(mapper)
                .collect(Collectors.joining(" "));
    }
}
