package draganddrop.studdybuddy.ui.interactiveprompt.edit;

/*
 * Logic of implementation:
 * IP will handle all interaction btw user and the window to get the final version of command
 * - FSM
 * Parser will handle to parsing of the command and create a command
 * command will execute the action
 * server display the response if needed
 * */

import static draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptType.ARCHIVE_TASK;

import java.text.ParseException;

import draganddrop.studdybuddy.commons.core.index.Index;
import draganddrop.studdybuddy.logic.commands.edit.ArchiveTaskCommand;
import draganddrop.studdybuddy.logic.commands.exceptions.CommandException;
import draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.ArchiveTaskCommandException;
import draganddrop.studdybuddy.ui.interactiveprompt.InteractivePrompt;
import draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms;

/**
 * pending.
 */
public class ArchiveTaskInteractivePrompt extends InteractivePrompt {
    static final String END_OF_COMMAND_MSG = "Task archived successfully!";
    static final String QUIT_COMMAND_MSG = "Successfully quited from archive command.";
    private int index;

    public ArchiveTaskInteractivePrompt() {
        super();
        this.interactivePromptType = ARCHIVE_TASK;
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
            this.reply = "Please enter the index number of task you wish to archive.";
            currentTerm = InteractivePromptTerms.TASK_INDEX;
            lastTerm = InteractivePromptTerms.INIT;
            terms.add(lastTerm);
            break;

        case TASK_INDEX:
            try {
                index = Integer.parseInt(userInput);
                reply = "The task at index " + userInput + " will be archived. \n "
                    + " Please press enter again to make the desired changes.";
                currentTerm = InteractivePromptTerms.READY_TO_EXECUTE;
                lastTerm = InteractivePromptTerms.TASK_INDEX;
                terms.add(lastTerm);
            } catch (ArchiveTaskCommandException ex) {
                reply = ex.getErrorMessage();
            }
            break;

        case READY_TO_EXECUTE:
            try {
                ArchiveTaskCommand archiveTaskCommand = new ArchiveTaskCommand(Index.fromZeroBased(index - 1));
                logic.executeCommand(archiveTaskCommand);
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
    public void endInteract(String reply) {
        this.reply = reply;
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
