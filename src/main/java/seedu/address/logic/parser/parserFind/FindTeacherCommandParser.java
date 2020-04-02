package seedu.address.logic.parser.parserFind;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import seedu.address.logic.commands.commandFind.FindTeacherCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.modelStaff.StaffNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindTeacherCommandParser implements Parser<FindTeacherCommand> {

  /**
   * Parses the given {@code String} of arguments in the context of the FindCommand and returns a
   * FindCommand object for execution.
   *
   * @throws ParseException if the user input does not conform the expected format
   */
  public FindTeacherCommand parse(String args) throws ParseException {
    String trimmedArgs = args.trim();
    if (trimmedArgs.isEmpty()) {
      throw new ParseException(
          String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTeacherCommand.MESSAGE_USAGE));
    }

    String[] nameKeywords = trimmedArgs.split("\\s+");

    return new FindTeacherCommand(
        new StaffNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
  }

}
