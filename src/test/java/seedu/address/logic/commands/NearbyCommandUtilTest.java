package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import seedu.address.commons.core.index.Index;

class NearbyCommandUtilTest {
    private static final Index INVALID_POSTAL_SECTOR = Index.fromOneBased(4000);
    private static final Index VALID_POSTAL_SECTOR = Index.fromOneBased(14);
    private static String location1 = "Raffles Place, Cecil, Marina, People’s Park";
    private static String location2 = "Anson, Tanjong Pagar";
    private static String location3 = "Queenstown, Tiong Bahru";
    private static String location4 = "Telok Blangah, Harbourfront";
    private static String location5 = "Pasir Panjang, Hong Leong Garden, Clementi New Town";
    private static String location6 = "High Street, Beach Road (part)";
    private static String location7 = "Middle Road, Golden Mile";
    private static String location8 = "Little India";
    private static String location9 = "Orchard, Cairnhill, River Valley";
    private static String location10 = "Ardmore, Bukit Timah, Holland Road, Tanglin";
    private static String location11 = "Watten Estate, Novena, Thomson";
    private static String location12 = "Balestier, Toa Payoh, Serangoon";
    private static String location13 = "Macpherson, Braddell";
    private static String location14 = "Geylang, Eunos";
    private static String location15 = "Katong, Joo Chiat, Amber Road";
    private static String location16 = "Bedok, Upper East Coast, Eastwood, Kew Drive";
    private static String location17 = "Loyang, Changi";
    private static String location18 = "Tampines, Pasir Ris";
    private static String location19 = "Serangoon Garden, Hougang, Punggol";
    private static String location20 = "Bishan, Ang Mo Kio";
    private static String location21 = "Upper Bukit Timah, Clementi Park, Ulu Pandan";
    private static String location22 = "Jurong";
    private static String location23 = "Hillview, Dairy Farm, Bukit Panjang, Choa Chu Kang";
    private static String location24 = "Lim Chu Kang, Tengah";
    private static String location25 = "Kranji, Woodgrove";
    private static String location26 = "Upper Thomson, Springleaf";
    private static String location27 = "Yishun, Sembawang";
    private static String location28 = "Seletar";

    @ParameterizedTest
    @MethodSource("sectorGeneralLocation")
    void getGeneralLocation_validPostalSector_returnsGeneralLocation(Index postalSector,
                                                                     String expectedLocation) {
        Optional<String> value = NearbyCommandUtil.getGeneralLocation(postalSector);
        if (value.isEmpty()) {
            fail("Should return a general location.");
        }
        assertEquals(expectedLocation, value.get());
    }

    @ParameterizedTest
    @MethodSource("sameLocation")
    void sameGeneralLocation_validLocation_returnsList(String location, List<String> expectedPostalSectors) {
        List<String> locations = NearbyCommandUtil.sameGeneralLocation(location);
        for (String s : expectedPostalSectors) {
            assertTrue(locations.contains(s));
        }
    }

    @Test
    void getGeneralLocation_invalidPostalSector_returnEmptyOptional() {
        Optional<String> value = NearbyCommandUtil.getGeneralLocation(INVALID_POSTAL_SECTOR);
        assertTrue(value.isEmpty());
    }

