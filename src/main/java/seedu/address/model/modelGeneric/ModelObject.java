package seedu.address.model.modelGeneric;

import seedu.address.commons.exceptions.DuplicateException;
import seedu.address.commons.exceptions.NotFoundException;
import seedu.address.model.person.ID;

public abstract class ModelObject {
    private String ENTITY_NAME = "";

    public abstract boolean weakEquals(ModelObject other);

    public abstract ID getId();

    public RuntimeException getNotFoundException() {
        return new NotFoundException(ENTITY_NAME);
    };

    public RuntimeException getDuplicateException() {
        return new DuplicateException(ENTITY_NAME);
    };

}
