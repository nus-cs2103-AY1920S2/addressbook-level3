package seedu.expensela.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import seedu.expensela.commons.core.Messages;
import seedu.expensela.logic.commands.exceptions.CommandException;
import seedu.expensela.model.Filter;
import seedu.expensela.model.Model;
import seedu.expensela.model.transaction.Amount;
import seedu.expensela.model.transaction.Category;
import seedu.expensela.model.transaction.Date;
import seedu.expensela.model.transaction.Name;
import seedu.expensela.model.transaction.Remark;
import seedu.expensela.model.transaction.Transaction;

/**
 * Exports currently filtered transactions to csv file, in the current directory.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_SUCCESS = "Imported transactions from csv file!";
    public static final Object MESSAGE_USAGE = "";

    private String csvFile = "";

    public ImportCommand(String string) {
        this.csvFile = string;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        String line = "./" + csvFile;
        String discardLine = "";
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(csvFile));
            discardLine = br.readLine();
            while ((line = br.readLine()) != null) {
                Transaction importedTransaction = buildTransactionFromString(line);
                try {
                    model.addTransaction(importedTransaction);
                } catch (Exception e) {
                    continue;
                }

            }
        } catch (IOException e) {
            throw new CommandException(Messages.MESSAGE_FAILED_IMPORT);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new CommandException(Messages.MESSAGE_FAILED_IMPORT);
                }
            }
        }
        model.setFilter(new Filter(null, null));
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    /**
     * Creates a new transaction from line of CSV file
     * @param string String from csv
     * @return A transaction object after it is created with the right parameters
     */
    private Transaction buildTransactionFromString(String string) {
        String[] transactionParam = string.split(",");
        Name name = new Name(transactionParam[0]);
        String[] splitAmount = transactionParam[1].split("");
        Amount amount = null;
        String inputAmount = "";
        if (splitAmount[0].equals("-")) {
            for (int i = 3; i < splitAmount.length; i++) {
                inputAmount += splitAmount[i];
            }
            amount = new Amount(inputAmount, false);
        } else if (splitAmount[0].equals("+")) {
            for (int i = 3; i < splitAmount.length; i++) {
                inputAmount += splitAmount[i];
            }
            amount = new Amount(inputAmount, true);
        }
        Date date = new Date(transactionParam[2]);
        Remark remark = new Remark(transactionParam[3]);
        Category category = new Category(transactionParam[4]);

        Transaction newTransaction = new Transaction(name, amount, date, remark, category);

        return newTransaction;

    }
}
