package seedu.eylah.expensesplitter.logic.parser;

import static seedu.eylah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eylah.expensesplitter.logic.commands.CommandTestUtil.INVALID_ITEMNAME_DESC;
import static seedu.eylah.expensesplitter.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.eylah.expensesplitter.logic.commands.CommandTestUtil.ITEMNAME_DESC_CHICKENRICE;
import static seedu.eylah.expensesplitter.logic.commands.CommandTestUtil.ITEMNAME_DESC_PIZZA;
import static seedu.eylah.expensesplitter.logic.commands.CommandTestUtil.ITEMPRICE_DESC_CHICKENRICE;
import static seedu.eylah.expensesplitter.logic.commands.CommandTestUtil.ITEMPRICE_DESC_PIZZA;
import static seedu.eylah.expensesplitter.logic.commands.CommandTestUtil.NAME_DESC_ANNA;
import static seedu.eylah.expensesplitter.logic.commands.CommandTestUtil.NAME_DESC_BRANDON;
import static seedu.eylah.expensesplitter.logic.commands.CommandTestUtil.NAME_DESC_CHARLIE;
import static seedu.eylah.expensesplitter.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.eylah.expensesplitter.logic.commands.CommandTestUtil.VALID_ITEMNAME_CHICKENRICE;
import static seedu.eylah.expensesplitter.logic.commands.CommandTestUtil.VALID_ITEMPRICE_CHICKENRICE;
import static seedu.eylah.expensesplitter.logic.commands.CommandTestUtil.VALID_NAME_ANNA;
import static seedu.eylah.expensesplitter.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.eylah.expensesplitter.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.eylah.expensesplitter.logic.commands.AddItemCommand;
import seedu.eylah.expensesplitter.model.item.Item;
import seedu.eylah.expensesplitter.model.item.ItemName;
import seedu.eylah.expensesplitter.model.person.Amount;
import seedu.eylah.expensesplitter.model.person.Name;
import seedu.eylah.expensesplitter.model.person.Person;
import seedu.eylah.expensesplitter.model.receipt.Entry;
import seedu.eylah.expensesplitter.testutil.EntryBuilder;
import seedu.eylah.expensesplitter.testutil.TypicalEntries;

public class AddItemCommandParserTest {
    private AddItemCommandParser parser = new AddItemCommandParser();

    @Test
    public void parse_allFieldsPresentSinglePerson_success() {
        Entry expectedEntry = TypicalEntries.ENTRY_FIVE;
        Item expectedItem = expectedEntry.getItem();
        ArrayList<Person> expectedPersons = expectedEntry.getPersonsList();
        Amount expectedAmount = expectedItem.getAmountPerPerson();

        //whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + ITEMNAME_DESC_CHICKENRICE
                + ITEMPRICE_DESC_CHICKENRICE + NAME_DESC_ANNA,
                new AddItemCommand(expectedItem, expectedPersons, expectedAmount));
    }

    @Test
    public void parse_allFieldsPresentMultiplePersons_success() {
        Entry expectedEntry = new EntryBuilder(TypicalEntries.ENTRY_SIX).build();
        Item expectedItem = expectedEntry.getItem();
        ArrayList<Person> expectedPersons = expectedEntry.getPersonsList();
        Amount expectedAmount = expectedItem.getAmountPerPerson();

        assertParseSuccess(parser, ITEMNAME_DESC_PIZZA + ITEMPRICE_DESC_PIZZA
                        + NAME_DESC_ANNA + NAME_DESC_BRANDON + NAME_DESC_CHARLIE,
                new AddItemCommand(expectedItem, expectedPersons, expectedAmount));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddItemCommand.MESSAGE_USAGE);

        //missing item name prefix
        assertParseFailure(parser, VALID_ITEMNAME_CHICKENRICE + ITEMPRICE_DESC_CHICKENRICE + NAME_DESC_ANNA,
                expectedMessage);

        //missing item price prefix
        assertParseFailure(parser, VALID_ITEMPRICE_CHICKENRICE + ITEMNAME_DESC_CHICKENRICE + NAME_DESC_ANNA,
                expectedMessage);

        //missing name prefix
        assertParseFailure(parser, VALID_NAME_ANNA + ITEMNAME_DESC_CHICKENRICE + ITEMPRICE_DESC_CHICKENRICE,
                expectedMessage);

        //missing all prefixes
        assertParseFailure(parser, VALID_ITEMNAME_CHICKENRICE + VALID_ITEMPRICE_CHICKENRICE + VALID_NAME_ANNA,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        //invalid item name
        assertParseFailure(parser, INVALID_ITEMNAME_DESC + ITEMPRICE_DESC_CHICKENRICE + NAME_DESC_ANNA,
                ItemName.MESSAGE_CONSTRAINTS);

        //invalid item price
        //        assertParseFailure(parser, INVALID_ITEMPRICE_DESC + ITEMNAME_DESC_CHICKENRICE + NAME_DESC_ANNA,
        //                ItemPrice.MESSAGE_CONSTRAINTS);

        //invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + ITEMNAME_DESC_CHICKENRICE + ITEMPRICE_DESC_CHICKENRICE,
                Name.MESSAGE_CONSTRAINTS);
    }
}
