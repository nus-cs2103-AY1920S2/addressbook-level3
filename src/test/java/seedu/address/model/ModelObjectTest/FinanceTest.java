package seedu.address.model.ModelObjectTest;

import org.junit.jupiter.api.Test;
import seedu.address.model.modelFinance.Finance;
import seedu.address.model.modelObjectTags.Amount;
import seedu.address.model.modelObjectTags.Date;
import seedu.address.model.modelObjectTags.FinanceType;
import seedu.address.model.modelObjectTags.Name;
import seedu.address.model.tag.Tag;

import java.util.HashSet;
import java.util.Set;

import static seedu.address.testutil.Assert.assertThrows;

public class FinanceTest {
    public static Name validName = new Name("Test Name");
    public static FinanceType validFinanceType = new FinanceType("m");
    public static Date validDate = new Date("2020-04-13");
    public static Amount validAmount = new Amount("2000");
    public static Set<Tag> validTags = new HashSet<Tag>();

    @Test
    public void isValidFinance() {
        // null Finance
        assertThrows(NullPointerException.class,
                () -> new Finance(null, validFinanceType, validDate, validAmount, validTags));
        // null FinanceType
        assertThrows(NullPointerException.class,
                () -> new Finance(validName, null, validDate, validAmount, validTags));
        // null Date
        assertThrows(NullPointerException.class,
                () -> new Finance(validName, validFinanceType, null, validAmount, validTags));
        // null Amount
        assertThrows(NullPointerException.class,
                () -> new Finance(validName, validFinanceType, validDate, null, validTags));
        // null Tags
        assertThrows(NullPointerException.class,
                () -> new Finance(validName, validFinanceType, validDate, validAmount, null));
    }
}
