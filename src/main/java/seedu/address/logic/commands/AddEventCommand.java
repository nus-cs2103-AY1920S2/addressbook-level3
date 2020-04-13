package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.group.Group;
import seedu.address.model.person.ActivityList;
import seedu.address.model.person.Person;
import seedu.address.model.person.PlaceList;
import seedu.address.model.person.Time;
import seedu.address.model.person.TimeList;

/**
 * Represents the command to add a new event to CoderLifeInsights.
 */
public class AddEventCommand extends Command {

    public static final String COMMAND_WORD = "add_event";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates an event with a group or an individual "
            + "that adds an activity, place and time to the subject. \n"
            + "Parameters: ACTIVITY "
            + PREFIX_PLACE
            + "PLACE "
            + PREFIX_MEMBER
            + "INDEX "
            + PREFIX_TIME
            + "TIME \n"
            + "OR \n"
            + "Parameters: ACTIVITY "
            + PREFIX_PLACE
            + "PLACE "
            + PREFIX_GROUP
            + "INDEX "
            + PREFIX_TIME
            + "TIME \n"
            + "Example: "
            + COMMAND_WORD
            + " Dancing "
            + PREFIX_PLACE
            + "SCAPE "
            + PREFIX_MEMBER
            + "1 "
            + PREFIX_TIME
            + "300";

    public static final String MESSAGE_INVALID_TIME_INPUT =
            "Time parameter needs to be unsigned integers of at least 2 digits.\n"
            + "For example: "
            + "[5 minutes = 05]; "
            + "[1 hour = 100]; "
            + "[10 hours and 30 minutes = 1030]";
    public static final String MESSAGE_INVALID_TIME = "Time parameter has to be greater than 0 minutes.";
    public static final String MESSAGE_SUCCESS = "New event successfully added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "Event with given arguments already exists. Please try again.";
    public static final String MESSAGE_ARGUMENTS = "Activity: %1$s, Index: %2$d, Place: %3$s, Time: %4$s";

    private static final Logger logger = LogsCenter.getLogger(AddEventCommand.class);
    private final Event toAdd;

    public AddEventCommand(Event event) {
        requireAllNonNull(event);
        this.toAdd = event;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);

        if (toAdd.getTime().equals(new Time(0, 0))) {
            throw new CommandException(MESSAGE_INVALID_TIME);
        }

        if (model.hasEvent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        if (toAdd.getWithPerson().isPresent()) {
            // edit person
            List<Person> lastShownList = model.getFilteredPersonList();
            int index = toAdd.getWithPerson().get();
            if (index - 1 >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            Person toEdit = lastShownList.get(index - 1);
            logger.info("Person to edit is " + toEdit.getName().toString());

            Time curr = toEdit.getTime();
            Time newTime = curr.addTime2(toAdd.getTime().getMinutes(), toAdd.getTime().getHours());

            PlaceList currentPlaceList = toEdit.getPlaceList2();
            PlaceList newPlaceList = currentPlaceList.addPlace(toAdd.getPlace());

            ActivityList currentActivityList = toEdit.getActivityList2();
            ActivityList newActivityList = currentActivityList.addActivity(toAdd.getActivity());

            TimeList currentTimeList = toEdit.getTimeList();
            TimeList newTimeList = currentTimeList.addTime(toAdd.getTime().toString());

            Person editedPerson = new Person(toEdit.getName(), toEdit.getPhone(), toEdit.getEmail(),
                    toEdit.getAddress(), toEdit.getTags(), newTime, newPlaceList, newActivityList, newTimeList);
            logger.info("Edited person is " + editedPerson.getName().toString());
            model.setPerson(toEdit, editedPerson);
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
            model.addEvent(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } else {
            // edit group
            List<Group> lastShownList = model.getFilteredGroupList();
            int index = toAdd.getWithGroup().get();
            if (index - 1 >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
            }
            Group toEdit = lastShownList.get(index - 1);

            Time curr = toEdit.getTimeSpent();
            Time newTime = curr.addTime2(toAdd.getTime().getMinutes(), toAdd.getTime().getHours());

            PlaceList currentPlaceList = toEdit.getPlaceList();
            PlaceList newPlaceList = currentPlaceList.addPlace(toAdd.getPlace());

            ActivityList currentActivityList = toEdit.getActivityList();
            ActivityList newActivityList = currentActivityList.addActivity(toAdd.getActivity());

            TimeList currentTimeList = toEdit.getTimeList();
            TimeList newTimeList = currentTimeList.addTime(toAdd.getTime().toString());

            Group editedGroup = new Group(toEdit.getName(), newPlaceList, newActivityList, newTimeList);
            editedGroup.setMemberIDs(toEdit.getMembers());
            editedGroup.setTimeSpent(newTime);
            editedGroup.setMemberIDs(toEdit.getMembers());
            ArrayList<Integer> events = toEdit.getEvents();
            events.add(toAdd.getEventId());
            editedGroup.setEventIDs(events);
            model.setGroup(toEdit, editedGroup);
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
            model.addEvent(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), ViewType.GROUPS);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddEventCommand)) {
            return false;
        }

        // state check
        AddEventCommand e = (AddEventCommand) other;
        return this.toAdd.getActivity().equalsIgnoreCase(e.toAdd.getActivity())
                && this.toAdd.getPlace().equalsIgnoreCase(e.toAdd.getPlace())
                && this.toAdd.getTime().equals(e.toAdd.getTime())
                && this.toAdd.getWithGroup().equals(e.toAdd.getWithGroup())
                && this.toAdd.getWithPerson().equals(e.toAdd.getWithPerson());
    }
}
