package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES_OPERATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES_PATH;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.notes.Notes;


/**
 * NotesCommand is created when a notecommand is parsed, to return what operation to be done.
 * implementation is not yet completed
 */
public class NotesCommand extends Command {

    public static final String COMMAND_WORD = "notes";
    public static final String HOME_DIRECTORY = System.getProperty("user.home");

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Allows for creating, deleting and opening of new file "
            + "Parameters: "
            + PREFIX_NOTES_OPERATION + "OPERATION "
            + PREFIX_NOTES_PATH + "PATH \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NOTES_OPERATION + "open "
            + PREFIX_NOTES_PATH + "test.docx ";

    public static final String MESSAGE_OPEN_SUCCESS = "Opened file ";
    public static final String MESSAGE_OPEN_FAIL = "Failed to open file ";
    public static final String MESSAGE_UNABLE_TO_FIND = "Unable to find file to open ";

    private static final String MESSAGE_CREATE_SUCCESS = "File is successfully created at ";
    private static final String MESSAGE_CREATE_FAIL = "File is unable to be created at ";
    private static final String MESSAGE_NOTHING_HAPPENED = "Nothing Happened ";
    private static final String MESSAGE_MAKEDIR_SUCCESSFUL = "Directory is successfully created ";
    private static final String MESSAGE_MAKEDIR_UNSUCCESSFUL = "Directory is unable to be created ";


    private String notesOperation;
    private String path;

    /**
     * Creates a new NotesCommand to Create/Open/Delete a new note.
     *
     * @param note the operation and location that will be done to the note.
     */
    public NotesCommand(Notes note) {
        this.notesOperation = note.getOperation();
        this.path = note.getPath(); // the path will be with respect to the Desktop.
    }

    /**
     * Opens a document at the specified path.
     *
     * @param path the given path that the document resides in.
     * @return a CommandResult based on whether the operation succeed or failed.
     */
    public CommandResult openDoc(String path) {
        String pathName = HOME_DIRECTORY + File.separatorChar + "Desktop" + File.separatorChar + path;
        File myFile = new File(pathName);
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(myFile);
            }
            return new CommandResult(MESSAGE_OPEN_SUCCESS);
        } catch (IOException ex) {
            return new CommandResult(MESSAGE_OPEN_FAIL);
        } catch (IllegalArgumentException ex) {
            return new CommandResult(MESSAGE_UNABLE_TO_FIND);
        }

    }

    /**
     * Creates a document at the specified path.
     *
     * @param path the given path that the created document will reside in.
     * @return a CommandResult based on whether the operation succeed or failed.
     */
    public CommandResult createDoc(String path) {

        String pathName = HOME_DIRECTORY + File.separatorChar + "Desktop" + File.separatorChar + path;
        File myFile = new File(pathName);
        try {
            myFile.createNewFile();
            return new CommandResult(MESSAGE_CREATE_SUCCESS + pathName);
        } catch (IOException ex) {
            return new CommandResult(MESSAGE_CREATE_FAIL + pathName);

        }

    }

    /**
     * Creates a folder at the specified path.
     * @param path the given path that the created document will reside in.
     * @return a CommandResult based on whether the operation succeed or failed.
     */
    public CommandResult createFolder(String path) {
        String pathName = HOME_DIRECTORY + File.separatorChar + "Desktop" + File.separatorChar + path;
        File myFile = new File(pathName);

        if (myFile.mkdir()) { // return true if directory is created
            return new CommandResult(MESSAGE_MAKEDIR_SUCCESSFUL + pathName);
        } else {
            return new CommandResult(MESSAGE_MAKEDIR_UNSUCCESSFUL + pathName);
        }

    }

    @Override
    public CommandResult execute(Model model) {

        if (this.notesOperation.equals("open")) {
            return openDoc(this.path);
        } else if (this.notesOperation.equals("create")) {
            return createDoc(this.path);
        } else if (this.notesOperation.equals("createfolder")) {
            return createFolder(this.path);
        }
        return new CommandResult(MESSAGE_NOTHING_HAPPENED);
    }

    @Override // not yet finish implementing
    public boolean equals(Object other) {
        return other == this;
    }
}
