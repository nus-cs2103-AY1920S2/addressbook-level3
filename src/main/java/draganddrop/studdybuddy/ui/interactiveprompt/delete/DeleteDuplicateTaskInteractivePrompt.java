package draganddrop.studdybuddy.ui.interactiveprompt.delete;

/*
 * Logic of implementation:
 * IP will handle all interaction btw user and the window to get the final version of command
 * - FSM
 * Parser will handle to parsing of the command and create a command
 * command will execute the action
 * server display the response if needed
 * */

import static draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptType.DELETE_DUPLICATE_TASK;

import java.text.ParseException;

import draganddrop.studdybuddy.logic.commands.delete.DeleteDuplicateTaskCommand;
import draganddrop.studdybuddy.logic.commands.exceptions.CommandException;
import draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.DeleteDuplicateTaskCommandException;
import draganddrop.studdybuddy.ui.interactiveprompt.InteractivePrompt;
import draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms;

/**
 * pending.
 */
public class DeleteDuplicateTaskInteractivePrompt extends InteractivePrompt {
    static final String END_OF_COMMAND_MSG = "Duplicated task deleted successfully!";
    static final String QUIT_COMMAND_MSG = "Successfully quited from delete duplication command.";

    public DeleteDuplicateTaskInteractivePrompt() {
        super();
        this.interactivePromptType = DELETE_DUPLICATE_TASK;
    }

    @Override
    public String interact(String userInput) {
        if (userInput.equals("quit")) {
            endInteract(QUIT_COMMAND_MSG);
            return reply;
        }

        switch (currentTerm) {

        case INIT:
            try {
                reply = "The duplicate tasks will be deleted\n "
                    + " Please press enter again to make the desired changes.";
                currentTerm = InteractivePromptTerms.READY_TO_EXECUTE;
                lastTerm = InteractivePromptTerms.INIT;
                terms.add(lastTerm);
            } catch (DeleteDuplicateTaskCommandException ex) {
                reply = ex.getErrorMessage();
            }
            break;

        case READY_TO_EXECUTE:
            try {
                DeleteDuplicateTaskCommand deleteDuplicateTaskCommand = new DeleteDuplicateTaskCommand();
                logic.executeCommand(deleteDuplicateTaskCommand);
                super.setEndOfCommand(true);
                endInteract(END_OF_COMMAND_MSG);
            } catch (CommandException | ParseException ex) {
                reply = ex.getMessage();
            }
            break;

        default:
        }
        return reply;
    }

    @Override
    public void interruptInteract() {

    }

    @Override
    public void endInteract(String msg) {
        this.reply = msg;
        super.setEndOfCommand(true);
    }

    @Override
    public void back() {

    }

    @Override
    public void next() {

    }

    /**
     * pending.
     */
    private String dateTime() {
        String result = "";


        return result;
    }
}
