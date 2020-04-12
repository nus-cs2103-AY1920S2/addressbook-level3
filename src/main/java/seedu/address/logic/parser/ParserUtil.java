package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.modelObjectTags.*;
import seedu.address.model.modelStaff.Staff;
import seedu.address.model.tag.Tag;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "ID is not an existing non-zero unsigned integer in the list.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    public static Staff.Level parseLevel(String level) throws ParseException {
        requireNonNull(level);
        String trimmedLevel = level.trim().toUpperCase();
        if (!Staff.isValidLevel(trimmedLevel)) {
            throw new ParseException(Staff.MESSAGE_CONSTRAINTS);
        }
        if (trimmedLevel.equals("TEACHER")) {
            return Staff.Level.TEACHER;
        } else {
            return Staff.Level.ADMIN;
        }
    }

    /**
     * Parses a {@code String gender} into a {@code gender}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Gender parseGender(String gender) throws ParseException {
        requireNonNull(gender);
        String trimmedGender = gender.trim();
        if (!Gender.isValidGender(trimmedGender)) {
            throw new ParseException(Gender.MESSAGE_CONSTRAINTS);
        }
        return new Gender(trimmedGender);
    }

    /**
     * Parses a {@code String financeType} into a {@code financeType}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code financeType} is invalid.
     */
    public static FinanceType parseFinanceType(String financeType) throws ParseException {
        requireNonNull(financeType);
        String trimmedFinanceType = financeType.trim();
        if (!FinanceType.isValidFinanceType(trimmedFinanceType)) {
            throw new ParseException(FinanceType.MESSAGE_CONSTRAINTS);
        }
        return new FinanceType(trimmedFinanceType);
    }


    /**
     * Parses a {@code String courseID} into a {@code ID}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code courseID} is invalid.
     */
    public static ID parseID(String StringID) throws ParseException {
        requireNonNull(StringID);
        String trimmedID = StringID.trim();
        if (!ID.isValidId(trimmedID)) {
            throw new ParseException(ID.MESSAGE_CONSTRAINTS);
        }
        return new ID(trimmedID);
    }


    /**
     * Parses a {@code String assignmentID} into a {@code ID}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code assignmentID} is invalid.
     */
    public static ID parseAssignmentID(String assignmentID) throws ParseException {
        requireNonNull(assignmentID);
        String trimmedAssignmentID = assignmentID.trim();
        if (!ID.isValidId(trimmedAssignmentID)) {
            throw new ParseException(ID.MESSAGE_CONSTRAINTS);
        }
        return new ID(trimmedAssignmentID);
    }

    /**
     * Parses a {@code String courseid} into a {@code Courseid}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code courseid} is invalid.
     */
    public static ID parseCourseid(String courseid) throws ParseException {
        requireNonNull(courseid);
        String trimmedCourseid = courseid.trim();
        if (!ID.isValidId(courseid)) {
            throw new ParseException(ID.MESSAGE_CONSTRAINTS);
        }
        return new ID(courseid);
    }

    /**
     * Parses a {@code String studentid} into a {@code Studentid}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code studentid} is invalid.
     */
    public static ID parseStudentid(String studentid) throws ParseException {
        requireNonNull(studentid);
        String trimmedStudentid = studentid.trim();
        if (!ID.isValidId(studentid)) {
            throw new ParseException(ID.MESSAGE_CONSTRAINTS);
        }
        return new ID(studentid);
    }

    /**
     * Parses a {@code String staffid} into a {@code Staffid}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code staffid} is invalid.
     */
    public static ID parseStaffid(String staffid) throws ParseException {
        requireNonNull(staffid);
        String trimmedStaffid = staffid.trim();
        if (!ID.isValidId(staffid)) {
            throw new ParseException(ID.MESSAGE_CONSTRAINTS);
        }
        return new ID(staffid);
    }

    /**
     * Parses a {@code String financeid} into a {@code Financeid}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code financeid} is invalid.
     */
    public static ID parseFinanceid(String financeid) throws ParseException {
        requireNonNull(financeid);
        String timmedFinanceid = financeid.trim();
        if (!ID.isValidId(timmedFinanceid)) {
            throw new ParseException(ID.MESSAGE_CONSTRAINTS);
        }
        return new ID(timmedFinanceid);
    }


    /**
     * Parses a {@code String amount} into a {@code Amount}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code amount} is invalid.
     */
    public static Amount parseAmount(String amount) throws ParseException {
        requireNonNull(amount);
        String trimmedAmount = amount.trim();
        if (!Amount.isValidAmount(trimmedAmount)) {
            throw new ParseException(Amount.MESSAGE_CONSTRAINTS);
        }
        return new Amount(trimmedAmount);
    }

    /**
     * Parses a {@code String assignedStaff} into a {@code AssignedStaff}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code assignedStaff} is invalid.
     */
    public static AssignedStaff parseAssignedStaff(String assignedStaff) throws ParseException {
        requireNonNull(assignedStaff);
        String trimmedAssignedStaff = assignedStaff.trim();
        if (!AssignedStaff.isValidAssignedStaff(assignedStaff)) {
            throw new ParseException(AssignedStaff.MESSAGE_CONSTRAINTS);
        }
        return new AssignedStaff(assignedStaff);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}. Leading and trailing whitespaces will
     * be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String Salary} into an {@code Salary}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code salary} is invalid.
     */
    public static Salary parseSalary(String salary) throws ParseException {
        requireNonNull(salary);
        String trimmedSalary = salary.trim();
        if (!Salary.isValidSalary(trimmedSalary)) {
            throw new ParseException(Salary.MESSAGE_CONSTRAINTS);
        }
        return new Salary(trimmedSalary);
    }

    /**
     * Parses a {@code String deadline} into a {@code Deadline}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code deadline} is invalid.
     */
    public static Deadline parseDeadline(String deadline) throws ParseException {
        requireNonNull(deadline);
        String trimmedDeadline = deadline.trim();
        if (!Deadline.isValidDeadline(trimmedDeadline)) {
            throw new ParseException(Deadline.MESSAGE_CONSTRAINTS);
        }
        return new Deadline(trimmedDeadline);
    }

    /**
     * Parses a {@code String date} into a {@code Date}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Date.isValidDate(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }
        return new Date(trimmedDate);
    }

    /**
     * Parses a {@code String} into a {@code string}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code string} is invalid.
     */
    public static String parseString(String string) throws ParseException {
        requireNonNull(string);
        return string.trim();
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
