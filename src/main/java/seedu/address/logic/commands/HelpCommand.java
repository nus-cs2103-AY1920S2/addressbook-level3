package seedu.address.logic.commands;

import seedu.address.logic.messages.AppMessage;
import seedu.address.logic.messages.BluetoothPingsMessage;
import seedu.address.logic.messages.CommandListMessage;
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

    public AppMessage execute(AppStorage dao)
    {
        ArrayList resp = dao.search();

        CommandListMessage result = new CommandListMessage("Help Guideline for Users");
        ArrayList<CommandList> allCommand = new ArrayList<CommandList>();


        List<String> searchCommands = new ArrayList<String>();
        String SearchType = "Query over trace data";
        searchCommands.add("from <ts: start> to <ts: end>");
        searchCommands.add("id is <user: id>");
        searchCommands.add("pairs <user1: id> and <user2: id>");
        searchCommands.add("danger <threshold: counts>");
        CommandList SearchList = new CommandList(SearchType, searchCommands);

        List<String> personCommands = new ArrayList<String>();
        String PersonType = "Querying User Related Information";
        personCommands.add("person");
        personCommands.add("person_by <person: id>");
        personCommands.add("person_add /name <person: name> /mobile <person: mobile> /nric <person: nric> /age <person: age> \n");
        personCommands.add("person_delete /userid <person: id>");
        CommandList PersonList = new CommandList(PersonType, personCommands);


        List<String> reportCommands = new ArrayList<String>();
        String ReportType = "Querying User Related Information";
        reportCommands.add("person");
        reportCommands.add("person_by <person: id>");
        reportCommands.add("person_add /name <person: name> /mobile <person: mobile> /nric <person: nric> /age <person: age> \n");
        reportCommands.add("person_delete /userid <person: id>");
        CommandList ReportList = new CommandList(PersonType, reportCommands);

        allCommand.add(SearchList);
        allCommand.add(PersonList);
        allCommand.add(ReportList);

        result.setToDisplayList(allCommand);
        return result;

    }
}
