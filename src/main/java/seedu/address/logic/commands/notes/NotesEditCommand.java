package seedu.address.logic.commands.notes;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_STUDENT_NOT_FOUND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;

import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.notes.Notes;
import seedu.address.model.student.Student;

/**
 * Represents NotesEditCommand class which edits Note tagged to a Student.
 */
public class NotesEditCommand extends Command {
    public static final String COMMAND_WORD = "notese";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + "<Index> " + PREFIX_CONTENT + "<Updated Content of Note> "
            + PREFIX_PRIORITY + "<HIGH/MEDIUM/LOW>";

    public static final String MESSAGE_SUCCESS = "Student's Note Edited. Wonderful!";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditNotesDescriptor editNotesDescriptor;

    /**
     * Constructor of NotesEditCommand
     * @param index of the note in the filtered notes list to edit
     * @param editNotesDescriptor details to edit the note with
     */
    public NotesEditCommand(Index index, EditNotesDescriptor editNotesDescriptor) {
        requireNonNull(index);
        requireNonNull(editNotesDescriptor);

        this.index = index;
        this.editNotesDescriptor = editNotesDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Notes> lastShownList = model.getFilteredNotesList();
        ObservableList<Student> students = model.getFilteredStudentList();


        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_NOTES_DISPLAYED_INDEX);
        }

        Notes noteToEdit = lastShownList.get(index.getZeroBased());
        Notes editedNote = createEditedNote(noteToEdit, editNotesDescriptor);

        boolean nameFound = false;

        for (Student student : students) {
            if (student.getName().toString().equals(editedNote.getStudent())) {
                nameFound = true;
            }
        }

        if (!nameFound) {
            throw new CommandException(MESSAGE_STUDENT_NOT_FOUND);
        }


        if (!noteToEdit.isSameNote(editedNote) && model.hasNote(editedNote)) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_NOTES);
        }

        model.setNote(noteToEdit, editedNote);
        model.updateFilteredNotesList(Model.PREDICATE_SHOW_ALL_NOTES);
        return new CommandResult(MESSAGE_SUCCESS + "\n"
                + "Student: " + editedNote.getStudent() + "\n"
                + "Content: " + editedNote.getContent() + "\n"
                + "Priority: " + editedNote.getPriority());
    }

    /**
     * Creates and returns a {@code Notes} with the details of {@code noteToEdit}
     * edited with {@code editNotesDescriptor}.
     */
    private static Notes createEditedNote(Notes noteToEdit, EditNotesDescriptor editNotesDescriptor) {
        assert noteToEdit != null;

        String updatedStudent = editNotesDescriptor.getStudent().orElse(noteToEdit.getStudent());
        String updatedContent = editNotesDescriptor.getContent().orElse(noteToEdit.getContent());
        String updatedPriority = editNotesDescriptor.getPriority().orElse(noteToEdit.getPriority());
        String unchangedDateTime = noteToEdit.getDateTime();

        return new Notes(updatedStudent, updatedContent, updatedPriority, unchangedDateTime);
    }

    /**
     * Stores the details to edit the note with. Each non-empty field value will replace the
     * corresponding field value of the note.
     */
    public static class EditNotesDescriptor {
        private String student;
        private String content;
        private String priority;

        public EditNotesDescriptor() {
        }

        /**
         * Copy constructor of EditNotesDescriptor
         */
        public EditNotesDescriptor(EditNotesDescriptor toCopy) {
            setStudent(toCopy.student);
            setContent(toCopy.content);
            setPriority(toCopy.priority);
        }

        /**
         * Returns true if at least one field is edited
         * @return boolean
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(student, content, priority);
        }

        /**
         * Setter of student field
         * @param student
         */
        public void setStudent(String student) {
            this.student = student;
        }

        /**
         * Getter of student field
         */
        public Optional<String> getStudent() {
            return Optional.ofNullable(student);
        }

        /**
         * Setter of content field
         * @param content
         */
        public void setContent(String content) {
            this.content = content;
        }

        /**
         * Getter of content field
         */
        public Optional<String> getContent() {
            return Optional.ofNullable(content);
        }

        /**
         * Setter of priority field
         * @param priority
         */
        public void setPriority(String priority) {
            this.priority = priority;
        }

        /**
         * Getter of priority field
         */
        public Optional<String> getPriority() {
            return Optional.ofNullable(priority);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof NotesEditCommand.EditNotesDescriptor)) {
                return false;
            }

            // state check
            NotesEditCommand.EditNotesDescriptor e = (NotesEditCommand.EditNotesDescriptor) other;

            return getStudent().equals(e.getStudent())
                    && getContent().equals(e.getContent())
                    && getPriority().equals(e.getPriority());
        }

    }
}
