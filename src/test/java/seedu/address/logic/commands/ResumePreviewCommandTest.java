package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ITEM;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.results.ResumePreviewCommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.item.Internship;
import seedu.address.model.item.Person;
import seedu.address.model.item.Project;
import seedu.address.model.item.Resume;
import seedu.address.model.item.Skill;
import seedu.address.testutil.TypicalInternship;
import seedu.address.testutil.TypicalProject;
import seedu.address.testutil.TypicalResumeBook;
import seedu.address.testutil.TypicalSkill;

public class ResumePreviewCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalResumeBook.TYPICAL_WITH_FILLED_RESUME, new UserPrefs());
    }

    @Test
    public void execute_invalidResumeIndex_throwsCommandException() {
        Index invalidIndex = INDEX_FOURTH_ITEM;
        ResumePreviewCommand resumePreviewCommand = new ResumePreviewCommand(invalidIndex);
        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_INDEX, () -> resumePreviewCommand.execute(model));
    }

    @Test
    public void execute_emptyResume_success() {
        Model expectedModel = new ModelManager(TypicalResumeBook.TYPICAL_WITH_FILLED_RESUME, new UserPrefs());
        Index validIndex = INDEX_FIRST_ITEM;
        ResumePreviewCommand resumePreviewCommand = new ResumePreviewCommand(validIndex);
        String data = createData(model.getUser(), model.getResumeByIndex(INDEX_FIRST_ITEM), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());
        assertCommandSuccess(resumePreviewCommand,
                model,
                new ResumePreviewCommandResult(data, String.format(ResumePreviewCommand.MESSAGE_SUCCESS, 1),
                        model.getDisplayType()),
                expectedModel);
    }

    @Test
    public void execute_filledResume_success() {
        Model expectedModel = new ModelManager(TypicalResumeBook.TYPICAL_WITH_FILLED_RESUME, new UserPrefs());
        Index validIndex = INDEX_THIRD_ITEM;
        ResumePreviewCommand resumePreviewCommand = new ResumePreviewCommand(validIndex);
        ArrayList<Internship> internships = new ArrayList<>();
        internships.add(TypicalInternship.GOOGLE);
        ArrayList<Project> projects = new ArrayList<>();
        projects.add(TypicalProject.ORBITAL);
        ArrayList<Skill> skills = new ArrayList<>();
        skills.add(TypicalSkill.GIT);
        String data = createData(model.getUser(), model.getResumeByIndex(INDEX_THIRD_ITEM), internships,
                projects, skills);
        assertCommandSuccess(resumePreviewCommand,
                model,
                new ResumePreviewCommandResult(data, String.format(ResumePreviewCommand.MESSAGE_SUCCESS, 3),
                        model.getDisplayType()),
                expectedModel);
    }

    /**
     * Function that mimics the createData functionality in ResumePreviewCommand.
     * @param person details of {@code person} to preview
     * @param resume details of {@code resume} to preview
     * @param internships list of {@code internships} to preview
     * @param projects list of {@code projects} to preview
     * @param skills list of {@code skills} to preview
     * @return data String to be shown in preview box.
     */
    private String createData(
            Person person, Resume resume, List<Internship> internships, List<Project> projects, List<Skill> skills) {

        StringBuilder data = new StringBuilder(resume.getName() + "\n");
        data.append("=========================\n")
                .append("PERSONAL DETAILS\n")
                .append("=========================\n\n")
                .append(person.toPreview())
                .append("\n\n");

        data.append("=========================\n")
                .append("INTERNSHIPS\n")
                .append("=========================\n\n");
        internships.forEach(internship -> data.append(internship.toPreview()).append("\n"));

        data.append("=========================\n")
                .append("PROJECTS\n")
                .append("=========================\n\n");
        projects.forEach(project -> data.append(project.toPreview()).append("\n"));

        data.append("=========================\n")
                .append("SKILLS\n")
                .append("=========================\n\n");
        skills.forEach(skill -> data.append(skill.toPreview()).append("\n"));

        return data.toString();
    }
}
