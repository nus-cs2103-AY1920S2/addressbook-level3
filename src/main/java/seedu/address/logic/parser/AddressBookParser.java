package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import seedu.address.logic.commands.CalculateEarningsFinanceCommand;
import seedu.address.logic.commands.CalculateExpensesFinanceCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.commandAssign.AssignCommandBase;
import seedu.address.logic.commands.commandAdd.AddCommand;
import seedu.address.logic.commands.commandAdd.AddCourseCommand;
import seedu.address.logic.commands.commandAdd.AddFinanceCommand;
import seedu.address.logic.commands.commandAdd.AddStudentCommand;
import seedu.address.logic.commands.commandAdd.AddTeacherCommand;
import seedu.address.logic.commands.commandClear.ClearCommand;
import seedu.address.logic.commands.commandClear.ClearCourseCommand;
import seedu.address.logic.commands.commandClear.ClearFinanceCommand;
import seedu.address.logic.commands.commandClear.ClearStudentCommand;
import seedu.address.logic.commands.commandClear.ClearTeacherCommand;
import seedu.address.logic.commands.commandDelete.DeleteCommand;
import seedu.address.logic.commands.commandDelete.DeleteCourseCommand;
import seedu.address.logic.commands.commandDelete.DeleteFinanceCommand;
import seedu.address.logic.commands.commandDelete.DeleteStudentCommand;
import seedu.address.logic.commands.commandDelete.DeleteTeacherCommand;
import seedu.address.logic.commands.commandEdit.EditCommand;
import seedu.address.logic.commands.commandEdit.EditCourseCommand;
import seedu.address.logic.commands.commandEdit.EditFinanceCommand;
import seedu.address.logic.commands.commandEdit.EditStudentCommand;
import seedu.address.logic.commands.commandEdit.EditTeacherCommand;
import seedu.address.logic.commands.commandFind.FindCommand;
import seedu.address.logic.commands.commandFind.FindCourseCommand;
import seedu.address.logic.commands.commandFind.FindFinanceCommand;
import seedu.address.logic.commands.commandFind.FindStudentCommand;
import seedu.address.logic.commands.commandFind.FindTeacherCommand;
import seedu.address.logic.commands.commandList.ListCommand;
import seedu.address.logic.commands.commandList.ListCourseCommand;
import seedu.address.logic.commands.commandList.ListFinanceCommand;
import seedu.address.logic.commands.commandList.ListStudentCommand;
import seedu.address.logic.commands.commandList.ListTeacherCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.parserAdd.AddCommandParser;
import seedu.address.logic.parser.parserAdd.AddCourseCommandParser;
import seedu.address.logic.parser.parserAdd.AddFinanceCommandParser;
import seedu.address.logic.parser.parserAdd.AddStudentCommandParser;
import seedu.address.logic.parser.parserAdd.AddTeacherCommandParser;
import seedu.address.logic.parser.parserDelete.DeleteCommandParser;
import seedu.address.logic.parser.parserDelete.DeleteCourseCommandParser;
import seedu.address.logic.parser.parserDelete.DeleteFinanceCommandParser;
import seedu.address.logic.parser.parserDelete.DeleteStudentCommandParser;
import seedu.address.logic.parser.parserDelete.DeleteTeacherCommandParser;
import seedu.address.logic.parser.parserEdit.EditCommandParser;
import seedu.address.logic.parser.parserEdit.EditCourseCommandParser;
import seedu.address.logic.parser.parserEdit.EditFinanceCommandParser;
import seedu.address.logic.parser.parserEdit.EditStudentCommandParser;
import seedu.address.logic.parser.parserEdit.EditTeacherCommandParser;
import seedu.address.logic.parser.parserFind.FindCommandParser;
import seedu.address.logic.parser.parserFind.FindCourseCommandParser;
import seedu.address.logic.parser.parserFind.FindFinanceCommandParser;
import seedu.address.logic.parser.parserFind.FindStudentCommandParser;
import seedu.address.logic.parser.parserFind.FindTeacherCommandParser;

/**
 * Parses user input.
 */
public class AddressBookParser {

  /**
   * Used for initial separation of command word and args.
   */
  private static final Pattern BASIC_COMMAND_FORMAT = Pattern
      .compile("(?<commandWord>\\S+)(?<arguments>.*)");

