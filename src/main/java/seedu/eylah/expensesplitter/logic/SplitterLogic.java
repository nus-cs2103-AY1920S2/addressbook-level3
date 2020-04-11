package seedu.eylah.expensesplitter.logic;

import seedu.eylah.commons.logic.Logic;
import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.commons.logic.command.exception.CommandException;
import seedu.eylah.commons.logic.parser.exception.ParseException;

/**
 * API of the SplitterLogic component
 */
public interface SplitterLogic extends Logic {

    @Override
    CommandResult execute(String commandText) throws CommandException, ParseException;
}
