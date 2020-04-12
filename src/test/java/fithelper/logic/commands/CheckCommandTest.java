package fithelper.logic.commands;

import static fithelper.logic.commands.CheckCommand.MESSAGE_MAX_THREE_RESULTS;
import static fithelper.logic.commands.CommandTestUtil.assertCommandSuccess;
import static fithelper.testutil.AssertUtil.assertThrows;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import fithelper.model.Model;
import fithelper.model.ModelStub;
import fithelper.model.calorietable.CalorieDatum;
import fithelper.model.calorietable.FoodCalorieDatum;
import fithelper.model.calorietable.SportsCalorieDatum;
import fithelper.model.entry.Type;

public class CheckCommandTest {

    @Test
    public void createCommand_withoutKeywords_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CheckCommand(new Type("food"), null));
    }

    @Test
    public void createCommand_withoutCheckType_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CheckCommand(null, "apple"));
    }

    @Test
    public void executeCommand_noMatchingResult_feedbackFailureToUser() {
        // we use a stub to simulate the scenario where the set returned from search is empty
        Model model = new ModelStub();
        CheckCommand command = new CheckCommand(new Type("food"), "test");
        String expectedMessage = CheckCommand.MESSAGE_FAILURE_PART1 + command.getCheckType().getValue()
                + CheckCommand.MESSAGE_FAILURE_PART2 + command.getKeywords() + "\n";
        assertCommandSuccess(command, model, expectedMessage, model);

        command = new CheckCommand(new Type("sports"), "test");
        expectedMessage = CheckCommand.MESSAGE_FAILURE_PART1 + command.getCheckType().getValue()
                + CheckCommand.MESSAGE_FAILURE_PART2 + command.getKeywords() + "\n";
        assertCommandSuccess(command, model, expectedMessage, model);
    }

    @Test
    public void executeCommand_hasMatchingResults_showResultsToUser() {
        // we use a stub to simulate the scenario where the set returned from search is non-empty
        // result contains 1 datum
        Model model = new ModelStubWithMatchingResults();
        CheckCommand command = new CheckCommand(new Type("food"), "test");
        String expectedMessage = CheckCommand.MESSAGE_SUCCESS
                + new FoodCalorieDatum("test", "123", "100g").toString()
                + MESSAGE_MAX_THREE_RESULTS;;
        assertCommandSuccess(command, model, expectedMessage, model);

        // result contains 3 data
        command = new CheckCommand(new Type("sports"), "test");
        expectedMessage = CheckCommand.MESSAGE_SUCCESS
                + new SportsCalorieDatum("test1", "456").toString()
                + new SportsCalorieDatum("test2", "789").toString()
                + new SportsCalorieDatum("test3", "123").toString()
                + MESSAGE_MAX_THREE_RESULTS;;
        assertCommandSuccess(command, model, expectedMessage, model);
    }

    private class ModelStubWithMatchingResults extends ModelStub {
        @Override
        public Set<CalorieDatum> searchFoodCalorieTable(String keywords) {
            Set<CalorieDatum> result = new LinkedHashSet<>();
            result.add(new FoodCalorieDatum("test", "123", "100g"));
            return result;
        }

        @Override
        public Set<CalorieDatum> searchSportsCalorieTable(String keywords) {
            Set<CalorieDatum> result = new LinkedHashSet<>();
            result.add(new SportsCalorieDatum("test1", "456"));
            result.add(new SportsCalorieDatum("test2", "789"));
            result.add(new SportsCalorieDatum("test3", "123"));
            return result;
        }
    }
}
