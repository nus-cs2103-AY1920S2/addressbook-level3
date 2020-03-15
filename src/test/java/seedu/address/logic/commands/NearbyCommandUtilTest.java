package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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

    /**
     * Used to generate location information for all sectors
     *
     * @return Stream of arguments containing postal sector and location information
     */
    private static Stream<Arguments> sectorGeneralLocation() {
        String location1 = "Raffles Place, Cecil, Marina, People’s Park";
        String location2 = "Anson, Tanjong Pagar";
        String location3 = "Queenstown, Tiong Bahru";
        String location4 = "Telok Blangah, Harbourfront";
        String location5 = "Pasir Panjang, Hong Leong Garden, Clementi New Town";
        String location6 = "High Street, Beach Road (part)";
        String location7 = "Middle Road, Golden Mile";
        String location8 = "Little India";
        String location9 = "Orchard, Cairnhill, River Valley";
        String location10 = "Ardmore, Bukit Timah, Holland Road, Tanglin";
        String location11 = "Watten Estate, Novena, Thomson";
        String location12 = "Balestier, Toa Payoh, Serangoon";
        String location13 = "Macpherson, Braddell";
        String location14 = "Geylang, Eunos";
        String location15 = "Katong, Joo Chiat, Amber Road";
        String location16 = "Bedok, Upper East Coast, Eastwood, Kew Drive";
        String location17 = "Loyang, Changi";
        String location18 = "Tampines, Pasir Ris";
        String location19 = "Serangoon Garden, Hougang, Punggol";
        String location20 = "Bishan, Ang Mo Kio";
        String location21 = "Upper Bukit Timah, Clementi Park, Ulu Pandan";
        String location22 = "Jurong";
        String location23 = "Hillview, Dairy Farm, Bukit Panjang, Choa Chu Kang";
        String location24 = "Lim Chu Kang, Tengah";
        String location25 = "Kranji, Woodgrove";
        String location26 = "Upper Thomson, Springleaf";
        String location27 = "Yishun, Sembawang";
        String location28 = "Seletar";

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
}
