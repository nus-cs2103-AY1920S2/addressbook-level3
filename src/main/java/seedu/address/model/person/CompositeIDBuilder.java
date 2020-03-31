package seedu.address.model.person;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;

import java.util.HashMap;

public class CompositeIDBuilder {
    public HashMap<String, ID> ids;
    private final String INVALID_COMPOSITEID = "CompositeIDs must have at least 2 IDs!";
    private final String ID_ALREADY_EXISTS = "CompositeIDBuilder already has this ID!";


    public CompositeIDBuilder() {
        ids = new HashMap<>();
    }

    private CompositeIDBuilder(HashMap<String, ID> beingBuilt) {
        this.ids = beingBuilt;
    }

    public CompositeIDBuilder addStudentID(ID studentID) throws CommandException {
        if(ids.containsKey("sid")) {
            throw new CommandException("ID_ALREADY_EXISTS");
        } else {
            ids.put("sid", studentID);
        }
        return new CompositeIDBuilder(this.ids);
    }

    public CompositeIDBuilder addAssignmentID(ID assignmentID) throws CommandException {
        if(ids.containsKey("aid")) {
            throw new CommandException("ID_ALREADY_EXISTS");
        } else {
            ids.put("aid", assignmentID);
        }
        return new CompositeIDBuilder(this.ids);
    }

    public CompositeID createCompositeID() throws CommandException {
        if(ids.size() < 2 ) {
            throw new CommandException(INVALID_COMPOSITEID);
        } else {
            return new CompositeID(this.ids);
        }
    }
}
