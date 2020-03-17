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
import seedu.expensela.model.transaction.Transaction;

/**
 * An Immutable ExpenseLa that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedTransaction> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedTransaction> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyExpenseLa} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyExpenseLa source) {
        persons.addAll(source.getTransactionList().stream().map(JsonAdaptedTransaction::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code ExpenseLa} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ExpenseLa toModelType() throws IllegalValueException {
        ExpenseLa expenseLa = new ExpenseLa();
        for (JsonAdaptedTransaction jsonAdaptedTransaction : persons) {
            Transaction transaction = jsonAdaptedTransaction.toModelType();
            if (expenseLa.hasPerson(transaction)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            expenseLa.addPerson(transaction);
        }
        return expenseLa;
    }

}
