package seedu.address.model.Parcel.returnorder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RETURN_TIMESTAMP;
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
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Parcel.ReturnOrderContainsKeywordsPredicate;
import seedu.address.testutil.ReturnOrderBuilder;

class ReturnOrderContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ArgumentMultimap argMultimapFirst = new ArgumentMultimap();
        ArgumentMultimap argumentMultimapSecond = new ArgumentMultimap();
        argumentMultimapSecond.put(new Prefix(""), "first second");

        // constructor with no argumentmultimap passed in.
        ReturnOrderContainsKeywordsPredicate firstPredicate =
            new ReturnOrderContainsKeywordsPredicate(firstPredicateKeywordList);
        ReturnOrderContainsKeywordsPredicate secondPredicate =
            new ReturnOrderContainsKeywordsPredicate(secondPredicateKeywordList);

        // constructor with argumentmultimap passed in.
        ReturnOrderContainsKeywordsPredicate secondPredicateWithEmptyArgMultimapNotGeneralSearch =
            new ReturnOrderContainsKeywordsPredicate(secondPredicateKeywordList, argMultimapFirst);
        ReturnOrderContainsKeywordsPredicate thirdPredicate =
            new ReturnOrderContainsKeywordsPredicate(firstPredicateKeywordList, argMultimapFirst);
        ReturnOrderContainsKeywordsPredicate thirdPredicateWithDifferentArgMultimap =
            new ReturnOrderContainsKeywordsPredicate(firstPredicateKeywordList, argumentMultimapSecond);
        ReturnOrderContainsKeywordsPredicate fourthPredicate =
            new ReturnOrderContainsKeywordsPredicate(secondPredicateKeywordList, argumentMultimapSecond);

        // same objects return true.
        assertTrue(firstPredicate.equals(firstPredicate));

        // same value return true.
        assertTrue(firstPredicate.equals(new ReturnOrderContainsKeywordsPredicate(firstPredicateKeywordList)));

        // different types returns false
        assertFalse(firstPredicate.equals(1));

        // null returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        //======================================= Overload constructor ================================================

        // same objects returns true
        assertTrue(thirdPredicate.equals(thirdPredicate));

        // same value return true
        assertTrue(thirdPredicate
            .equals(new ReturnOrderContainsKeywordsPredicate(firstPredicateKeywordList, argMultimapFirst)));

        // different predicate return false
        assertFalse(thirdPredicate.equals(fourthPredicate));

        // different argumentmultimap return false
        assertFalse(thirdPredicate.equals(thirdPredicateWithDifferentArgMultimap));

        // different in search mode return false
        assertFalse(secondPredicate.equals(secondPredicateWithEmptyArgMultimapNotGeneralSearch));
    }

    @ParameterizedTest
    @MethodSource("generaliseParametersGeneralSearchForSuccess")
    public void test_returnOrderContainKeywordsGeneralSearch_returnTrue(ReturnOrderContainsKeywordsPredicate predicate,
                                                                        ReturnOrder returnOrder) {
        assertTrue(predicate.test(returnOrder));
    }

    @ParameterizedTest
    @MethodSource("generaliseParametersGeneralSearchForFailure")
    public void test_returnOrderContainKeywordsGeneralSearch_returnFalse(ReturnOrderContainsKeywordsPredicate predicate,
                                                                         ReturnOrder returnOrder) {
        assertFalse(predicate.test(returnOrder));
    }

    // Test for overloaded search
    @ParameterizedTest
    @MethodSource("generaliseParametersSpecificSearchForSuccess")
    public void test_returnOrderContainKeywordsSpecificSearch_returnTrue(ReturnOrderContainsKeywordsPredicate predicate,
                                                                         ReturnOrder returnOrder) {
        assertTrue(predicate.test(returnOrder));
    }

    @ParameterizedTest
    @MethodSource("generaliseParametersSpecificSearchForFailure")
    public void test_returnOrderDoesNotContainKeywordsSpecificSearch_returnFalse(
        ReturnOrderContainsKeywordsPredicate predicate, ReturnOrder returnOrder) {
        assertFalse(predicate.test(returnOrder));
    }

    /**
     * Used to generate pass test case parameters for general search.
     *
     * @return Stream of valid parameters used for general search.
     */
    private static Stream<Arguments> generaliseParametersGeneralSearchForSuccess() {
        return Stream.of(
            // TID checks
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Collections.singletonList("1234567890")),
                new ReturnOrderBuilder().withTid("1234567890").build()
            ),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("1234567890", "087654321")),
                new ReturnOrderBuilder().withTid("1234567890").build()
            ),
            // Name checks
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Collections.singletonList("Alice")),
                new ReturnOrderBuilder().withName("Alice Bob").build()
            ),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("Bob", "Carol")),
                new ReturnOrderBuilder().withName("Alice Carol").build()
            ),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB")),
                new ReturnOrderBuilder().withName("Alice Bob").build()
            ),
            // Phone checks
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Collections.singletonList("97555838")),
                new ReturnOrderBuilder().withPhone("97555838").build()
            ),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("999", "98765432")),
                new ReturnOrderBuilder().withPhone("999").build()
            ),
            // Address checks
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Collections.singletonList("Geylang")),
                new ReturnOrderBuilder().withAddress("Lorong 10 Geylang st 10").build()
            ),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("Geylang", "Serangoon", "Tampines")),
                new ReturnOrderBuilder().withAddress("Tampines 10").build()
            ),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("cHuAn", "GeYLaNg")),
                new ReturnOrderBuilder().withAddress("geylang lorong").build()
            ),
            // Delivery date and timestamp checks
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Collections.singletonList("2020-05-02")),
                new ReturnOrderBuilder().withTimeStamp("2020-05-02 1500").build()
            ),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("2020-01-01", "2020-05-02")),
                new ReturnOrderBuilder().withTimeStamp("2020-05-02 1500").build()
            ),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("2020-10-01", "1500", "2020-10-02", "1400")),
                new ReturnOrderBuilder().withTimeStamp("2020-05-05 1500").build()
            ),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("2020-05-05", "1300", "2020-10-02", "1400")),
                new ReturnOrderBuilder().withTimeStamp("2020-05-05 1200").build()
            ),
            // Warehouse checks
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(
                    Arrays.asList("5", "Toh", "Guan", "Rd", "E", "#02-30", "S608831")),
                new ReturnOrderBuilder().withWarehouse("5 Toh Guan Rd E, #02-30 S608831").build()
            ),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("5", "Toh", "Guan", "Rd", "E", "Geylang")),
                new ReturnOrderBuilder().withWarehouse("Geylang").build()
            ),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("Yishun", "New", "Town")),
                new ReturnOrderBuilder().withWarehouse("Yishun Old").build()
            ),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("123456", "760844")),
                new ReturnOrderBuilder().withWarehouse("Yishun st 71 blk 777 760844").build()
            ),
            // Comments checks
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("Please", "say", "hi", "to", "me")),
                new ReturnOrderBuilder().withComment("Please say hi to me").build()
            ),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("5", "hi", "S608831", "Put", "in", "at", "my",
                    "shoe", "rack")),
                new ReturnOrderBuilder().withComment("Put in at my shoe rack").build()
            ),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Collections.singletonList("Hi")),
                new ReturnOrderBuilder().withComment("Say hi to me when you're here!").build()
            ),
            // item type checks
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("Plastic")),
                new ReturnOrderBuilder().withItemType("Plastic").build()
            ),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("Gold", "Silver", "Plastic")),
                new ReturnOrderBuilder().withItemType("Gold").build()
            ),
            // email checks
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("khsc96@gmail.com", "example@gmail.com")),
                new ReturnOrderBuilder().withEmail("example@gmail.com").build()
            )
        );
    }

    /**
     * Used to generate fail test case parameters for general search.
     *
     * @return Stream of invalid parameters used for general search.
     */
    private static Stream<Arguments> generaliseParametersGeneralSearchForFailure() {
        return Stream.of(
            // Check no matching keywords TID given
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Collections.emptyList()),
                new ReturnOrderBuilder().build()
            ),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Collections.singletonList("1234567890")),
                new ReturnOrderBuilder().withTid("0123947124d").build()
            ),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("1234abc", "3456def")),
                new ReturnOrderBuilder().withTid("4321cba").build()
            ),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("1234", "abc")),
                new ReturnOrderBuilder().withTid("1234abc").build()
            ),
            // Check no matching keywords name given
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("Carol")),
                new ReturnOrderBuilder().withName("Alice Bob").build()
            ),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("Aeicl", "oBb")),
                new ReturnOrderBuilder().withName("Alice Bob").build()
            ),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("Alic", "Bo")),
                new ReturnOrderBuilder().withName("Alice Bob").build()
            ),
            // check no matching keywords given in phone
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("999")),
                new ReturnOrderBuilder().withPhone("12345678").build()
            ),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("978", "654")),
                new ReturnOrderBuilder().withPhone("879").build()
            ),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("999", "888")),
                new ReturnOrderBuilder().withPhone("99988899").build()
            ),
            // check no matching keywords given in address
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("Yishun", "st", "81")),
                new ReturnOrderBuilder().withAddress("Jurong West 36").build()
            ),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("2020", "1345", "01")),
                new ReturnOrderBuilder().withTimeStamp("2040-02-03 1000").build()
            ),
            // check no matching keywords given in time stamp
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("2020", "1345", "01")),
                new ReturnOrderBuilder().withTimeStamp("2040-02-03 1000").build()
            ),
            // check no matching keywords given in warehouse
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("Toh", "134544", "#05-11")),
                new ReturnOrderBuilder().withWarehouse("Geylang Street 81, #02-30 S608831").build()
            ),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("Toh", "134544", "05-11")),
                new ReturnOrderBuilder().withWarehouse("Geylang Street 81, #05-11 S134544").build()
            ),
            // check no matching keywords given in comments
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("hi")),
                new ReturnOrderBuilder().withComment("bye").build()
            ),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("hiiiiiiiiiiiiiiiiii", "hi")),
                new ReturnOrderBuilder().withComment("hiii").build()
            ),
            // check no matching keywords given in item type
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("Plastic")),
                new ReturnOrderBuilder().withItemType("Gold").build()
            ),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("Go", "ld")),
                new ReturnOrderBuilder().withItemType("Gold").build()
            ),
            // email checks
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("khsc96@gmail.com", "example@gmail.com")),
                new ReturnOrderBuilder().withEmail("eample@gmail.com").build()
            ),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("eam@hotmail.com", "example@gmail.com")),
                new ReturnOrderBuilder().withEmail("eample@gmail.com").build()
            )
        );
    }


    /**
     * Used to generate pass test case parameters for specific search, which utilises the overloaded constructor
     * of {@code OrderContainsKeywordPredicate}.
     *
     * @return Stream of valid parameters used for specific search.
     */
    private static Stream<Arguments> generaliseParametersSpecificSearchForSuccess() {
        // Proxy ArgumentMultimap to test overload constructor
        ArgumentMultimap amTid = new ArgumentMultimap();
        amTid.put(PREFIX_TID, "");
        ArgumentMultimap amPhone = new ArgumentMultimap();
        amPhone.put(PREFIX_PHONE, "");
        ArgumentMultimap amName = new ArgumentMultimap();
        amName.put(PREFIX_NAME, "");
        ArgumentMultimap amAddress = new ArgumentMultimap();
        amAddress.put(PREFIX_ADDRESS, "");
        ArgumentMultimap amRts = new ArgumentMultimap();
        amRts.put(PREFIX_RETURN_TIMESTAMP, "");
        ArgumentMultimap amWarehouse = new ArgumentMultimap();
        amWarehouse.put(PREFIX_WAREHOUSE, "");
        ArgumentMultimap amEmail = new ArgumentMultimap();
        amEmail.put(PREFIX_EMAIL, "");
        ArgumentMultimap amComment = new ArgumentMultimap();
        amComment.put(PREFIX_COMMENT, "");
        ArgumentMultimap amType = new ArgumentMultimap();
        amType.put(PREFIX_TYPE, "");


        return Stream.of(
            // TID checking
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Collections.singletonList("1234567890"), amTid),
                new ReturnOrderBuilder().withTid("1234567890").build()),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("12345", "1023012", "12345abc"), amTid),
                new ReturnOrderBuilder().withTid("12345abc").build()),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Collections.singletonList("12345aBc"), amTid),
                new ReturnOrderBuilder().withTid("12345abc").build()),
            // Phone checking
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Collections.singletonList("12345678"), amPhone),
                new ReturnOrderBuilder().withPhone("12345678").build()),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("12345678", "111111"), amPhone),
                new ReturnOrderBuilder().withPhone("12345678").build()),
            // Name checking
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Collections.singletonList("Alice"), amName),
                new ReturnOrderBuilder().withName("Alice Bob").build()),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"), amName),
                new ReturnOrderBuilder().withName("Alice Bob").build()),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Collections.singletonList("ALiCe"), amName),
                new ReturnOrderBuilder().withName("Alice Bob").build()),
            // Address checking
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Collections.singletonList("Geylang"), amAddress),
                new ReturnOrderBuilder().withAddress("Geylang").build()),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("Geylang", "street"), amAddress),
                new ReturnOrderBuilder().withAddress("Geylang street").build()),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Collections.singletonList("geYlAnG"), amAddress),
                new ReturnOrderBuilder().withAddress("Geylang Street").build()),
            // Timestamp checking
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("2020-10-10", "1500"), amRts),
                new ReturnOrderBuilder().withTimeStamp("2020-10-10 1500").build()),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("2020-05-20", "2020-10-10", "1500"), amRts),
                new ReturnOrderBuilder().withTimeStamp("2020-10-10 1500").build()),
            // Warehouse checking
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Collections.singletonList("Yishun"), amWarehouse),
                new ReturnOrderBuilder().withWarehouse("Yishun Industrial Area Blk 51").build()),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("Yishun", "St", "81"), amWarehouse),
                new ReturnOrderBuilder().withWarehouse("Yishun st 81").build()),
            // Comment checking
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("Please", "say", "hi", "to", "me"), amComment),
                new ReturnOrderBuilder().withComment("Please say hi to me").build()),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("5", "hi", "S608831",
                    "Put", "it", "at", "my", "shoe", "rack"), amComment),
                new ReturnOrderBuilder().withComment("Please say hi to me").build()),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Collections.singletonList("Hi"), amComment),
                new ReturnOrderBuilder().withComment("Say hi to me when you're here!").build()),
            // Item type checking
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Collections.singletonList("Plastic"), amType),
                new ReturnOrderBuilder().withItemType("Plastic").build()),
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("Gold", "Silver", "Plastic"), amType),
                new ReturnOrderBuilder().withItemType("Gold").build()
            ),
            // email checks
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("khsc96@gmail.com", "example@gmail.com"),
                    amEmail),
                new ReturnOrderBuilder().withEmail("example@gmail.com").build()
            )
        );
    }

    /**
     * Used to generate fail test case parameters for specific search, which utilises the overloaded constructor
     * of {@code OrderContainsKeywordPredicate}.
     *
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
                new ReturnOrderContainsKeywordsPredicate(Collections.singletonList("1234567890"), am1),
                new ReturnOrderBuilder().withTid("1234567890").build()),
            // Name checking not in given prefix
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Collections.singletonList("Alice"), am1),
                new ReturnOrderBuilder().withName("Alice Bob").build()),
            // Phone checking not in given prefix
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Collections.singletonList("12345678"), am1),
                new ReturnOrderBuilder().withPhone("12345678").build()),
            // Address checking not in given prefix
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Collections.singletonList("Geylang"), am2),
                new ReturnOrderBuilder().withAddress("Geylang").build()),
            // Warehouse checking not in given prefix
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Collections.singletonList("Geylang"), am2),
                new ReturnOrderBuilder().withWarehouse("Geylang").build()),
            // Comment checking not in given prefix
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Collections.singletonList("hi"), am2),
                new ReturnOrderBuilder().withComment("hi").build()),
            // Item type cehcking not in given prefix
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Collections.singletonList("Plastic"), am2),
                new ReturnOrderBuilder().withItemType("Plastic").build()),
            // Delivery time stamp not in given prefix
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("2020-10-02", "1500"), am2),
                new ReturnOrderBuilder().withTimeStamp("2020-10-02 1500").build()),
            // Email not given in prefix
            Arguments.of(
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("eam@hotmail.com", "example@gmail.com"), am2),
                new ReturnOrderBuilder().withEmail("example@gmail.com").build()
            )
        );
    }
}
