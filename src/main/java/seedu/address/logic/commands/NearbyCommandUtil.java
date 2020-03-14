package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Optional;

import seedu.address.commons.core.index.Index;

/**
 * Contains utility methods used for Nearby Command
 */
public class NearbyCommandUtil {
    private static HashMap<Integer, String> postalSectorInfo = new HashMap<>();

    static {
        initializePostalSectorInfo();
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
     * Add postal sector information to HashMap {@code postalSectorInfo}.
     */
    private static void initializePostalSectorInfo() {
        postalSectorInfo.put(1, "Raffles Place, Cecil, Marina, People’s Park");
        postalSectorInfo.put(2, "Raffles Place, Cecil, Marina, People’s Park");
        postalSectorInfo.put(3, "Raffles Place, Cecil, Marina, People’s Park");
        postalSectorInfo.put(4, "Raffles Place, Cecil, Marina, People’s Park");
        postalSectorInfo.put(5, "Raffles Place, Cecil, Marina, People’s Park");
        postalSectorInfo.put(6, "Raffles Place, Cecil, Marina, People’s Park");

        postalSectorInfo.put(7, "Anson, Tanjong Pagar");
        postalSectorInfo.put(8, "Anson, Tanjong Pagar");

        postalSectorInfo.put(14, "Queenstown, Tiong Bahru");
        postalSectorInfo.put(15, "Queenstown, Tiong Bahru");
        postalSectorInfo.put(16, "Queenstown, Tiong Bahru");

        postalSectorInfo.put(9, "Telok Blangah, Harbourfront");
        postalSectorInfo.put(10, "Telok Blangah, Harbourfront");

        postalSectorInfo.put(11, "Pasir Panjang, Hong Leong Garden, Clementi New Town");
        postalSectorInfo.put(12, "Pasir Panjang, Hong Leong Garden, Clementi New Town");
        postalSectorInfo.put(13, "Pasir Panjang, Hong Leong Garden, Clementi New Town");

        postalSectorInfo.put(17, "High Street, Beach Road (part)");

        postalSectorInfo.put(18, "Middle Road, Golden Mile");
        postalSectorInfo.put(19, "Middle Road, Golden Mile");

        postalSectorInfo.put(20, "Little India");
        postalSectorInfo.put(21, "Little India");

        postalSectorInfo.put(22, "Orchard, Cairnhill, River Valley");
        postalSectorInfo.put(23, "Orchard, Cairnhill, River Valley");

        postalSectorInfo.put(24, "Ardmore, Bukit Timah, Holland Road, Tanglin");
        postalSectorInfo.put(25, "Ardmore, Bukit Timah, Holland Road, Tanglin");
        postalSectorInfo.put(26, "Ardmore, Bukit Timah, Holland Road, Tanglin");
        postalSectorInfo.put(27, "Ardmore, Bukit Timah, Holland Road, Tanglin");

        postalSectorInfo.put(28, "Watten Estate, Novena, Thomson");
        postalSectorInfo.put(29, "Watten Estate, Novena, Thomson");
        postalSectorInfo.put(30, "Watten Estate, Novena, Thomson");

        postalSectorInfo.put(31, "Balestier, Toa Payoh, Serangoon");
        postalSectorInfo.put(32, "Balestier, Toa Payoh, Serangoon");
        postalSectorInfo.put(33, "Balestier, Toa Payoh, Serangoon");

        postalSectorInfo.put(34, "Macpherson, Braddell");
        postalSectorInfo.put(35, "Macpherson, Braddell");
        postalSectorInfo.put(36, "Macpherson, Braddell");
        postalSectorInfo.put(37, "Macpherson, Braddell");

        postalSectorInfo.put(38, "Geylang, Eunos");
        postalSectorInfo.put(39, "Geylang, Eunos");
        postalSectorInfo.put(40, "Geylang, Eunos");
        postalSectorInfo.put(41, "Geylang, Eunos");

        postalSectorInfo.put(42, "Katong, Joo Chiat, Amber Road");
        postalSectorInfo.put(43, "Katong, Joo Chiat, Amber Road");
        postalSectorInfo.put(44, "Katong, Joo Chiat, Amber Road");
        postalSectorInfo.put(45, "Katong, Joo Chiat, Amber Road");

        postalSectorInfo.put(46, "Bedok, Upper East Coast, Eastwood, Kew Drive");
        postalSectorInfo.put(47, "Bedok, Upper East Coast, Eastwood, Kew Drive");
        postalSectorInfo.put(48, "Bedok, Upper East Coast, Eastwood, Kew Drive");

        postalSectorInfo.put(49, "Loyang, Changi");
        postalSectorInfo.put(50, "Loyang, Changi");
        postalSectorInfo.put(81, "Loyang, Changi");

        postalSectorInfo.put(51, "Tampines, Pasir Ris");
        postalSectorInfo.put(52, "Tampines, Pasir Ris");

        postalSectorInfo.put(53, "Serangoon Garden, Hougang, Ponggol");
        postalSectorInfo.put(54, "Serangoon Garden, Hougang, Ponggol");
        postalSectorInfo.put(55, "Serangoon Garden, Hougang, Ponggol");
        postalSectorInfo.put(82, "Serangoon Garden, Hougang, Ponggol");

        postalSectorInfo.put(56, "Bishan, Ang Mo Kio");
        postalSectorInfo.put(57, "Bishan, Ang Mo Kio");

        postalSectorInfo.put(58, "Upper Bukit Timah, Clementi Park, Ulu Pandan");
        postalSectorInfo.put(59, "Upper Bukit Timah, Clementi Park, Ulu Pandan");

        postalSectorInfo.put(60, "Jurong");
        postalSectorInfo.put(61, "Jurong");
        postalSectorInfo.put(62, "Jurong");
        postalSectorInfo.put(63, "Jurong");
        postalSectorInfo.put(64, "Jurong");

        postalSectorInfo.put(65, "Hillview, Dairy Farm, Bukit Panjang, Choa Chu Kang");
        postalSectorInfo.put(66, "Hillview, Dairy Farm, Bukit Panjang, Choa Chu Kang");
        postalSectorInfo.put(67, "Hillview, Dairy Farm, Bukit Panjang, Choa Chu Kang");
        postalSectorInfo.put(68, "Hillview, Dairy Farm, Bukit Panjang, Choa Chu Kang");

        postalSectorInfo.put(69, "Lim Chu Kang, Tengah");
        postalSectorInfo.put(70, "Lim Chu Kang, Tengah");
        postalSectorInfo.put(71, "Lim Chu Kang, Tengah");

        postalSectorInfo.put(72, "Kranji, Woodgrove");
        postalSectorInfo.put(73, "Kranji, Woodgrove");

        postalSectorInfo.put(77, "Upper Thomson, Springleaf");
        postalSectorInfo.put(78, "Upper Thomson, Springleaf");

        postalSectorInfo.put(75, "Yishun, Sembawang");
        postalSectorInfo.put(76, "Yishun, Sembawang");

        postalSectorInfo.put(79, "Seletar");
        postalSectorInfo.put(80, "Seletar");
    }
}
