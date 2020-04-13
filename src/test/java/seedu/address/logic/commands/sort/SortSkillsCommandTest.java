package seedu.address.logic.commands.sort;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.results.CommandResult;
import seedu.address.logic.commands.results.SortCommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.item.Skill;
import seedu.address.model.util.ItemUtil;
import seedu.address.testutil.TypicalResumeBook;

public class SortSkillsCommandTest {
    private Model expectedModel;
    private Model model;

    @BeforeEach
    public void setUp() {
        expectedModel = new ModelManager(TypicalResumeBook.TYPICAL_WITHOUT_GOOGLE, new UserPrefs());
        model = new ModelManager(TypicalResumeBook.TYPICAL_WITHOUT_GOOGLE, new UserPrefs());
    }

    @Test
    public void execute_sortSkillsByNameReverseFalse_success() {
        expectedModel.sortSkills(Comparator.comparing(Skill::getName));
        expectedModel.setSkillToDisplay();

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, Skill.class.getSimpleName());
        CommandResult commandResult =
                new SortCommandResult(expectedMessage, ItemUtil.SKILL_ALIAS);
        assertCommandSuccess(new SortSkillsCommand("name", false), model, commandResult, expectedModel);
    }

    @Test
    public void execute_sortSkillsByLevelReverseTrue_success() {
        expectedModel.sortSkills(Comparator.comparing(Skill::getLevel).reversed());
        expectedModel.setSkillToDisplay();

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, Skill.class.getSimpleName());
        CommandResult commandResult =
                new SortCommandResult(expectedMessage, ItemUtil.SKILL_ALIAS);
        assertCommandSuccess(
                new SortSkillsCommand("level", true), model, commandResult, expectedModel);
    }

}
