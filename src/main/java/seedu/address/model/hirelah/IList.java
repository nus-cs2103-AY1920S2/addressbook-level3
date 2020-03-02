package seedu.address.model.hirelah;

import seedu.address.commons.exceptions.IllegalValueException;

public interface IList<T> {
    String add(String description) throws IllegalValueException;
    String delete(String description) throws IllegalValueException;
    T find(String description) throws IllegalValueException;
}
