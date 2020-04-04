package seedu.address.model.person;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;

import java.util.HashMap;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;

public class CompositeIDBuilder {
    public HashMap<Prefix, ID> ids;
    private final String INVALID_COMPOSITEID = "CompositeIDs must have at least 2 IDs!";
    private final String ID_ALREADY_EXISTS = "CompositeIDBuilder already has this ID!";


    public CompositeIDBuilder() {
        ids = new HashMap<>();
    }

    private CompositeIDBuilder(HashMap<Prefix, ID> beingBuilt) {
        this.ids = beingBuilt;
    }

    public CompositeIDBuilder addStudentID(ID studentID) throws CommandException {
        if(ids.containsKey(PREFIX_STUDENTID)) {
            throw new CommandException("ID_ALREADY_EXISTS");
        } else {
            ids.put(PREFIX_STUDENTID, studentID);
        }
        return new CompositeIDBuilder(this.ids);
    }

    public CompositeIDBuilder addAssignmentID(ID assignmentID) throws CommandException {
        if(ids.containsKey(PREFIX_ASSIGNMENTID)) {
            throw new CommandException("ID_ALREADY_EXISTS");
        } else {
            ids.put(PREFIX_ASSIGNMENTID, assignmentID);
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
