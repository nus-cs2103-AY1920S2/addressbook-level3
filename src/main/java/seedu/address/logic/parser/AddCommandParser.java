package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CURRENT_WEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TARGET_WEIGHT;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.Address;
import seedu.address.model.client.Birthday;
import seedu.address.model.client.Client;
import seedu.address.model.client.CurrentWeight;
import seedu.address.model.client.Email;
import seedu.address.model.client.Gender;
import seedu.address.model.client.Height;
import seedu.address.model.client.Name;
import seedu.address.model.client.PersonalBest;
import seedu.address.model.client.Phone;
import seedu.address.model.client.Remark;
import seedu.address.model.client.Sport;
import seedu.address.model.client.TargetWeight;
import seedu.address.model.exercise.UniqueExerciseList;
import seedu.address.model.schedule.ScheduleList;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    public static final String EMPTY_ATTRIBUTE = "";

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME,
                PREFIX_GENDER, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_TAG, PREFIX_BIRTHDAY, PREFIX_CURRENT_WEIGHT, PREFIX_TARGET_WEIGHT,
                PREFIX_HEIGHT, PREFIX_REMARK, PREFIX_SPORT);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME,
                PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Optional<String> genderString = argMultimap.getValue(PREFIX_GENDER);
        Gender gender = genderString.isPresent()
                ? ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get())
                : new Gender(EMPTY_ATTRIBUTE);
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        // start of optional attributes
        Optional<String> birthdayString = argMultimap.getValue(PREFIX_BIRTHDAY);
        Birthday birthday = birthdayString.isPresent()
                ? ParserUtil.parseBirthday(birthdayString.get())
                : new Birthday(EMPTY_ATTRIBUTE);
        Optional<String> heightString = argMultimap.getValue(PREFIX_HEIGHT);
        Height height = heightString.isPresent()
                ? ParserUtil.parseHeight(heightString.get())
                : new Height(EMPTY_ATTRIBUTE);
        Optional<String> currentWeightString = argMultimap.getValue(PREFIX_CURRENT_WEIGHT);
        CurrentWeight currentWeight = currentWeightString.isPresent()
                ? ParserUtil.parseCurrentWeight(currentWeightString.get())
                : new CurrentWeight(EMPTY_ATTRIBUTE);
        Optional<String> targetWeightString = argMultimap.getValue(PREFIX_TARGET_WEIGHT);
        TargetWeight targetWeight = targetWeightString.isPresent()
                ? ParserUtil.parseTargetWeight(targetWeightString.get())
                : new TargetWeight(EMPTY_ATTRIBUTE);
        Optional<String> remarkString = argMultimap.getValue(PREFIX_REMARK);
        Remark remark = remarkString.isPresent()
                ? ParserUtil.parseRemark(remarkString.get())
                : new Remark(EMPTY_ATTRIBUTE);
        Set<Sport> sportList = ParserUtil.parseSports(argMultimap.getAllValues(PREFIX_SPORT));
        UniqueExerciseList exerciseList = new UniqueExerciseList();
        PersonalBest personalBest = new PersonalBest();
        ScheduleList scheduleList = new ScheduleList();
        Client client = new Client(name, gender, phone, email, address, tagList, birthday,
                currentWeight, targetWeight, height, remark, sportList, exerciseList, personalBest,
                scheduleList);

        return new AddCommand(client);
    }

    /*
     * Returns true if none of the prefixes contains empty {@code Optional} values
     * in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
