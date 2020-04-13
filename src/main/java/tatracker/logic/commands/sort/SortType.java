// @@author potatocombat

package tatracker.logic.commands.sort;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the different types of sorting.
 */
public enum SortType {
    // @@author aakanksha-rai

    ALPHABETIC("alphabetically", "alpha", "alphabetical"),
    MATRIC("matric"),
    RATING_ASC("rating asc", "asc"),
    RATING_DESC("rating desc", "desc");

    public static final String MESSAGE_CONSTRAINTS =
            "These are the only sort types: alphabetically, matric, rating asc, rating desc";

    // @@author potatocombat

    private static final Map<String, SortType> SORT_TYPES = createSortDictionary();

    private final List<String> names;

    SortType(String ... names) {
        this.names = List.of(names);
    }

    public static boolean isValidSortType(String test) {
        return SORT_TYPES.containsKey(test.toLowerCase());
    }

    public static SortType getSortType(String sortType) {
        requireNonNull(sortType);
        return SORT_TYPES.get(sortType.toLowerCase());
    }

    /**
     * Returns a String to {@code SortType} lookup table.
     */
    private static Map<String, SortType> createSortDictionary() {
        Map<String, SortType> dictionary = new HashMap<>();

        for (SortType type : values()) {
            for (String name : type.names) {
                dictionary.put(name, type);
            }
        }
        return Collections.unmodifiableMap(dictionary);
    }
}
