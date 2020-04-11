package seedu.address.logic.commands;

import seedu.address.logic.messages.AppMessage;
import seedu.address.logic.messages.BluetoothPingsMessage;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.bluetooth.BluetoothPings;
import seedu.address.storage.AppStorage;
import seedu.address.storage.BluetoothPingStorageAccess;

import java.util.ArrayList;

public class HelpCommand implements AppCommand, BluetoothPingStorageAccess {
    public static final String COMMAND_WORD = "help";

    public HelpCommand validate(String arguments) throws ParseException {
        assert (arguments.trim().equals(""));
        return this;
    }

    public AppMessage execute(AppStorage dao)
    {
        ArrayList resp = dao.search();
        BluetoothPingsMessage result = new BluetoothPingsMessage("Help Guideline with All Instances in Database: \n" +
                " ------------------------------------------ \n" +
                "Query over trace data: \n " +
                " ------------------------------------------ \n"+
                "from <ts: start> to <ts: end> \n " +
                "id is <user: id> \n " +
                "pairs <user1: id> and <user2: id> \n " +
                "danger <threshold: counts> \n" +
                " ------------------------------------------ \n" +
                "Querying User Related Information \n" +
                " ------------------------------------------ \n" +
                "person \n " +
                "person_by <person: id> \n " +
                "person_add /name <person: name> /mobile <person: mobile> /nric <person: nric> /age <person: age> \n " +
                "person_delete /userid <person: id> \n" +
                " ------------------------------------------ \n " +
                "Gererating Reports \n " +
                " ------------------------------------------ \n"+
                "report all \n " +
                "report time from <ts: start> to <ts:end> \n" +
                " report danger <threshold: count>\n " +
                "report pairs <user1: id> and <user2: id> \n " +
                "report id is <user: id>\n " +
                " ----------------------------------------- \n " +
                "All interaction instances are listed below:"
                , false);
        result.setToDisplayList(resp);
        return result;

    }
}
