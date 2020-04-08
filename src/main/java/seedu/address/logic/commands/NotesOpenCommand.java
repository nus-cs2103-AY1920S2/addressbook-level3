package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES_FILE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES_PATH_TYPE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_NOTES;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import seedu.address.model.Model;
import seedu.address.model.notes.Notes;


/**
 * NotesCommand is created when a notecommand is parsed, to return what operation to be done.
 * implementation is not yet completed
 */
public class NotesOpenCommand extends Command {

    public static final String COMMAND_WORD = "notesOpen";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Opens a file "
            + "Parameters: "
            + PREFIX_NOTES_PATH + "PATH "
            + PREFIX_NOTES_FILE_NAME + "FILE_NAME "
            + PREFIX_NOTES_PATH_TYPE + "PATH_TYPE \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NOTES_PATH + "Desktop "
            + PREFIX_NOTES_FILE_NAME + "test.doc "
            + PREFIX_NOTES_PATH_TYPE + "abs ";

    public static final String MESSAGE_OPEN_SUCCESS = "Opened file ";
    public static final String MESSAGE_OPEN_FAIL = "Failed to open file ";
    public static final String MESSAGE_UNABLE_TO_FIND = "Unable to find file to open ";

    private String path;
    private String filePath;
    private Notes note;

    /**
     * Creates a new NotesOpenCommand to Open a new note.
     *
     * @param note the operation and location that will be done to the note.
     */
    public NotesOpenCommand(Notes note) {
        this.note = note;
        this.path = note.getPath();
        this.filePath = note.getFilePathType();
    }

    /**
     * Opens a document at the specified path.
     *
     * @param path the given path that the document resides in.
     * @return a CommandResult based on whether the operation succeed or failed.
     */
    public CommandResult openDoc(String path) {
        String pathName = "";

        if (this.filePath.equals("abs")) {
            pathName = Notes.HOME_DIRECTORY + File.separatorChar + path;
        } else {
            pathName = Notes.getCurrentDirectory() + File.separatorChar + path;
        }

        File myFile = new File(pathName);


        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(myFile);
            }
            if (!myFile.isDirectory()) {
                buildDirectoryName(pathName);
            } else {
                Notes.setCurrentDirectory(pathName);
            }
            Notes.setList(NotesListCommand.listfilesArray(Notes.getCurrentDirectory()));
            return new CommandResult(MESSAGE_OPEN_SUCCESS);
        } catch (IOException ex) {
            return new CommandResult(MESSAGE_OPEN_FAIL + this.path);
        } catch (IllegalArgumentException ex) {
            return new CommandResult(MESSAGE_UNABLE_TO_FIND + this.path);
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
        return openDoc(this.path);

    }

    @Override
    public boolean equals(Object other) {

        NotesOpenCommand otherNotesOpen = (NotesOpenCommand) other;
        return this.note.equals(otherNotesOpen.getNote());

    }
}
