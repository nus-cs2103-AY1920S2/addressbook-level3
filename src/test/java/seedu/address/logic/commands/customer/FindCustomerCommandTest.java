package seedu.address.logic.commands.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.customer.TypicalCustomers.ALICE;
import static seedu.address.testutil.customer.TypicalCustomers.BENSON;
import static seedu.address.testutil.customer.TypicalCustomers.CARL;
import static seedu.address.testutil.customer.TypicalCustomers.DANIEL;
import static seedu.address.testutil.customer.TypicalCustomers.ELLE;
import static seedu.address.testutil.customer.TypicalCustomers.FIONA;
import static seedu.address.testutil.customer.TypicalCustomers.GEORGE;
import static seedu.address.testutil.customer.TypicalCustomers.getTypicalInventorySystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.customer.AddressContainsKeywordsPredicate;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.EmailContainsKeywordsPredicate;
import seedu.address.model.customer.JointCustomerPredicate;
import seedu.address.model.customer.Name;
import seedu.address.model.customer.NameContainsKeywordsPredicate;
import seedu.address.model.customer.PhoneContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCustomerCommand}.
 */
public class FindCustomerCommandTest {
    private Model model = new ModelManager(getTypicalInventorySystem(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalInventorySystem(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCustomerCommand findFirstCommand = new FindCustomerCommand(firstPredicate);
        FindCustomerCommand findSecondCommand = new FindCustomerCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCustomerCommand findFirstCommandCopy = new FindCustomerCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different customer -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noCustomerFound() {
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCustomerCommand command = new FindCustomerCommand(predicate);
        expectedModel.updateFilteredCustomerList(predicate);
        assertEquals(Collections.emptyList(), expectedModel.getFilteredCustomerList());
    }

    @Test
    public void execute_oneAddressKeyword_noCustomerFound() {
        String expectedMessage = "No customers staying in the area serangoon found!";
        AddressContainsKeywordsPredicate predicate = prepareAddressPredicate("serangoon");
        FindCustomerCommand command = new FindCustomerCommand(predicate);
        expectedModel.updateFilteredCustomerList(predicate);
        assertEquals(Collections.emptyList(), expectedModel.getFilteredCustomerList());
    }

    @Test
    public void execute_oneAddressKeyword_multipleCustomersFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        AddressContainsKeywordsPredicate predicate = prepareAddressPredicate("street");
        FindCustomerCommand command = new FindCustomerCommand(predicate);
        expectedModel.updateFilteredCustomerList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, DANIEL, GEORGE), model.getFilteredCustomerList());
    }

