package seedu.eylah.expensesplitter.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.eylah.commons.exceptions.IllegalValueException;
import seedu.eylah.expensesplitter.model.PersonAmountBook;
import seedu.eylah.expensesplitter.model.ReadOnlyPersonAmountBook;
import seedu.eylah.expensesplitter.model.person.Person;


/**
 * An Immutable PersonAmountBook that is serializable to JSON format.
 */
@JsonRootName(value = "personamountbook")

public class JsonSerializablePersonAmountBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializablePersonAmountBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializablePersonAmountBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyPersonAmountBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializablePersonAmountBook}.
     */
    public JsonSerializablePersonAmountBook(ReadOnlyPersonAmountBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this person amount book into the model's {@code PersonAmountBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public PersonAmountBook toModelType() throws IllegalValueException {
        PersonAmountBook personAmountBook = new PersonAmountBook();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (personAmountBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            personAmountBook.addPerson(person);

        }
        return personAmountBook;
    }

}
