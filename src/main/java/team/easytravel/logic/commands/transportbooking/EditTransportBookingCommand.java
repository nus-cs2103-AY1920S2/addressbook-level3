package team.easytravel.logic.commands.transportbooking;

import static java.util.Objects.requireNonNull;
import static team.easytravel.logic.commands.CommandResult.Action.SWITCH_TAB_TRANSPORT;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_END_DATE_TIME;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_END_LOCATION;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_MODE;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_START_DATE_TIME;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_START_LOCATION;
import static team.easytravel.model.Model.PREDICATE_SHOW_ALL_TRANSPORT_BOOKINGS;

import java.util.List;
import java.util.Optional;

import team.easytravel.commons.core.Messages;
import team.easytravel.commons.core.index.Index;
import team.easytravel.commons.core.time.DateTime;
import team.easytravel.commons.util.CollectionUtil;
import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.listmanagers.transportbooking.Mode;
import team.easytravel.model.listmanagers.transportbooking.TransportBooking;
import team.easytravel.model.trip.DayScheduleEntry;
import team.easytravel.model.trip.TripManager;
import team.easytravel.model.trip.exception.IllegalOperationException;
import team.easytravel.model.util.attributes.Location;

/**
 * Edits the details of an existing TransportBooking in the TransportBookingManager.
 */
public class EditTransportBookingCommand extends Command {

    public static final String COMMAND_WORD = "edittransport";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the transport booking identified "
            + "by the index number used in the displayed transport booking list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_MODE + "MODE] "
            + "[" + PREFIX_START_LOCATION + "START_LOCATION] "
            + "[" + PREFIX_END_LOCATION + "END_LOCATION] "
            + "[" + PREFIX_START_DATE_TIME + "START_TIME] "
            + "[" + PREFIX_END_DATE_TIME + "END_TIME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_END_LOCATION + "Italy";

    public static final String MESSAGE_EDIT_TRANSPORT_BOOKING_SUCCESS = "Edited Transport Booking: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TRANSPORT_BOOKING = "This transport booking already exists in the "
            + "transport booking list.";

    private final Index index;
    private final EditTransportBookingDescriptor editTransportBookingDescriptor;

    /**
     * @param index                          index of the transport booking in the filtered transport booking list to
     *                                       edit
     * @param editTransportBookingDescriptor details to edit the transport booking with
     */
    public EditTransportBookingCommand(Index index, EditTransportBookingDescriptor editTransportBookingDescriptor) {
        requireNonNull(index);
        requireNonNull(editTransportBookingDescriptor);

        this.index = index;
        this.editTransportBookingDescriptor = new EditTransportBookingDescriptor(editTransportBookingDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        List<TransportBooking> lastShownList = model.getFilteredTransportBookingList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_DISPLAYED_INDEX_FORMAT,
                    "transport booking"));
        }

        TransportBooking transportBookingToEdit = lastShownList.get(index.getZeroBased());
        TransportBooking editedTransportBooking;

        try {
            editedTransportBooking = createEditedTransportBooking(transportBookingToEdit,
                    editTransportBookingDescriptor);
        } catch (IllegalArgumentException e) {
            throw new CommandException(e.getMessage());
        }

        if (!transportBookingToEdit.isSame(editedTransportBooking)
                && model.hasTransportBooking(editedTransportBooking)) {
            throw new CommandException(MESSAGE_DUPLICATE_TRANSPORT_BOOKING);
        }

        DayScheduleEntry entry = DayScheduleEntry.fromTransportBooking(transportBookingToEdit);
        model.unscheduleTransport(entry);
        try {
            model.scheduleTransport(editedTransportBooking);
        } catch (IllegalOperationException e) {
            model.scheduleTransport(transportBookingToEdit);
            throw new CommandException(e.getMessage());
        }

        model.setTransportBooking(transportBookingToEdit, editedTransportBooking);
        model.updateFilteredTransportBookingList(PREDICATE_SHOW_ALL_TRANSPORT_BOOKINGS);
        return new CommandResult(String.format(MESSAGE_EDIT_TRANSPORT_BOOKING_SUCCESS, editedTransportBooking),
                SWITCH_TAB_TRANSPORT);
    }

    /**
     * Creates and returns a {@code TransportBooking} with the details of {@code transportBookingToEdit}
     * edited with {@code editTransportBookingDescriptor}.
     */
    private static TransportBooking createEditedTransportBooking(
            TransportBooking transportBookingToEdit, EditTransportBookingDescriptor editTransportBookingDescriptor) {
        assert transportBookingToEdit != null;

        Mode updatedMode = editTransportBookingDescriptor.getMode().orElse(transportBookingToEdit.getMode());
        Location updatedStartLocation =
                editTransportBookingDescriptor.getStartLocation().orElse(transportBookingToEdit.getStartLocation());
        Location updatedEndLocation =
                editTransportBookingDescriptor.getEndLocation().orElse(transportBookingToEdit.getEndLocation());
        DateTime updatedStartDateTime =
                editTransportBookingDescriptor.getStartDateTime().orElse(transportBookingToEdit.getStartDateTime());
        DateTime updatedEndDateTime =
                editTransportBookingDescriptor.getEndDateTime().orElse(transportBookingToEdit.getEndDateTime());

        return new TransportBooking(updatedMode, updatedStartLocation, updatedEndLocation, updatedStartDateTime,
                updatedEndDateTime);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditTransportBookingCommand // instanceof handles nulls
                && index.equals(((EditTransportBookingCommand) other).index)); // state check
    }

    /**
     * Stores the details to edit the transport booking with. Each non-empty field value will replace the
     * corresponding field value of the transport booking.
     */
    public static class EditTransportBookingDescriptor {
        private Mode mode;
        private Location startLocation;
        private Location endLocation;
        private DateTime startDateTime;
        private DateTime endDateTime;

        public EditTransportBookingDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public EditTransportBookingDescriptor(EditTransportBookingDescriptor toCopy) {
            setMode(toCopy.mode);
            setStartLocation(toCopy.startLocation);
            setEndLocation(toCopy.endLocation);
            setStartDateTime(toCopy.startDateTime);
            setEndDateTime(toCopy.endDateTime);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(mode, startLocation, endLocation, startDateTime, endDateTime);
        }

        public void setMode(Mode mode) {
            this.mode = mode;
        }

        public Optional<Mode> getMode() {
            return Optional.ofNullable(mode);
        }

        public void setStartLocation(Location startLocation) {
            this.startLocation = startLocation;
        }

        public Optional<Location> getStartLocation() {
            return Optional.ofNullable(startLocation);
        }

        public void setEndLocation(Location endLocation) {
            this.endLocation = endLocation;
        }

        public Optional<Location> getEndLocation() {
            return Optional.ofNullable(endLocation);
        }

        public void setStartDateTime(DateTime startDateTime) {
            this.startDateTime = startDateTime;
        }

        public Optional<DateTime> getStartDateTime() {
            return Optional.ofNullable(startDateTime);
        }

        public void setEndDateTime(DateTime endDateTime) {
            this.endDateTime = endDateTime;
        }

        public Optional<DateTime> getEndDateTime() {
            return Optional.ofNullable(endDateTime);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTransportBookingDescriptor)) {
                return false;
            }

            // state check
            EditTransportBookingDescriptor e = (EditTransportBookingDescriptor) other;

            return mode.equals(e.mode)
                    && startLocation.equals(e.startLocation)
                    && endLocation.equals(e.endLocation)
                    && startDateTime.equals(e.startDateTime)
                    && endDateTime.equals(e.endDateTime);
        }
    }
}
