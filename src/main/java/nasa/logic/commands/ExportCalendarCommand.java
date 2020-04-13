package nasa.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.collections.ObservableList;

import nasa.logic.calendar.IcsCalendar;
import nasa.logic.commands.exceptions.CommandException;
import nasa.model.Model;
import nasa.model.activity.Deadline;
import nasa.model.activity.Event;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;

//@@author kester-ng
/**
 * Exports current calendar to ics format.
 * Represents the command for exporting of calendar.
 */
public class ExportCalendarCommand extends Command {

    public static final String COMMAND_WORD = "calendar";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports calendar to .ics format\n"
        + "Parameters: FILEPATH\n"
        + "Example: " + COMMAND_WORD + " f/./Data";

    public static final String MESSAGE_FAILURE = "Failed to export calendar!";

    public static final String MESSAGE_SUCCESS = "Successfully exported calendar!";

    private Path filepath;

    /**
     * Constructor.
     * @param filepath filepath to write the ics file to
     */
    public ExportCalendarCommand(Path filepath) {
        this.filepath = filepath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (filepath == null) {
            filepath = model.getUserPrefs().getCalendarExportPath();
        }

        try {
            model.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
            model.updateFilteredActivityList(Model.PREDICATE_SHOW_ALL_ACTIVITIES);
            ObservableList<Module> modules = model.getFilteredModuleList();
            HashMap<ModuleCode, ArrayList<Deadline>> deadlines = new HashMap<>();
            HashMap<ModuleCode, ArrayList<Event>> events = new HashMap<>();
            for (Module module : modules) {
                ArrayList<Deadline> moduleDeadlines = new ArrayList<>(module.getFilteredDeadlineList());
                ArrayList<Event> moduleEvents = new ArrayList<>(module.getFilteredEventList());
                deadlines.put(module.getModuleCode(), moduleDeadlines);
                events.put(module.getModuleCode(), moduleEvents);
            }
            IcsCalendar.writeToIcsFile(filepath, deadlines, events);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CommandException(MESSAGE_FAILURE);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ExportCalendarCommand)) {
            return false;
        }

        if (filepath == null) {
            return ((ExportCalendarCommand) other).filepath == null;
        }

        return filepath.equals(((ExportCalendarCommand) other).filepath);
    }
}
