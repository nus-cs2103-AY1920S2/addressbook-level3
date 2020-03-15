package seedu.eylah.expensesplitter.logic;

//import java.nio.file.Path;

//import javafx.collections.ObservableList;
//import seedu.eylah.commons.core.GuiSettings;
import seedu.eylah.expensesplitter.logic.commands.CommandResult;
import seedu.eylah.expensesplitter.logic.commands.exceptions.CommandException;
import seedu.eylah.expensesplitter.logic.parser.exceptions.ParseException;

/**
 * API of the Logic component
 */
public interface Logic {

    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;
}
