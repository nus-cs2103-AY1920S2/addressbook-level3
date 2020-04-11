package fithelper.logic.parser;

import static java.util.Objects.requireNonNull;

import fithelper.commons.core.index.Index;
import fithelper.commons.util.StringUtil;
import fithelper.logic.parser.exceptions.ParseException;
import fithelper.model.diary.Content;
import fithelper.model.diary.DiaryDate;
import fithelper.model.entry.Calorie;
import fithelper.model.entry.Duration;
import fithelper.model.entry.Location;
import fithelper.model.entry.Name;
import fithelper.model.entry.Remark;
import fithelper.model.entry.SortBy;
import fithelper.model.entry.Status;
import fithelper.model.entry.Time;
import fithelper.model.entry.Type;
import fithelper.model.profile.Address;
import fithelper.model.profile.Age;
import fithelper.model.profile.Gender;
import fithelper.model.profile.Height;
import fithelper.model.profile.TargetWeight;
import fithelper.model.weight.Date;
import fithelper.model.weight.WeightValue;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
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
     * Parses a {@code String type} into a {@code Type}.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the given {@code type} is invalid.
     */
    public static Type parseType(String type) throws ParseException {
        requireNonNull(type);
        String trimmedType = type.trim();
        if (!Type.isValidType(trimmedType)) {
            throw new ParseException(Type.MESSAGE_CONSTRAINTS);
        }
        return new Type(trimmedType);
    }

    /**
     * Parses a {@code String} representing sorting criterion into a {@code SortBy} and returns it.
     *
     * @param sortBy sort criterion
     * @throws ParseException if the given {@code SortBy} is invalid.
     */
    public static SortBy parseSortBy(String sortBy) throws ParseException {
        requireNonNull(sortBy);
        String trimmed = sortBy.trim();
        if (!SortBy.isValidCategory(trimmed)) {
            throw new ParseException(SortBy.MESSAGE_CONSTRAINTS);
        }
        return new SortBy(trimmed);
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
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
     * Parses a {@code String location} into an {@code Location}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code location} is invalid.
     */
    public static Time parseTime(String time) throws ParseException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        if (!Time.isValidTime(trimmedTime)) {
            throw new ParseException(Time.MESSAGE_CONSTRAINTS);
        }
        return new Time(trimmedTime);
    }

    /**
     * Parses a {@code String location} into an {@code Location}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code location} is invalid.
     */
    public static DiaryDate parseDate(String dateStr) throws ParseException {
        requireNonNull(dateStr);
        String trimmedDate = dateStr.trim();
        if (!DiaryDate.isValidDate(trimmedDate)) {
            throw new ParseException(DiaryDate.MESSAGE_ERROR);
        }
        return new DiaryDate(trimmedDate);
    }

    /**
     * Parses a {@code String location} into an {@code Location}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code location} is invalid.
     */
    public static Location parseLocation(String location) throws ParseException {
        requireNonNull(location);
        String trimmedLocation = location.trim();
        if (!Location.isValidLocation(trimmedLocation)) {
            throw new ParseException(Location.MESSAGE_CONSTRAINTS);
        }
        return new Location(trimmedLocation);
    }

    /**
     * Parses a {@code String calorie} into an {@code Calorie}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code calorie} is invalid.
     */
    public static Calorie parseCalorie(String calorie) throws ParseException {
        requireNonNull(calorie);
        String trimmedCalorie = calorie.trim();
        if (!Calorie.isValidCalorie(trimmedCalorie)) {
            throw new ParseException(Calorie.MESSAGE_CONSTRAINTS);
        }
        return new Calorie(trimmedCalorie);
    }

    /**
     * Parses a {@code String status} into an {@code Status}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code status} is invalid.
     */
    public static Status parseStatus(String status) throws ParseException {
        requireNonNull(status);
        String trimmedStatus = status.trim();
        // capitalise the first letter in trimmedStatus
        trimmedStatus = trimmedStatus.substring(0, 1).toUpperCase() + trimmedStatus.substring(1);
        // lowercase the rest of the letters in trimmedStatus
        trimmedStatus = trimmedStatus.substring(0, 1) + trimmedStatus.substring(1).toLowerCase();
        if (!Status.isValidStatus(trimmedStatus)) {
            throw new ParseException(Status.MESSAGE_CONSTRAINTS);
        }
        return new Status(trimmedStatus);
    }

    /**
     * Parses a {@code String remark} into an {@code Remark}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Remark parseRemark(String remark) {
        requireNonNull(remark);
        String trimmedRemark = remark.trim();
        return new Remark(trimmedRemark);
    }


    /**
     * Parses a {@code String remark} into an {@code Content}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Content parseContent(String content) {
        requireNonNull(content);
        String trimmedContent = content.trim();
        return new Content(trimmedContent);
    }

    /**
     * Parses a {@code String duration} into an {@code Duration}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Duration parseDuration(String duration) {
        requireNonNull(duration);
        String trimmedDuration = duration.trim();
        return new Duration(trimmedDuration);
    }

    // methods related to profile

    /**
     * Parses a {@code String name} into a {@code Name} in user Profile.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static fithelper.model.profile.Name parseProfileName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!fithelper.model.profile.Name.isValidName(trimmedName)) {
            throw new ParseException(fithelper.model.profile.Name.MESSAGE_CONSTRAINTS);
        }
        return new fithelper.model.profile.Name(trimmedName);
    }

    /**
     * Parses a {@code String gender} into a {@code Gender} in user Profile.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the given {@code gender} is invalid.
     */
    public static Gender parseProfileGender(String gender) throws ParseException {
        requireNonNull (gender);
        String trimmedGender = gender.trim();
        if (!Gender.isValidGender(trimmedGender)) {
            throw new ParseException(Gender.MESSAGE_CONSTRAINTS);
        }
        return new Gender(trimmedGender);
    }

    /**
     * Parses a {@code String age} into a {@code Age} in user Profile.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the given {@code age} is invalid.
     */
    public static Age parseProfileAge(String age) throws ParseException {
        requireNonNull (age);
        String trimmedAge = age.trim();
        if (!Age.isValidAge(trimmedAge)) {
            throw new ParseException(Age.MESSAGE_CONSTRAINTS);
        }
        return new Age(trimmedAge);
    }

    /**
     * Parses a {@code String address} into a {@code Address} in user Profile.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseProfileAddress(String address) throws ParseException {
        requireNonNull (address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String height} into a {@code Height} in user Profile.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the given {@code height} is invalid.
     */
    public static Height parseProfileHeight(String height) throws ParseException {
        requireNonNull (height);
        String trimmedHeight = height.trim();
        if (!Height.isValidHeight(trimmedHeight)) {
            throw new ParseException(Height.MESSAGE_CONSTRAINTS);
        }
        return new Height(trimmedHeight);
    }

    /**
     * Parses a {@code String targetWeight} into a {@code TargetWeight} in user Profile.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the given {@code targetWeight} is invalid.
     */
    public static TargetWeight parseProfileTargetWeight(String targetWeight) throws ParseException {
        requireNonNull (targetWeight);
        String trimmedTargetWeight = targetWeight.trim();
        if (!TargetWeight.isValidTargetWeight(trimmedTargetWeight)) {
            throw new ParseException(TargetWeight.MESSAGE_CONSTRAINTS);
        }
        return new TargetWeight(trimmedTargetWeight);
    }


    // methods related to weight

    /**
     * Parses a {@code String date} into a {@code Date} in weight.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseWeightDate(String date) throws ParseException {
        requireNonNull (date);
        String trimmedDate = date.trim();
        if (!Date.isValidDate(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }
        return new Date(trimmedDate);
    }

    /**
     * Parses a {@code String weightValue} into a {@code WeightValue} in weight.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the given {@code weightValue} is invalid.
     */
    public static WeightValue parseWeightValue(String weightValue) throws ParseException {
        requireNonNull (weightValue);
        String trimmedWeightValue = weightValue.trim();
        if (!WeightValue.isValidWeightValue(trimmedWeightValue)) {
            throw new ParseException(WeightValue.MESSAGE_CONSTRAINTS);
        }
        return new WeightValue(trimmedWeightValue);
    }

    /**
     * Parses a {@code String} representing the sort order into a boolean indicating
     * if the order is ascending (otherwise is descending).
     *
     * @param order sort order (ascending or descending)
     * @throws ParseException if input order is invalid
     */
    public static boolean parseSortOrder(String order) throws ParseException {
        requireNonNull (order);
        if ("a".equals(order)) {
            return true;
        } else if ("d".equals(order)) {
            return false;
        } else {
            throw new ParseException("Sort order can only be ascending (a) or descending (d)");
        }
    }
}
