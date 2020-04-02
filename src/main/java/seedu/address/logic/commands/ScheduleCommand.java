package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CLIENTS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.ScheduleList;

/**
 * Adds the training schedule of the client in FitBiz.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds the training schedule of the client, identified "
            + "by the index number used in the displayed client list. "
            + "The schedule should include the first 3 letters of the day, \n"
            + "start and end time in 24 hour format, "
            + "in 1-minute denominations. Multiple training schedule can be added to a client.\n"
            + "Parameters: INDEX (a positive integer) d/DAY st/START_TIME et/END_TIME [MORE_SCHEDULE]...\n"
            + "Example: " + COMMAND_WORD + " 1 day/mon st/1200 et/1400 day/fri st/1330 et/1500";

    public static final String MESSAGE_INVALID_ARG_COUNT = "Invalid number of arguments found for adding schedules."
            + "Please check you have entered the right amount of Day(s), Start Time(s) and End Time(s). You have"
            + " entered %1$s Day(s), %2$s Start Time(s), %3$s End Time(s).";

    public static final String MESSAGE_CONTAINS_DUPLICATES = "One or more of your input schedules have overlapping"
            + " time periods. Please check again.";

    public static final String MESSAGE_SUCCESS = "Training schedule has been added for %1$s: \n%2$s";

    private final Index index;
    private final ArrayList<Schedule> toAdd;


    public ScheduleCommand(Index index, ArrayList<Schedule> scheduleList) {
        requireNonNull(scheduleList);
        this.index = index;
        this.toAdd = scheduleList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Client> lastShownList = model.getFilteredClientList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
        }

        Client clientToEdit = lastShownList.get(index.getZeroBased());
        ScheduleList newScheduleList = new ScheduleList();
        newScheduleList.setSchedule(toAdd);
        Client editedClient = new Client(clientToEdit.getName(), clientToEdit.getGender(), clientToEdit.getPhone(),
                clientToEdit.getEmail(), clientToEdit.getAddress(), clientToEdit.getTags(), clientToEdit.getBirthday(),
                clientToEdit.getCurrentWeight(), clientToEdit.getTargetWeight(), clientToEdit.getHeight(),
                clientToEdit.getRemark(), clientToEdit.getSports(), clientToEdit.getExerciseList(),
                clientToEdit.getPersonalBest(), newScheduleList);
        model.setClient(clientToEdit, editedClient);
        model.updateFilteredClientList(PREDICATE_SHOW_ALL_CLIENTS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, editedClient.getName().fullName,
                editedClient.getScheduleList().toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleCommand // instanceof handles nulls
                && toAdd.equals(((ScheduleCommand) other).toAdd)
                && index.equals(((ScheduleCommand) other).index));
    }
}
