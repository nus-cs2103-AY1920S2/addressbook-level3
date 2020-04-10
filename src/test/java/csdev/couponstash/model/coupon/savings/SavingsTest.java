package csdev.couponstash.model.coupon.savings;

import static csdev.couponstash.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for Savings, this also tests the
 * integration between MonetaryAmount, PercentageAmount
 * Saveables and Savings.
 */
public class SavingsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Savings((MonetaryAmount) null));
        assertThrows(NullPointerException.class, () -> new Savings((MonetaryAmount) null, null));
    }

    @Test
    public void isValidSaveablesList() {
        assertThrows(NullPointerException.class, () -> Savings.isValidSaveablesList(null)); // null
        assertFalse(Savings.isValidSaveablesList(new ArrayList<Saveable>())); // empty list

        // valid saveables list
        assertTrue(Savings.isValidSaveablesList(Arrays.asList(new Saveable("Hello"), new Saveable("World"))));
    }

    @Test
    public void condenseSaveablesList_duplicateSaveables_addsDuplicatesTogether() {
        List<Saveable> saveablesList = Arrays.asList(new Saveable("Planet"), new Saveable("Galaxy"),
                new Saveable("Planet"), new Saveable("Galaxy"), new Saveable("Planet"));
        List<Saveable> resultList = Savings.condenseSaveablesList(saveablesList);
        List<Saveable> expectedList = Arrays.asList(new Saveable("Galaxy", 2), new Saveable("Planet", 3));
        resultList.sort(Saveable::compareTo);
        expectedList.sort(Saveable::compareTo);
        assertEquals(resultList, expectedList);
    }

    @Test
    public void condenseSaveablesList_distinctSaveables_returnsSameItems() {
        List<Saveable> saveablesList = Arrays.asList(new Saveable("Mercury"), new Saveable("Venus"),
                new Saveable("Earth"), new Saveable("Mars"), new Saveable("Jupiter"));
        List<Saveable> clonedList = new ArrayList<Saveable>(saveablesList);
        List<Saveable> resultList = Savings.condenseSaveablesList(saveablesList);
        resultList.sort(Saveable::compareTo);
        clonedList.sort(Saveable::compareTo);
        assertEquals(resultList, clonedList);
    }

    @Test
    public void copy_validSavings_copiesAreEqual() {
        Savings savings = new Savings(
                new PercentageAmount(50),
                Arrays.asList(new Saveable("Saturn", 5), new Saveable("Uranus", 7), new Saveable("Neptune"))
        );
        assertEquals(savings.copy(), savings);
    }

    @Test
    public void equals_identicalApartFromSaveablesList_areNotEqual() {
        Savings savings1 = new Savings(
                Arrays.asList(new Saveable("Ceres", 12), new Saveable("Pluto", 3), new Saveable("Eris"),
                        new Saveable("Makemake", 2), new Saveable("Haumea", 10))
        );
        // savings2 is a subset of savings1
        Savings savings2 = new Savings(
                Arrays.asList(new Saveable("Ceres", 12), new Saveable("Pluto", 3), new Saveable("Eris"))
        );
        assertNotEquals(savings1, savings2);
        assertNotEquals(savings2, savings1);
    }
}
