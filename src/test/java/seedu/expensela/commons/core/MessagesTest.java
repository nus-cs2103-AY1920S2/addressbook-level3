package seedu.expensela.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MessagesTest {

    @Test
    public void correctMessageValues() {
        assertEquals(Messages.MESSAGE_UNKNOWN_COMMAND, "Unknown command");
        assertEquals(Messages.MESSAGE_INVALID_COMMAND_FORMAT, "Invalid command format! \n%1$s");
        assertEquals(Messages.MESSAGE_INVALID_FILTER, "Filter keyword is invalid! \nEnter command in format: "
                + "filter c/<CATEGORY> m/<MONTH> where <MONTH> is in YYYY-MM, starting from year 1900 \n"
                + "example: filter c/FOOD m/2020-01\n"
                + "example: filter c/GROCERIES m/ALL\n"
                + "example: filter m/2021-12");
        assertEquals(Messages.MESSAGE_WORD_NOT_FOUND, "Unable to find any matches! Enter 'list' to go back");
        assertEquals(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX, "The transaction index provided is invalid");
        assertEquals(Messages.MESSAGE_TRANSACTION_LISTED_OVERVIEW, "%1$d transactions listed!");
        assertEquals(Messages.MESSAGE_EMPTY_TRANSACTION_LIST, "The transaction list is empty."
                + " There is no transactions to export!");
        assertEquals(Messages.MESSAGE_FAILED_EXPORT, "Problem encountered while exporting transactions."
                + " Please try exporting again later.");
    }
}
