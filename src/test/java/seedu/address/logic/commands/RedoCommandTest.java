package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.logic.commands.results.RedoCommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.item.Internship;
import seedu.address.model.item.Person;
import seedu.address.model.item.Project;
import seedu.address.model.item.Resume;
import seedu.address.model.util.ItemUtil;
import seedu.address.testutil.InternshipBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.ResumeBuilder;
import seedu.address.testutil.TypicalInternship;
import seedu.address.testutil.TypicalResume;
import seedu.address.testutil.TypicalResumeBook;


public class RedoCommandTest {
    private Model expectedModel = new ModelManager(TypicalResumeBook.TYPICAL_WITH_FILLED_RESUME, new UserPrefs());
    private Model model = new ModelManager(TypicalResumeBook.TYPICAL_WITH_FILLED_RESUME, new UserPrefs());

    @BeforeEach
    public void setUp() {
        expectedModel = new ModelManager(TypicalResumeBook.TYPICAL_WITH_FILLED_RESUME, new UserPrefs());
        model = new ModelManager(TypicalResumeBook.TYPICAL_WITH_FILLED_RESUME, new UserPrefs());
    }

    @Test
    public void execute_setUser_success() {
        Person newUser = new PersonBuilder(model.getUser()).withName("Michael Ng").build();
        model.setUser(newUser);
        model.commitResumeBook();
        model.undoResumeBook();

        expectedModel.setUser(newUser);
        expectedModel.commitResumeBook();
        expectedModel.undoResumeBook();
        expectedModel.redoResumeBook();

        assertEquals("", expectedModel.getDisplayType());
        CommandResult commandResult = new RedoCommandResult(RedoCommand.MESSAGE_SUCCESS, model.getDisplayType());
        assertCommandSuccess(new RedoCommand(), model, commandResult, expectedModel);
    }


    @Test
    public void execute_deleteResume_success() {
        Resume meResume = new ResumeBuilder(TypicalResume.ME_RESUME).build();
        model.deleteResume(meResume);
        model.setResumeToDisplay();
        model.commitResumeBook();
        model.undoResumeBook();

        expectedModel.deleteResume(meResume);
        expectedModel.setResumeToDisplay();
        expectedModel.commitResumeBook();
        expectedModel.undoResumeBook();
        expectedModel.redoResumeBook();

        assertEquals(ItemUtil.RESUME_ALIAS, expectedModel.getDisplayType());
        CommandResult commandResult =
                new RedoCommandResult(RedoCommand.MESSAGE_SUCCESS, expectedModel.getDisplayType());
        assertCommandSuccess(new RedoCommand(), model, commandResult, expectedModel);
    }

    @Test
    public void execute_addInternship_success() {
        Internship paypal = new InternshipBuilder(TypicalInternship.PAYPAL).build();
        model.addInternship(paypal);
        model.setInternshipToDisplay();
        model.commitResumeBook();
        model.undoResumeBook();

        expectedModel.addInternship(paypal);
        expectedModel.setInternshipToDisplay();
        expectedModel.commitResumeBook();
        expectedModel.undoResumeBook();
        expectedModel.redoResumeBook();

        assertEquals(ItemUtil.INTERNSHIP_ALIAS, expectedModel.getDisplayType());
        CommandResult commandResult =
                new RedoCommandResult(RedoCommand.MESSAGE_SUCCESS, expectedModel.getDisplayType());
        assertCommandSuccess(new RedoCommand(), model, commandResult, expectedModel);
    }

    @Test
    public void execute_sortProject_success() {
        model.sortProjects(Comparator.comparing(Project::getTime));
        model.setProjectToDisplay();
        model.commitResumeBook();
        model.undoResumeBook();

        expectedModel.sortProjects(Comparator.comparing(Project::getTime));
        expectedModel.setProjectToDisplay();
        expectedModel.commitResumeBook();
        expectedModel.undoResumeBook();
        expectedModel.redoResumeBook();

        assertEquals(ItemUtil.PROJECT_ALIAS, expectedModel.getDisplayType());
        CommandResult commandResult =
                new RedoCommandResult(RedoCommand.MESSAGE_SUCCESS, expectedModel.getDisplayType());
        assertCommandSuccess(new RedoCommand(), model, commandResult, expectedModel);
    }

