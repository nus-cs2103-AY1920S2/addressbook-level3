package seedu.address.logic.commands.academics;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSESSMENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSESSMENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSESSMENT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EDIT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ASSESSMENTS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.academics.Assessment;
import seedu.address.model.academics.Exam;
import seedu.address.model.academics.Homework;

/**
 * Edits the details of an existing assessment in academics.
 */
public class AcademicsEditCommand extends AcademicsCommand {

    public static final String MESSAGE_USAGE = "This command edits the details of the assessment identified. \n"
            + "Format: " + COMMAND_WORD + " "
            + PREFIX_EDIT + " ASSESSMENT INDEX (must be a positive integer) "
            + "[" + PREFIX_ASSESSMENT_DESCRIPTION + "ASSESSMENT DESCRIPTION] "
            + "[" + PREFIX_ASSESSMENT_TYPE + "ASSESSMENT TYPE] (must be homework or exam) "
            + "[" + PREFIX_ASSESSMENT_DATE + "ASSESSMENT DATE]\n";

    public static final String MESSAGE_EDIT_ASSESSMENT_SUCCESS = "Edited Assessment: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ASSESSMENT = "This assessment already exists in the address book.";

    private final Index index;
    private final EditAssessmentDescriptor editAssessmentDescriptor;

    /**
     * @param index of the student in the filtered student list to edit
     * @param editAssessmentDescriptor details to edit the student with
     */
    public AcademicsEditCommand(Index index, EditAssessmentDescriptor editAssessmentDescriptor) {
        requireNonNull(index);
        requireNonNull(editAssessmentDescriptor);

        this.index = index;
        this.editAssessmentDescriptor = new EditAssessmentDescriptor(editAssessmentDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Assessment> lastShownList = model.getFilteredAcademicsList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ASSESSMENT_DISPLAYED_INDEX);
        }

        Assessment assessmentToEdit = lastShownList.get(index.getZeroBased());
        Assessment editedAssessment = createEditedAssessment(assessmentToEdit, editAssessmentDescriptor);

        if (!assessmentToEdit.isSameAssessment(editedAssessment) && model.hasAssessment(editedAssessment)) {
            throw new CommandException(MESSAGE_DUPLICATE_ASSESSMENT);
        }

        model.setAssessment(assessmentToEdit, editedAssessment);
        model.updateFilteredAcademicsList(PREDICATE_SHOW_ALL_ASSESSMENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_ASSESSMENT_SUCCESS, editedAssessment));
    }

    /**
     * Creates and returns an {@code Assessment} with the details of {@code assessmentToEdit}
     * edited with {@code editAssessmentDescriptor}.
     */
    private static Assessment createEditedAssessment(Assessment assessmentToEdit,
                                                     EditAssessmentDescriptor editAssessmentDescriptor)
            throws CommandException {
        assert assessmentToEdit != null;

        String updatedDescription = editAssessmentDescriptor.getDescription().orElse(assessmentToEdit.getDescription());
        String updatedDate = editAssessmentDescriptor.getDate().orElse(assessmentToEdit.getDate().toString());

        String updatedType;
        if (assessmentToEdit instanceof Homework) {
            updatedType = editAssessmentDescriptor.getType().orElse("homework");
        } else {
            updatedType = editAssessmentDescriptor.getType().orElse("exam");
        }
        Assessment updatedAssessment;
        if (updatedType.equals("homework")) {
            updatedAssessment = new Homework(updatedDescription, updatedDate);
        } else if (updatedType.equals("exam")) {
            updatedAssessment = new Exam(updatedDescription, updatedDate);
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_ASSESSMENT_TYPE);
        }
        updatedAssessment.setSubmissionTracker(assessmentToEdit.getSubmissionTracker());

        return updatedAssessment;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AcademicsEditCommand)) {
            return false;
        }

        // state check
        AcademicsEditCommand e = (AcademicsEditCommand) other;
        return index.equals(e.index)
                && editAssessmentDescriptor.equals(e.editAssessmentDescriptor);
    }

    /**
     * Stores the details to edit the assessment with. Each non-empty field value will replace the
     * corresponding field value of the assessment.
     */
    public static class EditAssessmentDescriptor {
        private String description;
        private String type;
        private String date;

        public EditAssessmentDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditAssessmentDescriptor(EditAssessmentDescriptor toCopy) {
            setDescription(toCopy.description);
            setType(toCopy.type);
            setDate(toCopy.date);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(description, type, date);
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Optional<String> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setType(String type) {
            this.type = type;
        }

        public Optional<String> getType() {
            return Optional.ofNullable(type);
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Optional<String> getDate() {
            return Optional.ofNullable(date);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditAssessmentDescriptor)) {
                return false;
            }

            // state check
            EditAssessmentDescriptor e = (EditAssessmentDescriptor) other;

            return getDescription().equals(e.getDescription())
                    && getType().equals(e.getType())
                    && getDate().equals(e.getDate());
        }
    }
}
