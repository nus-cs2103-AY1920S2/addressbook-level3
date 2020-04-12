package seedu.address.logic.commands;

import seedu.address.logic.messages.AppMessage;
import seedu.address.logic.messages.BluetoothPingsMessage;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.storage.AppStorage;

public interface AppCommand {

    /**
     * To validate that the arguments passed in are sound
     *
     * @param   arguments           String arguments
     * @return  AppCommand
     * @throws  ParseException      Raise if arguments do not meet the condition
     */
    public AppCommand validate(String arguments) throws ParseException;

    /**
     * Command interface
     *
     * @param   dao                 Data access object
     * @return  CommandResult
     */
    public AppMessage execute(AppStorage dao);
}
