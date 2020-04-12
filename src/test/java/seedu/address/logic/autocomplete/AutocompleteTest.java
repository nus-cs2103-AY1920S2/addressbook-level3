package seedu.address.logic.autocomplete;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddExerciseCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.GraphCommand;
import seedu.address.logic.commands.ScheduleCommand;

public class AutocompleteTest {

    // Ambiguous commands
    private static final String AC_COMMAND = "a";
    private static final int AC_CURRENT_CARET_POSITION = 1;
    private static final int AC_CARET_POSITION_TO_SET = Integer.MAX_VALUE;
    private static final String AC_FEEDBACK_TO_SET = Autocomplete.FEEDBACK_MULTIPLE_COMMANDS + "[add-e, add-c]";
    private static final String AC_COMMAND_TO_SET = "add-";

    // Invalid commands
    private static final String IC_COMMAND = "FitBiz!!";
    private static final int IC_CURRENT_CARET_POSITION = 8;
    private static final Integer IC_CARET_POSITION_TO_SET = null;
    private static final String IC_FEEDBACK_TO_SET = Autocomplete.FEEDBACK_NO_COMMANDS;
    private static final String IC_COMMAND_TO_SET = null;

    // Unambiguous command: "add-c"
    private static final String ADD_COMMAND = "add-c";
    private static final int ADD_CURRENT_CARET_POSITION = 5;
    private static final int ADD_CARET_POSITION_TO_SET = 8; // index right after the "/" in "n/"
    private static final String ADD_FEEDBACK_TO_SET = AddCommand.MESSAGE_USAGE;
    private static final String ADD_COMMAND_TO_SET = "add-c n/ p/ e/ a/ g/ b/ cw/ tw/ h/ r/ s/ t/";

    // Unambiguous command: "add-e"
    private static final String ADD_EXERCISE_COMMAND = "add-e";
    private static final int ADD_EXERCISE_CURRENT_CARET_POSITION = 5;
    private static final int ADD_EXERCISE_CARET_POSITION_TO_SET = 8; // index right after the "/" in "n/"
    private static final String ADD_EXERCISE_FEEDBACK_TO_SET = AddExerciseCommand.MESSAGE_USAGE;
    private static final String ADD_EXERCISE_COMMAND_TO_SET = "add-e n/ d/ reps/ ew/ sets/";

    // Unambiguous command: "filter-c"
    private static final String FILTER_COMMAND = "filt";
    private static final int FILTER_CURRENT_CARET_POSITION = 4;
    private static final int FILTER_CARET_POSITION_TO_SET = 11; // index right after the "/" in "t/"
    private static final String FILTER_FEEDBACK_TO_SET = FilterCommand.MESSAGE_USAGE;
    private static final String FILTER_COMMAND_TO_SET = "filter-c t/ s/";

    // Unambiguous command: "graph"
    private static final String GRAPH_COMMAND = "gra";
    private static final int GRAPH_CURRENT_CARET_POSITION = 3;
    private static final int GRAPH_CARET_POSITION_TO_SET = 8; // index right after the "/" in "n/"
    private static final String GRAPH_FEEDBACK_TO_SET = GraphCommand.MESSAGE_USAGE;
    private static final String GRAPH_COMMAND_TO_SET = "graph n/ a/ sd/ ed/";

    // Unambiguous command: "export"
    private static final String EXPORT_COMMAND = "exp";
    private static final int EXPORT_CURRENT_CARET_POSITION = 3;
    private static final int EXPORT_CARET_POSITION_TO_SET = Integer.MAX_VALUE;
    private static final String EXPORT_FEEDBACK_TO_SET = ExportCommand.MESSAGE_USAGE;
    private static final String EXPORT_COMMAND_TO_SET = "export";

    // Unambiguous command: "schedule"
    private static final String SCHEDULE_COMMAND = "sch";
    private static final int SCHEDULE_CURRENT_CARET_POSITION = 3;
    private static final int SCHEDULE_CARET_POSITION_TO_SET = 9; // index just after first white space
    private static final String SCHEDULE_FEEDBACK_TO_SET = ScheduleCommand.MESSAGE_USAGE;
    private static final String SCHEDULE_COMMAND_TO_SET = "schedule  sch/";

    // Using tab while command is completed to go to next prefix for "graph"
    private static final int GRAPH_FIRST_PREFIX_INDEX = 8;
    private static final int GRAPH_SECOND_PREFIX_INDEX = 11;
    private static final int GRAPH_THIRD_PREFIX_INDEX = 15;
    private static final int GRAPH_LAST_PREFIX_INDEX = 19;

    // Using tab while command is completed to go to next prefix for "export"
    private static final String EXPORT_COMMAND_WITHOUT_PREFIX = "export random things";
    private static final int EXPORT_COMMAND_WITHOUT_PREFIX_INDEX = 12;

    private Autocomplete autoComplete = new Autocomplete();

    @Test
    public void execute_ambiguousCommand_returnsCorrectAutoCompleteResult() {
        AutocompleteResult result = autoComplete.execute(AC_COMMAND, AC_CURRENT_CARET_POSITION);

        assertEquals(result.getTextToSet(), AC_COMMAND_TO_SET);
        assertEquals(result.getCaretPositionToSet(), AC_CARET_POSITION_TO_SET);
        assertEquals(result.getTextToFeedback(), AC_FEEDBACK_TO_SET);
    }

    @Test
    public void execute_invalidCommand_returnsCorrectAutoCompleteResult() {
        AutocompleteResult result = autoComplete.execute(IC_COMMAND, IC_CURRENT_CARET_POSITION);

        assertEquals(result.getTextToSet(), IC_COMMAND_TO_SET);
        assertEquals(result.getCaretPositionToSet(), IC_CARET_POSITION_TO_SET);
        assertEquals(result.getTextToFeedback(), IC_FEEDBACK_TO_SET);
    }

