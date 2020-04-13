package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ITEM;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.item.Resume;
import seedu.address.testutil.ItemIndicesBuilder;
import seedu.address.testutil.ResumeBuilder;
import seedu.address.testutil.TypicalInternship;
import seedu.address.testutil.TypicalProject;
import seedu.address.testutil.TypicalResume;
import seedu.address.testutil.TypicalResumeBook;
import seedu.address.testutil.TypicalSkill;

public class ResumeEditCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalResumeBook.TYPICAL_WITH_FILLED_RESUME, new UserPrefs());
    }

    @Test
    public void execute_invalidResumeIndex_throwsCommandException() {
        Index invalidIndex = INDEX_FOURTH_ITEM;
        Optional<List<Integer>> internshipIndices = ItemIndicesBuilder.empty();
        Optional<List<Integer>> projectIndices = ItemIndicesBuilder.empty();
        Optional<List<Integer>> skillIndices = ItemIndicesBuilder.empty();
        ResumeEditCommand resumeEditCommand = new ResumeEditCommand(invalidIndex, internshipIndices, projectIndices,
                skillIndices);
        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_INDEX, () -> resumeEditCommand.execute(model));
    }

    @Test
    public void execute_invalidInternshipIndex_throwsCommandException() {
        Index validIndex = INDEX_FIRST_ITEM;
        Optional<List<Integer>> internshipIndices = new ItemIndicesBuilder().add(3).build();
        Optional<List<Integer>> projectIndices = Optional.empty();
        Optional<List<Integer>> skillIndices = Optional.empty();
        ResumeEditCommand resumeEditCommand = new ResumeEditCommand(validIndex, internshipIndices, projectIndices,
                skillIndices);
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_REDIT_ITEM_INDEX, "internship", "3");
        assertThrows(CommandException.class, expectedMessage, () -> resumeEditCommand.execute(model));
    }

    @Test
    public void execute_invalidInternshipIndexWithDuplicates_throwsCommandException() {
        Index validIndex = INDEX_FIRST_ITEM;
        Optional<List<Integer>> internshipIndices = new ItemIndicesBuilder().add(3).add(3).add(3).build();
        Optional<List<Integer>> projectIndices = Optional.empty();
        Optional<List<Integer>> skillIndices = Optional.empty();
        ResumeEditCommand resumeEditCommand = new ResumeEditCommand(validIndex, internshipIndices, projectIndices,
                skillIndices);
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_REDIT_ITEM_INDEX, "internship", "3");
        assertThrows(CommandException.class, expectedMessage, () -> resumeEditCommand.execute(model));
    }

    @Test
    public void execute_invalidInternshipIndexMixedWithValid_throwsCommandException() {
        Index validIndex = INDEX_FIRST_ITEM;
        Optional<List<Integer>> internshipIndices = new ItemIndicesBuilder().add(4).add(1).add(3).build();
        Optional<List<Integer>> projectIndices = Optional.empty();
        Optional<List<Integer>> skillIndices = Optional.empty();
        ResumeEditCommand resumeEditCommand = new ResumeEditCommand(validIndex, internshipIndices, projectIndices,
                skillIndices);
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_REDIT_ITEM_INDEX, "internship", "4 3");
        assertThrows(CommandException.class, expectedMessage, () -> resumeEditCommand.execute(model));
    }

    @Test
    public void execute_invalidProjectIndex_throwsCommandException() {
        Index validIndex = INDEX_FIRST_ITEM;
        Optional<List<Integer>> internshipIndices = Optional.empty();
        Optional<List<Integer>> projectIndices = new ItemIndicesBuilder().add(3).build();
        Optional<List<Integer>> skillIndices = Optional.empty();
        ResumeEditCommand resumeEditCommand = new ResumeEditCommand(validIndex, internshipIndices, projectIndices,
                skillIndices);
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_REDIT_ITEM_INDEX, "project", "3");
        assertThrows(CommandException.class, expectedMessage, () -> resumeEditCommand.execute(model));
    }

    @Test
    public void execute_invalidProjectIndexMixedWithValid_throwsCommandException() {
        Index validIndex = INDEX_FIRST_ITEM;
        Optional<List<Integer>> internshipIndices = Optional.empty();
        Optional<List<Integer>> projectIndices = new ItemIndicesBuilder().add(4).add(1).add(3).build();
        Optional<List<Integer>> skillIndices = Optional.empty();
        ResumeEditCommand resumeEditCommand = new ResumeEditCommand(validIndex, internshipIndices, projectIndices,
                skillIndices);
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_REDIT_ITEM_INDEX, "project", "4 3");
        assertThrows(CommandException.class, expectedMessage, () -> resumeEditCommand.execute(model));
    }

    @Test
    public void execute_invalidSkillIndex_throwsCommandException() {
        Index validIndex = INDEX_FIRST_ITEM;
        Optional<List<Integer>> internshipIndices = Optional.empty();
        Optional<List<Integer>> projectIndices = Optional.empty();
        Optional<List<Integer>> skillIndices = new ItemIndicesBuilder().add(3).build();
        ResumeEditCommand resumeEditCommand = new ResumeEditCommand(validIndex, internshipIndices, projectIndices,
                skillIndices);
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_REDIT_ITEM_INDEX, "skill", "3");
        assertThrows(CommandException.class, expectedMessage, () -> resumeEditCommand.execute(model));
    }

    @Test
    public void execute_invalidSkillIndexMixedWithValid_throwsCommandException() {
        Index validIndex = INDEX_FIRST_ITEM;
        Optional<List<Integer>> internshipIndices = Optional.empty();
        Optional<List<Integer>> projectIndices = Optional.empty();
        Optional<List<Integer>> skillIndices = new ItemIndicesBuilder().add(4).add(1).add(3).build();
        ResumeEditCommand resumeEditCommand = new ResumeEditCommand(validIndex, internshipIndices, projectIndices,
                skillIndices);
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_REDIT_ITEM_INDEX, "skill", "4 3");
        assertThrows(CommandException.class, expectedMessage, () -> resumeEditCommand.execute(model));
    }

    @Test
    public void execute_allThreeItemValidIndex_success() throws CommandException {
        Index validIndex = INDEX_FIRST_ITEM;
        Optional<List<Integer>> internshipIndices = new ItemIndicesBuilder().add(2).build();
        Optional<List<Integer>> projectIndices = new ItemIndicesBuilder().add(2).build();
        Optional<List<Integer>> skillIndices = new ItemIndicesBuilder().add(1).build();
        ResumeEditCommand resumeEditCommand = new ResumeEditCommand(validIndex, internshipIndices, projectIndices,
                skillIndices);

        CommandResult commandResult = resumeEditCommand.execute(model);
        assertEquals(ResumeEditCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());

        Resume changedResume = model.getResumeByIndex(validIndex);
        Resume expectedResume = new ResumeBuilder(TypicalResume.ME_RESUME)
                .withInternship(TypicalInternship.NINJA_VAN)
                .withProject(TypicalProject.DUKE)
                .withSkill(TypicalSkill.REACT)
                .build();

        assertEquals(changedResume, expectedResume);
    }

    @Test
    public void execute_intAndProjValidIndex_success() throws CommandException {
        Index validIndex = INDEX_FIRST_ITEM;
        Optional<List<Integer>> internshipIndices = new ItemIndicesBuilder().add(2).build();
        Optional<List<Integer>> projectIndices = new ItemIndicesBuilder().add(2).build();
        Optional<List<Integer>> skillIndices = ItemIndicesBuilder.empty();
        ResumeEditCommand resumeEditCommand = new ResumeEditCommand(validIndex, internshipIndices, projectIndices,
                skillIndices);

        CommandResult commandResult = resumeEditCommand.execute(model);
        assertEquals(ResumeEditCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());

        Resume changedResume = model.getResumeByIndex(validIndex);
        Resume expectedResume = new ResumeBuilder(TypicalResume.ME_RESUME)
                .withInternship(TypicalInternship.NINJA_VAN)
                .withProject(TypicalProject.DUKE)
                .build();

        assertEquals(changedResume, expectedResume);
    }

    @Test
    public void execute_intAndSkiValidIndex_success() throws CommandException {
        Index validIndex = INDEX_FIRST_ITEM;
        Optional<List<Integer>> internshipIndices = new ItemIndicesBuilder().add(2).build();
        Optional<List<Integer>> projectIndices = ItemIndicesBuilder.empty();
        Optional<List<Integer>> skillIndices = new ItemIndicesBuilder().add(1).build();
        ResumeEditCommand resumeEditCommand = new ResumeEditCommand(validIndex, internshipIndices, projectIndices,
                skillIndices);

        CommandResult commandResult = resumeEditCommand.execute(model);
        assertEquals(ResumeEditCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());

        Resume changedResume = model.getResumeByIndex(validIndex);
        Resume expectedResume = new ResumeBuilder(TypicalResume.ME_RESUME)
                .withInternship(TypicalInternship.NINJA_VAN)
                .withSkill(TypicalSkill.REACT)
                .build();

        assertEquals(changedResume, expectedResume);
    }

    @Test
    public void execute_onlyProjValidIndex_success() throws CommandException {
        Index validIndex = INDEX_FIRST_ITEM;
        Optional<List<Integer>> internshipIndices = ItemIndicesBuilder.empty();
        Optional<List<Integer>> projectIndices = new ItemIndicesBuilder().add(2).build();
        Optional<List<Integer>> skillIndices = ItemIndicesBuilder.empty();
        ResumeEditCommand resumeEditCommand = new ResumeEditCommand(validIndex, internshipIndices, projectIndices,
                skillIndices);

        CommandResult commandResult = resumeEditCommand.execute(model);
        assertEquals(ResumeEditCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());

        Resume changedResume = model.getResumeByIndex(validIndex);
        Resume expectedResume = new ResumeBuilder(TypicalResume.ME_RESUME)
                .withProject(TypicalProject.DUKE)
                .build();

        assertEquals(changedResume, expectedResume);
    }
    @Test
    public void execute_onlySkiValidIndex_success() throws CommandException {
        Index validIndex = INDEX_FIRST_ITEM;
        Optional<List<Integer>> internshipIndices = ItemIndicesBuilder.empty();
        Optional<List<Integer>> projectIndices = ItemIndicesBuilder.empty();
        Optional<List<Integer>> skillIndices = new ItemIndicesBuilder().add(1).build();
        ResumeEditCommand resumeEditCommand = new ResumeEditCommand(validIndex, internshipIndices, projectIndices,
                skillIndices);

        CommandResult commandResult = resumeEditCommand.execute(model);
        assertEquals(ResumeEditCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());

        Resume changedResume = model.getResumeByIndex(validIndex);
        Resume expectedResume = new ResumeBuilder(TypicalResume.ME_RESUME)
                .withSkill(TypicalSkill.REACT)
                .build();

        assertEquals(changedResume, expectedResume);
    }

    @Test
    public void execute_removeAll_success() throws CommandException {
        Index validIndex = INDEX_THIRD_ITEM;
        Optional<List<Integer>> internshipIndices = new ItemIndicesBuilder().build();
        Optional<List<Integer>> projectIndices = new ItemIndicesBuilder().build();
        Optional<List<Integer>> skillIndices = new ItemIndicesBuilder().build();
        ResumeEditCommand resumeEditCommand = new ResumeEditCommand(validIndex, internshipIndices, projectIndices,
                skillIndices);

        CommandResult commandResult = resumeEditCommand.execute(model);
        assertEquals(ResumeEditCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());

        Resume changedResume = model.getResumeByIndex(validIndex);
        Resume expectedResume = new ResumeBuilder(TypicalResume.FILLED_RESUME)
                .build();

        assertEquals(changedResume, expectedResume);
    }

    @Test
    public void execute_removeSome_success() throws CommandException {
        Index validIndex = INDEX_THIRD_ITEM;
        Optional<List<Integer>> internshipIndices = new ItemIndicesBuilder().build();
        Optional<List<Integer>> projectIndices = ItemIndicesBuilder.empty();
        Optional<List<Integer>> skillIndices = new ItemIndicesBuilder().build();
        ResumeEditCommand resumeEditCommand = new ResumeEditCommand(validIndex, internshipIndices, projectIndices,
                skillIndices);

        CommandResult commandResult = resumeEditCommand.execute(model);
        assertEquals(ResumeEditCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());

        Resume changedResume = model.getResumeByIndex(validIndex);
        Resume expectedResume = new ResumeBuilder(TypicalResume.FILLED_RESUME)
                .withProject(TypicalProject.ORBITAL)
                .build();

        assertEquals(changedResume, expectedResume);
    }

    @Test
    public void execute_removeSomeWhileAdding_success() throws CommandException {
        Index validIndex = INDEX_THIRD_ITEM;
        Optional<List<Integer>> internshipIndices = new ItemIndicesBuilder().build();
        Optional<List<Integer>> projectIndices = new ItemIndicesBuilder().add(1).add(2).build();
        Optional<List<Integer>> skillIndices = new ItemIndicesBuilder().build();
        ResumeEditCommand resumeEditCommand = new ResumeEditCommand(validIndex, internshipIndices, projectIndices,
                skillIndices);

        CommandResult commandResult = resumeEditCommand.execute(model);
        assertEquals(ResumeEditCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());

        Resume changedResume = model.getResumeByIndex(validIndex);
        Resume expectedResume = new ResumeBuilder(TypicalResume.FILLED_RESUME)
                .withProject(TypicalProject.ORBITAL)
                .withProject(TypicalProject.DUKE)
                .build();

        assertEquals(changedResume, expectedResume);
    }

    @Test
    public void execute_nothingSupplied_success() throws CommandException {
        Index validIndex = INDEX_THIRD_ITEM;
        Optional<List<Integer>> internshipIndices = ItemIndicesBuilder.empty();
        Optional<List<Integer>> projectIndices = ItemIndicesBuilder.empty();
        Optional<List<Integer>> skillIndices = ItemIndicesBuilder.empty();
        ResumeEditCommand resumeEditCommand = new ResumeEditCommand(validIndex, internshipIndices, projectIndices,
                skillIndices);

        CommandResult commandResult = resumeEditCommand.execute(model);
        assertEquals(ResumeEditCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());

        Resume changedResume = model.getResumeByIndex(validIndex);
        Resume expectedResume = new ResumeBuilder(TypicalResume.FILLED_RESUME)
                .withInternship(TypicalInternship.GOOGLE)
                .withProject(TypicalProject.ORBITAL)
                .withSkill(TypicalSkill.GIT)
                .build();

        assertEquals(changedResume, expectedResume);
    }

    @Test
    public void execute_duplicateIndices_success() throws CommandException {
        Index validIndex = INDEX_FIRST_ITEM;
        Optional<List<Integer>> internshipIndices = ItemIndicesBuilder.empty();
        Optional<List<Integer>> projectIndices = ItemIndicesBuilder.empty();
        Optional<List<Integer>> skillIndices = new ItemIndicesBuilder().add(1).add(1).add(1).build();
        ResumeEditCommand resumeEditCommand = new ResumeEditCommand(validIndex, internshipIndices, projectIndices,
                skillIndices);

        CommandResult commandResult = resumeEditCommand.execute(model);
        assertEquals(ResumeEditCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());

        Resume changedResume = model.getResumeByIndex(validIndex);
        Resume expectedResume = new ResumeBuilder(TypicalResume.ME_RESUME)
                .withSkill(TypicalSkill.REACT)
                .build();

        assertEquals(changedResume, expectedResume);
    }

    @Test
    public void equals() {
        Optional<List<Integer>> internshipIndicesA = new ItemIndicesBuilder().add(1).build();
        Optional<List<Integer>> projectIndicesA = new ItemIndicesBuilder().add(1).build();
        Optional<List<Integer>> skillIndicesA = new ItemIndicesBuilder().add(1).build();

        Optional<List<Integer>> internshipIndicesB = new ItemIndicesBuilder().add(2).build();
        Optional<List<Integer>> projectIndicesB = new ItemIndicesBuilder().add(1).build();
        Optional<List<Integer>> skillIndicesB = new ItemIndicesBuilder().add(1).build();

        Index index = INDEX_FIRST_ITEM;

        ResumeEditCommand reditACommand = new ResumeEditCommand(index, internshipIndicesA, projectIndicesA,
                skillIndicesA);
        ResumeEditCommand reditBCommand = new ResumeEditCommand(index, internshipIndicesB, projectIndicesB,
                skillIndicesB);

        // same object -> returns true
        assertTrue(reditACommand.equals(reditACommand));

        // same values -> returns true
        ResumeEditCommand reditACommandCopy = new ResumeEditCommand(index, internshipIndicesA, projectIndicesA,
                skillIndicesA);
        assertTrue(reditACommand.equals(reditACommandCopy));

        // different types -> returns false
        assertFalse(reditACommand.equals(1));

        // null -> returns false
        assertFalse(reditACommand.equals(null));

        // different indices -> returns false
        assertFalse(reditACommand.equals(reditBCommand));
    }
}
