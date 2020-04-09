package seedu.expensela.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import seedu.expensela.commons.core.Messages;
import seedu.expensela.logic.commands.exceptions.CommandException;
import seedu.expensela.model.Model;
import seedu.expensela.model.transaction.Transaction;

/**
 * Exports currently filtered transactions to csv file, in the current directory.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_SUCCESS = "Exported to %s";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Transaction> transactionList = model.getFilteredTransactionList();

        if (transactionList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_EMPTY_TRANSACTION_LIST);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("NAME");
        sb.append(",");
        sb.append("AMOUNT");
        sb.append(",");
        sb.append("DATE");
        sb.append(",");
        sb.append("REMARK");
        sb.append(",");
        sb.append("CATEGORY\n");

        for (Transaction transaction : transactionList) {
            buildStringFromTransaction(transaction, sb);
        }

        File file = new File("transactions.csv");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(sb.toString());
        } catch (FileNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_FAILED_EXPORT);
        } catch (IOException ioe) {
            throw new CommandException(Messages.MESSAGE_FAILED_EXPORT);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, file.getAbsolutePath()));
    }

    /**
     * Inputs details of transaction into String Builder in required format for csv.
     * @param transaction
     * @param sb The String Builder object used to convert to csv file.
     * @return The String Builder object after transaction details added.
     */
    private StringBuilder buildStringFromTransaction(Transaction transaction, StringBuilder sb) {
        String name = transaction.getName().toString();
        String amount = transaction.getAmount().toString().replace(",", "");
        String date = transaction.getDate().toString();
        String remark = transaction.getRemark().toString();
        String category = transaction.getCategory().toString();

        sb.append(name);
        sb.append(",");
        sb.append(amount);
        sb.append(",");
        sb.append(date);
        sb.append(",");
        sb.append(remark);
        sb.append(",");
        sb.append(category);
        sb.append("\n");

        return sb;
    }
}
