package seedu.eylah;

import java.util.logging.Logger;

import seedu.eylah.commons.core.LogsCenter;
import seedu.eylah.commons.core.Version;
import seedu.eylah.diettracker.logic.Logic;
import seedu.eylah.diettracker.logic.LogicManager;
import seedu.eylah.diettracker.logic.commands.CommandResult;
import seedu.eylah.diettracker.logic.commands.exceptions.CommandException;
import seedu.eylah.diettracker.logic.parser.exceptions.ParseException;
import seedu.eylah.diettracker.model.Model;
import seedu.eylah.diettracker.model.ModelManager;
import seedu.eylah.ui.Ui;

/**
 * The main entry for the EYLAH.
 */
public class Eylah {

    public static final Version VERSION = new Version(0, 1, 0, true);

    private static final Logger logger = LogsCenter.getLogger(Eylah.class);

    protected Logic dietLogic;
    protected Model dietModel;
    protected seedu.eylah.expensesplitter.logic.Logic splitterLogic;
    protected seedu.eylah.expensesplitter.model.Model splitterModel;

    private Ui ui;

    public Eylah() {
        logger.info("=============================[ Initializing EYLAH ]===========================");
        ui = new Ui();
    }

    /**
     * Main method to run the application.
     * For now just ignore these messy code, this is a temporary code for testing purpose.
     * After start the app, only can choose to run in diet or splitter mode,
     * the way to exit the app just simply close the app in terminal.
     *
     */
    public void run() {
        boolean isExit = false;
        ui.showWelcome();
        System.out.println("Enter mode (diet/splitting): ");
        String input = ui.readCommand();
        if (input.equals("diet")) {
            // Diet mode
            logger.info("Entering Diet MODE.");
            dietModel = new ModelManager();
            dietLogic = new LogicManager(dietModel, null);
            while (!isExit) {
                System.out.println("Enter Diet Command: ");
                input = ui.readCommand();
                try {
                    CommandResult commandResult = dietLogic.execute(input);
                    // Here will print out the respond to user
                    System.out.println(commandResult.getFeedbackToUser());
                } catch (CommandException | ParseException e) {
                    e.printStackTrace();
                    break;
                }
            }
        } else {
            // Splitting mode
            System.out.println("Entering Splitting MODE.");
            splitterModel = new seedu.eylah.expensesplitter.model.ModelManager();
            splitterLogic = new seedu.eylah.expensesplitter.logic.LogicManager(splitterModel, null);
            while (!isExit) {
                System.out.println("Enter Splitting Command: ");
                input = ui.readCommand();
                try {
                    seedu.eylah.expensesplitter.logic.commands.CommandResult commandResult =
                            splitterLogic.execute(input);
                    // Here will print out the respond to user
                    System.out.println(commandResult.getFeedbackToUser());
                } catch (seedu.eylah.expensesplitter.logic.commands.exceptions.CommandException
                        | seedu.eylah.expensesplitter.logic.parser.exceptions.ParseException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    /**
     * Starting point for whole application.
     */
    public static void main(String[] args) {
        Eylah eylah = new Eylah();
        eylah.run();
    }
}
