package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSEID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FINANCEID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEACHERID;

import java.util.Arrays;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.modelAssignment.AssignmentIDContainsKeywordsPredicate;
import seedu.address.model.modelCourse.CourseIDContainsKeywordsPredicate;
import seedu.address.model.modelFinance.FinanceIDContainsKeywordsPredicate;
import seedu.address.model.modelStudent.StudentIDContainsKeywordsPredicate;
import seedu.address.model.modelTeacher.TeacherIDContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class SelectCommandParser implements Parser<SelectCommand> {

  /**
   * Parses the given {@code String} of arguments in the context of the FindCommand and returns a
   * FindCommand object for execution.
   *
   * @throws ParseException if the user input does not conform the expected format
   */
  public SelectCommand parse(String args) throws ParseException {
    requireNonNull(args);
    ArgumentMultimap argMultimap =
        ArgumentTokenizer
            .tokenize(args, PREFIX_STUDENTID, PREFIX_TEACHERID, PREFIX_COURSEID, PREFIX_FINANCEID,
                PREFIX_ASSIGNMENTID);

    if (argMultimap.getValue(PREFIX_STUDENTID).isPresent()) {
      String[] nameKeywords = ParserUtil.parseString(argMultimap.getValue(PREFIX_STUDENTID).get())
          .split("\\s+");
      return new SelectCommand(
          new StudentIDContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    } else if (argMultimap.getValue(PREFIX_TEACHERID).isPresent()) {
      String[] nameKeywords = ParserUtil.parseString(argMultimap.getValue(PREFIX_TEACHERID).get())
          .split("\\s+");
      return new SelectCommand(
          new TeacherIDContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    } else if (argMultimap.getValue(PREFIX_COURSEID).isPresent()) {
      String[] nameKeywords = ParserUtil.parseString(argMultimap.getValue(PREFIX_COURSEID).get())
          .split("\\s+");
      return new SelectCommand(
          new CourseIDContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    } else if (argMultimap.getValue(PREFIX_FINANCEID).isPresent()) {
      String[] nameKeywords = ParserUtil.parseString(argMultimap.getValue(PREFIX_FINANCEID).get())
          .split("\\s+");
      return new SelectCommand(
          new FinanceIDContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    } else if (argMultimap.getValue(PREFIX_ASSIGNMENTID).isPresent()) {
      String[] nameKeywords = ParserUtil
          .parseString(argMultimap.getValue(PREFIX_ASSIGNMENTID).get()).split("\\s+");
      return new SelectCommand(
          new AssignmentIDContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    } else {
      throw new ParseException(
          String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
    }
  }
}