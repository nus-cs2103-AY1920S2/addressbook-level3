package seedu.address.logic.parser.parserAdd;

import seedu.address.logic.commands.commandAdd.AddCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * An abstract class for all addcommand parsers
 */
public abstract class AddCommandParser implements Parser<AddCommand> {
    public abstract AddCommand parse(String args) throws ParseException;
}
