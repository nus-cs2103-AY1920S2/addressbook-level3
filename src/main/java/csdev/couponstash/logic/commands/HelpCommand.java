package csdev.couponstash.logic.commands;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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

    private static final String FILE_PATH =
            "https://ay1920s2-cs2103t-f09-1.github.io/main/UserGuide.html";

    @Override
    public CommandResult execute(Model model, String commandText) throws CommandException {
        openBrowser(FILE_PATH);
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }

    /**
     * Open path in system browser.
     * @param path
     * @throws CommandException
     */
    private static void openBrowser(String path) throws CommandException {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("linux") || os.contains(("unix"))) {
            Runtime runtime = Runtime.getRuntime();

            try {
                if (runtime.exec("which xdg-open").getInputStream().read() != -1) {
                    runtime.exec("xdg-open " + path);
                } else {
                    throw new CommandException(UNSUPPORTED_OS);
                }
            } catch (IOException e) {
                throw new CommandException(ERROR);
            }
        } else if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(path));
            } catch (IOException e) {
                throw new CommandException(ERROR);
            } catch (URISyntaxException e) {
                throw new CommandException("The link has error");
            }
        } else {
            throw new CommandException(UNSUPPORTED_OS);
        }
    }
}


