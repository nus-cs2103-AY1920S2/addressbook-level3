package seedu.address.logic.commands.diarycommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENTRY_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEATHER;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.diary.weather.Weather;

/**
 * Represents the command that tags a diary entry with weather.
 */
public class DiaryWeatherCommand extends Command {

    public static final String COMMAND_WORD = "diaryWeather";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Allows for tagging a diary entry with a specific weather "
            + "Parameters: "
            + PREFIX_ENTRY_ID + "ENTRY ID"
            + PREFIX_WEATHER + "WEATHER"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ENTRY_ID + "1"
            + PREFIX_WEATHER + "sunny";

    public static final String MESSAGE_SUCCESS = "weather recorded";

    private final int entryId;
    private final Weather weather;

    public DiaryWeatherCommand(int entryId, Weather weather) {
        this.entryId = entryId;
        this.weather = weather;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.isValidEntryId(entryId)) {
            throw new CommandException("The diary entry ID is not in range!");
        }

        model.tagWeather(entryId, weather);
        String messageResult = "Weather recorded.";
        return new CommandResult(messageResult);
    }
}
