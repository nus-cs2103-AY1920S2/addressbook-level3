package seedu.address.model.event.consult;

import java.util.List;

/**
 * Unmodifiable view of all Consults.
 */
public interface ReadOnlyConsult {
    /**
     * Returns an unmodifiable view of the consults list.
     * This list will not contain any duplicate consults.
     */
    List<Consult> getAllConsults();
}
