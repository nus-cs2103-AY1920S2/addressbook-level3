package seedu.address.model.modelProgress;

import seedu.address.commons.exceptions.DuplicateException;
import seedu.address.model.modelGeneric.UniqueList;

import static java.util.Objects.requireNonNull;

public class UniqueProgressList extends UniqueList<Progress> {
    public void addUndoProgress(Progress undoneProgress) {
        try{
            requireNonNull(undoneProgress);
            this.add(undoneProgress);
        } catch (DuplicateException de) {
            System.out.println(de.getMessage());
        }
    }
}
