package tatracker.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class PrefixDictionary {
    private static final Map<String, PrefixEntry> VALUE_TO_ENTRY = Arrays
            .stream(PrefixEntry.values())
            .collect(Collectors.toUnmodifiableMap(e -> e.getPrefix().toString(), prefix -> prefix));

    private static final Map<Prefix, PrefixEntry> PREFIX_TO_ENTRY = Arrays
            .stream(PrefixEntry.values())
            .collect(Collectors.toUnmodifiableMap(PrefixEntry::getPrefix, prefix -> prefix));

    /**
     * Returns the matching CommandEntry.
     */
    public static boolean hasPrefixEntry(String prefixValue) {
        requireNonNull(prefixValue);
        return VALUE_TO_ENTRY.containsKey(prefixValue);
    }

    /**
     * Returns the matching CommandEntry.
     */
    public static PrefixEntry getPrefixEntry(String prefixValue) {
        requireNonNull(prefixValue);
        return VALUE_TO_ENTRY.get(prefixValue);
    }

    /**
     * Returns the matching CommandEntry.
     */
    public static PrefixEntry getPrefixEntry(Prefix prefix) {
        requireNonNull(prefix);
        return PREFIX_TO_ENTRY.get(prefix);
    }

    public static String getExamplesWithPrefix(Prefix ... prefixes) {
        return formatPrefixes(Arrays.asList(prefixes), prefix -> getPrefixEntry(prefix).getExamplesWithPrefix());
    }

    public static String getInfoWithPrefix(List<Prefix> parameters) {
        return getInfoWithPrefix(parameters, List.of());
    }

    public static String getInfoWithPrefix(List<Prefix> parameters, List<Prefix> optionals) {
        String params = formatPrefixes(parameters, prefix -> getPrefixEntry(prefix).getInfoWithPrefix());
        String opts = formatPrefixes(optionals, prefix -> getPrefixEntry(prefix).getOptionalInfoWithPrefix());

        if (params.isEmpty()) {
            return opts;
        } else {
            return params + " " + opts;
        }
    }

    private static String formatPrefixes(List<Prefix> prefixes, Function<Prefix, String> mapper) {
        return prefixes.stream()
                .map(mapper)
                .collect(Collectors.joining(" "));
    }
}
