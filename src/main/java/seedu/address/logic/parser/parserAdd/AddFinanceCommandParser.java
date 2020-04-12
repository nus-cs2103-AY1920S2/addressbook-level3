package seedu.address.logic.parser.parserAdd;

import seedu.address.logic.commands.commandAdd.AddCommand;
import seedu.address.logic.commands.commandAdd.AddFinanceCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.modelFinance.Finance;
import seedu.address.model.modelObjectTags.*;
import seedu.address.model.tag.Tag;

import java.util.Set;
import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

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
        //Else if finance type is CourseStaff, then Date, CourseID, StaffID must be provided
        if (!arePrefixesPresent(argMultimap, PREFIX_FINANCETYPE)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFinanceCommand.MESSAGE_FINANCETYPE + AddFinanceCommand.MESSAGE_USAGE));
        } else if (!arePrefixesPresent(argMultimap, PREFIX_DATE)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFinanceCommand.MESSAGE_DATE + AddFinanceCommand.MESSAGE_USAGE));
        } else if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFinanceCommand.MESSAGE_USAGE));
        }

        FinanceType financeType = ParserUtil.parseFinanceType(argMultimap.getValue(PREFIX_FINANCETYPE).get());
        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        ID emptyCourseid = new ID("");
        ID emptyStudentid = new ID("");
        ID emptyStaffid = new ID("");

        switch (financeType.toString()) {
            case "m": {
                if (!arePrefixesPresent(argMultimap, PREFIX_NAME)) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFinanceCommand.MESSAGE_NAME + AddFinanceCommand.MESSAGE_USAGE));
                } else if (!arePrefixesPresent(argMultimap, PREFIX_AMOUNT)) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFinanceCommand.MESSAGE_AMOUNT + AddFinanceCommand.MESSAGE_USAGE));
                } else if (!argMultimap.getPreamble().isEmpty()) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFinanceCommand.MESSAGE_USAGE));
                }

                Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
                Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
                Finance finance = new Finance(name, financeType, date, amount, emptyCourseid,
                        emptyStudentid, emptyStaffid, tagList);
                return new AddFinanceCommand(finance);
            }
            case "cs": {
                if (!arePrefixesPresent(argMultimap, PREFIX_COURSEID)) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFinanceCommand.MESSAGE_COURSEID + AddFinanceCommand.MESSAGE_USAGE));
                } else if (!arePrefixesPresent(argMultimap, PREFIX_STUDENTID)) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFinanceCommand.MESSAGE_STUDENTID + AddFinanceCommand.MESSAGE_USAGE));
                } else if (!argMultimap.getPreamble().isEmpty()) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFinanceCommand.MESSAGE_USAGE));
                }

                ID courseid = ParserUtil
                        .parseCourseid(argMultimap.getValue(PREFIX_COURSEID).get());
                ID studentid = ParserUtil
                        .parseStudentid(argMultimap.getValue(PREFIX_STUDENTID).get());
                Name name = new Name("None");
                Amount amount = new Amount("999");
                Finance finance = new Finance(name, financeType, date, amount, courseid, studentid,
                        emptyStaffid, tagList);
                return new AddFinanceCommand(finance);
            }
            case "ct": {
                if (!arePrefixesPresent(argMultimap, PREFIX_COURSEID)) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFinanceCommand.MESSAGE_COURSEID + AddFinanceCommand.MESSAGE_USAGE));
                } else if (!arePrefixesPresent(argMultimap, PREFIX_TEACHERID)) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFinanceCommand.MESSAGE_TEACHERID + AddFinanceCommand.MESSAGE_USAGE));
                } else if (!argMultimap.getPreamble().isEmpty()) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFinanceCommand.MESSAGE_USAGE));
                }

                ID courseid = ParserUtil
                        .parseCourseid(argMultimap.getValue(PREFIX_COURSEID).get());
                ID staffid = ParserUtil
                        .parseStaffid(argMultimap.getValue(PREFIX_TEACHERID).get());
                Name name = new Name("None");
                Amount amount = new Amount("999");
                Finance finance = new Finance(name, financeType, date, amount, courseid, emptyStudentid,
                        staffid, tagList);
                return new AddFinanceCommand(finance);
            }
            default:
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFinanceCommand.MESSAGE_USAGE));
        }
    }

}
