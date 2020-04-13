package nasa.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;

import nasa.model.Model;
import nasa.model.activity.Deadline;
import nasa.model.activity.Event;
import nasa.model.module.Module;

/**
 * Command to refresh the page.
 */
public class RefreshCommand extends Command {

    public static final String COMMAND_WORD = "refresh";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": refresh data.\n"
            + "Parameters: none\nExample: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Refreshed!";

    public static final String MESSAGE_FAILURE = "Refreshed failed!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // show all activities and modules
        model.updateFilteredActivityList(model.PREDICATE_SHOW_ALL_ACTIVITIES);
        model.updateFilteredModuleList(model.PREDICATE_SHOW_ALL_MODULES);

        try {
            // now update all deadlines and events
            ObservableList<Module> modules = model.getFilteredModuleList();
            for (Module module : modules) {
                ObservableList<Deadline> deadlines = module.getFilteredDeadlineList();
                ObservableList<Event> events = module.getFilteredEventList();
                for (Deadline deadline : deadlines) {
                    if (deadline.isOverdue()) {
                        model.setDeadline(module.getModuleCode(), deadline, new Deadline(deadline.getName(),
                            deadline.getDateCreated(), deadline.getNote(),
                                deadline.getPriority(), deadline.getDueDate(),
                            false)); // auto set it to overdue
                    }
                }
                for (Event event : events) {
                    if (event.isOver()) {
                        model.setEvent(module.getModuleCode(), event, new Event(event.getName(),
                            event.getStartDate(), event.getEndDate(), event.getNote())); // auto set to its done
                    }
                }
            }
        } catch (Exception e) {
            return new CommandResult(MESSAGE_FAILURE);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
