package seedu.eylah.expensesplitter.logic.commands;

import seedu.eylah.commons.logic.command.Command;
import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.expensesplitter.model.SplitterModel;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command<SplitterModel> {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows Expense Splitter usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String DIVIDER = "--------------------------------\n";

    public static final String SHOWING_HELP_MESSAGE = "Welcome to Expense Splitter! The following commands are "
            + "available:\n"
            + DIVIDER
            + "1. additem - Use this to add an item, price and the person(s) involved in the splitting for that item.\n"
            + "   USAGE: additem -i ITEMNAME -p PRICE -n NAME [-n NAME]...\n"
            + "   EXAMPLE: additem -i pasta -p 19.90 -n alice -n bob -n charlie\n"
            + DIVIDER
            + "2. deleteitem - Use this to delete the item at the specified index in your receipt.\n"
            + "   USAGE: deleteitem INDEX\n"
            + "   EXAMPLE: deleteitem 1\n"
            + DIVIDER
            + "3. listreceipt - Use this to list all the item(s) in the receipt.\n"
            + "   USAGE: listreceipt\n"
            + "   EXAMPLE: listreceipt\n"
            + DIVIDER
            + "4. listamount - Use this to list all the person(s) and the amount they owe.\n"
            + "   USAGE: listamount\n"
            + "   EXAMPLE: listamount\n"
            + DIVIDER
            + "5. donereceipt - Use this to mark the receipt as done after adding all items.\n"
            + "   USAGE: donereceipt\n"
            + "   EXAMPLE: donereceipt\n"
            + DIVIDER
            + "6. paid - Use this to subtract the amount paid by a person at the specified index.\n"
            + "   USAGE: paid INDEX [AMOUNT]\n"
            + "   EXAMPLE: paid 2 3.90\n"
            + DIVIDER
            + "7. clearreceipt - Use this to clear the receipt and start a new receipt.\n"
            + "   USAGE: clearreceipt\n"
            + "   EXAMPLE: clearreceipt\n"
            + DIVIDER
            + "8. back - Use this to back to the main menu of EYLAH.\n"
            + "   USAGE: back\n"
            + "   EXAMPLE: back\n"
            + DIVIDER
            + "We hope you enjoy your usage of EYLAH Expense Splitter!";

    @Override
    public CommandResult execute(SplitterModel splitterModel) {
        return new CommandResult(SHOWING_HELP_MESSAGE, false, false);
    }

}
