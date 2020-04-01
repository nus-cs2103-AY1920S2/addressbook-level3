package seedu.address.model.parcel.order;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY_TIMESTAMP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WAREHOUSE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.model.parcel.OrderContainsKeywordsPredicate;
import seedu.address.testutil.OrderBuilder;

public class OrderContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        OrderContainsKeywordsPredicate firstPredicate = new OrderContainsKeywordsPredicate(firstPredicateKeywordList);
        OrderContainsKeywordsPredicate secondPredicate = new OrderContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        OrderContainsKeywordsPredicate firstPredicateCopy =
            new OrderContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @ParameterizedTest
    @MethodSource("generaliseParametersGeneralSearchForSuccess")
    public void test_ordersContainKeywordsGeneralSearch_returnTrue(OrderContainsKeywordsPredicate predicate,
                                                                   Order order) {
        assertTrue(predicate.test(order));
    }

    @ParameterizedTest
    @MethodSource("generaliseParametersGeneralSearchForFailure")
    public void test_ordersContainKeywordsGeneralSearch_returnFalse(OrderContainsKeywordsPredicate predicate,
                                                                   Order order) {
        assertFalse(predicate.test(order));
    }

    // Test for overloaded search
    @ParameterizedTest
    @MethodSource("generaliseParametersSpecificSearchForSuccess")
    public void test_ordersContainKeywordsSpecificSearch_returnTrue(OrderContainsKeywordsPredicate predicate,
                                                                    Order order) {
        assertTrue(predicate.test(order));
    }

    @ParameterizedTest
    @MethodSource("generaliseParametersSpecificSearchForFailure")
    public void test_ordersDoesNotContainKeywordsSpecificSearch_returnFalse(OrderContainsKeywordsPredicate predicate,
                                                                           Order order) {
        assertFalse(predicate.test(order));
    }

    /**
     * Used to generate pass test case parameters for general search.
     * @return Stream of valid parameters used for general search.
     */
    private static Stream<Arguments> generaliseParametersGeneralSearchForSuccess() {
        return Stream.of(
            // TID checks
            Arguments.of(
                new OrderContainsKeywordsPredicate(Collections.singletonList("1234567890")),
                new OrderBuilder().withTid("1234567890").build()
            ),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("1234567890", "087654321")),
                new OrderBuilder().withTid("1234567890").build()
            ),
            // Name checks
            Arguments.of(
                new OrderContainsKeywordsPredicate(Collections.singletonList("Alice")),
                new OrderBuilder().withName("Alice Bob").build()
            ),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("Bob", "Carol")),
                new OrderBuilder().withName("Alice Carol").build()
            ),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB")),
                new OrderBuilder().withName("Alice Bob").build()
            ),
            // Phone checks
            Arguments.of(
                new OrderContainsKeywordsPredicate(Collections.singletonList("97555838")),
                new OrderBuilder().withPhone("97555838").build()
            ),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("999", "98765432")),
                new OrderBuilder().withPhone("999").build()
            ),
            // Address checks
            Arguments.of(
                new OrderContainsKeywordsPredicate(Collections.singletonList("Geylang")),
                new OrderBuilder().withAddress("Lorong 10 Geylang st 10").build()
            ),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("Geylang", "Serangoon", "Tampines")),
                new OrderBuilder().withAddress("Tampines 10").build()
            ),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("cHuAn", "GeYLaNg")),
                new OrderBuilder().withAddress("geylang lorong").build()
            ),
            // Delivery date and timestamp checks
            Arguments.of(
                new OrderContainsKeywordsPredicate(Collections.singletonList("2020-05-02")),
                new OrderBuilder().withTimeStamp("2020-05-02 1500").build()
            ),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("2020-01-01", "2020-05-02")),
                new OrderBuilder().withTimeStamp("2020-05-02 1500").build()
            ),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("2020-10-01", "1500", "2020-10-02", "1400")),
                new OrderBuilder().withTimeStamp("2020-05-05 1500").build()
            ),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("2020-05-05", "1300", "2020-10-02", "1400")),
                new OrderBuilder().withTimeStamp("2020-05-05 1200").build()
            ),
            // Warehouse checks
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("5", "Toh", "Guan", "Rd", "E", "#02-30", "S608831")),
                new OrderBuilder().withWarehouse("5 Toh Guan Rd E, #02-30 S608831").build()
            ),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("5", "Toh", "Guan", "Rd", "E", "Geylang")),
                new OrderBuilder().withWarehouse("Geylang").build()
            ),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("Yishun", "New", "Town")),
                new OrderBuilder().withWarehouse("Yishun Old").build()
            ),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("123456", "760844")),
                new OrderBuilder().withWarehouse("Yishun st 71 blk 777 760844").build()
            ),
            // Cash checks
            Arguments.of(
                new OrderContainsKeywordsPredicate(Collections.singletonList("$5")),
                new OrderBuilder().withCash("$5").build()
            ),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("$5", "$3.50", "$0.10", "$10")),
                new OrderBuilder().withCash("$10").build()
            ),
            // Comments checks
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("Please", "say", "hi", "to", "me")),
                new OrderBuilder().withComment("Please say hi to me").build()
            ),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("5", "hi", "S608831", "Put", "in", "at", "my",
                    "shoe", "rack")),
                new OrderBuilder().withComment("Put in at my shoe rack").build()
            ),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Collections.singletonList("Hi")),
                new OrderBuilder().withComment("Say hi to me when you're here!").build()
            ),
            // item type checks
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("Plastic")),
                new OrderBuilder().withItemType("Plastic").build()
            ),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("Gold", "Silver", "Plastic")),
                new OrderBuilder().withItemType("Gold").build()
            ),
            // email checks
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("e123@gmail.com", "example@gmail.com")),
                new OrderBuilder().withEmail("example@gmail.com").build()
            )
        );
    }

    /**
     * Used to generate fail test case parameters for general search.
     * @return Stream of invalid parameters used for general search.
     */
    private static Stream<Arguments> generaliseParametersGeneralSearchForFailure() {
        return Stream.of(
            // Check no matching keywords TID given
            Arguments.of(
                new OrderContainsKeywordsPredicate(Collections.emptyList()),
                new OrderBuilder().build()
            ),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Collections.singletonList("1234567890")),
                new OrderBuilder().withTid("0123947124d").build()
            ),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("1234abc", "3456def")),
                new OrderBuilder().withTid("4321cba").build()
            ),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("1234", "abc")),
                new OrderBuilder().withTid("1234abc").build()
            ),
            // Check no matching keywords name given
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("Carol")),
                new OrderBuilder().withName("Alice Bob").build()
            ),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("Aeicl", "oBb")),
                new OrderBuilder().withName("Alice Bob").build()
            ),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("Alic", "Bo")),
                new OrderBuilder().withName("Alice Bob").build()
            ),
            // check no matching keywords given in phone
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("999")),
                new OrderBuilder().withPhone("12345678").build()
            ),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("978", "654")),
                new OrderBuilder().withPhone("879").build()
            ),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("999", "888")),
                new OrderBuilder().withPhone("99988899").build()
            ),
            // check no matching keywords given in address
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("Yishun", "st", "81")),
                new OrderBuilder().withAddress("Jurong West 36").build()
            ),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("2020", "1345", "01")),
                new OrderBuilder().withTimeStamp("2040-02-03 1000").build()
            ),
            // check no matching keywords given in time stamp
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("2020", "1345", "01")),
                new OrderBuilder().withTimeStamp("2040-02-03 1000").build()
            ),
            // check no matching keywords given in warehouse
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("Toh", "134544", "#05-11")),
                new OrderBuilder().withWarehouse("Geylang Street 81, #02-30 S608831").build()
            ),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("Toh", "134544", "05-11")),
                new OrderBuilder().withWarehouse("Geylang Street 81, #05-11 S134544").build()
            ),
            // check no matching keywords given in COD
            Arguments.of(
                new OrderContainsKeywordsPredicate(Collections.singletonList("$4")),
                new OrderBuilder().withCash("$0.4").build()
            ),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("$44", "$4.0")),
                new OrderBuilder().withCash("$4").build()
            ),
            // check no matching keywords given in comments
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("hi")),
                new OrderBuilder().withComment("bye").build()
            ),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("hiiiiiiiiiiiiiiiiii", "hi")),
                new OrderBuilder().withComment("hiii").build()
            ),
            // check no matching keywords given in item type
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("Plastic")),
                new OrderBuilder().withItemType("Gold").build()
            ),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("Go", "ld")),
                new OrderBuilder().withItemType("Gold").build()
            ),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("e123@gmail.com", "example@hotmail.com")),
                new OrderBuilder().withEmail("example@gmail.com").build()
            ),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("e123@gmail.com", "example@gmail.com")),
                new OrderBuilder().withEmail("examle@gmail.com").build()
            )
        );
    }


    /**
     * Used to generate pass test case parameters for specific search, which utilises the overloaded constructor
     * of {@code OrderContainsKeywordPredicate}.
     * @return Stream of valid parameters used for specific search.
     */
    private static Stream<Arguments> generaliseParametersSpecificSearchForSuccess() {
        // Proxy ArgumentMultimap to test overload constructor
        ArgumentMultimap am1 = new ArgumentMultimap();
        am1.put(PREFIX_TID, "");
        ArgumentMultimap am2 = new ArgumentMultimap();
        am2.put(PREFIX_PHONE, "");
        ArgumentMultimap am3 = new ArgumentMultimap();
        am3.put(PREFIX_NAME, "");
        ArgumentMultimap am4 = new ArgumentMultimap();
        am4.put(PREFIX_ADDRESS, "");
        ArgumentMultimap am5 = new ArgumentMultimap();
        am5.put(PREFIX_DELIVERY_TIMESTAMP, "");
        ArgumentMultimap am6 = new ArgumentMultimap();
        am6.put(PREFIX_WAREHOUSE, "");
        ArgumentMultimap am7 = new ArgumentMultimap();
        am7.put(PREFIX_COD, "");
        ArgumentMultimap am8 = new ArgumentMultimap();
        am8.put(PREFIX_COMMENT, "");
        ArgumentMultimap am9 = new ArgumentMultimap();
        am9.put(PREFIX_TYPE, "");
        ArgumentMultimap am10 = new ArgumentMultimap();
        am10.put(PREFIX_EMAIL, "");

        return Stream.of(
            // TID checking
            Arguments.of(
                new OrderContainsKeywordsPredicate(Collections.singletonList("1234567890"),
                    am1),
                new OrderBuilder().withTid("1234567890").build()),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("12345", "1023012", "12345abc"),
                    am1),
                new OrderBuilder().withTid("12345abc").build()),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Collections.singletonList("12345aBc"),
                    am1),
                new OrderBuilder().withTid("12345abc").build()),
            // Phone checking
            Arguments.of(
                new OrderContainsKeywordsPredicate(Collections.singletonList("12345678"),
                    am2),
                new OrderBuilder().withPhone("12345678").build()),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("12345678", "111111"),
                    am2),
                new OrderBuilder().withPhone("12345678").build()),
            // Name checking
            Arguments.of(
                new OrderContainsKeywordsPredicate(Collections.singletonList("Alice"),
                    am3),
                new OrderBuilder().withName("Alice Bob").build()),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"),
                    am3),
                new OrderBuilder().withName("Alice Bob").build()),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Collections.singletonList("ALiCe"),
                    am3),
                new OrderBuilder().withName("Alice Bob").build()),
            // Address checking
            Arguments.of(
                new OrderContainsKeywordsPredicate(Collections.singletonList("Geylang"),
                    am4),
                new OrderBuilder().withAddress("Geylang").build()),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("Geylang", "street"),
                    am4),
                new OrderBuilder().withAddress("Geylang street").build()),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Collections.singletonList("geYlAnG"),
                    am4),
                new OrderBuilder().withAddress("Geylang Street").build()),
            // Timestamp checking
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("2020-10-10", "1500"),
                    am5),
                new OrderBuilder().withTimeStamp("2020-10-10 1500").build()),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("2020-02-20", "2020-10-10", "1500"),
                    am5),
                new OrderBuilder().withTimeStamp("2020-10-10 1500").build()),
            // Warehouse checking
            Arguments.of(
                new OrderContainsKeywordsPredicate(Collections.singletonList("Yishun"),
                    am6),
                new OrderBuilder().withWarehouse("Yishun Industrial Area Blk 51").build()),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("Yishun", "St", "81"),
                    am6),
                new OrderBuilder().withWarehouse("Yishun st 81").build()),
            // Cash on delivery checking
            Arguments.of(
                new OrderContainsKeywordsPredicate(Collections.singletonList("$5"),
                    am7),
                new OrderBuilder().withCash("$5").build()),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("$5", "$3.50", "$0.10", "$10"),
                    am7),
                new OrderBuilder().withCash("$10").build()),
            // Comment checking
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("Please", "say", "hi", "to", "me"),
                    am8),
                new OrderBuilder().withComment("Please say hi to me").build()),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("5", "hi", "S608831",
                    "Put", "it", "at", "my", "shoe", "rack"), am8),
                new OrderBuilder().withComment("Please say hi to me").build()),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Collections.singletonList("Hi"), am8),
                new OrderBuilder().withComment("Say hi to me when you're here!").build()),
            // Item type checking
            Arguments.of(
                new OrderContainsKeywordsPredicate(Collections.singletonList("Plastic"), am9),
                new OrderBuilder().withItemType("Plastic").build()),
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("Gold", "Silver", "Plastic"), am9),
                new OrderBuilder().withItemType("Gold").build()),
            // Email checks
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("e123@gmail.com", "example@gmail.com"), am10),
                new OrderBuilder().withEmail("example@gmail.com").build()
            )
        );
    }

    /**
     * Used to generate fail test case parameters for specific search, which utilises the overloaded constructor
     * of {@code OrderContainsKeywordPredicate}.
     * @return Stream of invalid parameters used for specific search.
     */
    private static Stream<Arguments> generaliseParametersSpecificSearchForFailure() {
        ArgumentMultimap am1 = new ArgumentMultimap();
        am1.put(PREFIX_ADDRESS, "");
        ArgumentMultimap am2 = new ArgumentMultimap();
        am2.put(PREFIX_NAME, "");
        return Stream.of(
            // Tid checking not in given prefix
            Arguments.of(
                new OrderContainsKeywordsPredicate(Collections.singletonList("1234567890"), am1),
                new OrderBuilder().withTid("1234567890").build()),
            // Name checking not in given prefix
            Arguments.of(
                new OrderContainsKeywordsPredicate(Collections.singletonList("Alice"), am1),
                new OrderBuilder().withName("Alice Bob").build()),
            // Phone checking not in given prefix
            Arguments.of(
                new OrderContainsKeywordsPredicate(Collections.singletonList("12345678"), am1),
                new OrderBuilder().withPhone("12345678").build()),
            // Address checking not in given prefix
            Arguments.of(
                new OrderContainsKeywordsPredicate(Collections.singletonList("Geylang"), am2),
                new OrderBuilder().withAddress("Geylang").build()),
            // Warehouse checking not in given prefix
            Arguments.of(
                new OrderContainsKeywordsPredicate(Collections.singletonList("Geylang"), am2),
                new OrderBuilder().withWarehouse("Geylang").build()),
            // COD checking not in given prefix
            Arguments.of(
                new OrderContainsKeywordsPredicate(Collections.singletonList("$4"), am2),
                new OrderBuilder().withCash("$4").build()),
            // Comment checking not in given prefix
            Arguments.of(
                new OrderContainsKeywordsPredicate(Collections.singletonList("hi"), am2),
                new OrderBuilder().withComment("hi").build()),
            // Item type cehcking not in given prefix
            Arguments.of(
                new OrderContainsKeywordsPredicate(Collections.singletonList("Plastic"), am2),
                new OrderBuilder().withItemType("Plastic").build()),
            // Delivery time stamp not in given prefix
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("2020-10-02", "1500"), am2),
                new OrderBuilder().withTimeStamp("2020-10-02 1500").build()),
            // email checks
            Arguments.of(
                new OrderContainsKeywordsPredicate(Arrays.asList("e123@gmail.com", "example@hotmail.com"), am2),
                new OrderBuilder().withEmail("example@hotmail.com").build()
            )
        );
    }
}
