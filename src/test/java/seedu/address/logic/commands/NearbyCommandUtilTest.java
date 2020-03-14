package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    /**
     * Used to generate location information for sectors 1,2,3,4,5,6.
     *
     * @return Stream of arguments containing postal sector and location information
     */
    private static Stream<Arguments> sectorOneToSix() {
        String location = "Raffles Place, Cecil, Marina, People’s Park";
        return Stream.of(
                Arguments.of(Index.fromOneBased(1), location),
                Arguments.of(Index.fromOneBased(2), location),
                Arguments.of(Index.fromOneBased(3), location),
                Arguments.of(Index.fromOneBased(4), location),
                Arguments.of(Index.fromOneBased(5), location),
                Arguments.of(Index.fromOneBased(6), location)
        );
    }

    @ParameterizedTest
    @MethodSource("sectorOneToSix")
    void getGeneralLocation_validPostalSectorOneToSix_returnsGeneralLocation(Index postalSector,
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
}