    @Test
    public void execute_addCommand_returnsCorrectAutoCompleteResult() {
        AutocompleteResult result = autoComplete.execute(ADD_COMMAND, ADD_CURRENT_CARET_POSITION);

        assertEquals(result.getTextToSet(), ADD_COMMAND_TO_SET);
        assertEquals(result.getCaretPositionToSet(), ADD_CARET_POSITION_TO_SET);
        assertEquals(result.getTextToFeedback(), ADD_FEEDBACK_TO_SET);
    }

    @Test
    public void execute_addExerciseCommand_returnsCorrectAutoCompleteResult() {
        AutocompleteResult result = autoComplete.execute(ADD_EXERCISE_COMMAND, ADD_EXERCISE_CURRENT_CARET_POSITION);

        assertEquals(result.getTextToSet(), ADD_EXERCISE_COMMAND_TO_SET);
        assertEquals(result.getCaretPositionToSet(), ADD_EXERCISE_CARET_POSITION_TO_SET);
        assertEquals(result.getTextToFeedback(), ADD_EXERCISE_FEEDBACK_TO_SET);
    }

    @Test
    public void execute_filterCommand_returnsCorrectAutoCompleteResult() {
        AutocompleteResult result = autoComplete.execute(FILTER_COMMAND, FILTER_CURRENT_CARET_POSITION);

        assertEquals(result.getTextToSet(), FILTER_COMMAND_TO_SET);
        assertEquals(result.getCaretPositionToSet(), FILTER_CARET_POSITION_TO_SET);
        assertEquals(result.getTextToFeedback(), FILTER_FEEDBACK_TO_SET);
    }

    @Test
    public void execute_exportCommand_returnsCorrectAutoCompleteResult() {
        AutocompleteResult result = autoComplete.execute(EXPORT_COMMAND, EXPORT_CURRENT_CARET_POSITION);

        assertEquals(result.getTextToSet(), EXPORT_COMMAND_TO_SET);
        assertEquals(result.getCaretPositionToSet(), EXPORT_CARET_POSITION_TO_SET);
        assertEquals(result.getTextToFeedback(), EXPORT_FEEDBACK_TO_SET);
    }

    @Test
    public void execute_graphCommand_returnsCorrectAutoCompleteResult() {
        AutocompleteResult result = autoComplete.execute(GRAPH_COMMAND, GRAPH_CURRENT_CARET_POSITION);

        assertEquals(result.getTextToSet(), GRAPH_COMMAND_TO_SET);
        assertEquals(result.getCaretPositionToSet(), GRAPH_CARET_POSITION_TO_SET);
        assertEquals(result.getTextToFeedback(), GRAPH_FEEDBACK_TO_SET);
    }

    @Test
    public void execute_scheduleCommand_returnsCorrectAutoCompleteResult() {
        AutocompleteResult result = autoComplete.execute(SCHEDULE_COMMAND, SCHEDULE_CURRENT_CARET_POSITION);

        assertEquals(result.getTextToSet(), SCHEDULE_COMMAND_TO_SET);
        assertEquals(result.getCaretPositionToSet(), SCHEDULE_CARET_POSITION_TO_SET);
        assertEquals(result.getTextToFeedback(), SCHEDULE_FEEDBACK_TO_SET);
    }

    @Test
    public void execute_graphCommandAlreadyCompleted_returnsCorrectCaretPositions() {
        AutocompleteResult firstResult = autoComplete.execute(GRAPH_COMMAND, GRAPH_CURRENT_CARET_POSITION);
        assertEquals(firstResult.getCaretPositionToSet(), GRAPH_FIRST_PREFIX_INDEX);

        AutocompleteResult secondResult = autoComplete.execute(GRAPH_COMMAND_TO_SET, GRAPH_FIRST_PREFIX_INDEX);
        assertEquals(secondResult.getCaretPositionToSet(), GRAPH_SECOND_PREFIX_INDEX);

        AutocompleteResult thirdResult = autoComplete.execute(GRAPH_COMMAND_TO_SET, GRAPH_SECOND_PREFIX_INDEX);
        assertEquals(thirdResult.getCaretPositionToSet(), GRAPH_THIRD_PREFIX_INDEX);

        AutocompleteResult lastResult = autoComplete.execute(GRAPH_COMMAND_TO_SET, GRAPH_THIRD_PREFIX_INDEX);
        assertEquals(lastResult.getCaretPositionToSet(), GRAPH_LAST_PREFIX_INDEX);

        AutocompleteResult wrapAroundResult = autoComplete.execute(GRAPH_COMMAND_TO_SET, GRAPH_LAST_PREFIX_INDEX);
        assertEquals(wrapAroundResult.getCaretPositionToSet(), GRAPH_FIRST_PREFIX_INDEX);
    }

    @Test
    public void execute_exportCommandAlreadyCompleted_returnsCorrectCaretPositions() {
        AutocompleteResult firstResult = autoComplete.execute(EXPORT_COMMAND, EXPORT_CURRENT_CARET_POSITION);
        assertEquals(firstResult.getCaretPositionToSet(), EXPORT_CARET_POSITION_TO_SET);

        AutocompleteResult secondResult = autoComplete.execute(EXPORT_COMMAND_WITHOUT_PREFIX,
                EXPORT_COMMAND_WITHOUT_PREFIX_INDEX);
        assertEquals(secondResult.getCaretPositionToSet(), null);
    }

}
