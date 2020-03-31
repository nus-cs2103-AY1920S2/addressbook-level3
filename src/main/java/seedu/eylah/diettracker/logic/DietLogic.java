package seedu.eylah.diettracker.logic;

import seedu.eylah.commons.logic.Logic;
import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.commons.logic.command.exception.CommandException;
import seedu.eylah.commons.logic.parser.exception.ParseException;

/**
 * API of the Logic component
 */
public interface DietLogic extends Logic {

    @Override
    CommandResult execute(String commandText) throws CommandException, ParseException;

}
