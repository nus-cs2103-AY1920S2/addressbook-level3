package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENTRY_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEATHER;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.diarycommand.DiaryWeatherCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.diary.weather.SunnyWeather;
import seedu.address.model.diary.weather.Weather;

/**
 * Dummy java docs.
 */
public class DiaryWeatherCommandParser implements Parser<DiaryWeatherCommand> {

    /**
     * Dummy java docs.
     * @param args
     * @return
     * @throws ParseException
     */
    public DiaryWeatherCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ENTRY_ID, PREFIX_WEATHER);

        if (!arePrefixesPresent(argMultimap, PREFIX_ENTRY_ID, PREFIX_WEATHER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DiaryWeatherCommand.MESSAGE_USAGE));
        }

        String entryIdString = argMultimap.getValue(PREFIX_ENTRY_ID).get();
        String weatherString = argMultimap.getValue(PREFIX_WEATHER).get();
        int entryId = 0;
        Weather weather = new SunnyWeather();

        try {
            entryId = Integer.parseInt(entryIdString);
        } catch (NumberFormatException e) {
            throw new ParseException("Please enter an integer!");
        }

        try {
            weather = new WeatherParser().parseWeather(weatherString);
        } catch (IllegalValueException e) {
            throw new ParseException("Please enter a valid weather!");
        }

        return new DiaryWeatherCommand(entryId, weather);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
