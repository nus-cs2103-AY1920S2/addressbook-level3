package seedu.address.logic.parser.parserDelete;

import seedu.address.logic.commands.commandDelete.DeleteCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Abstract Parser for Delete Commands. All variants will extend off this one.
 */
public abstract class DeleteCommandParser implements Parser<DeleteCommand> {
    // TODO: Consider to have 1 DeleteCommand which will take in a variety of AddressBooks and delete from them instead, no need so much repeated code
    public abstract DeleteCommand parse(String args) throws ParseException;
}
