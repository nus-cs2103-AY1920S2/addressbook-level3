package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.model.person.RecentEvent;

/**
 * Displays various insights regarding the persons, groups
 * and events in the AddressBook.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String KEYWORD_PLACE = "places";
    public static final String KEYWORD_ACTIVITY = "activities";
    public static final String KEYWORD_RECENT = "recent";
    public static final String KEYWORD_ALL = "all";
    public static final String KEYWORD_TIME = "time";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays various insights "
            + "regarding the persons, groups and events in the AddressBook.\n"
            + "Parameters: INSIGHT_PARAMETER [m/INDEX] [g/INDEX]\n"
            + "Whether [m/INDEX] or [g/INDEX] are required depends on the insight parameter.\n"
            + "Possible insight parameters: \"" + KEYWORD_PLACE + "\", \"" + KEYWORD_ACTIVITY
            + "\", \"" + KEYWORD_RECENT + "\", \"" + KEYWORD_ALL + "\", or \"" + KEYWORD_TIME + "\".\n"
            + "Examples:\n" + COMMAND_WORD + " " + KEYWORD_PLACE + " m/1\n"
            + COMMAND_WORD + " " + KEYWORD_ACTIVITY + " g/1\n"
            + COMMAND_WORD + " " + KEYWORD_RECENT;

    public static final String MESSAGE_ALL_TIME = "Displaying total group time vs individual time spent.\n";
    public static final String MESSAGE_ALL_EVENTS = "Displaying all events.\n";
    public static final String MESSAGE_PLACE = "Displaying places visited with ";
    public static final String MESSAGE_ACTIVITY = "Displaying activities done with ";
    public static final String MESSAGE_RECENT_ALL = "Listing last 5 events.";
    public static final String MESSAGE_RECENT_PERSON = "Listing last 5 events with ";
    public static final String MESSAGE_INVALID_PARAMETER = "You have entered an invalid insight parameter.\n";
    public static final String MESSAGE_EXTRA_PREFIX = "Please choose either a member OR a group to view.\n";
    public static final String MESSAGE_INDEX_REQUIRED = "This insight parameter requires you "
            + "to indicate either a person or group.\n";
    public static final String MESSAGE_INDEX_FORBIDDEN = "This insight parameter cannot be "
            + "accompanied by a person or group.\n";
    public static final int MAX_EVENTS = 5;

    private final Index index;
    private final String parameter;
    private final InsightType type;

    public ViewCommand(Index index, String parameter, InsightType type) {
        requireNonNull(index);
        requireNonNull(parameter);

        this.index = index;
        this.parameter = parameter;
        this.type = type;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (type.equals(InsightType.ALL)) {
            return processTypeAll(model);
        } else if (type.equals(InsightType.PERSON)) {
            return processTypePerson(model);
        } else {
            assert type.equals(InsightType.GROUP);
            return processTypeGroup(model);
        }
    }

    public Index getIndex() {
        return index;
    }

    public String getParam() {
        return parameter;
    }

    public InsightType getType() {
        return type;
    }

    /**
     * This method processes the command execution for the scenario
     * where the InsightType is ALL.
     */
    private CommandResult processTypeAll(Model model) {
        switch (parameter) {

        case KEYWORD_ALL:
            ObservableList<RecentEvent> eventList = FXCollections.observableArrayList();
            eventList.clear();
            ObservableList<Event> fullList = model.getAddressBook().getEventList();
            int size1 = fullList.size();
            for (int i = 0; i < size1; i++) {
                eventList.add(new RecentEvent(
                        Integer.toString(fullList.get(i).getEventId()),
                        fullList.get(i).getPlace(),
                        fullList.get(i).getActivity(),
                        fullList.get(i).getTime().toString()));
            }
            model.copyRecent(eventList);
            return new CommandResult(MESSAGE_ALL_EVENTS, ViewType.ALL);

        case KEYWORD_RECENT:
            ObservableList<RecentEvent> recentEventList = FXCollections.observableArrayList();
            recentEventList.clear();
            ObservableList<Event> filteredRecentList = model.getFilteredEventList();
            int size2 = filteredRecentList.size();
            if (size2 < MAX_EVENTS) {
                for (int i = size2 - 1; i >= 0; i--) {
                    recentEventList.add(new RecentEvent(
                            Integer.toString(model.getFilteredEventList().get(i).getEventId()),
                            model.getFilteredEventList().get(i).getPlace(),
                            model.getFilteredEventList().get(i).getActivity(),
                            model.getFilteredEventList().get(i).getTime().toString()));
                }
            } else {
                for (int i = 0; i < MAX_EVENTS; i++) {
                    recentEventList.add(new RecentEvent(
                            Integer.toString(model.getFilteredEventList().get(size2 - 1 - i).getEventId()),
                            model.getFilteredEventList().get(size2 - 1 - i).getPlace(),
                            model.getFilteredEventList().get(size2 - 1 - i).getActivity(),
                            model.getFilteredEventList().get(size2 - 1 - i).getTime().toString()));
                }
            }
            model.copyRecent(recentEventList);
            return new CommandResult(MESSAGE_RECENT_ALL, ViewType.RECENT);

        default:
            assert parameter.equals(KEYWORD_TIME);
            model.showTime();
            return new CommandResult(MESSAGE_ALL_TIME, ViewType.TIME);
        }
    }

    /**
     * This method processes the command execution for the scenario
     * where the InsightType is PERSON.
     */
    private CommandResult processTypePerson(Model model) throws CommandException {
        List<Person> lastShownPersonList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToView = lastShownPersonList.get(index.getZeroBased());
        ViewType vt;
        String message;

        switch (parameter) {

        case KEYWORD_PLACE:
            model.showPlaceList(personToView);
            message = MESSAGE_PLACE + personToView.getName() + ".";
            vt = ViewType.PLACES;
            break;

        case KEYWORD_ACTIVITY:
            model.showActivityList(personToView);
            message = MESSAGE_ACTIVITY + personToView.getName() + ".";
            vt = ViewType.ACTIVITIES;
            break;

        default:
            assert parameter.equals(KEYWORD_RECENT);
            model.showRecentList(personToView);
            message = MESSAGE_RECENT_PERSON + personToView.getName() + ".";
            vt = ViewType.RECENT;
        }

        return new CommandResult(message, vt);
    }

    /**
     * This method processes the command execution for the scenario
     * where the InsightType is GROUP.
     */
    private CommandResult processTypeGroup(Model model) throws CommandException {
        List<Group> lastShownGroupList = model.getFilteredGroupList();
        if (index.getZeroBased() >= lastShownGroupList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
        }

        Group groupToView = lastShownGroupList.get(index.getZeroBased());
        ViewType vt;
        String message;

        switch (parameter) {

        case KEYWORD_PLACE:
            model.showGroupPlaceList(groupToView);
            message = MESSAGE_PLACE + groupToView.getName() + ".";
            vt = ViewType.PLACES;
            break;

        case KEYWORD_ACTIVITY:
            model.showGroupActivityList(groupToView);
            message = MESSAGE_ACTIVITY + groupToView.getName() + ".";
            vt = ViewType.ACTIVITIES;
            break;

        default:
            assert parameter.equals(KEYWORD_RECENT);
            model.showGroupRecentList(groupToView);
            message = MESSAGE_RECENT_PERSON + groupToView.getName() + ".";
            vt = ViewType.RECENT;
        }

        return new CommandResult(message, vt);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ViewCommand)) {
            return false;
        }
        return ((ViewCommand) o).getIndex().equals(this.getIndex())
                && ((ViewCommand) o).getParam().equals(this.getParam())
                && ((ViewCommand) o).getType().equals(this.getType());
    }
}
