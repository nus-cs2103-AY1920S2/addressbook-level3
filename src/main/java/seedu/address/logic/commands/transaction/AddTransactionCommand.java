package seedu.address.logic.commands.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANS_DESCIPTION;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.TransactionFactory;

/**
 * Adds a transaction to the system.
 */
public class AddTransactionCommand extends Command {

    public static final String COMMAND_WORD = "addt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": add a transaction to the application. "
            + "Parameters: "
            + "[" + PREFIX_CUSTOMER + "CUSTOMER] "
            + "[" + PREFIX_PRODUCT + "PRODUCT] "
            + "[" + PREFIX_DATETIME + "DATETIME] "
            + "[" + PREFIX_QUANTITY + "QUANTITY] "
            + "[" + PREFIX_MONEY + "MONEY] "
            + "[" + PREFIX_TRANS_DESCIPTION + "DESCRIPTION] \n"
            + "Example: " + COMMAND_WORD
            + PREFIX_CUSTOMER + "1 "
            + PREFIX_PRODUCT + "1 "
            + PREFIX_DATETIME + "2020-02-20 10:00 "
            + PREFIX_QUANTITY + "30"
            + PREFIX_MONEY + "30 "
            + PREFIX_TRANS_DESCIPTION + "under discount ";;

    public static final String MESSAGE_SUCCESS = "New transaction added: %1$s";
    public static final String MESSAGE_DUPLICATE_TRANSACTION = "This transaction already exists in the address book";

    private final TransactionFactory transactionFactory;

    public AddTransactionCommand(TransactionFactory transactionFactory) {
        requireNonNull(transactionFactory);
        this.transactionFactory = transactionFactory;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Transaction toAdd = transactionFactory.createTransaction(model);

        if (model.hasTransaction(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TRANSACTION);
        }

        model.addTransaction(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }
}
