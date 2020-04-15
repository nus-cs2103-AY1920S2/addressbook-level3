package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PRODUCT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PRODUCT;
import static seedu.address.testutil.customer.TypicalCustomers.ALICE_ID;
import static seedu.address.testutil.product.TypicalProducts.ABACUS_ID;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.customer.AddCustomerCommand;
import seedu.address.logic.commands.customer.ClearCustomerCommand;
import seedu.address.logic.commands.customer.DeleteCustomerCommand;
import seedu.address.logic.commands.customer.EditCustomerCommand;
import seedu.address.logic.commands.customer.EditCustomerCommand.EditCustomerDescriptor;
import seedu.address.logic.commands.customer.FindCustomerCommand;
import seedu.address.logic.commands.customer.ListCustomerCommand;
import seedu.address.logic.commands.product.AddProductCommand;
import seedu.address.logic.commands.product.ClearProductCommand;
import seedu.address.logic.commands.product.DeleteProductCommand;
import seedu.address.logic.commands.product.EditProductCommand;
import seedu.address.logic.commands.product.FindProductCommand;
import seedu.address.logic.commands.product.ListProductCommand;
import seedu.address.logic.commands.product.LowLimitCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.customer.Customer;
import seedu.address.model.product.Product;
import seedu.address.model.util.QuantityThreshold;
import seedu.address.testutil.customer.CustomerBuilder;
import seedu.address.testutil.customer.CustomerUtil;
import seedu.address.testutil.customer.EditCustomerDescriptorBuilder;
import seedu.address.testutil.product.EditProductDescriptorBuilder;
import seedu.address.testutil.product.ProductBuilder;
import seedu.address.testutil.product.ProductUtil;

public class InventorySystemParserTest {

    private final InventorySystemParser parser = new InventorySystemParser();

    @Test
    public void parseCommand_addCustomer() throws Exception {
        Customer customer = new CustomerBuilder(ALICE_ID).build();
        AddCustomerCommand command = (AddCustomerCommand) parser.parseCommand(CustomerUtil.getAddCommand(customer));
        assertEquals(new AddCustomerCommand(customer), command);
    }

    @Test
    public void parseCommand_clearCustomer() throws Exception {
        assertTrue(parser.parseCommand(ClearCustomerCommand.COMMAND_WORD) instanceof ClearCustomerCommand);
        assertTrue(parser.parseCommand(ClearCustomerCommand.COMMAND_WORD + " 3") instanceof ClearCustomerCommand);
    }

    @Test
    public void parseCommand_deleteCustomer() throws Exception {
        DeleteCustomerCommand command = (DeleteCustomerCommand) parser.parseCommand(
                DeleteCustomerCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCustomerCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_editCustomer() throws Exception {
        Customer customer = new CustomerBuilder(ALICE_ID).build();
        EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder(customer).build();
        EditCustomerCommand command = (EditCustomerCommand) parser.parseCommand(EditCustomerCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + CustomerUtil.getEditPersonDescriptorDetails(descriptor));
        // assertEquals(new EditCustomerCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_findCustomer() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCustomerCommand command = (FindCustomerCommand) parser.parseCommand(
                FindCustomerCommand.COMMAND_WORD + " n/" + keywords.stream().collect(Collectors.joining(" ")));
        //        assertEquals(new FindCustomerCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_listCustomer() throws Exception {
        assertTrue(parser.parseCommand(ListCustomerCommand.COMMAND_WORD) instanceof ListCustomerCommand);
        assertTrue(parser.parseCommand(ListCustomerCommand.COMMAND_WORD + " 3") instanceof ListCustomerCommand);
    }

    @Test
    public void parseCommand_addProduct() throws Exception {
        Product product = new ProductBuilder(ABACUS_ID).build();
        AddProductCommand command = (AddProductCommand) parser.parseCommand(ProductUtil.getAddCommand(product));
        assertEquals(new AddProductCommand(product), command);
    }

    @Test
    public void parseCommand_clearProduct() throws Exception {
        assertTrue(parser.parseCommand(ClearProductCommand.COMMAND_WORD) instanceof ClearProductCommand);
        assertTrue(parser.parseCommand(ClearProductCommand.COMMAND_WORD + " 3") instanceof ClearProductCommand);
    }

    @Test
    public void parseCommand_deleteProduct() throws Exception {
        DeleteProductCommand command = (DeleteProductCommand) parser.parseCommand(
                DeleteProductCommand.COMMAND_WORD + " " + INDEX_FIRST_PRODUCT.getOneBased());
        assertEquals(new DeleteProductCommand(INDEX_FIRST_PRODUCT), command);
    }

    @Test
    public void parseCommand_editProduct() throws Exception {
        Product product = new ProductBuilder(ABACUS_ID).build();
        EditProductCommand.EditProductDescriptor descriptor = new EditProductDescriptorBuilder(product).build();
        EditProductCommand command = (EditProductCommand) parser.parseCommand(EditProductCommand.COMMAND_WORD + " "
                + INDEX_SECOND_PRODUCT.getOneBased() + " " + ProductUtil.getEditProductDescriptorDetails(descriptor));
        //        assertEquals(new EditProductCommand(INDEX_SECOND_PRODUCT, descriptor), command);
    }

    @Test
    public void parseCommand_findProduct() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindProductCommand command = (FindProductCommand) parser.parseCommand(
                FindProductCommand.COMMAND_WORD + " p/" + keywords.stream().collect(Collectors.joining(" ")));
        //        assertEquals(new FindProductCommand(new DescriptionContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_listProduct() throws Exception {
        assertTrue(parser.parseCommand(ListProductCommand.COMMAND_WORD) instanceof ListProductCommand);
        assertTrue(parser.parseCommand(ListProductCommand.COMMAND_WORD + " 3") instanceof ListProductCommand);
    }

    @Test
    public void parseCommand_lowLimit() throws Exception {
        LowLimitCommand command = (LowLimitCommand) parser.parseCommand(LowLimitCommand.COMMAND_WORD + " p/"
                + INDEX_SECOND_PRODUCT.getOneBased() + " " + " t/10");
        assertEquals(new LowLimitCommand(INDEX_SECOND_PRODUCT, new QuantityThreshold(10)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
