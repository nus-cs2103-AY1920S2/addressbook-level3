package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.AppCommandResult;
import seedu.address.logic.conditions.Conditions;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contact tracing application logic rendering
 */
public interface AppLogic<T> {
    /**
     * Fetches all pings stored in model
     * @return
     */
    public ObservableList<T> getAll();

    /**
     * Same as {@code AppLogic} but returns pings on filter condition
     * @return
     */
    public ObservableList<T> filterBy(Conditions<T> cond);

    public AppCommandResult execute(String command) throws ParseException;
}
