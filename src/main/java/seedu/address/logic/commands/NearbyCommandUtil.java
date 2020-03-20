package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;

/**
 * Contains utility methods used for Nearby Command
 */
public class NearbyCommandUtil {
    private static HashMap<Integer, String> postalSectorInfo = new HashMap<>();
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

    static {
        initialize();
    }

    /**
     * Checks if given {@code postalSector} is a valid postal sector.
     *
     * @param postalSector Used to find postal sector
     * @return whether given {@code postalSector} is a valid postal sector
     */
    public static boolean isValidPostalSector(Index postalSector) {
        requireNonNull(postalSector);
        int givenPostalSector = postalSector.getOneBased();
        return postalSectorInfo.containsKey(givenPostalSector);
    }

    /**
     * Obtains the general location for a given {@code postalSector}.
     *
     * @param postalSector used to find general location
     * @return {@code Optional<String>} representing the general location of given postal sector
     */
    public static Optional<String> getGeneralLocation(Index postalSector) {
        requireNonNull(postalSector);
        int givenPostalSector = postalSector.getOneBased();
        return Optional.ofNullable(postalSectorInfo.get(givenPostalSector));
    }

    /**
     * Obtain a list of postal sectors with the same general location.
     *
     * @param location used to find all matching postal sectors
     * @return {@code List<String>} of all postal sectors with same location
     */
    public static List<String> sameGeneralLocation(String location) {
        requireNonNull(location);
        List<String> output = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : postalSectorInfo.entrySet()) {
            if (location.equals(entry.getValue())) {
                Integer sector = entry.getKey();
                String sectorStr = "S" + ((sector < 10) ? ("0" + sector)
                        : sector);
                output.add(sectorStr);
            }
        }
        return output;
    }

    /**
     * Obtain a list of postal sectors regex with the same area.
     *
     * @param searchTerm used to find all matching areas
     * @return {@code List<String>} of all postal sectors regex with same area
     */
    public static List<String> sameAreaRegex(String searchTerm) {
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

    /**
     * Add postal sector information to HashMap {@code postalSectorInfo}.
     */
    private static void initialize() {
        addLocation1();
        addLocation2();
        addLocation3();
        addLocation4();
        addLocation5();
        addLocation6();
        addLocation7();
        addLocation8();
        addLocation9();
        addLocation10();
        addLocation11();
        addLocation12();
        addLocation13();
        addLocation14();
        addLocation15();
        addLocation16();
        addLocation17();
        addLocation18();
        addLocation19();
        addLocation20();
        addLocation21();
        addLocation22();
        addLocation23();
        addLocation24();
        addLocation25();
        addLocation26();
        addLocation27();
        addLocation28();
    }

    /**
     * Add postal sector information for 79,80.
     */
    private static void addLocation28() {
        String generalLocation = "Seletar";
        postalSectorInfo.put(79, generalLocation);
        postalSectorInfo.put(80, generalLocation);
    }

    /**
     * Add postal sector information for 75,76.
     */
    private static void addLocation27() {
        String generalLocation = "Yishun, Sembawang";
        postalSectorInfo.put(75, generalLocation);
        postalSectorInfo.put(76, generalLocation);
    }

    /**
     * Add postal sector information for 77,78.
     */
    private static void addLocation26() {
        String generalLocation = "Upper Thomson, Springleaf";
        postalSectorInfo.put(77, generalLocation);
        postalSectorInfo.put(78, generalLocation);
    }

    /**
     * Add postal sector information for 72,73.
     */
    private static void addLocation25() {
        String generalLocation = "Kranji, Woodgrove";
        postalSectorInfo.put(72, generalLocation);
        postalSectorInfo.put(73, generalLocation);
    }

    /**
     * Add postal sector information for 69,70,71.
     */
    private static void addLocation24() {
        String generalLocation = "Lim Chu Kang, Tengah";
        postalSectorInfo.put(69, generalLocation);
        postalSectorInfo.put(70, generalLocation);
        postalSectorInfo.put(71, generalLocation);
    }

    /**
     * Add postal sector information for 65,66,67,68.
     */
    private static void addLocation23() {
        String generalLocation = "Hillview, Dairy Farm, Bukit Panjang, Choa Chu Kang";
        postalSectorInfo.put(65, generalLocation);
        postalSectorInfo.put(66, generalLocation);
        postalSectorInfo.put(67, generalLocation);
        postalSectorInfo.put(68, generalLocation);
    }

    /**
     * Add postal sector information for 60,61,62,63,64.
     */
    private static void addLocation22() {
        String generalLocation = "Jurong";
        postalSectorInfo.put(60, generalLocation);
        postalSectorInfo.put(61, generalLocation);
        postalSectorInfo.put(62, generalLocation);
        postalSectorInfo.put(63, generalLocation);
        postalSectorInfo.put(64, generalLocation);
    }

    /**
     * Add postal sector information for 58,59.
     */
    private static void addLocation21() {
        String generalLocation = "Upper Bukit Timah, Clementi Park, Ulu Pandan";
        postalSectorInfo.put(58, generalLocation);
        postalSectorInfo.put(59, generalLocation);
    }

    /**
     * Add postal sector information for 56,57.
     */
    private static void addLocation20() {
        String generalLocation = "Bishan, Ang Mo Kio";
        postalSectorInfo.put(56, generalLocation);
        postalSectorInfo.put(57, generalLocation);
    }

    /**
     * Add postal sector information for 53,54,55,82.
     */
    private static void addLocation19() {
        String generalLocation = "Serangoon Garden, Hougang, Punggol";
        postalSectorInfo.put(53, generalLocation);
        postalSectorInfo.put(54, generalLocation);
        postalSectorInfo.put(55, generalLocation);
        postalSectorInfo.put(82, generalLocation);
    }

    /**
     * Add postal sector information for 51,52.
     */
    private static void addLocation18() {
        String generalLocation = "Tampines, Pasir Ris";
        postalSectorInfo.put(51, generalLocation);
        postalSectorInfo.put(52, generalLocation);
    }

    /**
     * Add postal sector information for 49,50,81.
     */
    private static void addLocation17() {
        String generalLocation = "Loyang, Changi";
        postalSectorInfo.put(49, generalLocation);
        postalSectorInfo.put(50, generalLocation);
        postalSectorInfo.put(81, generalLocation);
    }

    /**
     * Add postal sector information for 46,47,48.
     */
    private static void addLocation16() {
        String generalLocation = "Bedok, Upper East Coast, Eastwood, Kew Drive";
        postalSectorInfo.put(46, generalLocation);
        postalSectorInfo.put(47, generalLocation);
        postalSectorInfo.put(48, generalLocation);
    }

    /**
     * Add postal sector information for 42,43,44,45.
     */
    private static void addLocation15() {
        String generalLocation = "Katong, Joo Chiat, Amber Road";
        postalSectorInfo.put(42, generalLocation);
        postalSectorInfo.put(43, generalLocation);
        postalSectorInfo.put(44, generalLocation);
        postalSectorInfo.put(45, generalLocation);
    }

    /**
     * Add postal sector information for 38,39,40,41.
     */
    private static void addLocation14() {
        String generalLocation = "Geylang, Eunos";
        postalSectorInfo.put(38, generalLocation);
        postalSectorInfo.put(39, generalLocation);
        postalSectorInfo.put(40, generalLocation);
        postalSectorInfo.put(41, generalLocation);
    }

    /**
     * Add postal sector information for 34,35,36,37.
     */
    private static void addLocation13() {
        String generalLocation = "Macpherson, Braddell";
        postalSectorInfo.put(34, generalLocation);
        postalSectorInfo.put(35, generalLocation);
        postalSectorInfo.put(36, generalLocation);
        postalSectorInfo.put(37, generalLocation);
    }

    /**
     * Add postal sector information for 31,32,33.
     */
    private static void addLocation12() {
        String generalLocation = "Balestier, Toa Payoh, Serangoon";
        postalSectorInfo.put(31, generalLocation);
        postalSectorInfo.put(32, generalLocation);
        postalSectorInfo.put(33, generalLocation);
    }

    /**
     * Add postal sector information for 28,29,30.
     */
    private static void addLocation11() {
        String generalLocation = "Watten Estate, Novena, Thomson";
        postalSectorInfo.put(28, generalLocation);
        postalSectorInfo.put(29, generalLocation);
        postalSectorInfo.put(30, generalLocation);
    }

    /**
     * Add postal sector information for 24,25,26,27.
     */
    private static void addLocation10() {
        String generalLocation = "Ardmore, Bukit Timah, Holland Road, Tanglin";
        postalSectorInfo.put(24, generalLocation);
        postalSectorInfo.put(25, generalLocation);
        postalSectorInfo.put(26, generalLocation);
        postalSectorInfo.put(27, generalLocation);
    }

    /**
     * Add postal sector information for 22,23.
     */
    private static void addLocation9() {
        String generalLocation = "Orchard, Cairnhill, River Valley";
        postalSectorInfo.put(22, generalLocation);
        postalSectorInfo.put(23, generalLocation);
    }

    /**
     * Add postal sector information for 20,21.
     */
    private static void addLocation8() {
        String generalLocation = "Little India";
        postalSectorInfo.put(20, generalLocation);
        postalSectorInfo.put(21, generalLocation);
    }

    /**
     * Add postal sector information for 18,19.
     */
    private static void addLocation7() {
        String generalLocation = "Middle Road, Golden Mile";
        postalSectorInfo.put(18, generalLocation);
        postalSectorInfo.put(19, generalLocation);
    }

    /**
     * Add postal sector information for 17.
     */
    private static void addLocation6() {
        postalSectorInfo.put(17, "High Street, Beach Road (part)");
    }

    /**
     * Add postal sector information for 11,12,13.
     */
    private static void addLocation5() {
        String generalLocation = "Pasir Panjang, Hong Leong Garden, Clementi New Town";
        postalSectorInfo.put(11, generalLocation);
        postalSectorInfo.put(12, generalLocation);
        postalSectorInfo.put(13, generalLocation);
    }

    /**
     * Add postal sector information for 9,10.
     */
    private static void addLocation4() {
        String generalLocation = "Telok Blangah, Harbourfront";
        postalSectorInfo.put(9, generalLocation);
        postalSectorInfo.put(10, generalLocation);
    }

    /**
     * Add postal sector information for 14,15,16.
     */
    private static void addLocation3() {
        String generalLocation = "Queenstown, Tiong Bahru";
        postalSectorInfo.put(14, generalLocation);
        postalSectorInfo.put(15, generalLocation);
        postalSectorInfo.put(16, generalLocation);
    }

    /**
     * Add postal sector information for 7,8.
     */
    private static void addLocation2() {
        String generalLocation = "Anson, Tanjong Pagar";
        postalSectorInfo.put(7, generalLocation);
        postalSectorInfo.put(8, generalLocation);
    }

    /**
     * Add postal sector information for 1,2,3,4,5,6.
     */
    private static void addLocation1() {
        String generalLocation = "Raffles Place, Cecil, Marina, People’s Park";
        postalSectorInfo.put(1, generalLocation);
        postalSectorInfo.put(2, generalLocation);
        postalSectorInfo.put(3, generalLocation);
        postalSectorInfo.put(4, generalLocation);
        postalSectorInfo.put(5, generalLocation);
        postalSectorInfo.put(6, generalLocation);
    }
}
