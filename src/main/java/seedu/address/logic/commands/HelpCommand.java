package seedu.address.logic.commands;

import seedu.address.logic.messages.HelpCommandMessage;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.bluetooth.CommandList;
import seedu.address.storage.AppStorage;
import seedu.address.storage.BluetoothPingStorageAccess;

import java.util.ArrayList;
import java.util.List;

public class HelpCommand implements AppCommand, BluetoothPingStorageAccess {
    public static final String COMMAND_WORD = "help";

    public HelpCommand validate(String arguments) throws ParseException {
        assert (arguments.trim().equals(""));
        return this;
    }

    public HelpCommandMessage execute(final AppStorage dao)
    {
        HelpCommandMessage result = new HelpCommandMessage("Help Guideline for Users");
        ArrayList<CommandList> allCommand = new ArrayList<CommandList>();

        // Query over bluetooth ping records
        allCommand.add(new CommandList("Timestamp filtering", "from <ts: start> to <ts: end>"));
        allCommand.add(new CommandList("UserID filtering", "id is <user: id>"));
        allCommand.add(new CommandList("User pair filtering", "pairs <user1: id> and <user2: id>"));
        allCommand.add(new CommandList("Identifying dangerous users", "danger <threshold: counts>"));

        // Query over user user information
        allCommand.add(new CommandList("Show all user details", "person"));
        allCommand.add(new CommandList("Filter user by ID", "person_by <person: id>"));
        allCommand.add(new CommandList("New user entry", "person_add /name <person: name> /mobile <person: mobile> /nric <person: nric> /age <person: age>"));
        allCommand.add(new CommandList("Delete present user ID", "person_delete /userid <person: id>"));

        // Report generation
        allCommand.add(new CommandList("Report all Users with userID", "report all"));
        allCommand.add(new CommandList("Report danger users", "report danger <threshold: counts>"));
        allCommand.add(new CommandList("Report cases in time range", "report time from <ts: start> to <ts: end>"));
        allCommand.add(new CommandList("Report users with a given userID", "report id is <user: id>"));
        allCommand.add(new CommandList("Report all user information details", "report_person"));
        allCommand.add(new CommandList("Report person information with given ID", "report_person <person: id>"));

        result.setToDisplayList(allCommand);
        return result;
    }
}
