package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ITEM;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.delete.DeleteCommand;
import seedu.address.logic.commands.delete.DeleteInternshipCommand;
import seedu.address.logic.commands.delete.DeleteProjectCommand;
import seedu.address.logic.commands.delete.DeleteResumeCommand;
import seedu.address.logic.commands.delete.DeleteSkillCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.results.DeleteCommandResult;
import seedu.address.model.Model;

import seedu.address.model.ModelManager;
import seedu.address.model.ResumeBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.item.Internship;
import seedu.address.model.item.Project;
import seedu.address.model.item.Resume;
import seedu.address.model.item.Skill;
import seedu.address.model.util.ItemUtil;
import seedu.address.testutil.ResumeBookBuilder;
import seedu.address.testutil.TypicalResumeBook;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandIntegrationTest {
    private Model model;
    private Model expectedModel;
    private ResumeBook resumeBook = new ResumeBookBuilder(TypicalResumeBook.TYPICAL).build();
    private ResumeBook resumeBookCopy = new ResumeBookBuilder(TypicalResumeBook.TYPICAL).build();

    @BeforeEach
    public void setUp() {
        model = new ModelManager(resumeBook, new UserPrefs());
        expectedModel = new ModelManager(resumeBookCopy, new UserPrefs());
    }

    @Test
    public void execute_deleteFirstInternship_success() {
        Internship internshipToDelete = model.getInternshipByIndex(INDEX_FIRST_ITEM);
        DeleteInternshipCommand deleteCommand = new DeleteInternshipCommand(INDEX_FIRST_ITEM);

        String expectedFeedback = String.format(DeleteCommand.MESSAGE_DELETE_ITEM_SUCCESS, "Internship");
        String expectedData = internshipToDelete.toString();

        expectedModel.deleteInternship(internshipToDelete);
        expectedModel.setInternshipToDisplay();

        assertCommandSuccess(deleteCommand, model, new DeleteCommandResult(expectedData, expectedFeedback,
                ItemUtil.INTERNSHIP_ALIAS), expectedModel);
    }

    @Test
    public void execute_deleteSecondInternship_success() {
        Internship internshipToDelete = model.getInternshipByIndex(INDEX_SECOND_ITEM);
        DeleteInternshipCommand deleteCommand = new DeleteInternshipCommand(INDEX_SECOND_ITEM);

        String expectedFeedback = String.format(DeleteCommand.MESSAGE_DELETE_ITEM_SUCCESS, "Internship");
        String expectedData = internshipToDelete.toString();

        expectedModel.deleteInternship(internshipToDelete);
        expectedModel.setInternshipToDisplay();

        assertCommandSuccess(deleteCommand, model, new DeleteCommandResult(expectedData, expectedFeedback,
                ItemUtil.INTERNSHIP_ALIAS), expectedModel);
    }

    @Test
    public void execute_invalidInternshipIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getInternshipSize() + 1);
        DeleteInternshipCommand deleteCommand = new DeleteInternshipCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, new CommandException(Messages.MESSAGE_INVALID_INDEX));
    }

    @Test
    public void execute_deleteFirstProject_success() {
        Project projectToDelete = model.getProjectByIndex(INDEX_FIRST_ITEM);
        DeleteProjectCommand deleteCommand = new DeleteProjectCommand(INDEX_FIRST_ITEM);

        String expectedFeedback = String.format(DeleteCommand.MESSAGE_DELETE_ITEM_SUCCESS, "Project");
        String expectedData = projectToDelete.toString();

        expectedModel.deleteProject(projectToDelete);
        expectedModel.setProjectToDisplay();

        assertCommandSuccess(deleteCommand, model, new DeleteCommandResult(expectedData, expectedFeedback,
                ItemUtil.PROJECT_ALIAS), expectedModel);
    }

    @Test
    public void execute_deleteSecondProject_success() {
        Project projectToDelete = model.getProjectByIndex(INDEX_SECOND_ITEM);
        DeleteProjectCommand deleteCommand = new DeleteProjectCommand(INDEX_SECOND_ITEM);

        String expectedFeedback = String.format(DeleteCommand.MESSAGE_DELETE_ITEM_SUCCESS, "Project");
        String expectedData = projectToDelete.toString();

        expectedModel.deleteProject(projectToDelete);
        expectedModel.setProjectToDisplay();

        assertCommandSuccess(deleteCommand, model, new DeleteCommandResult(expectedData, expectedFeedback,
                ItemUtil.PROJECT_ALIAS), expectedModel);
    }

    @Test
    public void execute_invalidProjectIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getProjectSize() + 1);
        DeleteProjectCommand deleteCommand = new DeleteProjectCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, new CommandException(Messages.MESSAGE_INVALID_INDEX));
    }

    @Test
    public void execute_deleteFirstSkill_success() {
        Skill skillToDelete = model.getSkillByIndex(INDEX_FIRST_ITEM);
        DeleteSkillCommand deleteCommand = new DeleteSkillCommand(INDEX_FIRST_ITEM);

        String expectedFeedback = String.format(DeleteCommand.MESSAGE_DELETE_ITEM_SUCCESS, "Skill");
        String expectedData = skillToDelete.toString();

        expectedModel.deleteSkill(skillToDelete);
        expectedModel.setSkillToDisplay();

        assertCommandSuccess(deleteCommand, model, new DeleteCommandResult(expectedData, expectedFeedback,
                ItemUtil.SKILL_ALIAS), expectedModel);
    }

    @Test
    public void execute_deleteSecondSkill_success() {
        Skill skillToDelete = model.getSkillByIndex(INDEX_SECOND_ITEM);
        DeleteSkillCommand deleteCommand = new DeleteSkillCommand(INDEX_SECOND_ITEM);

        String expectedFeedback = String.format(DeleteCommand.MESSAGE_DELETE_ITEM_SUCCESS, "Skill");
        String expectedData = skillToDelete.toString();

        expectedModel.deleteSkill(skillToDelete);
        expectedModel.setSkillToDisplay();

        assertCommandSuccess(deleteCommand, model, new DeleteCommandResult(expectedData, expectedFeedback,
                ItemUtil.SKILL_ALIAS), expectedModel);
    }

    @Test
    public void execute_invalidSkillIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getSkillSize() + 1);
        DeleteSkillCommand deleteCommand = new DeleteSkillCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, new CommandException(Messages.MESSAGE_INVALID_INDEX));
    }

    @Test
    public void execute_deleteFirstResume_success() {
        Resume resumeToDelete = model.getResumeByIndex(INDEX_FIRST_ITEM);
        DeleteResumeCommand deleteCommand = new DeleteResumeCommand(INDEX_FIRST_ITEM);

        String expectedFeedback = String.format(DeleteCommand.MESSAGE_DELETE_ITEM_SUCCESS, "Resume");
        String expectedData = resumeToDelete.toString();

        expectedModel.deleteResume(resumeToDelete);
        expectedModel.setResumeToDisplay();

        assertCommandSuccess(deleteCommand, model, new DeleteCommandResult(expectedData, expectedFeedback,
                ItemUtil.RESUME_ALIAS), expectedModel);
    }

    @Test
    public void execute_deleteSecondResume_success() {
        Resume resumeToDelete = model.getResumeByIndex(INDEX_SECOND_ITEM);
        DeleteResumeCommand deleteCommand = new DeleteResumeCommand(INDEX_SECOND_ITEM);

        String expectedFeedback = String.format(DeleteCommand.MESSAGE_DELETE_ITEM_SUCCESS, "Resume");
        String expectedData = resumeToDelete.toString();

        expectedModel.deleteResume(resumeToDelete);
        expectedModel.setResumeToDisplay();

        assertCommandSuccess(deleteCommand, model, new DeleteCommandResult(expectedData, expectedFeedback,
                ItemUtil.RESUME_ALIAS), expectedModel);
    }

    @Test
    public void execute_invalidResumeIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getResumeSize() + 1);
        DeleteResumeCommand deleteCommand = new DeleteResumeCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, new CommandException(Messages.MESSAGE_INVALID_INDEX));
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstInternshipCommand = new DeleteInternshipCommand(INDEX_FIRST_ITEM);
        DeleteCommand deleteFirstProjectCommand = new DeleteProjectCommand(INDEX_FIRST_ITEM);
        DeleteCommand deleteFirstResumeCommand = new DeleteResumeCommand(INDEX_FIRST_ITEM);
        DeleteCommand deleteFirstSkillCommand = new DeleteSkillCommand(INDEX_FIRST_ITEM);
        assertNotEquals(deleteFirstInternshipCommand, deleteFirstProjectCommand);
        assertNotEquals(deleteFirstInternshipCommand, deleteFirstResumeCommand);
        assertNotEquals(deleteFirstInternshipCommand, deleteFirstSkillCommand);
        assertNotEquals(deleteFirstProjectCommand, deleteFirstResumeCommand);
        assertNotEquals(deleteFirstProjectCommand, deleteFirstSkillCommand);
        assertNotEquals(deleteFirstResumeCommand, deleteFirstSkillCommand);
    }
}
