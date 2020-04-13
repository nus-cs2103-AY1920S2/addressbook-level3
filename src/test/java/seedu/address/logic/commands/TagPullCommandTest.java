package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRONTEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_JAVA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_NONE_USAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TECH;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ITEM;

import java.util.HashSet;
import java.util.Set;

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
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ResumeBuilder;
import seedu.address.testutil.TypicalInternship;
import seedu.address.testutil.TypicalProject;
import seedu.address.testutil.TypicalResume;
import seedu.address.testutil.TypicalResumeBook;
import seedu.address.testutil.TypicalSkill;

public class TagPullCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalResumeBook.TYPICAL_WITH_FILLED_RESUME, new UserPrefs());
    }


    @Test
    public void execute_invalidResumeIndex_throwsCommandException() {
        Index invalidIndex = INDEX_FOURTH_ITEM;
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(VALID_TAG_TECH));
        TagPullCommand tagPullCommand = new TagPullCommand(invalidIndex, tags);
        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_INDEX, () -> tagPullCommand.execute(model));
    }

    @Test
    public void execute_validTag_successAndAllCountsIncrease() throws CommandException {
        Index firstIndex = INDEX_FIRST_ITEM;
        Index thirdIndex = INDEX_THIRD_ITEM;
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(VALID_TAG_TECH));

        Resume expectedFirstResume = new ResumeBuilder(TypicalResume.ME_RESUME)
                .withInternship(TypicalInternship.GOOGLE)
                .withInternship(TypicalInternship.NINJA_VAN)
                .withProject(TypicalProject.ORBITAL)
                .withProject(TypicalProject.DUKE)
                .withSkill(TypicalSkill.REACT)
                .withSkill(TypicalSkill.GIT)
                .build();

        Resume expectedThirdResume = new ResumeBuilder(TypicalResume.FILLED_RESUME)
                .withInternship(TypicalInternship.GOOGLE)
                .withInternship(TypicalInternship.NINJA_VAN)
                .withProject(TypicalProject.ORBITAL)
                .withProject(TypicalProject.DUKE)
                .withSkill(TypicalSkill.GIT) // Order matters here
                .withSkill(TypicalSkill.REACT)
                .build();

        // Resume at first index
        CommandResult commandResultFirstIndex = new TagPullCommand(firstIndex, tags).execute(model);
        assertEquals(String.format(TagPullCommand.MESSAGE_SUCCESS, 2, 2, 2),
                commandResultFirstIndex.getFeedbackToUser());

        Resume firstIndexResume = model.getResumeByIndex(firstIndex);
        assertEquals(firstIndexResume, expectedFirstResume);

        // Resume at third index
        CommandResult commandResultThirdIndex = new TagPullCommand(thirdIndex, tags).execute(model);
        assertEquals(String.format(TagPullCommand.MESSAGE_SUCCESS, 1, 1, 1),
                commandResultThirdIndex.getFeedbackToUser());

        Resume thirdIndexResume = model.getResumeByIndex(thirdIndex);
        assertEquals(thirdIndexResume, expectedThirdResume);
    }

    @Test
    public void execute_validTag_successAndOnlyOneCountIncreases() throws CommandException {
        Index index = INDEX_THIRD_ITEM;
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(VALID_TAG_FRONTEND));

        Resume expectedResume = new ResumeBuilder(TypicalResume.FILLED_RESUME)
                .withInternship(TypicalInternship.GOOGLE)
                .withProject(TypicalProject.ORBITAL)
                .withSkill(TypicalSkill.GIT) // Order matters here
                .withSkill(TypicalSkill.REACT)
                .build();

        CommandResult commandResult = new TagPullCommand(index, tags).execute(model);
        assertEquals(String.format(TagPullCommand.MESSAGE_SUCCESS, 0, 0, 1), commandResult.getFeedbackToUser());

        Resume resume = model.getResumeByIndex(index);
        assertEquals(resume, expectedResume);
    }

    @Test
    public void execute_validTag_successAndTwoCountsIncreases() throws CommandException {
        Index index = INDEX_FIRST_ITEM;
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(VALID_TAG_FRONTEND));

        Resume expectedResume = new ResumeBuilder(TypicalResume.ME_RESUME)
                .withInternship(TypicalInternship.GOOGLE)
                .withSkill(TypicalSkill.REACT)
                .build();

        CommandResult commandResult = new TagPullCommand(index, tags).execute(model);
        assertEquals(String.format(TagPullCommand.MESSAGE_SUCCESS, 1, 0, 1), commandResult.getFeedbackToUser());

        Resume resume = model.getResumeByIndex(index);
        assertEquals(resume, expectedResume);
    }

    @Test
    public void execute_validTag_successNoCountsIncreases() throws CommandException {
        Index firstIndex = INDEX_FIRST_ITEM;
        Index thirdIndex = INDEX_THIRD_ITEM;
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(VALID_TAG_NONE_USAGE));

        Resume expectedFirstResume = new ResumeBuilder(TypicalResume.ME_RESUME)
                .build();

        Resume expectedThirdResume = new ResumeBuilder(TypicalResume.FILLED_RESUME)
                .withInternship(TypicalInternship.GOOGLE)
                .withProject(TypicalProject.ORBITAL)
                .withSkill(TypicalSkill.GIT) // Order matters here
                .build();

        // Resume at first index
        CommandResult commandResultFirstIndex = new TagPullCommand(firstIndex, tags).execute(model);
        assertEquals(String.format(TagPullCommand.MESSAGE_SUCCESS, 0, 0, 0),
                commandResultFirstIndex.getFeedbackToUser());

        Resume firstIndexResume = model.getResumeByIndex(firstIndex);
        assertEquals(firstIndexResume, expectedFirstResume);

        // Resume at third index
        CommandResult commandResultThirdIndex = new TagPullCommand(thirdIndex, tags).execute(model);
        assertEquals(String.format(TagPullCommand.MESSAGE_SUCCESS, 0, 0, 0),
                commandResultThirdIndex.getFeedbackToUser());

        Resume thirdIndexResume = model.getResumeByIndex(thirdIndex);
        assertEquals(thirdIndexResume, expectedThirdResume);
    }

    @Test
    public void execute_validTagCalledTwice_success() throws CommandException {
        Index index = INDEX_FIRST_ITEM;
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(VALID_TAG_TECH));

        Resume expectedResume = new ResumeBuilder(TypicalResume.ME_RESUME)
                .withInternship(TypicalInternship.GOOGLE)
                .withInternship(TypicalInternship.NINJA_VAN)
                .withProject(TypicalProject.ORBITAL)
                .withProject(TypicalProject.DUKE)
                .withSkill(TypicalSkill.REACT)
                .withSkill(TypicalSkill.GIT)
                .build();

        CommandResult commandResult = new TagPullCommand(index, tags).execute(model);
        assertEquals(String.format(TagPullCommand.MESSAGE_SUCCESS, 2, 2, 2), commandResult.getFeedbackToUser());

        Resume resume = model.getResumeByIndex(index);
        assertEquals(resume, expectedResume);

        CommandResult commandResultAgain = new TagPullCommand(index, tags).execute(model);
        assertEquals(String.format(TagPullCommand.MESSAGE_SUCCESS, 0, 0, 0), commandResultAgain.getFeedbackToUser());

        Resume resumeAgain = model.getResumeByIndex(index);
        assertEquals(resumeAgain, expectedResume);
    }

    @Test
    public void execute_multipleTags_success() throws CommandException {
        Index firstIndex = INDEX_FIRST_ITEM;
        Index thirdIndex = INDEX_THIRD_ITEM;
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(VALID_TAG_JAVA));
        tags.add(new Tag(VALID_TAG_FRONTEND));

        Resume expectedFirstResume = new ResumeBuilder(TypicalResume.ME_RESUME)
                .withInternship(TypicalInternship.GOOGLE)
                .withProject(TypicalProject.DUKE)
                .withSkill(TypicalSkill.REACT)
                .build();

        Resume expectedThirdResume = new ResumeBuilder(TypicalResume.FILLED_RESUME)
                .withInternship(TypicalInternship.GOOGLE)
                .withProject(TypicalProject.ORBITAL)
                .withProject(TypicalProject.DUKE)
                .withSkill(TypicalSkill.GIT) // Order matters here
                .withSkill(TypicalSkill.REACT)
                .build();

        // Resume at first index
        CommandResult commandResultFirstIndex = new TagPullCommand(firstIndex, tags).execute(model);
        assertEquals(String.format(TagPullCommand.MESSAGE_SUCCESS, 1, 1, 1),
                commandResultFirstIndex.getFeedbackToUser());

        Resume firstIndexResume = model.getResumeByIndex(firstIndex);
        assertEquals(firstIndexResume, expectedFirstResume);

        // Resume at third index
        CommandResult commandResultThirdIndex = new TagPullCommand(thirdIndex, tags).execute(model);
        assertEquals(String.format(TagPullCommand.MESSAGE_SUCCESS, 0, 1, 1),
                commandResultThirdIndex.getFeedbackToUser());

        Resume thirdIndexResume = model.getResumeByIndex(thirdIndex);
        assertEquals(thirdIndexResume, expectedThirdResume);
    }
    @Test
    public void equals() {
        Index index = INDEX_FIRST_ITEM;
        Set<Tag> tagsA = new HashSet<>();
        tagsA.add(new Tag(VALID_TAG_JAVA));
        tagsA.add(new Tag(VALID_TAG_FRONTEND));

        Set<Tag> tagsB = new HashSet<>();
        tagsA.add(new Tag(VALID_TAG_TECH));

        TagPullCommand tagPullACommand = new TagPullCommand(index, tagsA);
        TagPullCommand tagPullBCommand = new TagPullCommand(index, tagsB);

        // same object -> returns true
        assertTrue(tagPullACommand.equals(tagPullACommand));

        // same values -> returns true
        TagPullCommand tagPullACommandCopy = new TagPullCommand(index, tagsA);
        assertTrue(tagPullACommand.equals(tagPullACommandCopy));

        // different types -> returns false
        assertFalse(tagPullACommand.equals(1));

        // null -> returns false
        assertFalse(tagPullACommand.equals(null));

        // different tags -> returns false
        assertFalse(tagPullACommand.equals(tagPullBCommand));
    }
}
