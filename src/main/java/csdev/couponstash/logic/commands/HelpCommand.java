package csdev.couponstash.logic.commands;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import csdev.couponstash.logic.commands.exceptions.CommandException;
import csdev.couponstash.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows program user guide in a browser window!\n\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened program user guide in a browser window.";

    public static final String UNSUPPORTED_OS =
            "Your operating system does not allow the opening of the browser automatically. "
                    + "Visit https://ay1920s2-cs2103t-f09-1.github.io/main/UserGuide.html with your web browser "
                    + "to view the user guide.";

    public static final String ERROR = "An error has occurred. Please try the help command again.";

    private static final String HTML_NAME = "help.html";

    @Override
    public CommandResult execute(Model model, String commandText) throws CommandException {
        openBrowser(getHelpHtmlPath());
        return new CommandResult(SHOWING_HELP_MESSAGE);
    }

    /**
     * Get URI of help.html. If help.html is not extracted from jar file yet,
     * extract it.
     * @return URI of help.html
     * @throws CommandException
     */
    private static URI getHelpHtmlPath() throws CommandException {
        try {
            File file = new File(HTML_NAME);
            if (file.exists()) {
                return file.toURI();
            }

            // If help.html is not extracted yet, extract HTML from jar file
            URI jarPath = HelpCommand.class.getProtectionDomain().getCodeSource().getLocation().toURI();
            JarFile jar = new JarFile(jarPath.getPath());

            InputStream inputStream = jar.getInputStream(new JarEntry(HTML_NAME));
            FileOutputStream fileOutputStream = new java.io.FileOutputStream(file);
            while (inputStream.available() > 0) {
                fileOutputStream.write(inputStream.read());
            }
            fileOutputStream.close();
            inputStream.close();

            return file.toURI();
        } catch (URISyntaxException e) {
            throw new CommandException(UNSUPPORTED_OS);
        } catch (IOException e) {
            throw new CommandException(ERROR);
        }
    }

    /**
     * Open path in system browser.
     *
     * @param path
     * @throws CommandException
     */
    private static void openBrowser(URI path) throws CommandException {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("linux") || os.contains(("unix"))) {
            Runtime runtime = Runtime.getRuntime();

            try {
                if (runtime.exec("which xdg-open").getInputStream().read() != -1) {
                    runtime.exec("xdg-open " + path.toString());
                } else {
                    throw new CommandException(UNSUPPORTED_OS);
                }
            } catch (IOException e) {
                throw new CommandException(ERROR);
            }
        } else if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(path);
            } catch (IOException e) {
                throw new CommandException(ERROR);
            }
        } else {
            throw new CommandException(UNSUPPORTED_OS);
        }
    }
}