  /**
   * Parses user input into command for execution.
   *
   * @param userInput full user input string
   * @return the command based on the user input
   * @throws ParseException if the user input does not conform the expected format
   */
  public Command parseCommand(String userInput) throws ParseException {
    final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
    if (!matcher.matches()) {
      throw new ParseException(
          String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    final String commandWord = matcher.group("commandWord");
    final String arguments = matcher.group("arguments");
    switch (commandWord) {

      case AssignCommandBase.COMMAND_WORD:
        return new AssignCommandParser().parse(arguments);

      case AddTeacherCommand.COMMAND_WORD:
        return new AddTeacherCommandParser().parse(arguments);

      case AddStudentCommand.COMMAND_WORD:
        return new AddStudentCommandParser().parse(arguments);

      case AddFinanceCommand.COMMAND_WORD:
        return new AddFinanceCommandParser().parse(arguments);

      case AddCourseCommand.COMMAND_WORD:
        return new AddCourseCommandParser().parse(arguments);

      case DeleteTeacherCommand.COMMAND_WORD:
        return new DeleteTeacherCommandParser().parse(arguments);

      case DeleteStudentCommand.COMMAND_WORD:
        return new DeleteStudentCommandParser().parse(arguments);

      case DeleteFinanceCommand.COMMAND_WORD:
        return new DeleteFinanceCommandParser().parse(arguments);

      case DeleteCourseCommand.COMMAND_WORD:
        return new DeleteCourseCommandParser().parse(arguments);

      case FindTeacherCommand.COMMAND_WORD:
        return new FindTeacherCommandParser().parse(arguments);

      case FindStudentCommand.COMMAND_WORD:
        return new FindStudentCommandParser().parse(arguments);

      case FindFinanceCommand.COMMAND_WORD:
        return new FindFinanceCommandParser().parse(arguments);

      case FindCourseCommand.COMMAND_WORD:
        return new FindCourseCommandParser().parse(arguments);

      case CalculateExpensesFinanceCommand.COMMAND_WORD:
        return new CalculateExpensesFinanceCommand();

      case CalculateEarningsFinanceCommand.COMMAND_WORD:
        return new CalculateEarningsFinanceCommand();

      case ListTeacherCommand.COMMAND_WORD:
        return new ListTeacherCommand();

      case ListStudentCommand.COMMAND_WORD:
        return new ListStudentCommand();

      case ListFinanceCommand.COMMAND_WORD:
        return new ListFinanceCommand();

      case ListCourseCommand.COMMAND_WORD:
        return new ListCourseCommand();

      case ClearTeacherCommand.COMMAND_WORD:
        return new ClearTeacherCommand();

      case ClearStudentCommand.COMMAND_WORD:
        return new ClearStudentCommand();

      case ClearFinanceCommand.COMMAND_WORD:
        return new ClearFinanceCommand();

      case ClearCourseCommand.COMMAND_WORD:
        return new ClearCourseCommand();

      case EditTeacherCommand.COMMAND_WORD:
        return new EditTeacherCommandParser().parse(arguments);

      case EditStudentCommand.COMMAND_WORD:
        return new EditStudentCommandParser().parse(arguments);

      case EditFinanceCommand.COMMAND_WORD:
        return new EditFinanceCommandParser().parse(arguments);

      case EditCourseCommand.COMMAND_WORD:
        return new EditCourseCommandParser().parse(arguments);

      case AddCommand.COMMAND_WORD:
        return new AddCommandParser().parse(arguments);

      case EditCommand.COMMAND_WORD:
        return new EditCommandParser().parse(arguments);

      case DeleteCommand.COMMAND_WORD:
        return new DeleteCommandParser().parse(arguments);

      case ClearCommand.COMMAND_WORD:
        return new ClearCommand();

      case FindCommand.COMMAND_WORD:
        return new FindCommandParser().parse(arguments);

      case ListCommand.COMMAND_WORD:
        return new ListCommand();

      case ExitCommand.COMMAND_WORD:
        return new ExitCommand();

      case HelpCommand.COMMAND_WORD:
        return new HelpCommand();

      default:
        throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
    }
  }

}
