package seedu.address.logic.commands.commandAssign;

import seedu.address.model.person.ID;

public abstract class AssignCommandModelManager {
    public abstract void performAssign(ID courseID, ID otherID);
}
