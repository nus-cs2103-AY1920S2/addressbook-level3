package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Parcel.comment.Comment;
import seedu.address.model.Parcel.itemtype.TypeOfItem;
import seedu.address.model.Parcel.ParcelAttributes.Address;
import seedu.address.model.Parcel.order.CashOnDelivery;
import seedu.address.model.Parcel.ParcelAttributes.Email;
import seedu.address.model.Parcel.ParcelAttributes.Name;
import seedu.address.model.Parcel.ParcelAttributes.Phone;
import seedu.address.model.Parcel.ParcelAttributes.TimeStamp;
import seedu.address.model.Parcel.ParcelAttributes.TransactionId;
import seedu.address.model.Parcel.ParcelAttributes.Warehouse;
import seedu.address.storage.CsvProcessor;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {
    public static final String MESSAGE_INVALID_CSV_FILEPATH = "The csv file is not found in the data folder";
    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    private static final Logger logger = LogsCenter.getLogger(ParserUtil.class);

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
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
     * Parses a {@code String tid} into a {@code TransactionId}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tid} is invalid.
     */
    public static TransactionId parseTid(String tid) throws ParseException {
        requireNonNull(tid);
        String trimmedTid = tid.trim();
        if (!TransactionId.isValidTid(trimmedTid)) {
            throw new ParseException(TransactionId.MESSAGE_CONSTRAINTS);
        }
        return new TransactionId(trimmedTid);
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
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

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
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
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
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
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
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
     * Parses a {@code String timeStamp} into an {@code TimeStamp}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code timeStamp} is invalid.
     */
    public static TimeStamp parseTimeStamp(String timeStamp) throws ParseException {
        requireNonNull(timeStamp);
        String trimmedTimeStamp = timeStamp.trim();
        logger.fine("Checking whether it is valid timestamp");
        if (!TimeStamp.isValidTimeStamp(trimmedTimeStamp)) {
            logger.info("Invalid timestamp: " + trimmedTimeStamp);
            throw new ParseException(TimeStamp.MESSAGE_CONSTRAINTS);
        }
        return new TimeStamp(trimmedTimeStamp);
    }

    /**
     * Parses a {@code String address} into an {@code Warehouse}.
     * Leading and trailing whitespace will be removed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Warehouse parseWarehouse(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Warehouse.isValidAddress(trimmedAddress)) {
            throw new ParseException(Warehouse.MESSAGE_CONSTRAINTS);
        }
        return new Warehouse(trimmedAddress);
    }

    /**
     * Parses a {@code String cod} into a {@code CashOnDelivery}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code cod} is invalid.
     */
    public static CashOnDelivery parseCash(String cod) throws ParseException {
        requireNonNull(cod);
        String trimmedCash = cod.trim();
        if (!CashOnDelivery.isValidCashValue(trimmedCash)) {
            throw new ParseException(CashOnDelivery.MESSAGE_CONSTRAINTS);
        }
        return new CashOnDelivery(trimmedCash);
    }

    /**
     * Parses a {@code String comment} into a {@code Comment}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code comment} is invalid.
     */
    public static Comment parseComment(String comment) throws ParseException {
        requireNonNull(comment);
        String trimmedComment = comment.trim();
        logger.fine("Check if it is a valid comment" + trimmedComment);
        if (!Comment.isValidComment(trimmedComment)) {
            logger.info("Invalid Comment encountered: " + trimmedComment);
            throw new ParseException(Comment.MESSAGE_CONSTRAINTS);
        }
        return new Comment(trimmedComment);
    }

    /**
     * Parses a {@code String itemType} into a {@code TypeOfItem}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code itemType} is invalid.
     */
    public static TypeOfItem parseItemType(String itemType) throws ParseException {
        requireNonNull(itemType);
        String itemTypeTrimmed = itemType.trim();
        if (!TypeOfItem.isValidItemType(itemTypeTrimmed)) {
            throw new ParseException(TypeOfItem.MESSAGE_CONSTRAINTS);
        }

        return new TypeOfItem(itemTypeTrimmed);
    }

    /**
     * Parses a {@code String filePath} into a {@code List<String>}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code filePath} is invalid or the csv file unable to read.
     */
    public static List<String> parseCsvFile(String filePath) throws ParseException {
        requireNonNull(filePath);
        String filePathTrimmed = filePath.trim();

        logger.fine("Getting the path of the file: " + filePath);
        Path csvFilePath = Paths.get("data", filePathTrimmed);
        logger.fine("Checking whether the file exists: " + filePath);
        if (!FileUtil.isFileExists(csvFilePath)) {
            logger.info("File not exists: " + filePath);
            throw new ParseException(MESSAGE_INVALID_CSV_FILEPATH);
        }
        return CsvProcessor.retrieveData(csvFilePath);
    }

}
