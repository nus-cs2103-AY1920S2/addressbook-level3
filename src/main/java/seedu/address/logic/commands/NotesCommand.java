package seedu.address.logic.commands;

import seedu.address.model.Model;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class NotesCommand extends Command {

    public static final String COMMAND_WORD = "notes";
    public static final String MESSAGE_SUCCESS = "Opened microsoft Word";
    public static final String MESSAGE_FAIL = "Failed to open microsoft Word";
    private static final String HOME_DIRECTORY = System.getProperty("user.home");


    public void openWord(String fileName) throws IOException {
        String pathName = HOME_DIRECTORY + File.separatorChar + "Desktop" + File.separatorChar + fileName;
        System.out.println(pathName);
        File myFile = new File(pathName);
        // change it pathing,
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(myFile);
        }

    }

    @Override
    public CommandResult execute(Model model) {
        try {
            openWord("test.docx");
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (IOException ex) {
            ex.printStackTrace();
            return new CommandResult(MESSAGE_FAIL);
        }


    }

    @Override // not yet finish implementing
    public boolean equals(Object other) {
        return other == this;
    }
}
