//@@author aakanksha-rai

package tatracker.model.group;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a group type.
 * Can be a lab or a tutorial.
 */
public enum GroupType {
    LAB,
    TUTORIAL,
    RECITATION,
    OTHER;

    public static final String MESSAGE_CONSTRAINTS =
            "These are the only group types: lab, tutorial, recitation, other";

    //@@author potatocombat
    private static final Map<String, GroupType> GROUP_TYPES = Arrays.stream(values())
            .collect(Collectors.toUnmodifiableMap(type -> type.name().toLowerCase(), type -> type));

    public static boolean isValidGroupType(String test) {
        return GROUP_TYPES.containsKey(test.toLowerCase());
    }

    public static GroupType getGroupType(String groupType) {
        requireNonNull(groupType);
        return GROUP_TYPES.get(groupType.toLowerCase());
    }

    //@@author aakanksha-rai
    @Override
    public String toString() {
        String name = name();
        if (name.length() > 0) {
            // Capitalise first letter
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        return name;
    }
}
