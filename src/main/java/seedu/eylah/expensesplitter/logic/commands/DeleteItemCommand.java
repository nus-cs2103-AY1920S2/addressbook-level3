package seedu.eylah.expensesplitter.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.eylah.commons.core.index.Index;
import seedu.eylah.commons.logic.command.Command;
import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.commons.logic.command.exception.CommandException;
import seedu.eylah.expensesplitter.model.SplitterModel;
import seedu.eylah.expensesplitter.model.item.Item;
import seedu.eylah.expensesplitter.model.person.Amount;
import seedu.eylah.expensesplitter.model.person.Person;
import seedu.eylah.expensesplitter.model.receipt.Entry;

/**
 * Deletes an Item from the Receipt and the reduces the Amount of the Person(s) involved in splitting this Item.
 */
public class DeleteItemCommand extends Command<SplitterModel> {

    public static final String COMMAND_WORD = "deleteitem";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the item identified by the index number used in the displayed item list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Item: %1$s";

    // note:
    // if receipt is done, can only do paid, back, listamount and listreceipt. CANNOT: add/deleteitem.
    // if receipt is undone, can only additem, back, deleteitem, listreceipt, listamount. CANNOT: paid.
    public static final String MESSAGE_RECEIPT_DONE = "The current receipt is marked as completed. You may not use "
            + "the deleteitem command.\n"
            + "To start a clean receipt, please use the clearreceipt command.";

    private final Index targetIndex;

    public DeleteItemCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(SplitterModel splitterModel) throws CommandException {
        requireNonNull(splitterModel);
        if (splitterModel.isReceiptDone()) {
            return new CommandResult(MESSAGE_RECEIPT_DONE);
        } else {
            Entry currentEntry;

            try {
                currentEntry = splitterModel.getEntry(targetIndex.getZeroBased());
            } catch (IndexOutOfBoundsException ex) {
                throw new CommandException("There is no Item with this index. Please use `listreceipt` to get the"
                + " Index of item.");
            }
            Item currentItem = currentEntry.getItem();
            Amount amountPerPerson = currentItem.getAmountPerPerson();
            ArrayList<Person> persons = currentEntry.getPersonsList();
            for (Person person : persons) {
                splitterModel.removeAmount(person, amountPerPerson);
            }
            splitterModel.deleteEntry(targetIndex.getZeroBased());
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, targetIndex.getOneBased()));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteItemCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteItemCommand) other).targetIndex)); // state check
    }
}
