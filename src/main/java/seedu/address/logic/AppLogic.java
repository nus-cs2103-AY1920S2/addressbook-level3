package seedu.address.logic;

import seedu.address.logic.messages.AppMessage;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contact tracing application logic rendering
 */
public interface AppLogic<T> {
    public AppMessage execute(String command) throws ParseException;
}
