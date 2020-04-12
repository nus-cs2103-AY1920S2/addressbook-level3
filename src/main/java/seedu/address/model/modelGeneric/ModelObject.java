package seedu.address.model.modelGeneric;

import seedu.address.commons.exceptions.DuplicateException;
import seedu.address.commons.exceptions.NotFoundException;
import seedu.address.model.modelObjectTags.ID;

/**
 * Data-centric class to be extended which encapsulate closely related items.
 */
public abstract class ModelObject implements Cloneable {
    private String ENTITY_NAME = "";

    public abstract ID getId();

    public abstract boolean weakEquals(ModelObject other);

    public RuntimeException getNotFoundException() {
        return new NotFoundException(ENTITY_NAME);
    }

    ;

    public RuntimeException getDuplicateException() {
        return new DuplicateException(ENTITY_NAME);
    }

    ;

    public abstract ModelObject clone();
}
