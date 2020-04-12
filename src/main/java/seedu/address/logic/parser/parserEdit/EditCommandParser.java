package seedu.address.logic.parser.parserEdit;

import seedu.address.logic.commands.commandEdit.EditCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public abstract class EditCommandParser implements Parser<EditCommand> {

    public abstract EditCommand parse(String args) throws ParseException;

}
