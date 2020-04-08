package csdev.couponstash.model.coupon.savings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for DateSavingsSumMap.
 */
public class DateSavingsSumMapTest {

    @Test
    public void add_existingEntriesInMap_notOverwritten() {
        // initial entries
        DateSavingsSumMap originalDssm = new DateSavingsSumMap();
        originalDssm.put(LocalDate.of(2003, 8, 8),
                new PureMonetarySavings(new MonetaryAmount(80, 65),
                        Arrays.asList(new Saveable("Havarti", 5), new Saveable("Brie", 9))));
        originalDssm.put(LocalDate.of(2006, 5, 18),
                new PureMonetarySavings(new MonetaryAmount(91, 25)));
        originalDssm.put(LocalDate.of(2025, 10, 29),
                new PureMonetarySavings(new MonetaryAmount(11, 35),
                        Arrays.asList(new Saveable("Mozzarella"), new Saveable("Gouda"))));
        originalDssm.put(LocalDate.of(2033, 8, 25),
                new PureMonetarySavings(new MonetaryAmount(34, 68)));

        // add new entries
        originalDssm.add(LocalDate.of(2033, 8, 25),
                new PureMonetarySavings(new MonetaryAmount(89, 28)));
        originalDssm.add(LocalDate.of(2036, 9, 14),
                new PureMonetarySavings(new MonetaryAmount(27, 71)));
        originalDssm.add(LocalDate.of(2003, 8, 8),
                new PureMonetarySavings(new MonetaryAmount(57, 83),
                        Arrays.asList(new Saveable("Havarti", 12), new Saveable("Cheddar", 3))));
        originalDssm.add(LocalDate.of(2083, 1, 15),
                new PureMonetarySavings(new MonetaryAmount(5, 0)));

        // expected result after addition
        DateSavingsSumMap expectedDssm = new DateSavingsSumMap();
        expectedDssm.put(LocalDate.of(2036, 9, 14),
                new PureMonetarySavings(new MonetaryAmount(27, 71)));
        expectedDssm.put(LocalDate.of(2083, 1, 15),
                new PureMonetarySavings(new MonetaryAmount(5, 0)));
        expectedDssm.put(LocalDate.of(2033, 8, 25),
                new PureMonetarySavings(new MonetaryAmount(123, 96)));
        expectedDssm.put(LocalDate.of(2006, 5, 18),
                new PureMonetarySavings(new MonetaryAmount(91, 25)));
        expectedDssm.put(LocalDate.of(2003, 8, 8),
                new PureMonetarySavings(new MonetaryAmount(138, 48),
                        Arrays.asList(new Saveable("Havarti", 17), new Saveable("Brie", 9),
                                new Saveable("Cheddar", 3))));
        expectedDssm.put(LocalDate.of(2025, 10, 29),
                new PureMonetarySavings(new MonetaryAmount(11, 35),
                        Arrays.asList(new Saveable("Mozzarella"), new Saveable("Gouda"))));

        assertEquals(originalDssm, expectedDssm);
    }
}
