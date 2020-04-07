package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.assignment.Deadline;
import seedu.address.model.assignment.Status;
import seedu.address.model.assignment.Title;
import seedu.address.model.assignment.Workload;
import seedu.address.model.event.Duration;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventTitle;
import seedu.address.model.event.Place;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Organization;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.restaurant.Cuisine;
import seedu.address.model.restaurant.Hours;
import seedu.address.model.restaurant.Location;
import seedu.address.model.restaurant.Note;
import seedu.address.model.restaurant.Price;
import seedu.address.model.restaurant.Visit;
import seedu.address.model.tag.Tag;

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
     * Parses a {@code String birthday} into a {@code Birthday}.
     *
     * @throws ParseException if the given {@code Birthday} is invalid.
     */
    public static Birthday parseBirthday(String birthday) throws ParseException {
        requireNonNull(birthday);
        String trimmedBirthday = birthday.trim();
        if (!Birthday.isValidDate(trimmedBirthday)) {
            throw new ParseException(Birthday.MESSAGE_CONSTRAINTS);
        }
        return new Birthday(trimmedBirthday);
    }

    /**
     * Parses a {@code String organization} into a {@code Organization}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code organization} is invalid.
     */
    public static Organization parseOrganization(String organization) throws ParseException {
        requireNonNull(organization);
        String trimmedOrganization = organization.trim();
        return new Organization(trimmedOrganization);
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
     * Parses a {@code String birthday} into a {@code Birthday}.
     *
     * @throws ParseException if the given {@code Birthday} is invalid.
     */
    public static seedu.address.model.restaurant.Name parseNameR(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!seedu.address.model.restaurant.Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new seedu.address.model.restaurant.Name(trimmedName);
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
     * Parses a {@code String remark} into a {@code Remark}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code remark} is invalid.
     */
    public static Remark parseRemark(String remark) throws ParseException {
        requireNonNull(remark);
        String trimmedRemark = remark.trim();
        if (!Remark.isValidRemark(trimmedRemark)) {
            throw new ParseException(Remark.MESSAGE_CONSTRAINTS);
        }
        return new Remark(trimmedRemark);
    }

    /**
     * Parses {@code Collection<String> remarks} into a {@code ArrayList<Remark>}.
     */
    public static ArrayList<Remark> parseRemarks(Collection<String> remarks) throws ParseException {
        requireNonNull(remarks);
        final ArrayList<Remark> remarkArrayList = new ArrayList<>();
        for (String remarkName : remarks) {
            remarkArrayList.add(parseRemark(remarkName));
        }
        return remarkArrayList;
    }

    /**
     * Parses a {@code String note} into a {@code Note}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code note} is invalid.
     */
    public static Note parseNote(String note) throws ParseException {
        requireNonNull(note);
        String trimmedNote = note.trim();

        return new Note(trimmedNote);
    }

    /**
     * Parses {@code Collection<String> notes} into a {@code ArrayList<Note>}.
     */
    public static ArrayList<Note> parseNotes(Collection<String> notes) throws ParseException {
        requireNonNull(notes);
        final ArrayList<Note> noteArrayList = new ArrayList<>();
        for (String noteName : notes) {
            noteArrayList.add(parseNote(noteName));
        }
        return noteArrayList;
    }

    /**
     * Parses a {@code String line} into a {@code Integer}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code line} is invalid.
     */
    public static Integer parseLine(String line) throws ParseException {
        requireNonNull(line);
        String trimmedLine = line.trim();

        return Integer.parseInt(trimmedLine);
    }

    /**
     * Parses {@code Collection<String> remarks} into a {@code ArrayList<Remark>}.
     */
    public static ArrayList<Integer> parseLines(Collection<String> lines) throws ParseException {
        requireNonNull(lines);
        final ArrayList<Integer> lineArrayList = new ArrayList<>();
        for (String line : lines) {
            lineArrayList.add(parseLine(line));
        }
        return lineArrayList;
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
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

    /**
     * Parses a {@code String location} into a {@code Location}.
     *
     * @throws ParseException if the given {@code Location} is invalid.
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
     * Parses a {@code String hours} into a {@code Hours}.
     *
     * @throws ParseException if the given {@code Hours} is invalid.
     */
    public static Hours parseHours(String hours) throws ParseException {
        requireNonNull(hours);
        String trimmedHours = hours.trim();
        if (!Hours.isValidHours(trimmedHours)) {
            throw new ParseException(Hours.MESSAGE_CONSTRAINTS);
        }
        return new Hours(trimmedHours);
    }

    /**
     * Parses a {@code String price} into a {@code Price}.
     *
     * @throws ParseException if the given {@code Price} is invalid.
     */
    public static Price parsePrice(String price) throws ParseException {
        requireNonNull(price);
        String trimmedPrice = price.trim();
        if (!Price.isValidPrice(trimmedPrice)) {
            throw new ParseException(Price.MESSAGE_CONSTRAINTS);
        }
        return new Price(trimmedPrice);
    }

    /**
     * Parses a {@code String cuisine} into a {@code Cuisine}.
     *
     * @throws ParseException if the given {@code Cuisine} is invalid.
     */
    public static Cuisine parseCuisine(String cuisine) throws ParseException {
        requireNonNull(cuisine);
        String trimmedCuisine = cuisine.trim();
        if (!Cuisine.isValidCuisine(trimmedCuisine)) {
            throw new ParseException(Cuisine.MESSAGE_CONSTRAINTS);
        }
        return new Cuisine(trimmedCuisine);
    }

    /**
     * Parses a {@code String visit} into a {@code Visit}.
     *
     * @throws ParseException if the given {@code Visit} is invalid.
     */
    public static Visit parseVisit(String visit) throws ParseException {
        requireNonNull(visit);
        String trimmedVisit = visit.trim();
        if (!Visit.isValidVisit(trimmedVisit)) {
            throw new ParseException(Visit.MESSAGE_CONSTRAINTS);
        }
        return new Visit(trimmedVisit);
    }

    /**
     * Parses a {@code String deadline} into a {@code Deadline}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code deadline} is invalid.
     */
    public static Deadline parseDeadline(String deadline) throws ParseException {
        requireNonNull(deadline);
        String trimmedDeadline = deadline.trim();

        if (!Deadline.isValidDeadline(trimmedDeadline) || Deadline.hasDeadlinePassed(trimmedDeadline)) {
            throw new ParseException(Deadline.MESSAGE_CONSTRAINTS);
        }

        return new Deadline(trimmedDeadline);
    }

    /**
     * Parses a {@code String title} into a {@code Title}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code title} is invalid.
     */
    public static Title parseTitle(String title) throws ParseException {
        requireNonNull(title);
        String trimmedTitle = title.trim();

        if (!Title.isValidTitle(trimmedTitle)) {
            throw new ParseException(Title.MESSAGE_CONSTRAINTS);
        }
        return new Title(trimmedTitle);
    }

    /**
     * Parses a {@code String deadline} into a {@code Deadline}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code deadline} is invalid.
     */
    public static Workload parseWorkload(String estTime) throws ParseException {
        requireNonNull(estTime);
        String trimmedEstTime = estTime.trim();

        if (!Workload.isValidWorkload(trimmedEstTime)) {
            throw new ParseException(Workload.MESSAGE_CONSTRAINTS);
        }
        return new Workload(trimmedEstTime);
    }

    /**
     * Parses a {@code String duration} into an {@code Duration}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code duration} is invalid.
     */
    public static Duration parseDuration(String duration) throws ParseException {
        requireNonNull(duration);
        String trimmedDuration = duration.trim();

        if (!Duration.isValidDuration(trimmedDuration)) {
            throw new ParseException(Duration.MESSAGE_CONSTRAINTS);
        }
        return new Duration(trimmedDuration);
    }

    /**
     * Parses a {@code String eventTitle} into an {@code EventTitle}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code EventTitle} is invalid.
     */
    public static EventTitle parseEventTitle(String eventTitle) throws ParseException {
        requireNonNull(eventTitle);
        String trimmedEventTitle = eventTitle.trim();

        if (!EventTitle.isValidEventTitle(trimmedEventTitle)) {
            throw new ParseException(EventTitle.MESSAGE_CONSTRAINTS);
        }
        return new EventTitle(trimmedEventTitle);
    }

    /**
     * Parses a {@code String eventDate} into an {@code EventDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code EventDate} is invalid.
     */
    public static EventDate parseEventDate(String eventDate) throws ParseException {
        requireNonNull(eventDate);
        String trimmedEventDate = eventDate.trim();

        if (!EventDate.isValidEventDate(trimmedEventDate) || EventDate.hasEventDatePassed(trimmedEventDate)) {
            throw new ParseException(EventDate.MESSAGE_CONSTRAINTS);
        }

        return new EventDate(trimmedEventDate);
    }

    /**
     * Parses a {@code String place} into an {@code Place}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Place} is invalid.
     */
    public static Place parsePlace(String place) {
        requireNonNull(place);
        String trimmedPlace = place.trim(); // there is no invalid value for place.

        return new Place(trimmedPlace);
    }

    /**
     * Parses a {@code String status} into a {@code Status}.
     * Leading and training whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Status} is invalid.
     */
    public static Status parseStatus(String status) throws ParseException {
        requireNonNull(status);
        String trimmedStatus = status.trim();

        if (!Status.isValidStatus(trimmedStatus)) {
            throw new ParseException(Status.MESSAGE_CONSTRAINTS);
        }

        return new Status(trimmedStatus);
    }
}
