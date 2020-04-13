package seedu.address.logic.commands.consults;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_CONSULT_TIMING_CLASH;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONSULT_BEGIN_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONSULT_END_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.consult.Consult;
import seedu.address.model.student.MatricNumber;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;

/**
 * Adds a consult into TAble.
 */
public class AddConsultCommand extends Command {

    public static final String COMMAND_WORD = "addConsult";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a consultation to TAble. "
            + "Parameters: "
            + PREFIX_STUDENT + "STUDENT_INDEX "
            + PREFIX_CONSULT_BEGIN_DATE_TIME + "EVENT_BEGIN_DATE_TIME "
            + PREFIX_CONSULT_END_DATE_TIME + "EVENT_END_DATE_TIME "
            + PREFIX_PLACE + "PLACE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_STUDENT + "1 "
            + PREFIX_CONSULT_BEGIN_DATE_TIME + "2020-03-03 10:00 "
            + PREFIX_CONSULT_END_DATE_TIME + "2020-03-03 12:00 "
            + PREFIX_PLACE + "Outside SR1";

    public static final String MESSAGE_SUCCESS = "New consultation added: %1$s";
    public static final String MESSAGE_DUPLICATE_CONSULT = "There is already a consultation at that timing!";

    private final Logger logger = LogsCenter.getLogger(getClass());
    private final Index index;
    private final Consult toAdd;

    /**
     * Creates an AddConsultCommand to add the specified {@code Consult}
     */
    public AddConsultCommand(Index index, Consult consult) {
        requireAllNonNull(index, consult);
        this.index = index;
        toAdd = consult;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();


        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        if (model.hasConsult(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CONSULT);
        }

        if (model.hasSameDateTime(toAdd)) {
            throw new CommandException(MESSAGE_CONSULT_TIMING_CLASH);
        }

        if (toAdd.getEndDateTime().isBefore(LocalDateTime.now())) {
            throw new CommandException(Messages.MESSAGE_CONSULT_PAST_CONSULT);
        }

        Student studentToEdit = lastShownList.get(index.getZeroBased());
        MatricNumber studentMatricNumber = new MatricNumber(studentToEdit.getMatricNumber().toString());
        Name studentName = new Name(studentToEdit.getName().toString());
        toAdd.setMatricNumber(studentMatricNumber);
        toAdd.setStudentName(studentName);
        logger.fine("Student name: " + studentName.toString());
        logger.fine("Student matric number: " + studentMatricNumber.toString());

        model.addConsult(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddConsultCommand // instanceof handles nulls
                && toAdd.equals(((AddConsultCommand) other).toAdd));
    }
}

