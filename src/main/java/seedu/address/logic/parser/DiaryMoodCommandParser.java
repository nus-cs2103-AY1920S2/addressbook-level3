package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENTRY_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MOOD;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.diarycommand.DiaryDeleteCommand;
import seedu.address.logic.commands.diarycommand.DiaryMoodCommand;
import seedu.address.logic.commands.diarycommand.DiaryWeatherCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.diary.DiaryEntry;
import seedu.address.model.diary.mood.Mood;
import seedu.address.model.diary.mood.StressedMood;
import seedu.address.model.diary.weather.SunnyWeather;
import seedu.address.model.diary.weather.Weather;
import seedu.address.model.nusmodule.Grade;
import seedu.address.model.nusmodule.ModuleCode;
import seedu.address.model.nusmodule.NusModule;

public class DiaryMoodCommandParser implements Parser<DiaryMoodCommand> {

    public DiaryMoodCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ENTRY_ID, PREFIX_MOOD);

        if (!arePrefixesPresent(argMultimap, PREFIX_ENTRY_ID, PREFIX_MOOD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DiaryMoodCommand.MESSAGE_USAGE));
        }

        String entryIdString = argMultimap.getValue(PREFIX_ENTRY_ID).get();
        String moodString = argMultimap.getValue(PREFIX_MOOD).get();
        int entryId = 0;
        Mood mood = new StressedMood();

        try {
            entryId = Integer.parseInt(entryIdString);
        } catch (NumberFormatException e) {
            throw new ParseException("Please enter an integer!");
        }

        try {
            mood = new MoodParser().parseMood(moodString);
        } catch (IllegalValueException e) {
            throw new ParseException("Please enter a valid mood!");
        }

        return new DiaryMoodCommand(entryId, mood);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}