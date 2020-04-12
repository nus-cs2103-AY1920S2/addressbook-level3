package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.ScheduleList;

/**
 * Adds the training schedule of the client in FitBiz.
 */
public class ScheduleCommand extends Command {

    public static final List<Prefix> PREFIXES = List.of(PREFIX_SCHEDULE);

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds the training schedule of the client, identified "
            + "by the index number used in the displayed client list. "
            + "The schedule should include the first 3 letters of the day, \n"
            + "start and end time in 24 hour format, "
            + "in 1-minute denominations. Multiple training schedule can be added to a client.\n"
            + "Parameters: INDEX (a positive integer) sch/DAY-START_TIME-END_TIME\n"
            + "Example: " + COMMAND_WORD + " 1 sch/MON-1100-1200";

    public static final String MESSAGE_INVALID_ARG_COUNT = "Invalid number of arguments found for adding schedules."
            + "Please check you have entered the right amount of Day(s), Start Time(s) and End Time(s). You have"
            + " entered %1$s Day(s), %2$s Start Time(s), %3$s End Time(s).";

    public static final String MESSAGE_CONTAINS_DUPLICATES = "One or more of your input schedules have overlapping"
            + " time periods. Please check again.";

    public static final String MESSAGE_SUCCESS = "%1$.15s's overall schedule has been changed to: \n%2$s";
    public static final String MESSAGE_CLEARED = "%1$.15s's schedule has been cleared.";

    private final Index index;
    private final ArrayList<Schedule> toAdd;


    public ScheduleCommand(Index index, ArrayList<Schedule> scheduleList) {
        requireNonNull(scheduleList);
        this.index = index;
        this.toAdd = scheduleList;
    }

    /**
     * @author @Dban1
     */
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

        // if toAdd is empty, meaning the schedule has been cleared
        if (toAdd.isEmpty()) {
            assert(toAdd.size() == 0);
            return new CommandResult(String.format(MESSAGE_CLEARED, editedClient.getName().fullName));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedClient.getName().fullName,
                editedClient.getScheduleList().toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleCommand // instance of handles nulls
                && toAdd.equals(((ScheduleCommand) other).toAdd)
                && index.equals(((ScheduleCommand) other).index));
    }
}
