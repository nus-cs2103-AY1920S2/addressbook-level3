package seedu.address.logic.autocomplete;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;

import javafx.scene.control.TextField;
import seedu.address.commons.trie.SimilarWordsResult;
import seedu.address.commons.trie.Trie;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddExerciseCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteExerciseCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.ui.ResultDisplay;

/**
 * This class contains the logic behind the auto complete function. It makes use
 * of {@code Trie} as the underlying data structure.
 */
public class AutoComplete {

    public static final String FEEDBACK_EMPTY_STRING = "";
    public static final String FEEDBACK_MULTIPLE_COMMANDS = "Commands found:\n";
    public static final String FEEDBACK_NO_COMMANDS = "No commands found";

    private static final int CARET_POSITION_INDEX = Integer.MAX_VALUE;
    private static final String WHITE_SPACE_STRING = " ";

    private final Trie trie;
    private final TextField commandTextField;
    private final ResultDisplay resultDisplay;

    public AutoComplete(TextField commandTextField, ResultDisplay resultDisplay) {
        requireAllNonNull(commandTextField, resultDisplay);
        trie = new Trie();
        this.commandTextField = commandTextField;
        this.resultDisplay = resultDisplay;
        addAllCommands();
    }

    /**
     * Adds all the commands included in FitBiz to the {@code Trie}.
     */
    private void addAllCommands() {
        trie.insert(AddCommand.COMMAND_WORD);
        trie.insert(AddExerciseCommand.COMMAND_WORD);
        trie.insert(ClearCommand.COMMAND_WORD);
        trie.insert(DeleteCommand.COMMAND_WORD);
        trie.insert(DeleteExerciseCommand.COMMAND_WORD);
        trie.insert(EditCommand.COMMAND_WORD);
        trie.insert(ExitCommand.COMMAND_WORD);
        trie.insert(FindCommand.COMMAND_WORD);
        trie.insert(HelpCommand.COMMAND_WORD);
        trie.insert(ListCommand.COMMAND_WORD);
        trie.insert(ScheduleCommand.COMMAND_WORD);
        trie.insert(ViewCommand.COMMAND_WORD);
    }

    /**
     * Executes the main logic behind the autocomplete. Should be called when the
     * user presses "tab".
     */
    public void execute() {
        String currCommand = commandTextField.getText();

        // command has already been completed
        if (currCommand.contains(WHITE_SPACE_STRING)) {
            return;
        }

        SimilarWordsResult similarWords = trie.listAllSimilarWords(currCommand);
        String longestPrefix = similarWords.longestPrefixString;
        ArrayList<String> similarCommands = similarWords.similarWords;
        if (similarCommands.isEmpty()) {
            resultDisplay.setFeedbackToUser(FEEDBACK_NO_COMMANDS);
        } else if (similarCommands.size() == 1) {
            resultDisplay.setFeedbackToUser(FEEDBACK_EMPTY_STRING);
            commandTextField.setText(similarCommands.get(0));
            commandTextField.positionCaret(CARET_POSITION_INDEX);
        } else {
            commandTextField.setText(longestPrefix);
            commandTextField.positionCaret(CARET_POSITION_INDEX);
            resultDisplay.setFeedbackToUser(FEEDBACK_MULTIPLE_COMMANDS + similarCommands.toString());
        }
    }
}
