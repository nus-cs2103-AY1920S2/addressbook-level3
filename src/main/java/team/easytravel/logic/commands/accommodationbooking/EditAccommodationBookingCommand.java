package team.easytravel.logic.commands.accommodationbooking;

import static java.util.Objects.requireNonNull;
import static team.easytravel.commons.core.Messages.MESSAGE_INVALID_DISPLAYED_INDEX_FORMAT;
import static team.easytravel.logic.commands.CommandResult.Action.SWITCH_TAB_ACCOMMODATION;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ACCOMMODATION_END_DAY;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ACCOMMODATION_LOCATION;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ACCOMMODATION_NAME;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ACCOMMODATION_REMARK;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ACCOMMODATION_START_DAY;
import static team.easytravel.model.Model.PREDICATE_SHOW_ALL_ACCOMMODATION_BOOKINGS;

import java.util.List;
import java.util.Optional;

import team.easytravel.commons.core.index.Index;
import team.easytravel.commons.util.CollectionUtil;
import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.listmanagers.accommodationbooking.AccommodationBooking;
import team.easytravel.model.listmanagers.accommodationbooking.AccommodationName;
import team.easytravel.model.listmanagers.accommodationbooking.Day;
import team.easytravel.model.listmanagers.accommodationbooking.Remark;
import team.easytravel.model.trip.TripManager;
import team.easytravel.model.util.attributes.Location;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditAccommodationBookingCommand extends Command {

    public static final String COMMAND_WORD = "editacc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the accommodation booking "
            + "identified by the index number used in the displayed accommodation booking list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_ACCOMMODATION_NAME + "NAME] "
            + "[" + PREFIX_ACCOMMODATION_LOCATION + "LOCATION] "
            + "[" + PREFIX_ACCOMMODATION_START_DAY + "START_DAY] "
            + "[" + PREFIX_ACCOMMODATION_END_DAY + "END_DAY] "
            + "[" + PREFIX_ACCOMMODATION_REMARK + "REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ACCOMMODATION_NAME + "Casa del Rio "
            + PREFIX_ACCOMMODATION_LOCATION + "Malacca";

    public static final String MESSAGE_EDIT_ACCOMMODATION_BOOKING_SUCCESS = "Edited Accommodation Booking: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ACCOMMODATION_BOOKING = "This Accommodation Booking already exists "
            + "in the accommodation booking list.";
    public static final String MESSAGE_OVERLAPPING_ACCOMMODATION_BOOKING = "The edited accommodation booking overlaps "
            + "with another booking in the list";
    public static final String MESSAGE_INVALID_END_DAY = "The edited accommodation booking end day does not fall "
            + "within the number of days in the trip";

    private final Index index;
    private final EditAccommodationBookingDescriptor editAccommodationBookingDescriptor;

    /**
     * @param index                              of the person in the filtered person list to edit
     * @param editAccommodationBookingDescriptor details to edit the person with
     */
    public EditAccommodationBookingCommand(Index index,
                                           EditAccommodationBookingDescriptor editAccommodationBookingDescriptor) {
        requireNonNull(index);
        requireNonNull(editAccommodationBookingDescriptor);

        this.index = index;
        this.editAccommodationBookingDescriptor = new EditAccommodationBookingDescriptor(
                editAccommodationBookingDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        List<AccommodationBooking> lastShownList = model.getFilteredAccommodationBookingList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_DISPLAYED_INDEX_FORMAT, "accommodation booking"));
        }

        AccommodationBooking accommodationBookingToEdit = lastShownList.get(index.getZeroBased());

        AccommodationBooking editedAccommodationBooking;
        try {
            editedAccommodationBooking = createEditedAccommodationBooking(accommodationBookingToEdit,
                    editAccommodationBookingDescriptor);
        } catch (IllegalArgumentException e) {
            throw new CommandException(e.getMessage());
        }

        if (editedAccommodationBooking.getEndDay().value > model.getTripNumDays()) {
            throw new CommandException(MESSAGE_INVALID_END_DAY);
        }

        if (!accommodationBookingToEdit.isSame(editedAccommodationBooking)
                && model.hasAccommodationBooking(editedAccommodationBooking)) {
            throw new CommandException(MESSAGE_DUPLICATE_ACCOMMODATION_BOOKING);
        }

        model.deleteAccommodationBooking(accommodationBookingToEdit);
        if (model.isOverlappingWithOthers(editedAccommodationBooking)) {
            model.addAccommodationBooking(accommodationBookingToEdit);
            throw new CommandException(MESSAGE_OVERLAPPING_ACCOMMODATION_BOOKING);
        } else {
            model.addAccommodationBooking(editedAccommodationBooking);
        }

        model.updateFilteredAccommodationBookingList(PREDICATE_SHOW_ALL_ACCOMMODATION_BOOKINGS);
        return new CommandResult(String.format(MESSAGE_EDIT_ACCOMMODATION_BOOKING_SUCCESS,
                editedAccommodationBooking), SWITCH_TAB_ACCOMMODATION);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static AccommodationBooking createEditedAccommodationBooking(
            AccommodationBooking accommodationBookingToEdit,
            EditAccommodationBookingDescriptor editAccommodationBookingDescriptor) {
        assert accommodationBookingToEdit != null;

        AccommodationName updatedName = editAccommodationBookingDescriptor.getAccommodationName()
                .orElse(accommodationBookingToEdit.getAccommodationName());
        Location updatedLocation = editAccommodationBookingDescriptor.getLocation().orElse(accommodationBookingToEdit
                .getLocation());
        Day updatedStartDay = editAccommodationBookingDescriptor.getStartDay().orElse(accommodationBookingToEdit
                .getStartDay());
        Day updatedEndDay = editAccommodationBookingDescriptor.getEndDay().orElse(accommodationBookingToEdit
                .getEndDay());
        Remark updatedRemark = editAccommodationBookingDescriptor.getRemark().orElse(accommodationBookingToEdit
                .getRemark());

        return new AccommodationBooking(updatedName, updatedLocation, updatedStartDay, updatedEndDay, updatedRemark);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditAccommodationBookingCommand // instanceof handles nulls
                && index.equals(((EditAccommodationBookingCommand) other).index))
                && editAccommodationBookingDescriptor.equals(((EditAccommodationBookingCommand) other)
                .editAccommodationBookingDescriptor); // state check

    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditAccommodationBookingDescriptor {
        private AccommodationName accommodationName;
        private Location accommodationLocation;
        private Day accommodationStartDay;
        private Day accommodationEndDay;
        private Remark accommodationRemark;

        public EditAccommodationBookingDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditAccommodationBookingDescriptor(EditAccommodationBookingDescriptor toCopy) {
            setAccommodationName(toCopy.accommodationName);
            setLocation(toCopy.accommodationLocation);
            setStartDay(toCopy.accommodationStartDay);
            setEndDay(toCopy.accommodationEndDay);
            setRemark(toCopy.accommodationRemark);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(accommodationName, accommodationLocation, accommodationStartDay,
                    accommodationEndDay, accommodationRemark);
        }

        public void setAccommodationName(AccommodationName accommodationName) {
            this.accommodationName = accommodationName;
        }

        public Optional<AccommodationName> getAccommodationName() {
            return Optional.ofNullable(accommodationName);
        }

        public void setLocation(Location accommodationLocation) {
            this.accommodationLocation = accommodationLocation;
        }

        public Optional<Location> getLocation() {
            return Optional.ofNullable(accommodationLocation);
        }

        public void setStartDay(Day accommodationStartDay) {
            this.accommodationStartDay = accommodationStartDay;
        }

        public Optional<Day> getStartDay() {
            return Optional.ofNullable(accommodationStartDay);
        }

        public void setEndDay(Day accommodationEndDay) {
            this.accommodationEndDay = accommodationEndDay;
        }

        public Optional<Day> getEndDay() {
            return Optional.ofNullable(accommodationEndDay);
        }

        public void setRemark(Remark accommodationRemark) {
            this.accommodationRemark = accommodationRemark;
        }

        public Optional<Remark> getRemark() {
            return Optional.ofNullable(accommodationRemark);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditAccommodationBookingDescriptor)) {
                return false;
            }

            // state check
            EditAccommodationBookingDescriptor e = (EditAccommodationBookingDescriptor) other;

            return getAccommodationName().equals(e.getAccommodationName())
                    && getLocation().equals(e.getLocation())
                    && getStartDay().equals(e.getStartDay())
                    && getEndDay().equals(e.getEndDay())
                    && getRemark().equals(e.getRemark());
        }
    }

}
