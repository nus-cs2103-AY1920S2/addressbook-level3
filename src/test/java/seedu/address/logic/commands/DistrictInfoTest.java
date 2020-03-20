package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DistrictInfoTest {
    /**
     * Used to generate invalid arguments for method {@code isValidArea}.
     *
     * @return Stream of Arguments containing invalid input
     */
    private static Stream<Arguments> invalidSearchArgs() {
        return Stream.of(
                Arguments.of("a"),
                Arguments.of(" "));
    }

    /**
     * Used to generate valid arguments for method {@code isValidArea}.
     *
     * @return Stream of Arguments containing valid input
     */
    private static Stream<Arguments> validSearchArgs() {
        return Stream.of(
                Arguments.of("central"),
                Arguments.of("CENTRAL"),
                Arguments.of("east"),
                Arguments.of("EAST"),
                Arguments.of("north-east"),
                Arguments.of("NORTH-EAST"),
                Arguments.of("west"),
                Arguments.of("WEST"),
                Arguments.of("north"),
                Arguments.of("NORTH"));
    }

    @Test
    void isValidArea_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> DistrictInfo.isValidArea(null));
    }

    @ParameterizedTest
    @MethodSource("invalidSearchArgs")
    void isValidArea_invalid_returnsFalse(String args) {
        assertFalse(DistrictInfo.isValidArea(args));
    }

    @ParameterizedTest
    @MethodSource("validSearchArgs")
    void isValidArea_valid_returnsTrue(String args) {
        assertTrue(DistrictInfo.isValidArea(args));
    }

    @Test
    void sameAreaRegex_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> DistrictInfo.sameAreaRegex(null));
    }

    @Test
    void sameAreaRegex_invalidSearchTerm_returnsEmptyList() {
        String searchTerm = "La La Land";
        List<String> output = DistrictInfo.sameAreaRegex(searchTerm);
        assertEquals(0, output.size());
    }
}
