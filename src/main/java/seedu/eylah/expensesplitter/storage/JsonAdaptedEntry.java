package seedu.eylah.expensesplitter.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.eylah.commons.exceptions.IllegalValueException;
import seedu.eylah.expensesplitter.model.item.Item;
import seedu.eylah.expensesplitter.model.person.Person;
import seedu.eylah.expensesplitter.model.receipt.Entry;

/**
 * Jackson-friendly version of {@link Entry}.
 */
public class JsonAdaptedEntry {

    private final JsonAdaptedItem item;
    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedEntry} with the given entry details.
     */
    @JsonCreator
    public JsonAdaptedEntry(@JsonProperty("item") JsonAdaptedItem item,
            @JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.item = item;
        if (persons != null) {
            this.persons.addAll(persons);
        }
    }

    /**
     * Converts a given {@code Entry} into this class for Jackson use.
     */
    public JsonAdaptedEntry(Entry source) {
        item = new JsonAdaptedItem(source.getItem());
        persons.addAll(source.getPersonsList().stream()
                .map(JsonAdaptedPerson::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted entry object into the model's {@code Entry} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Entry toModelType() throws IllegalValueException {
        final ArrayList<Person> modelPersons = new ArrayList<>();
        for (JsonAdaptedPerson person : persons) {
            modelPersons.add(person.toModelType());
        }

        Item modelItem = item.toModelType();

        return new Entry(modelItem, modelPersons);
    }
}
