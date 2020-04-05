package seedu.address.logic.parser;

import java.util.HashMap;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.diary.weather.CloudyWeather;
import seedu.address.model.diary.weather.RainyWeather;
import seedu.address.model.diary.weather.SunnyWeather;
import seedu.address.model.diary.weather.Weather;
import seedu.address.model.diary.weather.WindyWeather;

public class WeatherParser {
    private static HashMap<String, Weather> WEATHER_IDENTIFIERS = new HashMap<>();

    static {
        WEATHER_IDENTIFIERS.put("cloudy", new CloudyWeather());
        WEATHER_IDENTIFIERS.put("rainy", new RainyWeather());
        WEATHER_IDENTIFIERS.put("sunny", new SunnyWeather());
        WEATHER_IDENTIFIERS.put("windy", new WindyWeather());
    }

    public Weather parseWeather(String weatherString) throws IllegalValueException {
        if (WEATHER_IDENTIFIERS.get(weatherString) == null) {
            throw new IllegalValueException("Please enter a valid weather!");
        }
        return WEATHER_IDENTIFIERS.get(weatherString);
    }
}
