package team.easytravel.logic.commands.activity;

import static java.util.Objects.requireNonNull;
import static team.easytravel.commons.core.Messages.MESSAGE_INVALID_DISPLAYED_INDEX_FORMAT;
import static team.easytravel.logic.commands.CommandResult.Action.SWITCH_TAB_ACTIVITY;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ACTIVITY_DURATION;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ACTIVITY_LOCATION;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ACTIVITY_TAG;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ACTIVITY_TITLE;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import team.easytravel.commons.core.index.Index;
import team.easytravel.commons.util.CollectionUtil;
import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.listmanagers.activity.Activity;
import team.easytravel.model.listmanagers.activity.Duration;
import team.easytravel.model.trip.DayScheduleEntry;
import team.easytravel.model.trip.TripManager;
import team.easytravel.model.trip.exception.IllegalOperationException;
import team.easytravel.model.util.attributes.Location;
import team.easytravel.model.util.attributes.Title;
import team.easytravel.model.util.attributes.tag.Tag;

/**
 * Edits the details of an existing activity.
 */
public class EditActivityCommand extends Command {

    public static final String COMMAND_WORD = "editactivity";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the activity identified "
            + "by the index number used in the displayed list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_ACTIVITY_TITLE + "TITLE] "
            + "[" + PREFIX_ACTIVITY_DURATION + "DURATION] "
            + "[" + PREFIX_ACTIVITY_LOCATION + "LOCATION] "
            + "[" + PREFIX_ACTIVITY_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ACTIVITY_TITLE + "Shopping "
            + PREFIX_ACTIVITY_DURATION + "2";

    public static final String MESSAGE_EDIT_ACTIVITY_SUCCESS = "Edited Activity: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ACTIVITY = "This activity already exists in the trip.";

    private final Index index;
    private final EditActivityDescriptor editActivityDescriptor;

    /**
     * @param index                  of the activity in the filtered activity list to edit
     * @param editActivityDescriptor details to edit the activity with
     */
    public EditActivityCommand(Index index, EditActivityDescriptor editActivityDescriptor) {
        requireNonNull(index);
        requireNonNull(editActivityDescriptor);

        this.index = index;
        this.editActivityDescriptor = new EditActivityDescriptor(editActivityDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        List<Activity> lastShownList = model.getFilteredActivityList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_DISPLAYED_INDEX_FORMAT, "activity"));
        }

        Activity activityToEdit = lastShownList.get(index.getZeroBased());
        Activity editedActivity = createEditedActivity(activityToEdit, editActivityDescriptor);

        if (!activityToEdit.isSame(editedActivity) && model.hasActivity(editedActivity)) {
            throw new CommandException(MESSAGE_DUPLICATE_ACTIVITY);
        }

        if (activityToEdit.getScheduledDateTime().isPresent()) {
            DayScheduleEntry entry = DayScheduleEntry.fromActivity(activityToEdit);
            model.unscheduleActivity(entry);
            try {
                model.scheduleActivity(editedActivity);
            } catch (IllegalOperationException e) {
                model.scheduleActivity(activityToEdit);
                throw new CommandException(e.getMessage());
            }
        }
        model.setActivity(activityToEdit, editedActivity);
        model.updateFilteredActivityList(Model.PREDICATE_SHOW_ALL_ACTIVITIES);
        return new CommandResult(String.format(MESSAGE_EDIT_ACTIVITY_SUCCESS, editedActivity), SWITCH_TAB_ACTIVITY);
    }

    /**
     * Creates and returns a {@code FixedExpense} with the details of {@code activityToEdit}
     * edited with {@code editActivityDescriptor}
     */
    private static Activity createEditedActivity(Activity activityToEdit,
                                                 EditActivityDescriptor editActivityDescriptor) {
        assert activityToEdit != null;

        Title updatedTitle = editActivityDescriptor.getTitle().orElse(activityToEdit.getTitle());
        Duration updatedDuration = editActivityDescriptor.getDuration().orElse(activityToEdit.getDuration());
        Location updatedLocation = editActivityDescriptor.getLocation().orElse(activityToEdit.getLocation());
        Set<Tag> updatedTags = editActivityDescriptor.getTags().orElse(activityToEdit.getTags());

        return new Activity(updatedTitle, updatedDuration, updatedLocation, updatedTags,
                activityToEdit.getScheduledDateTime());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditActivityCommand)) {
            return false;
        }

        // state check
        EditActivityCommand e = (EditActivityCommand) other;
        return index.equals(e.index)
                && editActivityDescriptor.equals(e.editActivityDescriptor);
    }

    /**
     * Stores the details to edit the fixed expense with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditActivityDescriptor {
        private Title title;
        private Duration duration;
        private Location location;
        private Set<Tag> tags;

        public EditActivityDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditActivityDescriptor(EditActivityDescriptor toCopy) {
            setTitle(toCopy.title);
            setDuration(toCopy.duration);
            setLocation(toCopy.location);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, duration, location, tags);
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Optional<Title> getTitle() {
            return Optional.ofNullable(title);
        }

        public void setDuration(Duration duration) {
            this.duration = duration;
        }

        public Optional<Duration> getDuration() {
            return Optional.ofNullable(duration);
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Optional<Location> getLocation() {
            return Optional.ofNullable(location);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditActivityDescriptor)) {
                return false;
            }

            // state check
            EditActivityDescriptor e = (EditActivityDescriptor) other;

            return getTitle().equals(e.getTitle())
                    && getDuration().equals(e.getDuration())
                    && getLocation().equals(e.getLocation())
                    && getTags().equals(e.getTags());
        }
    }
}
