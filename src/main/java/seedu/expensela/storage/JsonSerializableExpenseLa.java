package seedu.expensela.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.expensela.commons.exceptions.IllegalValueException;
import seedu.expensela.model.ExpenseLa;
import seedu.expensela.model.ReadOnlyExpenseLa;
import seedu.expensela.model.monthlydata.MonthlyData;
import seedu.expensela.model.transaction.Transaction;

/**
 * An Immutable ExpenseLa that is serializable to JSON format.
 */
@JsonRootName(value = "expensela")
class JsonSerializableExpenseLa {

    public static final String MESSAGE_DUPLICATE_TRANSACTION = "Transactions list contains duplicate transaction(s).";

    private final List<JsonAdaptedTransaction> transactions = new ArrayList<>();
    private final JsonAdaptedMonthlyData monthlyData;

    /**
     * Constructs a {@code JsonSerializableExpenseLa} with the given transactions.
     */
    @JsonCreator
    public JsonSerializableExpenseLa(@JsonProperty("transactions") List<JsonAdaptedTransaction> transactions,
                                     @JsonProperty("monthlyData") JsonAdaptedMonthlyData monthlyData) {
        this.transactions.addAll(transactions);
        this.monthlyData = monthlyData;
    }

    /**
     * Converts a given {@code ReadOnlyExpenseLa} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableExpenseLa}.
     */
    public JsonSerializableExpenseLa(ReadOnlyExpenseLa source) {
        transactions.addAll(source.getTransactionList().stream().map(JsonAdaptedTransaction::new)
                .collect(Collectors.toList()));
        monthlyData = new JsonAdaptedMonthlyData(source.getMonthlyData());
    }

    /**
     * Converts this expenseLa into the model's {@code ExpenseLa} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ExpenseLa toModelType() throws IllegalValueException {
        ExpenseLa expenseLa = new ExpenseLa();
        for (JsonAdaptedTransaction jsonAdaptedTransaction : transactions) {
            Transaction transaction = jsonAdaptedTransaction.toModelType();
            if (expenseLa.hasTransaction(transaction)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TRANSACTION);
            }
            expenseLa.addTransaction(transaction);
        }
        MonthlyData monthlyData = this.monthlyData.toModelType();
        expenseLa.setMonthlyData(monthlyData);
        return expenseLa;
    }

}
