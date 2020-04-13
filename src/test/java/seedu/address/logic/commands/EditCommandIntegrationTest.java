package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FROM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERNSHIP_NAME_GOOGLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERNSHIP_NAME_PAYPAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERNSHIP_ROLE_BACKEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_NAME_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_NAME_DUKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_NAME_ORBITAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RESUME_NAME_SE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_NAME_GIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_NAME_REACT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BACKEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRONTEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TECH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_URGENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEBSITE_DUKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEBSITE_ORBITAL;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ITEM;
import static seedu.address.testutil.TypicalInternship.GOOGLE;
import static seedu.address.testutil.TypicalInternship.NINJA_VAN;
import static seedu.address.testutil.TypicalInternship.PAYPAL;
import static seedu.address.testutil.TypicalNote.FINISH_CS_2103;
import static seedu.address.testutil.TypicalNote.FINISH_HOMEWORK;
import static seedu.address.testutil.TypicalNote.FINISH_RESUME_2;
import static seedu.address.testutil.TypicalProject.DUKE;
import static seedu.address.testutil.TypicalProject.ORBITAL;
import static seedu.address.testutil.TypicalProject.RESUME;
import static seedu.address.testutil.TypicalResume.CE_RESUME;
import static seedu.address.testutil.TypicalResume.ME_RESUME;
import static seedu.address.testutil.TypicalResume.SE_RESUME;
import static seedu.address.testutil.TypicalSkill.CODE;
import static seedu.address.testutil.TypicalSkill.GIT;
import static seedu.address.testutil.TypicalSkill.REACT;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.edit.EditCommand;
import seedu.address.logic.commands.edit.EditInternshipCommand;
import seedu.address.logic.commands.edit.EditInternshipDescriptor;
import seedu.address.logic.commands.edit.EditNoteCommand;
import seedu.address.logic.commands.edit.EditNoteDescriptor;
import seedu.address.logic.commands.edit.EditProjectCommand;
import seedu.address.logic.commands.edit.EditProjectDescriptor;
import seedu.address.logic.commands.edit.EditResumeCommand;
import seedu.address.logic.commands.edit.EditResumeDescriptor;
import seedu.address.logic.commands.edit.EditSkillCommand;
import seedu.address.logic.commands.edit.EditSkillDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.results.EditCommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.item.Internship;
import seedu.address.model.item.Note;
import seedu.address.model.item.Project;
import seedu.address.model.item.Resume;
import seedu.address.model.item.Skill;
import seedu.address.model.item.field.Level;
import seedu.address.model.util.ItemUtil;
import seedu.address.testutil.EditInternshipDescriptorBuilder;
import seedu.address.testutil.EditNoteDescriptorBuilder;
import seedu.address.testutil.EditProjectDescriptorBuilder;
import seedu.address.testutil.EditResumeDescriptorBuilder;
import seedu.address.testutil.EditSkillDescriptorBuilder;
import seedu.address.testutil.InternshipBuilder;
import seedu.address.testutil.NoteBuilder;
import seedu.address.testutil.ProjectBuilder;
import seedu.address.testutil.ResumeBookBuilder;
import seedu.address.testutil.ResumeBuilder;
import seedu.address.testutil.SkillBuilder;
import seedu.address.testutil.TypicalInternship;
import seedu.address.testutil.TypicalNote;
import seedu.address.testutil.TypicalResume;
import seedu.address.testutil.TypicalResumeBook;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandIntegrationTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new ResumeBookBuilder(TypicalResumeBook.TYPICAL_WITHOUT_GOOGLE).build(),
                new UserPrefs());
        expectedModel = new ModelManager(new ResumeBookBuilder(TypicalResumeBook.TYPICAL_WITHOUT_GOOGLE).build(),
                new UserPrefs());
    }

    @Test
    public void execute_editInternship_success() {
        Internship validInternship = GOOGLE;
        EditInternshipDescriptor editInternshipDescriptor = new EditInternshipDescriptorBuilder(GOOGLE).build();

        expectedModel.setInternship(TypicalInternship.NINJA_VAN, validInternship);
        expectedModel.setInternshipToDisplay();

        assertCommandSuccess(new EditInternshipCommand(INDEX_FIRST_ITEM, editInternshipDescriptor),
                model,
                new EditCommandResult(validInternship.toString(),
                        String.format(EditInternshipCommand.MESSAGE_EDIT_INTERNSHIP_SUCCESS,
                                validInternship.getName().fullName), ItemUtil.INTERNSHIP_ALIAS),
                expectedModel);
    }

    @Test
    public void execute_editSingleFieldInternshipName_success() {
        Internship validInternship = new InternshipBuilder(NINJA_VAN).withName(VALID_INTERNSHIP_NAME_GOOGLE).build();
        EditInternshipDescriptor editInternshipDescriptorSingleName = new EditInternshipDescriptorBuilder()
                .withName(VALID_INTERNSHIP_NAME_GOOGLE)
                .build();

        expectedModel.setInternship(TypicalInternship.NINJA_VAN, validInternship);
        expectedModel.setInternshipToDisplay();

        assertCommandSuccess(new EditInternshipCommand(INDEX_FIRST_ITEM, editInternshipDescriptorSingleName),
                model,
                new EditCommandResult(validInternship.toString(),
                        String.format(EditInternshipCommand.MESSAGE_EDIT_INTERNSHIP_SUCCESS,
                                validInternship.getName().fullName), ItemUtil.INTERNSHIP_ALIAS),
                expectedModel);
    }

    @Test
    public void execute_editInternshipWithoutChanges_throwsCommandException() {
        // Same role
        EditInternshipDescriptor editInternshipDescriptor = new EditInternshipDescriptorBuilder()
                .withRole(VALID_INTERNSHIP_ROLE_BACKEND)
                .build();
        assertCommandFailure(new EditInternshipCommand(INDEX_SECOND_ITEM, editInternshipDescriptor),
                model,
                new CommandException(EditInternshipCommand.MESSAGE_SAME_INTERNSHIP));

        // Same name
        editInternshipDescriptor = new EditInternshipDescriptorBuilder()
                .withName(VALID_INTERNSHIP_NAME_PAYPAL)
                .build();
        assertCommandFailure(new EditInternshipCommand(INDEX_SECOND_ITEM, editInternshipDescriptor),
                model,
                new CommandException(EditInternshipCommand.MESSAGE_SAME_INTERNSHIP));

        // Same from, to, tags
        editInternshipDescriptor = new EditInternshipDescriptorBuilder()
                .withFrom(VALID_FROM)
                .withTo(VALID_TO)
                .withTags(VALID_TAG_BACKEND, VALID_TAG_SE, VALID_TAG_TECH)
                .build();
        assertCommandFailure(new EditInternshipCommand(INDEX_SECOND_ITEM, editInternshipDescriptor),
                model,
                new CommandException(EditInternshipCommand.MESSAGE_SAME_INTERNSHIP));
    }

    @Test
    public void execute_outOfBoundsInternship_throwsCommandException() {
        EditInternshipDescriptor editInternshipDescriptor = new EditInternshipDescriptor();
        assertCommandFailure(new EditInternshipCommand(INDEX_FOURTH_ITEM, editInternshipDescriptor),
                model,
                new CommandException(Messages.MESSAGE_INVALID_INDEX));
    }

    @Test
    public void execute_fullDuplicateInternship_throwsCommandException() {
        EditInternshipDescriptor editInternshipDescriptor = new EditInternshipDescriptorBuilder(PAYPAL).build();

        assertCommandFailure(new EditInternshipCommand(INDEX_FIRST_ITEM, editInternshipDescriptor),
                model,
                new CommandException(EditCommand.MESSAGE_DUPLICATE_ITEM));
    }

    @Test
    public void execute_isSameDuplicateInternship_throwsCommandException() {
        EditInternshipDescriptor editInternshipDescriptor = new EditInternshipDescriptorBuilder()
                .withName(VALID_INTERNSHIP_NAME_PAYPAL).withRole(VALID_INTERNSHIP_ROLE_BACKEND)
                .withFrom(VALID_FROM).withTo(VALID_TO).build();

        assertCommandFailure(new EditInternshipCommand(INDEX_FIRST_ITEM, editInternshipDescriptor),
                model,
                new CommandException(EditCommand.MESSAGE_DUPLICATE_ITEM));
    }

    @Test
    public void execute_editNote_success() {
        Note validNote = TypicalNote.FINISH_RESUME_2;
        EditNoteDescriptor editNoteDescriptor = new EditNoteDescriptorBuilder(FINISH_RESUME_2).build();

        expectedModel.setNote(FINISH_CS_2103, validNote);

        assertCommandSuccess(new EditNoteCommand(INDEX_FIRST_ITEM, editNoteDescriptor),
                model,
                new EditCommandResult(validNote.toString(),
                        String.format(EditNoteCommand.MESSAGE_EDIT_NOTE_SUCCESS, validNote.getName().fullName),
                        ItemUtil.NOTE_ALIAS),
                expectedModel);
    }

    @Test
    public void execute_editSingleFieldNoteTime_success() {
        Note validNote = new NoteBuilder(FINISH_CS_2103).withTime(VALID_TIME_2).build();
        EditNoteDescriptor editNoteDescriptorSingleTime = new EditNoteDescriptorBuilder()
                .withTime(VALID_TIME_2)
                .build();

        expectedModel.setNote(FINISH_CS_2103, validNote);

        assertCommandSuccess(new EditNoteCommand(INDEX_FIRST_ITEM, editNoteDescriptorSingleTime),
                model,
                new EditCommandResult(validNote.toString(),
                        String.format(EditNoteCommand.MESSAGE_EDIT_NOTE_SUCCESS, validNote.getName().fullName),
                        ItemUtil.NOTE_ALIAS),
                expectedModel);
    }

    @Test
    public void execute_editNoteWithoutChanges_throwsCommandException() {
        // Same name
        EditNoteDescriptor editNoteDescriptor = new EditNoteDescriptorBuilder()
                .withName(VALID_NOTE_NAME_CS2103)
                .build();
        assertCommandFailure(new EditNoteCommand(INDEX_FIRST_ITEM, editNoteDescriptor),
                model,
                new CommandException(EditNoteCommand.MESSAGE_SAME_NOTE));

        // Same time and tags
        editNoteDescriptor = new EditNoteDescriptorBuilder()
                .withTime(VALID_NOTE_TIME)
                .withTags(VALID_TAG_URGENT)
                .build();
        assertCommandFailure(new EditNoteCommand(INDEX_FIRST_ITEM, editNoteDescriptor),
                model,
                new CommandException(EditNoteCommand.MESSAGE_SAME_NOTE));
    }

    @Test
    public void execute_outOfBoundsNote_throwsCommandException() {
        EditNoteDescriptor editNoteDescriptor = new EditNoteDescriptor();
        assertCommandFailure(new EditNoteCommand(INDEX_THIRD_ITEM, editNoteDescriptor),
                model,
                new CommandException(Messages.MESSAGE_INVALID_INDEX));
    }

    @Test
    public void execute_duplicateNote_throwsCommandException() {
        EditNoteDescriptor editNoteDescriptor = new EditNoteDescriptorBuilder(FINISH_HOMEWORK).build();

        assertCommandFailure(new EditNoteCommand(INDEX_FIRST_ITEM, editNoteDescriptor),
                model,
                new CommandException(EditCommand.MESSAGE_DUPLICATE_ITEM));
    }

    @Test
    public void execute_isSameDuplicateNote_throwsCommandException() {
        EditNoteDescriptor editNoteDescriptor = new EditNoteDescriptorBuilder()
                .withName("Finish Homework").withTime("03-2020").build();

        assertCommandFailure(new EditNoteCommand(INDEX_FIRST_ITEM, editNoteDescriptor),
                model,
                new CommandException(EditCommand.MESSAGE_DUPLICATE_ITEM));
    }

    @Test
    public void execute_editProject_success() {
        Project validProject = RESUME;
        EditProjectDescriptor editProjectDescriptor = new EditProjectDescriptorBuilder(RESUME).build();

        expectedModel.setProject(ORBITAL, validProject);
        expectedModel.setProjectToDisplay();

        assertCommandSuccess(new EditProjectCommand(INDEX_FIRST_ITEM, editProjectDescriptor),
                model,
                new EditCommandResult(validProject.toString(),
                        String.format(EditProjectCommand.MESSAGE_EDIT_PROJECT_SUCCESS,
                                validProject.getName().fullName), ItemUtil.PROJECT_ALIAS),
                expectedModel);
    }

    @Test
    public void execute_editSingleFieldProjectWebsite_success() {
        Project validProject = new ProjectBuilder(ORBITAL).withWebsite(VALID_WEBSITE_DUKE).build();
        EditProjectDescriptor editProjectDescriptorSingleWebsite = new EditProjectDescriptorBuilder()
                .withWebsite(VALID_WEBSITE_DUKE)
                .build();

        expectedModel.setProject(ORBITAL, validProject);
        expectedModel.setProjectToDisplay();

        assertCommandSuccess(new EditProjectCommand(INDEX_FIRST_ITEM, editProjectDescriptorSingleWebsite),
                model,
                new EditCommandResult(validProject.toString(),
                        String.format(EditProjectCommand.MESSAGE_EDIT_PROJECT_SUCCESS,
                                validProject.getName().fullName), ItemUtil.PROJECT_ALIAS),
                expectedModel);
    }

    @Test
    public void execute_editProjectWithoutChanges_throwsCommandException() {
        // Same website
        EditProjectDescriptor editProjectDescriptor = new EditProjectDescriptorBuilder()
                .withWebsite(VALID_WEBSITE_ORBITAL)
                .build();
        assertCommandFailure(new EditProjectCommand(INDEX_FIRST_ITEM, editProjectDescriptor),
                model,
                new CommandException(EditProjectCommand.MESSAGE_SAME_PROJECT));

        // Same name
        editProjectDescriptor = new EditProjectDescriptorBuilder()
                .withName(VALID_PROJECT_NAME_ORBITAL)
                .build();
        assertCommandFailure(new EditProjectCommand(INDEX_FIRST_ITEM, editProjectDescriptor),
                model,
                new CommandException(EditProjectCommand.MESSAGE_SAME_PROJECT));

        // Same time and tags
        editProjectDescriptor = new EditProjectDescriptorBuilder()
                .withTime(VALID_TIME_1)
                .withTags(VALID_TAG_TECH)
                .build();
        assertCommandFailure(new EditProjectCommand(INDEX_FIRST_ITEM, editProjectDescriptor),
                model,
                new CommandException(EditProjectCommand.MESSAGE_SAME_PROJECT));
    }

    @Test
    public void execute_outOfBoundsProject_throwsCommandException() {
        EditProjectDescriptor editProjectDescriptor = new EditProjectDescriptor();
        assertCommandFailure(new EditProjectCommand(INDEX_THIRD_ITEM, editProjectDescriptor),
                model,
                new CommandException(Messages.MESSAGE_INVALID_INDEX));
    }

    @Test
    public void execute_duplicateProject_throwsCommandException() {
        EditProjectDescriptor editProjectDescriptor = new EditProjectDescriptorBuilder(DUKE).build();

        assertCommandFailure(new EditProjectCommand(INDEX_FIRST_ITEM, editProjectDescriptor),
                model,
                new CommandException(EditCommand.MESSAGE_DUPLICATE_ITEM));
    }

    @Test
    public void execute_isSameDuplicateProject_throwsCommandException() {
        EditProjectDescriptor editProjectDescriptor = new EditProjectDescriptorBuilder()
                .withName(VALID_PROJECT_NAME_DUKE).withTime(VALID_TIME_2).withWebsite(VALID_WEBSITE_DUKE).build();

        assertCommandFailure(new EditProjectCommand(INDEX_FIRST_ITEM, editProjectDescriptor),
                model,
                new CommandException(EditCommand.MESSAGE_DUPLICATE_ITEM));
    }

    @Test
    public void execute_editSkill_success() {
        Skill validSkill = CODE;
        EditSkillDescriptor editSkillDescriptor = new EditSkillDescriptorBuilder(CODE).build();

        expectedModel.setSkill(REACT, validSkill);
        expectedModel.setSkillToDisplay();

        assertCommandSuccess(new EditSkillCommand(INDEX_FIRST_ITEM, editSkillDescriptor),
                model,
                new EditCommandResult(validSkill.toString(),
                        String.format(EditSkillCommand.MESSAGE_EDIT_SKILL_SUCCESS,
                                validSkill.getName().fullName), ItemUtil.SKILL_ALIAS),
                expectedModel);
    }

    @Test
    public void execute_editSingleFieldSkillLevel_success() {
        Skill validSkill = new SkillBuilder(REACT).withLevel(Level.INTERMEDIATE).build();
        EditSkillDescriptor editSkillDescriptorSingleLevel = new EditSkillDescriptorBuilder()
                .withLevel(Level.INTERMEDIATE)
                .build();

        expectedModel.setSkill(REACT, validSkill);
        expectedModel.setSkillToDisplay();

        assertCommandSuccess(new EditSkillCommand(INDEX_FIRST_ITEM, editSkillDescriptorSingleLevel),
                model,
                new EditCommandResult(validSkill.toString(),
                        String.format(EditSkillCommand.MESSAGE_EDIT_SKILL_SUCCESS,
                                validSkill.getName().fullName), ItemUtil.SKILL_ALIAS),
                expectedModel);
    }

    @Test
    public void execute_editSkillWithoutChanges_throwsCommandException() {
        // Same name
        EditSkillDescriptor editSkillDescriptor = new EditSkillDescriptorBuilder()
                .withName(VALID_SKILL_NAME_REACT)
                .build();
        assertCommandFailure(new EditSkillCommand(INDEX_FIRST_ITEM, editSkillDescriptor),
                model,
                new CommandException(EditSkillCommand.MESSAGE_SAME_SKILL));

        // Same level and tags
        editSkillDescriptor = new EditSkillDescriptorBuilder()
                .withLevel(Level.BASIC)
                .withTags(VALID_TAG_TECH, VALID_TAG_FRONTEND)
                .build();
        assertCommandFailure(new EditSkillCommand(INDEX_FIRST_ITEM, editSkillDescriptor),
                model,
                new CommandException(EditSkillCommand.MESSAGE_SAME_SKILL));
    }

    @Test
    public void execute_outOfBoundsSkill_throwsCommandException() {
        EditSkillDescriptor editSkillDescriptor = new EditSkillDescriptor();
        assertCommandFailure(new EditSkillCommand(INDEX_THIRD_ITEM, editSkillDescriptor),
                model,
                new CommandException(Messages.MESSAGE_INVALID_INDEX));
    }

    @Test
    public void execute_duplicateSkill_throwsCommandException() {
        EditSkillDescriptor editSkillDescriptor = new EditSkillDescriptorBuilder(GIT).build();

        assertCommandFailure(new EditSkillCommand(INDEX_FIRST_ITEM, editSkillDescriptor),
                model,
                new CommandException(EditCommand.MESSAGE_DUPLICATE_ITEM));
    }

    @Test
    public void execute_isSameDuplicateSkill_throwsCommandException() {
        EditSkillDescriptor editSkillDescriptor = new EditSkillDescriptorBuilder()
                .withName(VALID_SKILL_NAME_GIT).build();

        assertCommandFailure(new EditSkillCommand(INDEX_FIRST_ITEM, editSkillDescriptor),
                model,
                new CommandException(EditCommand.MESSAGE_DUPLICATE_ITEM));
    }

    @Test
    public void execute_editResume_success() {
        Resume validResume = TypicalResume.CE_RESUME;
        EditResumeDescriptor editResumeDescriptor = new EditResumeDescriptorBuilder(CE_RESUME).build();

        expectedModel.setResume(ME_RESUME, validResume);
        expectedModel.setResumeToDisplay();

        assertCommandSuccess(new EditResumeCommand(INDEX_FIRST_ITEM, editResumeDescriptor),
                model,
                new EditCommandResult(validResume.toString(),
                        String.format(EditResumeCommand.MESSAGE_EDIT_RESUME_SUCCESS,
                                validResume.getName().fullName), ItemUtil.RESUME_ALIAS),
                expectedModel);
    }

    @Test
    public void execute_editSingleFieldResumeTags_success() {
        Resume validResume = new ResumeBuilder(ME_RESUME).withTags(VALID_TAG_TECH).build();
        EditResumeDescriptor editResumeDescriptorSingleTagField = new EditResumeDescriptorBuilder()
                .withTags(VALID_TAG_TECH)
                .build();

        expectedModel.setResume(ME_RESUME, validResume);
        expectedModel.setResumeToDisplay();

        assertCommandSuccess(new EditResumeCommand(INDEX_FIRST_ITEM, editResumeDescriptorSingleTagField),
                model,
                new EditCommandResult(validResume.toString(),
                        String.format(EditResumeCommand.MESSAGE_EDIT_RESUME_SUCCESS,
                                validResume.getName().fullName), ItemUtil.RESUME_ALIAS),
                expectedModel);
    }

    @Test
    public void execute_editResumeWithoutChanges_throwsCommandException() {
        // Same name
        EditResumeDescriptor editResumeDescriptor = new EditResumeDescriptorBuilder()
                .withName(VALID_RESUME_NAME_SE)
                .build();
        assertCommandFailure(new EditResumeCommand(INDEX_SECOND_ITEM, editResumeDescriptor),
                model,
                new CommandException(EditResumeCommand.MESSAGE_SAME_RESUME));
    }

    @Test
    public void execute_outOfBoundsResume_throwsCommandException() {
        EditResumeDescriptor editResumeDescriptor = new EditResumeDescriptor();
        assertCommandFailure(new EditResumeCommand(INDEX_THIRD_ITEM, editResumeDescriptor),
                model,
                new CommandException(Messages.MESSAGE_INVALID_INDEX));
    }

    @Test
    public void execute_duplicateResume_throwsCommandException() {
        EditResumeDescriptor editResumeDescriptor = new EditResumeDescriptorBuilder(SE_RESUME).build();

        assertCommandFailure(new EditResumeCommand(INDEX_FIRST_ITEM, editResumeDescriptor),
                model,
                new CommandException(EditCommand.MESSAGE_DUPLICATE_ITEM));
    }

    @Test
    public void execute_isSameDuplicateResume_throwsCommandException() {
        EditResumeDescriptor editResumeDescriptor = new EditResumeDescriptorBuilder()
                .withName(VALID_RESUME_NAME_SE).build();

        assertCommandFailure(new EditResumeCommand(INDEX_FIRST_ITEM, editResumeDescriptor),
                model,
                new CommandException(EditCommand.MESSAGE_DUPLICATE_ITEM));
    }

    @Test
    public void equals() {
        EditInternshipDescriptor editInternshipDescriptor = new EditInternshipDescriptorBuilder(GOOGLE).build();
        EditSkillDescriptor editSkillDescriptor = new EditSkillDescriptorBuilder(GIT).build();
        EditProjectDescriptor editProjectDescriptor = new EditProjectDescriptorBuilder(ORBITAL).build();
        EditResumeDescriptor editResumeDescriptor = new EditResumeDescriptorBuilder(ME_RESUME).build();
        EditNoteDescriptor editNoteDescriptor = new EditNoteDescriptorBuilder(FINISH_CS_2103).build();

        EditCommand validEditInternship = new EditInternshipCommand(INDEX_FIRST_ITEM, editInternshipDescriptor);
        EditCommand validEditProject = new EditProjectCommand(INDEX_FIRST_ITEM, editProjectDescriptor);
        EditCommand validEditResume = new EditResumeCommand(INDEX_FIRST_ITEM, editResumeDescriptor);
        EditCommand validEditSkill = new EditSkillCommand(INDEX_FIRST_ITEM, editSkillDescriptor);
        EditCommand validEditNote = new EditNoteCommand(INDEX_FIRST_ITEM, editNoteDescriptor);

        assertNotEquals(validEditInternship, validEditNote);
        assertNotEquals(validEditInternship, validEditProject);
        assertNotEquals(validEditInternship, validEditResume);
        assertNotEquals(validEditInternship, validEditSkill);
        assertNotEquals(validEditNote, validEditProject);
        assertNotEquals(validEditNote, validEditResume);
        assertNotEquals(validEditNote, validEditSkill);
        assertNotEquals(validEditProject, validEditResume);
        assertNotEquals(validEditProject, validEditSkill);
        assertNotEquals(validEditResume, validEditSkill);
    }
}


