package seedu.expensela.storage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.expensela.commons.exceptions.IllegalValueException;
import seedu.expensela.model.Balance;
import seedu.expensela.model.GlobalData;
import seedu.expensela.model.ReadOnlyGlobalData;
import seedu.expensela.model.monthlydata.Budget;
import seedu.expensela.model.transaction.Transaction;

/**
 * An Immutable GlobalData that is serializable to JSON format.
 */
@JsonRootName(value = "globaldata")
public class JsonSerializableGlobalData {

    private final List<JsonAdaptedTransaction> transactions = new ArrayList<>();
    private final String budget;
    private final String totalBalance;
    private final String date;


    /**
     * Constructs a {@code JsonSerializableGlobalData} with the given transactions.
     */
    @JsonCreator
    public JsonSerializableGlobalData(@JsonProperty("transactions") List<JsonAdaptedTransaction> transactions,
                                     @JsonProperty("budget") String budget,
                                     @JsonProperty("balance") String balance,
                                     @JsonProperty("date") String date) {
        this.transactions.addAll(transactions);
        this.budget = budget;
        this.totalBalance = balance;
        this.date = date;
    }

    /**
     * Converts a given {@code ReadOnlyGlobalData} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableGlobalData}.
     */
    public JsonSerializableGlobalData(ReadOnlyGlobalData source) {
        transactions.addAll(source.getRecurringTransactionList().stream().map(JsonAdaptedTransaction::new)
                .collect(Collectors.toList()));
        budget = source.getRecurringBudget() != null ? source.getRecurringBudget().budgetAmount.toString() : "null";
        totalBalance = source.getTotalBalance().balanceAmount.toString();
        date = source.getLastUpdatedDate().toString();
    }

    /**
     * Converts this globalData into the model's {@code GlobalData} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public GlobalData toModelType() throws IllegalValueException {
        GlobalData globalData = new GlobalData();
        for (JsonAdaptedTransaction jsonAdaptedTransaction : transactions) {
            Transaction transaction = jsonAdaptedTransaction.toModelType();
            globalData.addTransaction(transaction);
        }
        globalData.setRecurringBudget(!budget.equals("null") ? new Budget(budget) : null);
        globalData.setLastUpdatedDate(LocalDate.parse(date));
        globalData.setTotalBalance(new Balance(totalBalance));
        return globalData;
    }

}
