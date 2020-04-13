package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.logic.commands.results.UndoCommandResult;
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


public class UndoCommandTest {
    private Model expectedModel;
    private Model model;

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
        expectedModel.setUser(newUser);
        expectedModel.commitResumeBook();
        expectedModel.undoResumeBook();

        // Empty string because set user does not cause change to any item list
        assertEquals("", expectedModel.getDisplayType());
        CommandResult commandResult = new UndoCommandResult("Undo successfully!", model.getDisplayType());
        assertCommandSuccess(new UndoCommand(), model, commandResult, expectedModel);
    }

    @Test
    public void execute_deleteResume_success() {
        Resume meResume = new ResumeBuilder(TypicalResume.ME_RESUME).build();
        model.deleteResume(meResume);
        model.setResumeToDisplay();
        model.commitResumeBook();
        expectedModel.deleteResume(meResume);
        expectedModel.setResumeToDisplay();
        expectedModel.commitResumeBook();
        expectedModel.undoResumeBook();

        // We need to ensure that the display type of the resume book is set correctly
        assertEquals(ItemUtil.RESUME_ALIAS, expectedModel.getDisplayType());
        CommandResult commandResult =
                new UndoCommandResult("Undo successfully!", expectedModel.getDisplayType());
        assertCommandSuccess(new UndoCommand(), model, commandResult, expectedModel);
    }

    @Test
    public void execute_addInternship_success() {
        Internship paypal = new InternshipBuilder(TypicalInternship.PAYPAL).build();
        model.addInternship(paypal);
        model.setInternshipToDisplay();
        model.commitResumeBook();
        expectedModel.addInternship(paypal);
        expectedModel.setInternshipToDisplay();
        expectedModel.commitResumeBook();
        expectedModel.undoResumeBook();

        assertEquals(ItemUtil.INTERNSHIP_ALIAS, expectedModel.getDisplayType());
        CommandResult commandResult =
                new UndoCommandResult("Undo successfully!", expectedModel.getDisplayType());
        assertCommandSuccess(new UndoCommand(), model, commandResult, expectedModel);
    }

    @Test
    public void execute_sortProject_success() {
        model.sortProjects(Comparator.comparing(Project::getTime));
        model.setProjectToDisplay();
        model.commitResumeBook();
        expectedModel.sortProjects(Comparator.comparing(Project::getTime));
        expectedModel.setProjectToDisplay();
        expectedModel.commitResumeBook();
        expectedModel.undoResumeBook();


        assertEquals(ItemUtil.PROJECT_ALIAS, expectedModel.getDisplayType());
        CommandResult commandResult =
                new UndoCommandResult("Undo successfully!", expectedModel.getDisplayType());
        assertCommandSuccess(new UndoCommand(), model, commandResult, expectedModel);
    }

    @Test
    public void execute_multipleChanges_success() {
        // Changes to the model copied from the tests above
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

        // Undo the sort project command
        expectedModel.undoResumeBook();
        assertEquals(ItemUtil.PROJECT_ALIAS, expectedModel.getDisplayType());
        CommandResult commandResult1 =
                new UndoCommandResult("Undo successfully!", expectedModel.getDisplayType());
        assertCommandSuccess(new UndoCommand(), model, commandResult1, expectedModel);

        // Undo the add internship command
        expectedModel.undoResumeBook();
        assertEquals(ItemUtil.INTERNSHIP_ALIAS, expectedModel.getDisplayType());
        CommandResult commandResult2 =
                new UndoCommandResult("Undo successfully!", expectedModel.getDisplayType());
        assertCommandSuccess(new UndoCommand(), model, commandResult2, expectedModel);

        // Undo the delete resume command
        expectedModel.undoResumeBook();
        assertEquals(ItemUtil.RESUME_ALIAS, expectedModel.getDisplayType());
        CommandResult commandResult3 =
                new UndoCommandResult("Undo successfully!", expectedModel.getDisplayType());
        assertCommandSuccess(new UndoCommand(), model, commandResult3, expectedModel);
    }

    @Test
    public void execute_moreUndosThanChanges_throwsCommandException() {
        // Same thing but this round we try to undo a fourth time
        Resume meResume = new ResumeBuilder(TypicalResume.ME_RESUME).build();
        model.deleteResume(meResume);
        model.setResumeToDisplay();
        model.commitResumeBook();
        expectedModel.deleteResume(meResume);
        expectedModel.setResumeToDisplay();
        expectedModel.commitResumeBook();

        model.sortProjects(Comparator.comparing(Project::getTime));
        model.setProjectToDisplay();
        model.commitResumeBook();
        expectedModel.sortProjects(Comparator.comparing(Project::getTime));
        expectedModel.setProjectToDisplay();
        expectedModel.commitResumeBook();

        Internship paypal = new InternshipBuilder(TypicalInternship.PAYPAL).build();
        model.addInternship(paypal);
        model.setInternshipToDisplay();
        model.commitResumeBook();
        expectedModel.addInternship(paypal);
        expectedModel.setInternshipToDisplay();
        expectedModel.commitResumeBook();

        expectedModel.undoResumeBook();
        expectedModel.undoResumeBook();
        expectedModel.undoResumeBook();

        model.undoResumeBook();
        model.undoResumeBook();
        model.undoResumeBook();

        assertThrows(CommandException.class, UndoCommand.MESSAGE_FAILURE, () -> new UndoCommand().execute(model));
    }

}