    @Test
    public void execute_multipleChanges_success() {
        // Changes to the model are copied from the tests above
        Resume meResume = new ResumeBuilder(TypicalResume.ME_RESUME).build();
        model.deleteResume(meResume);
        model.setResumeToDisplay();
        model.commitResumeBook();
        expectedModel.deleteResume(meResume);
        expectedModel.setResumeToDisplay();
        expectedModel.commitResumeBook();

        Internship paypal = new InternshipBuilder(TypicalInternship.PAYPAL).build();
        model.addInternship(paypal);
        model.setInternshipToDisplay();
        model.commitResumeBook();
        expectedModel.addInternship(paypal);
        expectedModel.setInternshipToDisplay();
        expectedModel.commitResumeBook();

        model.sortProjects(Comparator.comparing(Project::getTime));
        model.setProjectToDisplay();
        model.commitResumeBook();
        expectedModel.sortProjects(Comparator.comparing(Project::getTime));
        expectedModel.setProjectToDisplay();
        expectedModel.commitResumeBook();

        model.undoResumeBook();
        model.undoResumeBook();
        model.undoResumeBook();

        expectedModel.undoResumeBook();
        expectedModel.undoResumeBook();
        expectedModel.undoResumeBook();

        // Redo -> do delete resume
        expectedModel.redoResumeBook();
        assertEquals(ItemUtil.RESUME_ALIAS, expectedModel.getDisplayType());
        CommandResult commandResult1 =
                new RedoCommandResult(RedoCommand.MESSAGE_SUCCESS, expectedModel.getDisplayType());
        assertCommandSuccess(new RedoCommand(), model, commandResult1, expectedModel);

        // Redo -> do add internship
        expectedModel.redoResumeBook();
        assertEquals(ItemUtil.INTERNSHIP_ALIAS, expectedModel.getDisplayType());
        CommandResult commandResult2 =
                new RedoCommandResult(RedoCommand.MESSAGE_SUCCESS, expectedModel.getDisplayType());
        assertCommandSuccess(new RedoCommand(), model, commandResult2, expectedModel);

        // Redo -> do sort project
        expectedModel.redoResumeBook();
        assertEquals(ItemUtil.PROJECT_ALIAS, expectedModel.getDisplayType());
        CommandResult commandResult3 =
                new RedoCommandResult(RedoCommand.MESSAGE_SUCCESS, expectedModel.getDisplayType());
        assertCommandSuccess(new RedoCommand(), model, commandResult3, expectedModel);

    }

    @Test
    public void execute_moreRedosThanUndos_throwsCommandException() {
        Resume meResume = new ResumeBuilder(TypicalResume.ME_RESUME).build();
        model.deleteResume(meResume);
        model.setResumeToDisplay();
        model.commitResumeBook();
        expectedModel.deleteResume(meResume);
        expectedModel.setResumeToDisplay();
        expectedModel.commitResumeBook();

        Internship paypal = new InternshipBuilder(TypicalInternship.PAYPAL).build();
        model.addInternship(paypal);
        model.setInternshipToDisplay();
        model.commitResumeBook();
        expectedModel.addInternship(paypal);
        expectedModel.setInternshipToDisplay();
        expectedModel.commitResumeBook();

        model.sortProjects(Comparator.comparing(Project::getTime));
        model.setProjectToDisplay();
        model.commitResumeBook();
        expectedModel.sortProjects(Comparator.comparing(Project::getTime));
        expectedModel.setProjectToDisplay();
        expectedModel.commitResumeBook();

        model.undoResumeBook();
        model.undoResumeBook();
        model.undoResumeBook();

        expectedModel.undoResumeBook();
        expectedModel.undoResumeBook();
        expectedModel.undoResumeBook();

        expectedModel.redoResumeBook();
        expectedModel.redoResumeBook();
        expectedModel.redoResumeBook();

        model.redoResumeBook();
        model.redoResumeBook();
        model.redoResumeBook();

        assertThrows(CommandException.class, RedoCommand.MESSAGE_FAILURE, () -> new RedoCommand().execute(model));
    }

    @Test
    public void execute_redoAfterCommit_throwsCommandException() {
        // Here the model undoes twice before commiting.
        Resume meResume = new ResumeBuilder(TypicalResume.ME_RESUME).build();
        model.deleteResume(meResume);
        model.setResumeToDisplay();
        model.commitResumeBook();

        Internship paypal = new InternshipBuilder(TypicalInternship.PAYPAL).build();
        model.addInternship(paypal);
        model.setInternshipToDisplay();
        model.commitResumeBook();

        model.undoResumeBook();
        model.undoResumeBook();

        // A commit is done thereby erasing all future states.
        model.sortProjects(Comparator.comparing(Project::getTime));
        model.setProjectToDisplay();
        model.commitResumeBook();

        assertThrows(CommandException.class, RedoCommand.MESSAGE_FAILURE, () -> new RedoCommand().execute(model));
    }

}
