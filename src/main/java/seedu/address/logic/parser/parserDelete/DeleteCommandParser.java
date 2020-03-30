package seedu.address.logic.parser.parserDelete;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.commandDelete.DeleteCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Abstract Parser for Delete Commands. All variants will extend off this one.
 */
public abstract class DeleteCommandParser implements Parser<DeleteCommand> {
  // TODO: Consider to have 1 DeleteCommand which will take in a variety of AddressBooks and delete from them instead, no need so much repeated code
  public abstract DeleteCommand parse(String args) throws ParseException;
}
