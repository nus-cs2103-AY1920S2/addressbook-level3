package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import seedu.address.commons.core.index.Index;

/**
 * Contains utility methods used for Nearby Command.
 */
public class NearbyCommandUtil {
    private static HashMap<Integer, String> postalSectorInfo = new HashMap<>();

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
                String sectorStr = "%s" + ((sector < 10) ? ("0" + sector)
                        : sector);
                output.add(String.format(sectorStr, "S"));
                output.add(String.format(sectorStr, "s"));
            }
        }
        return output;
    }

    /**
     * Add postal sector information to HashMap {@code postalSectorInfo}.
     */
    private static void initialize() {
        addDistrict1();
        addDistrict2();
        addDistrict3();
        addDistrict4();
        addDistrict5();
        addDistrict6();
        addDistrict7();
        addDistrict8();
        addDistrict9();
        addDistrict10();
        addDistrict11();
        addDistrict12();
        addDistrict13();
        addDistrict14();
        addDistrict15();
        addDistrict16();
        addDistrict17();
        addDistrict18();
        addDistrict19();
        addDistrict20();
        addDistrict21();
        addDistrict22();
        addDistrict23();
        addDistrict24();
        addDistrict25();
        addDistrict26();
        addDistrict27();
        addDistrict28();
    }

    /**
     * Add postal sector information for 79,80.
     */
    private static void addDistrict28() {
        String generalLocation = "Seletar";
        postalSectorInfo.put(79, generalLocation);
        postalSectorInfo.put(80, generalLocation);
    }

    /**
     * Add postal sector information for 75,76.
     */
    private static void addDistrict27() {
        String generalLocation = "Yishun, Sembawang";
        postalSectorInfo.put(75, generalLocation);
        postalSectorInfo.put(76, generalLocation);
    }

    /**
     * Add postal sector information for 77,78.
     */
    private static void addDistrict26() {
        String generalLocation = "Upper Thomson, Springleaf";
        postalSectorInfo.put(77, generalLocation);
        postalSectorInfo.put(78, generalLocation);
    }

    /**
     * Add postal sector information for 72,73.
     */
    private static void addDistrict25() {
        String generalLocation = "Kranji, Woodgrove";
        postalSectorInfo.put(72, generalLocation);
        postalSectorInfo.put(73, generalLocation);
    }

    /**
     * Add postal sector information for 69,70,71.
     */
    private static void addDistrict24() {
        String generalLocation = "Lim Chu Kang, Tengah";
        postalSectorInfo.put(69, generalLocation);
        postalSectorInfo.put(70, generalLocation);
        postalSectorInfo.put(71, generalLocation);
    }

    /**
     * Add postal sector information for 65,66,67,68.
     */
    private static void addDistrict23() {
        String generalLocation = "Hillview, Dairy Farm, Bukit Panjang, Choa Chu Kang";
        postalSectorInfo.put(65, generalLocation);
        postalSectorInfo.put(66, generalLocation);
        postalSectorInfo.put(67, generalLocation);
        postalSectorInfo.put(68, generalLocation);
    }

    /**
     * Add postal sector information for 60,61,62,63,64.
     */
    private static void addDistrict22() {
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
    private static void addDistrict21() {
        String generalLocation = "Upper Bukit Timah, Clementi Park, Ulu Pandan";
        postalSectorInfo.put(58, generalLocation);
        postalSectorInfo.put(59, generalLocation);
    }

    /**
     * Add postal sector information for 56,57.
     */
    private static void addDistrict20() {
        String generalLocation = "Bishan, Ang Mo Kio";
        postalSectorInfo.put(56, generalLocation);
        postalSectorInfo.put(57, generalLocation);
    }

    /**
     * Add postal sector information for 53,54,55,82.
     */
    private static void addDistrict19() {
        String generalLocation = "Serangoon Garden, Hougang, Punggol";
        postalSectorInfo.put(53, generalLocation);
        postalSectorInfo.put(54, generalLocation);
        postalSectorInfo.put(55, generalLocation);
        postalSectorInfo.put(82, generalLocation);
    }

    /**
     * Add postal sector information for 51,52.
     */
    private static void addDistrict18() {
        String generalLocation = "Tampines, Pasir Ris";
        postalSectorInfo.put(51, generalLocation);
        postalSectorInfo.put(52, generalLocation);
    }

    /**
     * Add postal sector information for 49,50,81.
     */
    private static void addDistrict17() {
        String generalLocation = "Loyang, Changi";
        postalSectorInfo.put(49, generalLocation);
        postalSectorInfo.put(50, generalLocation);
        postalSectorInfo.put(81, generalLocation);
    }

    /**
     * Add postal sector information for 46,47,48.
     */
    private static void addDistrict16() {
        String generalLocation = "Bedok, Upper East Coast, Eastwood, Kew Drive";
        postalSectorInfo.put(46, generalLocation);
        postalSectorInfo.put(47, generalLocation);
        postalSectorInfo.put(48, generalLocation);
    }

    /**
     * Add postal sector information for 42,43,44,45.
     */
    private static void addDistrict15() {
        String generalLocation = "Katong, Joo Chiat, Amber Road";
        postalSectorInfo.put(42, generalLocation);
        postalSectorInfo.put(43, generalLocation);
        postalSectorInfo.put(44, generalLocation);
        postalSectorInfo.put(45, generalLocation);
    }

    /**
     * Add postal sector information for 38,39,40,41.
     */
    private static void addDistrict14() {
        String generalLocation = "Geylang, Eunos";
        postalSectorInfo.put(38, generalLocation);
        postalSectorInfo.put(39, generalLocation);
        postalSectorInfo.put(40, generalLocation);
        postalSectorInfo.put(41, generalLocation);
    }

    /**
     * Add postal sector information for 34,35,36,37.
     */
    private static void addDistrict13() {
        String generalLocation = "Macpherson, Braddell";
        postalSectorInfo.put(34, generalLocation);
        postalSectorInfo.put(35, generalLocation);
        postalSectorInfo.put(36, generalLocation);
        postalSectorInfo.put(37, generalLocation);
    }

    /**
     * Add postal sector information for 31,32,33.
     */
    private static void addDistrict12() {
        String generalLocation = "Balestier, Toa Payoh, Serangoon";
        postalSectorInfo.put(31, generalLocation);
        postalSectorInfo.put(32, generalLocation);
        postalSectorInfo.put(33, generalLocation);
    }

    /**
     * Add postal sector information for 28,29,30.
     */
    private static void addDistrict11() {
        String generalLocation = "Watten Estate, Novena, Thomson";
        postalSectorInfo.put(28, generalLocation);
        postalSectorInfo.put(29, generalLocation);
        postalSectorInfo.put(30, generalLocation);
    }

    /**
     * Add postal sector information for 24,25,26,27.
     */
    private static void addDistrict10() {
        String generalLocation = "Ardmore, Bukit Timah, Holland Road, Tanglin";
        postalSectorInfo.put(24, generalLocation);
        postalSectorInfo.put(25, generalLocation);
        postalSectorInfo.put(26, generalLocation);
        postalSectorInfo.put(27, generalLocation);
    }

    /**
     * Add postal sector information for 22,23.
     */
    private static void addDistrict9() {
        String generalLocation = "Orchard, Cairnhill, River Valley";
        postalSectorInfo.put(22, generalLocation);
        postalSectorInfo.put(23, generalLocation);
    }

    /**
     * Add postal sector information for 20,21.
     */
    private static void addDistrict8() {
        String generalLocation = "Little India";
        postalSectorInfo.put(20, generalLocation);
        postalSectorInfo.put(21, generalLocation);
    }

    /**
     * Add postal sector information for 18,19.
     */
    private static void addDistrict7() {
        String generalLocation = "Middle Road, Golden Mile";
        postalSectorInfo.put(18, generalLocation);
        postalSectorInfo.put(19, generalLocation);
    }

    /**
     * Add postal sector information for 17.
     */
    private static void addDistrict6() {
        postalSectorInfo.put(17, "High Street, Beach Road (part)");
    }

    /**
     * Add postal sector information for 11,12,13.
     */
    private static void addDistrict5() {
        String generalLocation = "Pasir Panjang, Hong Leong Garden, Clementi New Town";
        postalSectorInfo.put(11, generalLocation);
        postalSectorInfo.put(12, generalLocation);
        postalSectorInfo.put(13, generalLocation);
    }

    /**
     * Add postal sector information for 9,10.
     */
    private static void addDistrict4() {
        String generalLocation = "Telok Blangah, Harbourfront";
        postalSectorInfo.put(9, generalLocation);
        postalSectorInfo.put(10, generalLocation);
    }

    /**
     * Add postal sector information for 14,15,16.
     */
    private static void addDistrict3() {
        String generalLocation = "Queenstown, Tiong Bahru";
        postalSectorInfo.put(14, generalLocation);
        postalSectorInfo.put(15, generalLocation);
        postalSectorInfo.put(16, generalLocation);
    }

    /**
     * Add postal sector information for 7,8.
     */
    private static void addDistrict2() {
        String generalLocation = "Anson, Tanjong Pagar";
        postalSectorInfo.put(7, generalLocation);
        postalSectorInfo.put(8, generalLocation);
    }

    /**
     * Add postal sector information for 1,2,3,4,5,6.
     */
    private static void addDistrict1() {
        String generalLocation = "Raffles Place, Cecil, Marina, People’s Park";
        postalSectorInfo.put(1, generalLocation);
        postalSectorInfo.put(2, generalLocation);
        postalSectorInfo.put(3, generalLocation);
        postalSectorInfo.put(4, generalLocation);
        postalSectorInfo.put(5, generalLocation);
        postalSectorInfo.put(6, generalLocation);
    }
}
