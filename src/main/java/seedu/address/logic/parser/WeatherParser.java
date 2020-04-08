package seedu.address.logic.parser;

import java.util.HashMap;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.diary.weather.CloudyWeather;
import seedu.address.model.diary.weather.RainyWeather;
import seedu.address.model.diary.weather.SunnyWeather;
import seedu.address.model.diary.weather.Weather;
import seedu.address.model.diary.weather.WindyWeather;

/**
 * Dummy java docs.
 */
public class WeatherParser {
    private static HashMap<String, Weather> weatherIdentifiers = new HashMap<>();

    static {
        weatherIdentifiers.put("cloudy", new CloudyWeather());
        weatherIdentifiers.put("rainy", new RainyWeather());
        weatherIdentifiers.put("sunny", new SunnyWeather());
        weatherIdentifiers.put("windy", new WindyWeather());
    }

    /**
     * Dummy java docs.
     * @param weatherString
     * @return
     * @throws IllegalValueException
     */
    public Weather parseWeather(String weatherString) throws IllegalValueException {
        if (weatherIdentifiers.get(weatherString) == null) {
            throw new IllegalValueException("Please enter a valid weather!");
        }
        return weatherIdentifiers.get(weatherString);
    }
}
