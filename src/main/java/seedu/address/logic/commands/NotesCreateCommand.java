package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES_FILE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES_PATH_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES_TYPE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_NOTES;

import java.io.File;
import java.io.IOException;

import seedu.address.model.Model;
import seedu.address.model.notes.Notes;


/**
 * NotesCommand is created when a notecommand is parsed, to return what operation to be done.
 * implementation is not yet completed
 */
public class NotesCreateCommand extends Command {

    public static final String COMMAND_WORD = "notesCreate";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Create new file in specified directory "
            + "Parameters: "
            + PREFIX_NOTES_PATH + "PATH "
            + PREFIX_NOTES_FILE_NAME + "FILE_NAME "
            + PREFIX_NOTES_TYPE + "TYPE "
            + PREFIX_NOTES_PATH_TYPE + "PATH_TYPE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NOTES_PATH + "Desktop/ "
            + PREFIX_NOTES_FILE_NAME + "Test.doc "
            + PREFIX_NOTES_TYPE + "file "
            + PREFIX_NOTES_PATH_TYPE + "abs ";


    private static final String MESSAGE_CREATE_SUCCESS = "File is successfully created at ";
    private static final String MESSAGE_CREATE_FAIL = "File is unable to be created at ";
    private static final String MESSAGE_CREATE_DUPLICATE = "File already exists";
    private static final String MESSAGE_NOTHING_HAPPENED = "Nothing Happened ";
    private static final String MESSAGE_MAKEDIR_SUCCESSFUL = "Directory is successfully created ";
    private static final String MESSAGE_MAKEDIR_UNSUCCESSFUL = "Directory is unable to be created ";
    private static final String MESSAGE_MAKEDIR_DUPLICATE = " already exists";

    private String path;
    private String type;
    private String filePath;
    private Notes note;

    /**
     * Creates a new NotesCommand to Create/Open/Delete a new note.
     *
     * @param note the operation and location that will be done to the note.
     */
    public NotesCreateCommand(Notes note) {
        this.note = note;
        this.type = note.getType();
        this.path = note.getPath();
        this.filePath = note.getFilePathType();
    }

    /**
     * Creates a document at the specified path.
     *
     * @param path the given path that the created document will reside in.
     * @return a CommandResult based on whether the operation succeed or failed.
     */
    public CommandResult createDoc(String path) {
        String pathName = "";
        if (this.filePath.equals("abs")) {
            pathName = Notes.HOME_DIRECTORY + File.separatorChar + path;
        } else {
            pathName = Notes.getCurrentDirectory() + File.separatorChar + path;
        }
        buildDirectoryName(pathName); // Build the CurrentDirectory name
        File myFile = new File(pathName);
        if (myFile.exists() == true) {
            return new CommandResult(MESSAGE_CREATE_DUPLICATE);
        }
        try {
            myFile.createNewFile();
            Notes.setList(NotesListCommand.listfilesArray(Notes.getCurrentDirectory()));
            return new CommandResult(MESSAGE_CREATE_SUCCESS + pathName);
        } catch (IOException ex) {
            return new CommandResult(MESSAGE_CREATE_FAIL + pathName);

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

    /**
     * Creates a folder at the specified path.
     *
     * @param path the given path that the created document will reside in.
     * @return a CommandResult based on whether the operation succeed or failed.
     */
    public CommandResult createFolder(String path) {
        String pathName = "";
        if (this.filePath.equals("abs")) {
            pathName = Notes.HOME_DIRECTORY + File.separatorChar + path;
        } else {
            pathName = Notes.getCurrentDirectory() + File.separatorChar + path;
        }
        File myFile = new File(pathName);
        if (myFile.exists()) {
            return new CommandResult(pathName + MESSAGE_MAKEDIR_DUPLICATE);
        }
        Notes.setCurrentDirectory(pathName);
        if (myFile.mkdir()) { // return true if directory is created
            Notes.setList(NotesListCommand.listfilesArray(Notes.getCurrentDirectory()));
            System.out.println("successfully created");
            System.out.println(Notes.getCurrentDirectory());
            return new CommandResult(MESSAGE_MAKEDIR_SUCCESSFUL + pathName);
        } else {
            return new CommandResult(MESSAGE_MAKEDIR_UNSUCCESSFUL + pathName);
        }

    }

    public Notes getNote() {
        return this.note;
    }


    @Override
    public CommandResult execute(Model model) {

        model.updateNotesList(PREDICATE_SHOW_ALL_NOTES);

        if (this.type.equals("file")) {
            return createDoc(this.path);
        } else if (this.type.equals("folder")) {
            return createFolder(this.path);
        }

        return new CommandResult(MESSAGE_NOTHING_HAPPENED);
    }

    @Override
    public boolean equals(Object other) {

        NotesCreateCommand otherNotesOpen = (NotesCreateCommand) other;
        return this.note.equals(otherNotesOpen.getNote());

    }
}
