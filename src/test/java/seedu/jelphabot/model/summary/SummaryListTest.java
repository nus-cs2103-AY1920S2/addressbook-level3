//@@author eedenong
package seedu.jelphabot.model.summary;

import static seedu.jelphabot.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SummaryListTest {

    @Test
    public void addSummary_nullArgument_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SummaryList().addSummary(null));
    }

    @Test
    public void setSummary_nullArgument_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SummaryList().setSummary(null));
    }
}
