package tatracker.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class PrefixDictionary {
    /** Collects all the prefix entries into a lookup table. */
    private static final Map<Prefix, PrefixEntry> PREFIXES = Arrays
            .stream(PrefixEntry.values())
            .collect(Collectors.toUnmodifiableMap(PrefixEntry::getPrefix, entry -> entry));

    private static final PrefixDictionary EMPTY_DICTIONARY = new PrefixDictionary();

    private static final String MESSAGE_UNKNOWN_ENTRY = "Dictionary does not contain the prefix: %s";

    private final Map<String, PrefixEntry> dictionary;

    private final List<Prefix> parameters;
    private final List<Prefix> optionals;

    public PrefixDictionary() {
        this(List.of(), List.of());
    }

    public PrefixDictionary(List<Prefix> parameters) {
        this(parameters, List.of());
    }

    public PrefixDictionary(List<Prefix> parameters, List<Prefix> optionals) {
        requireNonNull(parameters);
        requireNonNull(optionals);

        this.parameters = List.copyOf(parameters);
        this.optionals = List.copyOf(optionals);

        this.dictionary = createDictionary(this.parameters, this.optionals);
    }

    /**
     * Creates a new Prefix dictionary for syntax matching.
     *
     * If there are duplicate prefixes, the first inserted prefix is kept, and the rest are skipped.
     * Duplicates are skipped since repeated prefixes must mean the same thing, or else
     * it will be unclear what the prefix represents.
     *
     * Each prefix in {@code parameters} and {@code optionals} must have a matching {@code PrefixEntry}.
     */
    private static Map<String, PrefixEntry> createDictionary(List<Prefix> parameters, List<Prefix> optionals) {
        Map<String, PrefixEntry> dict = new HashMap<>();

        for (Prefix p : parameters) {
            assert PREFIXES.containsKey(p);

            String prefix = p.getPrefix();
            if (!dict.containsKey(prefix)) {
                dict.put(prefix, PREFIXES.get(p));
            }
        }

        for (Prefix p : optionals) {
            assert PREFIXES.containsKey(p);

            String prefix = p.getPrefix();
            if (!dict.containsKey(prefix)) {
                dict.put(prefix, PREFIXES.get(p));
            }
        }

        return Collections.unmodifiableMap(dict);
    }

    /**
     * Returns the same unmodifiable empty {@code PrefixDictionary}.
     */
    public static PrefixDictionary getEmptyDictionary() {
        return EMPTY_DICTIONARY;
    }

    public List<Prefix> getParameters() {
        return parameters;
    }

    public List<Prefix> getOptionals() {
        return optionals;
    }

    /**
     * Returns true if the dictionary requires a preamble.
     */
    public boolean hasPreamble() {
        return dictionary.containsValue(PrefixEntry.INDEX)
                || dictionary.containsValue(PrefixEntry.KEYWORD)
                || dictionary.containsValue(PrefixEntry.MORE_KEYWORDS)
                || dictionary.containsValue(PrefixEntry.TAB_NAME)
                || dictionary.containsValue(PrefixEntry.MODULE_ID);
    }

    /**
     * Returns true if the {@code prefix} is part of the dictionary.
     */
    public boolean hasPrefix(Prefix prefix) {
        return dictionary.containsKey(prefix.getPrefix());
    }

    /**
     * Returns true if the {@code prefix} is part of the dictionary.
     */
    public boolean hasPrefix(String prefix) {
        requireNonNull(prefix);
        return dictionary.containsKey(prefix);
    }

    public PrefixEntry getPrefixEntry(Prefix prefix) {
        return getPrefixEntry(prefix.getPrefix());
    }

    public PrefixEntry getPrefixEntry(String prefix) {
        requireNonNull(prefix);
        return dictionary.get(prefix);
    }

    /**
     * Returns true if the {@code prefix} is part of the dictionary.
     */
    public String getPrefixesWithInfo() {
        String params = joinPrefixes(parameters, p -> getPrefixEntry(p).getPrefixWithInfo());
        String opts = joinPrefixes(optionals, p -> getPrefixEntry(p).getPrefixWithOptionalInfo());

        if (params.isEmpty()) {
            return opts;
        } else {
            return params + " " + opts;
        }
    }

    /**
     * Returns a String joining all the prefix examples in {@code prefixes}.
     * Each prefix in {@code prefixes} must have a matching PrefixEntry in the current dictionary.
     *
     * @throws IllegalArgumentException if there is a prefix in {@code prefixes}
     * that does not match an entries in the current dictionary.
     */
    public String getPrefixesWithExamples(Prefix ... prefixes) throws IllegalArgumentException {
        return joinPrefixes(Arrays.asList(prefixes), p -> {
            if (!hasPrefix(p)) {
                throw new IllegalArgumentException(String.format(MESSAGE_UNKNOWN_ENTRY, p.getInfo()));
            }
            return getPrefixEntry(p).getPrefixWithExamples();
        });
    }

    private static String joinPrefixes(List<Prefix> prefixes, Function<Prefix, String> mapper) {
        return prefixes.stream()
                .map(mapper)
                .collect(Collectors.joining(" "));
    }
}
