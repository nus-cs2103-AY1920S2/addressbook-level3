package seedu.address.logic.commands.consults;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_CONSULT_BEGIN_TIME_BEFORE_END_TIME;
import static seedu.address.commons.core.Messages.MESSAGE_CONSULT_DIFFERENT_DATE;
import static seedu.address.commons.core.Messages.MESSAGE_CONSULT_TIMING_CLASH;
import static seedu.address.commons.util.ConsultUtil.checkSameDate;
import static seedu.address.commons.util.ConsultUtil.checkStartEndDateTime;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONSULT_BEGIN_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONSULT_END_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLACE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONSULTS;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Location;
import seedu.address.model.event.consult.Consult;
import seedu.address.model.student.MatricNumber;
import seedu.address.model.student.Name;

/**
 * Edits the details of an existing consult in TAble.
 */
public class EditConsultCommand extends Command {

    public static final String COMMAND_WORD = "editConsult";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the consult identified "
            + "by the index number used in the displayed consult list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_CONSULT_BEGIN_DATE_TIME + "BEGINDATETIME] "
            + "[" + PREFIX_CONSULT_END_DATE_TIME + "ENDDATETIME] "
            + "[" + PREFIX_PLACE + "PLACE] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_CONSULT_BEGIN_DATE_TIME + "2020-03-03 15:00 "
            + PREFIX_PLACE + "The Deck";

    public static final String MESSAGE_EDIT_CONSULT_SUCCESS = "Edited Consult: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CONSULT = "There is already a consultation at that timing.";

    private final Index index;
    private final EditConsultDescriptor editConsultDescriptor;

    /**
     * @param index of the consult in the filtered consult list to edit
     * @param editConsultDescriptor details to edit the consult with
     */
    public EditConsultCommand(Index index, EditConsultDescriptor editConsultDescriptor) {
        requireNonNull(index);
        requireNonNull(editConsultDescriptor);

        this.index = index;
        this.editConsultDescriptor = new EditConsultDescriptor(editConsultDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Consult> lastShownList = model.getFilteredConsultList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONSULT_DISPLAYED_INDEX);
        }

        Consult consultToEdit = lastShownList.get(index.getZeroBased());
        Consult editedConsult = createEditedConsult(consultToEdit, editConsultDescriptor);

        if (!consultToEdit.equals(editedConsult) && model.hasConsult(editedConsult)) {
            throw new CommandException(MESSAGE_DUPLICATE_CONSULT);
        }

        if (model.hasSameDateTimeEdit(editedConsult)) {
            throw new CommandException(MESSAGE_CONSULT_TIMING_CLASH);
        }

        if (editedConsult.getEndDateTime().isBefore(LocalDateTime.now())) {
            throw new CommandException(Messages.MESSAGE_CONSULT_PAST_CONSULT);
        }

        model.setConsult(consultToEdit, editedConsult);
        model.updateFilteredConsultList(PREDICATE_SHOW_ALL_CONSULTS);
        return new CommandResult(String.format(MESSAGE_EDIT_CONSULT_SUCCESS, editedConsult));
    }

    /**
     * Creates and returns a {@code Consult} with the details of {@code consultToEdit}
     * edited with {@code editConsultDescriptor}.
     */
    private static Consult createEditedConsult(Consult consultToEdit, EditConsultDescriptor editConsultDescriptor)
            throws CommandException {

        assert consultToEdit != null;

        LocalDateTime updatedBeginStartTime = editConsultDescriptor.getBeginDateTime()
                .orElse(consultToEdit.getBeginDateTime());

        LocalDateTime updatedEndStartTime = editConsultDescriptor.getEndDateTime()
                .orElse(consultToEdit.getEndDateTime());

        if (!checkStartEndDateTime(updatedBeginStartTime, updatedEndStartTime)) {
            throw new CommandException(MESSAGE_CONSULT_BEGIN_TIME_BEFORE_END_TIME);
        }

        if (!checkSameDate(updatedBeginStartTime, updatedEndStartTime)) {
            throw new CommandException(MESSAGE_CONSULT_DIFFERENT_DATE);
        }

        Location updatedLocation = editConsultDescriptor.getLocation().orElse(consultToEdit.getLocation());
        MatricNumber studentMatricNumber = consultToEdit.getMatricNumber();
        Name studentName = consultToEdit.getStudentName();

        return new Consult(updatedBeginStartTime, updatedEndStartTime,
            updatedLocation, studentName, studentMatricNumber);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditConsultCommand)) {
            return false;
        }

        // state check
        EditConsultCommand e = (EditConsultCommand) other;
        return index.equals(e.index)
                && editConsultDescriptor.equals(e.editConsultDescriptor);
    }

    /**
     * Stores the details to edit the consult with. Each non-empty field value will replace the
     * corresponding field value of the consult.
     */
    public static class EditConsultDescriptor {
        private LocalDateTime beginDateTime;
        private LocalDateTime endDateTime;
        private Location location;


        public EditConsultDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditConsultDescriptor(EditConsultDescriptor toCopy) {
            setBeginDateTime(toCopy.beginDateTime);
            setEndDateTime(toCopy.endDateTime);
            setLocation(toCopy.location);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(beginDateTime,
                    endDateTime, location);
        }

        public void setBeginDateTime(LocalDateTime beginDateTime) {
            this.beginDateTime = beginDateTime;
        }

        public Optional<LocalDateTime> getBeginDateTime() {
            return Optional.ofNullable(beginDateTime);
        }

        public void setEndDateTime(LocalDateTime endDateTime) {
            this.endDateTime = endDateTime;
        }

        public Optional<LocalDateTime> getEndDateTime() {
            return Optional.ofNullable(endDateTime);
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Optional<Location> getLocation() {
            return Optional.ofNullable(location);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditConsultDescriptor)) {
                return false;
            }

            // state check
            EditConsultDescriptor e = (EditConsultDescriptor) other;

            return getBeginDateTime().equals(e.getBeginDateTime())
                    && getEndDateTime().equals(e.getEndDateTime())
                    && getLocation().equals(e.getLocation());
        }


    }
}
