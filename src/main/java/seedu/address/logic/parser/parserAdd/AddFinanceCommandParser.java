package seedu.address.logic.parser.parserAdd;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSEID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FINANCETYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEACHERID;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.commandAdd.AddCommand;
import seedu.address.logic.commands.commandAdd.AddFinanceCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.modelFinance.Finance;
import seedu.address.model.modelTeacher.Teacher;
import seedu.address.model.person.Amount;
import seedu.address.model.person.Courseid;
import seedu.address.model.person.Date;
import seedu.address.model.person.FinanceType;
import seedu.address.model.person.Name;
import seedu.address.model.person.Studentid;
import seedu.address.model.person.Teacherid;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddFinanceCommandParser extends AddCommandParser {

  /**
   * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
   * ArgumentMultimap}.
   */
  private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
    return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
  }

  /**
   * Parses the given {@code String} of arguments in the context of the AddCommand and returns an
   * AddCommand object for execution.
   *
   * @return
   * @throws ParseException if the user input does not conform the expected format
   */
  public AddCommand parse(String args) throws ParseException {
    ArgumentMultimap argMultimap =
        ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_FINANCETYPE, PREFIX_DATE, PREFIX_AMOUNT, PREFIX_COURSEID, PREFIX_STUDENTID, PREFIX_TEACHERID, PREFIX_TAG);

    //If finance type is misc, then Name, Date, Amount must be provided
    //Else if finance type is CourseStudent, then Date, CourseID, StudentID must be provided
    //Else if finance type is CourseTeacher, then Date, CourseID, TeacherID must be provided
    if (!arePrefixesPresent(argMultimap, PREFIX_FINANCETYPE, PREFIX_DATE)
        || !argMultimap.getPreamble().isEmpty()) {
      throw new ParseException(
          String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFinanceCommand.MESSAGE_USAGE));
    }
    FinanceType financeType = ParserUtil.parseFinanceType(argMultimap.getValue(PREFIX_FINANCETYPE).get());
    Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
    Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
    Courseid emptyCourseid = new Courseid("");
    Studentid emptyStudentid = new Studentid("");
    Teacherid emptyTeacherid = new Teacherid("");

    switch (financeType.toString()) {
      case "m": {
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_AMOUNT)
            || !argMultimap.getPreamble().isEmpty()) {
          throw new ParseException(
              String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFinanceCommand.MESSAGE_USAGE));
        }
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        Finance finance = new Finance(name, financeType, date, amount, emptyCourseid,
            emptyStudentid, emptyTeacherid, tagList);
        return new AddFinanceCommand(finance);
      }
      case "cs": {
        if (!arePrefixesPresent(argMultimap, PREFIX_COURSEID, PREFIX_STUDENTID)
            || !argMultimap.getPreamble().isEmpty()) {
          throw new ParseException(
              String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFinanceCommand.MESSAGE_USAGE));
        }
        Courseid courseid = (Courseid) ParserUtil
            .parseCourseid(argMultimap.getValue(PREFIX_COURSEID).get());
        Studentid studentid = (Studentid) ParserUtil
            .parseStudentid(argMultimap.getValue(PREFIX_STUDENTID).get());
        Name name = new Name(
            String.format("Student %s has paid for Course %s", studentid, courseid));
        Amount amount = new Amount("99999");
        Finance finance = new Finance(name, financeType, date, amount, courseid, studentid,
            emptyTeacherid, tagList);
        return new AddFinanceCommand(finance);
      }
      case "ct": {
        if (!arePrefixesPresent(argMultimap, PREFIX_COURSEID, PREFIX_TEACHERID)
            || !argMultimap.getPreamble().isEmpty()) {
          throw new ParseException(
              String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFinanceCommand.MESSAGE_USAGE));
        }
        Courseid courseid = (Courseid) ParserUtil
            .parseCourseid(argMultimap.getValue(PREFIX_COURSEID).get());
        Teacherid teacherid = (Teacherid) ParserUtil
            .parseTeacherid(argMultimap.getValue(PREFIX_TEACHERID).get());
        Name name = new Name(
            String.format("Teacher %s has been paid for Course %s", teacherid, courseid));
        Amount amount = new Amount("99999");
        Finance finance = new Finance(name, financeType, date, amount, courseid, emptyStudentid,
            teacherid, tagList);
        return new AddFinanceCommand(finance);
      }
      default:
        throw new ParseException(
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFinanceCommand.MESSAGE_USAGE));
    }
  }

}
