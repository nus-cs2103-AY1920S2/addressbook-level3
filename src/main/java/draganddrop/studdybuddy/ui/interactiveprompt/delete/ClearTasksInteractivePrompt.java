package draganddrop.studdybuddy.ui.interactiveprompt.delete;

/*
 * Logic of implementation:
 * IP will handle all interaction btw user and the window to get the final version of command
 * - FSM
 * Parser will handle to parsing of the command and create a command
 * command will execute the action
 * server display the response if needed
 */

import java.text.ParseException;

import draganddrop.studdybuddy.logic.commands.delete.ClearTasksCommand;
import draganddrop.studdybuddy.logic.commands.exceptions.CommandException;
import draganddrop.studdybuddy.ui.interactiveprompt.InteractivePrompt;
import draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms;
import draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptType;

/**
 * pending.
 */
public class ClearTasksInteractivePrompt extends InteractivePrompt {
    static final String END_OF_COMMAND_MSG = "Tasks cleared successfully!";
    static final String QUIT_COMMAND_MSG = "Successfully cleared all tasks.";

    public ClearTasksInteractivePrompt() {
        super();
        this.interactivePromptType = InteractivePromptType.CLEAR_TASK;
    }

    @Override
    public String interact(String userInput) {
        if (userInput.equals("quit")) {
            endInteract(QUIT_COMMAND_MSG);
            return reply;
        } else {
            userInput = checkForBackInput(userInput);
        }

        switch (currentTerm) {
        case INIT:
            this.reply = "Please press enter to clear all your tasks.\n"
                    + " Else enter quit to go back.";
            currentTerm = InteractivePromptTerms.READY_TO_EXECUTE;
            lastTerm = InteractivePromptTerms.INIT;
            terms.add(lastTerm);
            break;

        case READY_TO_EXECUTE:
            try {
                ClearTasksCommand clearTaskCommand = new ClearTasksCommand();
                logic.executeCommand(clearTaskCommand);
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
