package seedu.address.logic.autocomplete;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

public class AutoCompleteResultTest {

    @Test
    public void constructor_nullValues_doesNotThrow() {
        // since null is used to indicate whether the member is present or not, this should not throw any errors
        assertDoesNotThrow(() -> new AutoCompleteResult(null, null, null));
    }

}
