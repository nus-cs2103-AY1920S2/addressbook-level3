package draganddrop.studdybuddy.ui.interactiveprompt.edit;

import static draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptType.EDIT_TASK;

import java.time.LocalDateTime;

import draganddrop.studdybuddy.commons.core.index.Index;
import draganddrop.studdybuddy.logic.commands.edit.EditTaskCommand;
import draganddrop.studdybuddy.logic.commands.exceptions.CommandException;
import draganddrop.studdybuddy.logic.parser.exceptions.ParseException;
import draganddrop.studdybuddy.logic.parser.interactivecommandparser.EditTaskCommandParser;
import draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.EditTaskCommandException;
import draganddrop.studdybuddy.model.module.Module;
import draganddrop.studdybuddy.model.task.Task;
import draganddrop.studdybuddy.model.task.TaskField;
import draganddrop.studdybuddy.model.task.TaskType;
import draganddrop.studdybuddy.ui.interactiveprompt.InteractivePrompt;
import draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms;

/**
 * Interactive prompt for editing tasks
 */
public class EditTaskInteractivePrompt extends InteractivePrompt {
    static final String END_OF_COMMAND_MSG = "Task edited successfully";
    static final String QUIT_COMMAND_MSG = "Successfully quit from the edit task command";

    private int taskNumber;
    private TaskField taskField;

    public EditTaskInteractivePrompt() {
        super();
        this.interactivePromptType = EDIT_TASK;
    }

    @Override
    public String interact(String userInput) {
        if (isQuitOrBack(userInput)) {
            this.reply = handleQuitAndBack(userInput, QUIT_COMMAND_MSG);
        } else {
            this.reply = handleEdit(userInput);
        }
        return this.reply;
    }

    /**
     * handles the sequence of commands for edit
     * @param userInput input from user
     * @return reply to user
     */
    public String handleEdit(String userInput) {
        if (userInput.equals("quit")) {
            endInteract(QUIT_COMMAND_MSG);
            return reply;
        } else if (userInput.equals("back")) {
            userInput = checkForBackInput(userInput);
        }

        switch (currentTerm) {
        case INIT:
            this.reply = "Please enter the index of the task that you wish to edit.";
            this.currentTerm = InteractivePromptTerms.TASK_NUMBER;
            break;
        case TASK_NUMBER:
            this.taskNumber = parseTaskNumber(userInput);
            break;
        case TASK_FIELD:
            this.taskField = parseTaskFieldNumber(userInput);
            break;
        case NEW_VALUE:
            this.reply = handleNewValue(userInput);
            break;
        default:
            break;
        }
        return this.reply;
    }

    /**
     * Creates and executes an edit command, with the new values provided by the user
     * @param userInput input from user
     * @return reply to user
     */
    public String handleNewValue(String userInput) {
        Index taskIndex = Index.fromZeroBased(taskNumber - 1);
        EditTaskCommand editTaskCommand = new EditTaskCommand(taskIndex, taskField);
        boolean parseSuccess = true;
        String successMessage = END_OF_COMMAND_MSG;
        try {
            switch (taskField) {
            case TASK_NAME:
                String newName = EditTaskCommandParser.parseName(userInput);
                editTaskCommand.provideNewTaskName(newName);
                break;
            case TASK_TYPE:
                TaskType newTaskType = EditTaskCommandParser.parseType(userInput, TaskType.getTaskTypes().length);
                editTaskCommand.provideNewTaskType(newTaskType);
                successMessage = "The type of task is successfully changed to: " + newTaskType + ".\n";
                break;
            case TASK_DATETIME:
                LocalDateTime[] newDateTimes = EditTaskCommandParser.parseDateTime(userInput);
                editTaskCommand.provideNewDateTime(newDateTimes);
                break;
            case TASK_MODULE:
                Module newModule = EditTaskCommandParser.parseModule(userInput);
                editTaskCommand.provideNewModule(newModule);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + taskField);
            }
        } catch (EditTaskCommandException ex) {
            parseSuccess = false;
            reply = ex.getErrorMessage();
        }

        if (parseSuccess) {
            try {
                logic.executeCommand(editTaskCommand);
                endInteract(successMessage);
            } catch (java.text.ParseException | CommandException ex) {
                reply = ex.getMessage();
            }
        }
        return reply;
    }

    /**
     * parses task number
     * @param userInput user input for task number
     * @return an int of task number
     */
    public int parseTaskNumber(String userInput) {
        boolean isParseSuccessful = true;
        int taskNum = -1;

        try {
            taskNum = Integer.parseInt(userInput);
            if (taskNum > Task.getCurrentTasks().size() || taskNum < 1) {
                throw new ParseException("task number not in range");
            }
        } catch (NumberFormatException | ParseException ex) {
            isParseSuccessful = false;
        }

        if (isParseSuccessful) {
            this.reply = "Please choose the field that you wish to edit in task number " + taskNum + ".\n"
                        + TaskField.getFieldString();
            this.currentTerm = InteractivePromptTerms.TASK_FIELD;
        } else {
            // prompt for a new value
            this.reply = "Please choose a valid task number.";
            this.currentTerm = InteractivePromptTerms.TASK_NUMBER;
        }
        return taskNum;
    }

    /**
     * parses the task field
     * @param userInput userInput for task number
     * @return a TaskField
     */
    public TaskField parseTaskFieldNumber(String userInput) {
        boolean isParseSuccessful = true;
        TaskField taskField = null;

        try {
            int taskFieldNumber = Integer.parseInt(userInput);
            if (taskFieldNumber > 4 || taskFieldNumber < 1) {
                throw new ParseException("task field number not in range");
            }
            taskField = TaskField.getTaskFieldFromNumber(taskFieldNumber);
        } catch (NumberFormatException | ParseException ex) {
            isParseSuccessful = false;
        }

        if (isParseSuccessful) {
            assert(taskField != null);
            this.reply = getTaskFieldMessage(taskField);
            this.currentTerm = InteractivePromptTerms.NEW_VALUE;
        } else {
            // prompt for a new value
            this.reply = "Please choose a valid task field index";
            this.currentTerm = InteractivePromptTerms.TASK_FIELD;
        }
        return taskField;
    }

    private String getTaskFieldMessage(TaskField taskField) {
        String result = "You are now editing the " + taskField.getLabel() + "field\n";
        switch (taskField) {
        case TASK_NAME:
            result += "Please enter the new task name.";
            break;
        case TASK_TYPE:
            result += "Please choose the task type:\n"
                + TaskType.getTypeString();
            break;
        case TASK_DATETIME:
            result += "Please enter the deadline with format: "
                + "HH:mm dd/MM/yyyy-HH:mm dd/MM/yyyy";;
            break;
        case TASK_MODULE:
            result += "Please enter the module code that you wish to assign to this task.\n"
                    + "You can find all the modules available under Modules/Show Modules in the menu bar above.";
            break;
        default:
            throw new IllegalStateException("Unexpected value: " + taskField);
        }
        return result;
    }


}

