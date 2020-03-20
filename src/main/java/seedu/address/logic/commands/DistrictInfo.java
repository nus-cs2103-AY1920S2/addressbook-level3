package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Contains district and area information of Singapore.
 */
public class DistrictInfo {
    // Areas of Singapore
    public static final String CENTRAL = "CENTRAL";
    public static final String EAST = "EAST";
    public static final String NORTH_EAST = "NORTH-EAST";
    public static final String WEST = "WEST";
    public static final String NORTH = "NORTH";

    // Regex for postal district and area
    private static List<String> district1 = new ArrayList<>(Arrays.asList(
            ".*S01\\d{4}.*", ".*S02\\d{4}.*", ".*S03\\d{4}.*", ".*S04\\d{4}.*", ".*S05\\d{4}.*", ".*S06\\d{4}.*"));
    private static List<String> district2 = new ArrayList<>(Arrays.asList(
            ".*S07\\d{4}.*", ".*S08\\d{4}.*"));
    private static List<String> district3 = new ArrayList<>(Arrays.asList(
            ".*S14\\d{4}.*", ".*S15\\d{4}.*", ".*S16\\d{4}.*"));
    private static List<String> district4 = new ArrayList<>(Arrays.asList(
            ".*S09\\d{4}.*", ".*S10\\d{4}.*"));
    private static List<String> district5 = new ArrayList<>(Arrays.asList(
            ".*S11\\d{4}.*", ".*S12\\d{4}.*", ".*S13\\d{4}.*"));
    private static List<String> district6 = new ArrayList<>(Collections.singletonList(
            ".*S17\\d{4}.*"));
    private static List<String> district7 = new ArrayList<>(Arrays.asList(
            ".*S18\\d{4}.*", ".*S19\\d{4}.*"));
    private static List<String> district8 = new ArrayList<>(Arrays.asList(
            ".*S20\\d{4}.*", ".*S21\\d{4}.*"));
    private static List<String> district9 = new ArrayList<>(Arrays.asList(
            ".*S22\\d{4}.*", ".*S23\\d{4}.*"));
    private static List<String> district10 = new ArrayList<>(Arrays.asList(
            ".*S24\\d{4}.*", ".*S25\\d{4}.*", ".*S26\\d{4}.*", ".*S27\\d{4}.*"));
    private static List<String> district11 = new ArrayList<>(Arrays.asList(
            ".*S28\\d{4}.*", ".*S29\\d{4}.*", ".*S30\\d{4}.*"));
    private static List<String> district12 = new ArrayList<>(Arrays.asList(
            ".*S31\\d{4}.*", ".*S32\\d{4}.*", ".*S33\\d{4}.*"));
    private static List<String> district13 = new ArrayList<>(Arrays.asList(
            ".*S34\\d{4}.*", ".*S35\\d{4}.*", ".*S36\\d{4}.*", ".*S37\\d{4}.*"));
    private static List<String> district14 = new ArrayList<>(Arrays.asList(
            ".*S38\\d{4}.*", ".*S39\\d{4}.*", ".*S40\\d{4}.*", ".*S41\\d{4}.*"));
    private static List<String> district15 = new ArrayList<>(Arrays.asList(
            ".*S42\\d{4}.*", ".*S43\\d{4}.*", ".*S44\\d{4}.*", ".*S45\\d{4}.*"));
    private static List<String> district16 = new ArrayList<>(Arrays.asList(
            ".*S46\\d{4}.*", ".*S47\\d{4}.*", ".*S48\\d{4}.*"));
    private static List<String> district17 = new ArrayList<>(Arrays.asList(
            ".*S49\\d{4}.*", ".*S50\\d{4}.*", ".*S81\\d{4}.*"));
    private static List<String> district18 = new ArrayList<>(Arrays.asList(
            ".*S51\\d{4}.*", ".*S52\\d{4}.*"));
    private static List<String> district19 = new ArrayList<>(Arrays.asList(
            ".*S53\\d{4}.*", ".*S54\\d{4}.*", ".*S55\\d{4}.*", ".*S82\\d{4}.*"));
    private static List<String> district20 = new ArrayList<>(Arrays.asList(
            ".*S56\\d{4}.*", ".*S57\\d{4}.*"));
    private static List<String> district21 = new ArrayList<>(Arrays.asList(
            ".*S58\\d{4}.*", ".*S59\\d{4}.*"));
    private static List<String> district22 = new ArrayList<>(Arrays.asList(
            ".*S60\\d{4}.*", ".*S61\\d{4}.*", ".*S62\\d{4}.*", ".*S63\\d{4}.*", ".*S64\\d{4}.*"));
    private static List<String> district23 = new ArrayList<>(Arrays.asList(
            ".*S65\\d{4}.*", ".*S66\\d{4}.*", ".*S67\\d{4}.*", ".*S68\\d{4}.*"));
    private static List<String> district24 = new ArrayList<>(Arrays.asList(
            ".*S69\\d{4}.*", ".*S70\\d{4}.*", ".*S71\\d{4}.*"));
    private static List<String> district25 = new ArrayList<>(Arrays.asList(
            ".*S72\\d{4}.*", ".*S73\\d{4}.*"));
    private static List<String> district26 = new ArrayList<>(Arrays.asList(
            ".*S77\\d{4}.*", ".*S78\\d{4}.*"));
    private static List<String> district27 = new ArrayList<>(Arrays.asList(
            ".*S75\\d{4}.*", ".*S76\\d{4}.*"));
    private static List<String> district28 = new ArrayList<>(Arrays.asList(
            ".*S79\\d{4}.*", ".*S80\\d{4}.*"));

    private static List<String> centralArea = Stream.of(
            district1, district2, district3, district4, district5, district6,
            district7, district8, district9, district10, district11, district12,
            district13, district14, district15, district21)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

    private static List<String> eastArea = Stream.of(
            district16, district17, district18)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

    private static List<String> northEastArea = Stream.of(
            district19, district20, district28)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

    private static List<String> westArea = Stream.of(
            district22, district23, district24)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

    private static List<String> northArea = Stream.of(
            district25, district26, district27)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

    /**
     * Checks if a given {@code searchTerm} is a valid area.
     *
     * @param searchTerm used for checking validity
     * @return boolean indicating validity of given {@code searchTerm}
     */
    public static boolean isValidArea(String searchTerm) {
        requireNonNull(searchTerm);
        String term = searchTerm.toUpperCase();
        return Stream.of(CENTRAL, EAST, NORTH_EAST, WEST, NORTH)
                .anyMatch(term::contains);
    }

    /**
     * Obtain a list of postal sectors regex with the same area.
     *
     * @param searchTerm used to find all matching areas
     * @return {@code List<String>} of all postal sectors regex with same area
     */
    public static List<String> sameAreaRegex(String searchTerm) {
        requireNonNull(searchTerm);
        switch (searchTerm.toUpperCase()) {
        case "CENTRAL":
            return centralArea;
        case "EAST":
            return eastArea;
        case "NORTH-EAST":
            return northEastArea;
        case "WEST":
            return westArea;
        case "NORTH":
            return northArea;
        default:
            return new ArrayList<>();
        }
    }
}
