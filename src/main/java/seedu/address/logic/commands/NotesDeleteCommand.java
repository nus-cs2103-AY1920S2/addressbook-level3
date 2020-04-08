package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES_FILE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES_PATH_TYPE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_NOTES;

import java.io.File;

import seedu.address.model.Model;
import seedu.address.model.notes.Notes;


/**
 * NotesCommand is created when a notecommand is parsed, to return what operation to be done.
 * implementation is not yet completed
 */
public class NotesDeleteCommand extends Command {

    public static final String COMMAND_WORD = "notesDelete";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Delete specified file "
            + "Parameters: "
            + PREFIX_NOTES_PATH + "PATH "
            + PREFIX_NOTES_FILE_NAME + "FILE_NAME "
            + PREFIX_NOTES_PATH_TYPE + "PATH_TYPE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NOTES_PATH + "Desktop/ "
            + PREFIX_NOTES_FILE_NAME + "Test.doc "
            + PREFIX_NOTES_PATH_TYPE + "abs ";

    private static final String MESSAGE_DELETE_SUCESSFUL = " is successfully deleted ";
    private static final String MESSAGE_DELETE_UNSUCCESSFUL = " is unable to be deleted ";
    private static final String MESSAGE_DELETE_NOT_FOUND = " does not exists";
    private static final String MESSAGE_DELETE_FOLDER =
            "You can only delete a file through this method, not a directory";

    private String path;
    private String filePath;
    private Notes note;

    /**
     * Creates a new NotesCommand to Create/Open/Delete a new note.
     *
     * @param note the operation and location that will be done to the note.
     */
    public NotesDeleteCommand(Notes note) {
        this.note = note;
        this.path = note.getPath();
        this.filePath = note.getFilePathType();
    }

    /**
     * Deletes a document at the specified path.
     *
     * @param path the given path that the created document will reside in.
     * @return a CommandResult based on whether the operation succeed or failed.
     */
    public CommandResult deleteDoc(String path) {
        String pathName = "";
        if (this.filePath.equals("abs")) {
            pathName = Notes.HOME_DIRECTORY + File.separatorChar + path;
        } else {
            pathName = Notes.getCurrentDirectory() + File.separatorChar + path;
        }
        buildDirectoryName(pathName); // Build the CurrentDirectory name
        File myFile = new File(pathName);
        if (!myFile.exists()) {
            return new CommandResult(pathName + MESSAGE_DELETE_NOT_FOUND);
        }

        if (myFile.isDirectory()) {
            return new CommandResult(MESSAGE_DELETE_FOLDER);
        }

        String[] fileNames = this.path.split(File.separator);

        if (myFile.delete()) {
            Notes.setList(NotesListCommand.listfilesArray(Notes.getCurrentDirectory()));
            return new CommandResult(fileNames[fileNames.length - 1] + MESSAGE_DELETE_SUCESSFUL);
        } else {
            return new CommandResult(fileNames[fileNames.length - 1] + MESSAGE_DELETE_UNSUCCESSFUL);
        }

    }

    /**
     * Build the directory name to be shown as current directory.
     * @param pathName current path name inputted
     */
    public void buildDirectoryName(String pathName) {

        String[] splittedDirectoryName = pathName.split(File.separator);
        String newDirectory = "";
        for (int i = 0; i < splittedDirectoryName.length - 1; i++) {
            newDirectory += splittedDirectoryName[i] + File.separator;
        }
        Notes.setCurrentDirectory(newDirectory);

    }

    public Notes getNote() {
        return this.note;
    }


    @Override
    public CommandResult execute(Model model) {

        model.updateNotesList(PREDICATE_SHOW_ALL_NOTES);

        return deleteDoc(this.path);
    }

    @Override
    public boolean equals(Object other) {

        NotesDeleteCommand otherNotesOpen = (NotesDeleteCommand) other;
        return this.note.equals(otherNotesOpen.getNote());

    }
}