    @Test
    public void execute_multipleAddressKeywords_oneCustomerFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        AddressContainsKeywordsPredicate predicate = prepareAddressPredicate("jurong bugis changi");
        FindCustomerCommand command = new FindCustomerCommand(predicate);
        expectedModel.updateFilteredCustomerList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), expectedModel.getFilteredCustomerList());
    }

    @Test
    public void execute_multipleAddressKeywords_multipleCustomersFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        AddressContainsKeywordsPredicate predicate = prepareAddressPredicate("jurong clementi tokyo");
        FindCustomerCommand command = new FindCustomerCommand(predicate);
        expectedModel.updateFilteredCustomerList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, FIONA), expectedModel.getFilteredCustomerList());
    }

    @Test
    public void execute_oneNameKeyword_oneCustomerFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        NameContainsKeywordsPredicate predicate = preparePredicate("george");
        FindCustomerCommand command = new FindCustomerCommand(predicate);
        expectedModel.updateFilteredCustomerList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(GEORGE), expectedModel.getFilteredCustomerList());
    }

    @Test
    public void execute_oneNameKeyword_multipleCustomersFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        NameContainsKeywordsPredicate predicate = preparePredicate("Meier");
        FindCustomerCommand command = new FindCustomerCommand(predicate);
        expectedModel.updateFilteredCustomerList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, DANIEL), expectedModel.getFilteredCustomerList());
    }

    @Test
    public void execute_multipleNameKeywords_oneCustomerFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        NameContainsKeywordsPredicate predicate = preparePredicate("george alex peter");
        FindCustomerCommand command = new FindCustomerCommand(predicate);
        expectedModel.updateFilteredCustomerList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(GEORGE), expectedModel.getFilteredCustomerList());
    }

    @Test
    public void execute_multipleNameKeywords_multipleCustomersFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindCustomerCommand command = new FindCustomerCommand(predicate);
        expectedModel.updateFilteredCustomerList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), expectedModel.getFilteredCustomerList());
    }

    @Test
    public void execute_oneEmailKeyword_noCustomerFound() {
        String expectedMessage = "No customers with email jack@example.com found!";
        EmailContainsKeywordsPredicate predicate = prepareEmailPredicate("jack@example.com");
        FindCustomerCommand command = new FindCustomerCommand(predicate);
        expectedModel.updateFilteredCustomerList(predicate);
        assertEquals(Collections.emptyList(), expectedModel.getFilteredCustomerList());
    }

    @Test
    public void execute_oneEmailKeyword_oneCustomerFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        EmailContainsKeywordsPredicate predicate = prepareEmailPredicate("heinz@example.com");
        FindCustomerCommand command = new FindCustomerCommand(predicate);
        expectedModel.updateFilteredCustomerList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL), expectedModel.getFilteredCustomerList());
    }

    @Test
    public void execute_onePhoneKeyword_noCustomerFound() {
        String expectedMessage = "No customers with phone number 81232712 found!";
        PhoneContainsKeywordsPredicate predicate = preparePhonePredicate("81232712");
        FindCustomerCommand command = new FindCustomerCommand(predicate);
        expectedModel.updateFilteredCustomerList(predicate);
        assertEquals(Collections.emptyList(), expectedModel.getFilteredCustomerList());
    }

    @Test
    public void execute_onePhoneKeyword_oneCustomerFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        PhoneContainsKeywordsPredicate predicate = preparePhonePredicate("87652533");
        FindCustomerCommand command = new FindCustomerCommand(predicate);
        expectedModel.updateFilteredCustomerList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DANIEL), expectedModel.getFilteredCustomerList());
    }

    @Test
    public void execute_oneNameAndAddressKeyword_noCustomerFound() {
        List<Predicate<Customer>> predicates = new ArrayList<>();
        predicates.add(preparePredicate("paul"));
        predicates.add(prepareAddressPredicate("bugis"));
        JointCustomerPredicate jointCustomerPredicate = new JointCustomerPredicate(predicates);
        expectedModel.updateFilteredCustomerList(jointCustomerPredicate);
        assertEquals(Collections.emptyList(), expectedModel.getFilteredCustomerList());
    }

    @Test
    public void execute_oneNameAndAddressKeyword_oneCustomerFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        List<Predicate<Customer>> predicates = new ArrayList<>();
        predicates.add(preparePredicate("pauline"));
        predicates.add(prepareAddressPredicate("jurong"));
        JointCustomerPredicate jointCustomerPredicate = new JointCustomerPredicate(predicates);
        FindCustomerCommand command = new FindCustomerCommand(jointCustomerPredicate);
        expectedModel.updateFilteredCustomerList(jointCustomerPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), expectedModel.getFilteredCustomerList());
    }

    @Test
    public void execute_multipleNameAndAddressKeyword_noCustomerFound() {
        List<Predicate<Customer>> predicates = new ArrayList<>();
        predicates.add(preparePredicate("paul peter"));
        predicates.add(prepareAddressPredicate("bugis bishan changi"));
        JointCustomerPredicate jointCustomerPredicate = new JointCustomerPredicate(predicates);
        expectedModel.updateFilteredCustomerList(jointCustomerPredicate);
        assertEquals(Collections.emptyList(), expectedModel.getFilteredCustomerList());
    }

    @Test
    public void execute_multipleNameAndAddressKeyword_oneCustomerFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        List<Predicate<Customer>> predicates = new ArrayList<>();
        predicates.add(preparePredicate("pauline peter pan"));
        predicates.add(prepareAddressPredicate("jurong bugis bishan"));
        JointCustomerPredicate jointCustomerPredicate = new JointCustomerPredicate(predicates);
        FindCustomerCommand command = new FindCustomerCommand(jointCustomerPredicate);
        expectedModel.updateFilteredCustomerList(jointCustomerPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), expectedModel.getFilteredCustomerList());
    }

    @Test
    public void execute_multipleNameAndAddressKeyword_multipleCustomerFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        List<Predicate<Customer>> predicates = new ArrayList<>();
        predicates.add(preparePredicate("pauline benson"));
        predicates.add(prepareAddressPredicate("jurong clementi"));
        JointCustomerPredicate jointCustomerPredicate = new JointCustomerPredicate(predicates);
        FindCustomerCommand command = new FindCustomerCommand(jointCustomerPredicate);
        expectedModel.updateFilteredCustomerList(jointCustomerPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON), expectedModel.getFilteredCustomerList());
    }

    @Test
    public void execute_oneNameAndPhoneKeyword_noCustomerFound() {
        List<Predicate<Customer>> predicates = new ArrayList<>();
        predicates.add(preparePredicate("paul"));
        predicates.add(preparePhonePredicate("9999999"));
        JointCustomerPredicate jointCustomerPredicate = new JointCustomerPredicate(predicates);
        expectedModel.updateFilteredCustomerList(jointCustomerPredicate);
        assertEquals(Collections.emptyList(), expectedModel.getFilteredCustomerList());
    }

    @Test
    public void execute_oneNameAndPhoneKeyword_oneCustomerFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        List<Predicate<Customer>> predicates = new ArrayList<>();
        predicates.add(preparePredicate("pauline"));
        predicates.add(preparePhonePredicate("94351253"));
        JointCustomerPredicate jointCustomerPredicate = new JointCustomerPredicate(predicates);
        FindCustomerCommand command = new FindCustomerCommand(jointCustomerPredicate);
        expectedModel.updateFilteredCustomerList(jointCustomerPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), expectedModel.getFilteredCustomerList());
    }

    @Test
    public void execute_multipleNameAndPhoneKeyword_noCustomerFound() {
        List<Predicate<Customer>> predicates = new ArrayList<>();
        predicates.add(preparePredicate("paul peter jojo"));
        predicates.add(preparePhonePredicate("999999 122313123 7872318237"));
        JointCustomerPredicate jointCustomerPredicate = new JointCustomerPredicate(predicates);
        expectedModel.updateFilteredCustomerList(jointCustomerPredicate);
        assertEquals(Collections.emptyList(), expectedModel.getFilteredCustomerList());
    }

    @Test
    public void execute_multipleNameAndPhoneKeyword_multipleCustomerFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        List<Predicate<Customer>> predicates = new ArrayList<>();
        predicates.add(preparePredicate("pauline benson"));
        predicates.add(preparePhonePredicate("94351253 98765432"));
        JointCustomerPredicate jointCustomerPredicate = new JointCustomerPredicate(predicates);
        FindCustomerCommand command = new FindCustomerCommand(jointCustomerPredicate);
        expectedModel.updateFilteredCustomerList(jointCustomerPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON), expectedModel.getFilteredCustomerList());
    }

    @Test
    public void execute_oneNameAndEmailKeyword_noCustomerFound() {
        List<Predicate<Customer>> predicates = new ArrayList<>();
        predicates.add(preparePredicate("paul"));
        predicates.add(prepareEmailPredicate("test@example.com"));
        JointCustomerPredicate jointCustomerPredicate = new JointCustomerPredicate(predicates);
        expectedModel.updateFilteredCustomerList(jointCustomerPredicate);
        assertEquals(Collections.emptyList(), expectedModel.getFilteredCustomerList());
    }

    @Test
    public void execute_oneNameAndEmailKeyword_oneCustomerFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        List<Predicate<Customer>> predicates = new ArrayList<>();
        predicates.add(preparePredicate("pauline"));
        predicates.add(prepareEmailPredicate("alice@example.com"));
        JointCustomerPredicate jointCustomerPredicate = new JointCustomerPredicate(predicates);
        FindCustomerCommand command = new FindCustomerCommand(jointCustomerPredicate);
        expectedModel.updateFilteredCustomerList(jointCustomerPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), expectedModel.getFilteredCustomerList());
    }

    @Test
    public void execute_multipleNameAndEmailKeyword_noCustomerFound() {
        List<Predicate<Customer>> predicates = new ArrayList<>();
        predicates.add(preparePredicate("paul peter jojo"));
        predicates.add(prepareEmailPredicate("test@example.com hahah@wahaha.com"));
        JointCustomerPredicate jointCustomerPredicate = new JointCustomerPredicate(predicates);
        expectedModel.updateFilteredCustomerList(jointCustomerPredicate);
        assertEquals(Collections.emptyList(), expectedModel.getFilteredCustomerList());
    }

    @Test
    public void execute_multipleNameAndEmailKeyword_multipleCustomerFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        List<Predicate<Customer>> predicates = new ArrayList<>();
        predicates.add(preparePredicate("pauline benson"));
        predicates.add(prepareEmailPredicate("alice@example.com johnd@example.com"));
        JointCustomerPredicate jointCustomerPredicate = new JointCustomerPredicate(predicates);
        FindCustomerCommand command = new FindCustomerCommand(jointCustomerPredicate);
        expectedModel.updateFilteredCustomerList(jointCustomerPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON), expectedModel.getFilteredCustomerList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code AddressContainsKeywordsPredicate}.
     */
    private AddressContainsKeywordsPredicate prepareAddressPredicate(String userInput) {
        return new AddressContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code EmailContainsKeywordsPredicate}.
     */
    private EmailContainsKeywordsPredicate prepareEmailPredicate(String userInput) {
        return new EmailContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code PhoneContainsKeywordsPredicate}.
     */
    private PhoneContainsKeywordsPredicate preparePhonePredicate(String userInput) {
        return new PhoneContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