    @Test
    void getGeneralLocation_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> NearbyCommandUtil.getGeneralLocation(null));
    }

    @Test
    void isValidPostalSector_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> NearbyCommandUtil.isValidPostalSector(null));
    }

    @Test
    void isValidPostalSector_invalidPostalSector_returnsFalse() {
        assertFalse(NearbyCommandUtil.isValidPostalSector(INVALID_POSTAL_SECTOR));
    }

    @Test
    void isValidPostalSector_validPostalSector_returnsTrue() {
        assertTrue(NearbyCommandUtil.isValidPostalSector(VALID_POSTAL_SECTOR));
    }

    /**
     * Used to generate list of sectors belonging to the same general location
     *
     * @return Stream of arguments containing location and list of sectors
     */
    private static Stream<Arguments> sameLocation() {
        List<String> sector1 = new ArrayList<>(
                Arrays.asList("S01", "s01",
                        "S02", "s02",
                        "S03", "s03",
                        "S04", "s04",
                        "S05", "s05",
                        "S06", "s06"));

        List<String> sector2 = new ArrayList<>(
                Arrays.asList("S07", "s07",
                        "S08", "s08"));

        List<String> sector3 = new ArrayList<>(
                Arrays.asList("S14", "s14",
                        "S15", "s15",
                        "S16", "s16"));

        List<String> sector4 = new ArrayList<>(
                Arrays.asList("S09", "s09",
                        "S10", "s10"));

        List<String> sector5 = new ArrayList<>(
                Arrays.asList("S11", "s11",
                        "S12", "s12",
                        "S13", "s13"));

        List<String> sector6 = new ArrayList<>(
                Arrays.asList("S17", "s17"));

        List<String> sector7 = new ArrayList<>(
                Arrays.asList("S18", "s18",
                        "S19", "s19"));

        List<String> sector8 = new ArrayList<>(
                Arrays.asList("S20", "s20",
                        "S21", "s21"));

        List<String> sector9 = new ArrayList<>(
                Arrays.asList("S22", "s22",
                        "S23", "s23"));

        List<String> sector10 = new ArrayList<>(
                Arrays.asList("S24", "s24",
                        "S25", "s25",
                        "S26", "s26",
                        "S27", "s27"));

        List<String> sector11 = new ArrayList<>(
                Arrays.asList("S28", "s28",
                        "S29", "s29",
                        "S30", "s30"));

        List<String> sector12 = new ArrayList<>(
                Arrays.asList("S31", "s31",
                        "S32", "s32",
                        "S33", "s33"));

        List<String> sector13 = new ArrayList<>(
                Arrays.asList("S34", "s34",
                        "S35", "s35",
                        "S36", "s36",
                        "S37", "s37"));

        List<String> sector14 = new ArrayList<>(
                Arrays.asList("S38", "s38",
                        "S39", "s39",
                        "S40", "s40",
                        "S41", "s41"));

        List<String> sector15 = new ArrayList<>(
                Arrays.asList("S42", "s42",
                        "S43", "s43",
                        "S44", "s44",
                        "S45", "s45"));

        List<String> sector16 = new ArrayList<>(
                Arrays.asList("S46", "s46",
                        "S47", "s47",
                        "S48", "s48"));

        List<String> sector17 = new ArrayList<>(
                Arrays.asList("S49", "s49",
                        "S50", "s50",
                        "S81", "s81"));

        List<String> sector18 = new ArrayList<>(
                Arrays.asList("S51", "s51",
                        "S52", "s52"));

        List<String> sector19 = new ArrayList<>(
                Arrays.asList("S53", "s53",
                        "S54", "s54",
                        "S55", "s55",
                        "S82", "s82"));

        List<String> sector20 = new ArrayList<>(
                Arrays.asList("S56", "s56",
                        "S57", "s57"));

        List<String> sector21 = new ArrayList<>(
                Arrays.asList("S58", "s58",
                        "S59", "s59"));

        List<String> sector22 = new ArrayList<>(
                Arrays.asList("S60", "s60",
                        "S61", "s61",
                        "S62", "s62",
                        "S63", "s63",
                        "S64", "s64"));

        List<String> sector23 = new ArrayList<>(
                Arrays.asList("S65", "s65",
                        "S66", "s66",
                        "S67", "s67",
                        "S68", "s68"));

        List<String> sector24 = new ArrayList<>(
                Arrays.asList("S69", "s69",
                        "S70", "s70",
                        "S71", "s71"));

        List<String> sector25 = new ArrayList<>(
                Arrays.asList("S72", "s72",
                        "S73", "s73"));

        List<String> sector26 = new ArrayList<>(
                Arrays.asList("S77", "s77",
                        "S78", "s78"));

        List<String> sector27 = new ArrayList<>(
                Arrays.asList("S75", "s75",
                        "S76", "s76"));

        List<String> sector28 = new ArrayList<>(
                Arrays.asList("S79", "s79",
                        "S80", "s80"));

        return Stream.of(
                Arguments.of(location1, sector1),
                Arguments.of(location2, sector2),
                Arguments.of(location3, sector3),
                Arguments.of(location4, sector4),
                Arguments.of(location5, sector5),
                Arguments.of(location6, sector6),
                Arguments.of(location7, sector7),
                Arguments.of(location8, sector8),
                Arguments.of(location9, sector9),
                Arguments.of(location10, sector10),
                Arguments.of(location11, sector11),
                Arguments.of(location12, sector12),
                Arguments.of(location13, sector13),
                Arguments.of(location14, sector14),
                Arguments.of(location15, sector15),
                Arguments.of(location16, sector16),
                Arguments.of(location17, sector17),
                Arguments.of(location18, sector18),
                Arguments.of(location19, sector19),
                Arguments.of(location20, sector20),
                Arguments.of(location21, sector21),
                Arguments.of(location22, sector22),
                Arguments.of(location23, sector23),
                Arguments.of(location24, sector24),
                Arguments.of(location25, sector25),
                Arguments.of(location26, sector26),
                Arguments.of(location27, sector27),
                Arguments.of(location28, sector28)
        );
    }

    /**
     * Used to generate location information for all sectors
     *
     * @return Stream of arguments containing postal sector and location information
     */
    private static Stream<Arguments> sectorGeneralLocation() {
        return Stream.of(
                Arguments.of(Index.fromOneBased(1), location1),
                Arguments.of(Index.fromOneBased(2), location1),
                Arguments.of(Index.fromOneBased(3), location1),
                Arguments.of(Index.fromOneBased(4), location1),
                Arguments.of(Index.fromOneBased(5), location1),
                Arguments.of(Index.fromOneBased(6), location1),

                Arguments.of(Index.fromOneBased(7), location2),
                Arguments.of(Index.fromOneBased(8), location2),

                Arguments.of(Index.fromOneBased(14), location3),
                Arguments.of(Index.fromOneBased(15), location3),
                Arguments.of(Index.fromOneBased(16), location3),

                Arguments.of(Index.fromOneBased(9), location4),
                Arguments.of(Index.fromOneBased(10), location4),

                Arguments.of(Index.fromOneBased(11), location5),
                Arguments.of(Index.fromOneBased(12), location5),
                Arguments.of(Index.fromOneBased(13), location5),

                Arguments.of(Index.fromOneBased(17), location6),

                Arguments.of(Index.fromOneBased(18), location7),
                Arguments.of(Index.fromOneBased(19), location7),

                Arguments.of(Index.fromOneBased(20), location8),
                Arguments.of(Index.fromOneBased(21), location8),

                Arguments.of(Index.fromOneBased(22), location9),
                Arguments.of(Index.fromOneBased(23), location9),

                Arguments.of(Index.fromOneBased(24), location10),
                Arguments.of(Index.fromOneBased(25), location10),
                Arguments.of(Index.fromOneBased(26), location10),
                Arguments.of(Index.fromOneBased(27), location10),

                Arguments.of(Index.fromOneBased(28), location11),
                Arguments.of(Index.fromOneBased(29), location11),
                Arguments.of(Index.fromOneBased(30), location11),

                Arguments.of(Index.fromOneBased(31), location12),
                Arguments.of(Index.fromOneBased(32), location12),
                Arguments.of(Index.fromOneBased(33), location12),

                Arguments.of(Index.fromOneBased(34), location13),
                Arguments.of(Index.fromOneBased(35), location13),
                Arguments.of(Index.fromOneBased(36), location13),
                Arguments.of(Index.fromOneBased(37), location13),

                Arguments.of(Index.fromOneBased(38), location14),
                Arguments.of(Index.fromOneBased(39), location14),
                Arguments.of(Index.fromOneBased(40), location14),
                Arguments.of(Index.fromOneBased(41), location14),

                Arguments.of(Index.fromOneBased(42), location15),
                Arguments.of(Index.fromOneBased(43), location15),
                Arguments.of(Index.fromOneBased(44), location15),
                Arguments.of(Index.fromOneBased(45), location15),

                Arguments.of(Index.fromOneBased(46), location16),
                Arguments.of(Index.fromOneBased(47), location16),
                Arguments.of(Index.fromOneBased(48), location16),

                Arguments.of(Index.fromOneBased(49), location17),
                Arguments.of(Index.fromOneBased(50), location17),
                Arguments.of(Index.fromOneBased(81), location17),

                Arguments.of(Index.fromOneBased(51), location18),
                Arguments.of(Index.fromOneBased(52), location18),

                Arguments.of(Index.fromOneBased(53), location19),
                Arguments.of(Index.fromOneBased(54), location19),
                Arguments.of(Index.fromOneBased(55), location19),
                Arguments.of(Index.fromOneBased(82), location19),

                Arguments.of(Index.fromOneBased(56), location20),
                Arguments.of(Index.fromOneBased(57), location20),

                Arguments.of(Index.fromOneBased(58), location21),
                Arguments.of(Index.fromOneBased(59), location21),

                Arguments.of(Index.fromOneBased(60), location22),
                Arguments.of(Index.fromOneBased(61), location22),
                Arguments.of(Index.fromOneBased(62), location22),
                Arguments.of(Index.fromOneBased(63), location22),
                Arguments.of(Index.fromOneBased(64), location22),

                Arguments.of(Index.fromOneBased(65), location23),
                Arguments.of(Index.fromOneBased(66), location23),
                Arguments.of(Index.fromOneBased(67), location23),
                Arguments.of(Index.fromOneBased(68), location23),

                Arguments.of(Index.fromOneBased(69), location24),
                Arguments.of(Index.fromOneBased(70), location24),
                Arguments.of(Index.fromOneBased(71), location24),

                Arguments.of(Index.fromOneBased(72), location25),
                Arguments.of(Index.fromOneBased(73), location25),

                Arguments.of(Index.fromOneBased(77), location26),
                Arguments.of(Index.fromOneBased(78), location26),

                Arguments.of(Index.fromOneBased(75), location27),
                Arguments.of(Index.fromOneBased(76), location27),

                Arguments.of(Index.fromOneBased(79), location28),
                Arguments.of(Index.fromOneBased(80), location28)
        );
    }
}
