package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

class DistrictInfoTest {

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
