package seedu.address.model.hirelah;

import seedu.address.commons.exceptions.IllegalValueException;

/*
 * IList
 *
 * CS2103 AY19/20 Semester 2
 * Team Project
 * HireLah!
 *
 * 02 Mar 2020
 *
 */

/**
 * IList interface serves as a base for other lists that
 * manage the CRUD of a model.
 * @param <T> The type of the model.
 */

public interface IList<T> {
    String add(String description) throws IllegalValueException;
    String delete(String description) throws IllegalValueException;
    T find(String description) throws IllegalValueException;
}
