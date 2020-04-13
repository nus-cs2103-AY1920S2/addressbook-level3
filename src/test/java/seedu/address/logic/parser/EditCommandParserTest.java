package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.INVALID_FROM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LEVEL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TO_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_WEBSITE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ITEM_TYPE_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.ITEM_TYPE_NOTE;
import static seedu.address.logic.commands.CommandTestUtil.ITEM_TYPE_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.ITEM_TYPE_RESUME;
import static seedu.address.logic.commands.CommandTestUtil.ITEM_TYPE_SKILL;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_BASIC;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_DESCRIPTION_DUKE;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_DESCRIPTION_ORBITAL;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_INTERMEDIATE;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_INTERNSHIP_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_NAME_DUKE;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_NAME_GOOGLE;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_NAME_ME;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_NAME_ORBITAL;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_NAME_REACT;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_NOTE_NAME;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_NOTE_TIME;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_ROLE_BACKEND;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_ROLE_FRONTEND;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_TAG_BACKEND;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_TAG_FRONTEND;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_TAG_JAVA;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_TAG_TECH;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_TAG_URGENT;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_TIME_FROM;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_TIME_FROM_2;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_TIME_ORBITAL;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_TIME_TO;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_TIME_TO_2;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_WEBSITE_DUKE;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_WEBSITE_ORBITAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_ORBITAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FROM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERNSHIP_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERNSHIP_NAME_GOOGLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERNSHIP_ROLE_FRONTEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_NAME_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_NAME_ORBITAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RESUME_NAME_ME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_NAME_REACT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BACKEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRONTEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_JAVA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_URGENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEBSITE_ORBITAL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalInternship.GOOGLE;
import static seedu.address.testutil.TypicalNote.FINISH_CS_2103;
import static seedu.address.testutil.TypicalProject.ORBITAL;
import static seedu.address.testutil.TypicalResume.ME_RESUME;
import static seedu.address.testutil.TypicalSkill.REACT;

import org.junit.jupiter.api.Test;

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
import seedu.address.model.item.Item;
import seedu.address.model.item.field.Level;
import seedu.address.model.item.field.Name;
import seedu.address.model.item.field.Time;
import seedu.address.model.item.field.Website;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditInternshipDescriptorBuilder;
import seedu.address.testutil.EditNoteDescriptorBuilder;
import seedu.address.testutil.EditProjectDescriptorBuilder;
import seedu.address.testutil.EditResumeDescriptorBuilder;
import seedu.address.testutil.EditSkillDescriptorBuilder;

public class EditCommandParserTest {
    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_editAllInternshipFieldsPresent_success() {
        EditInternshipDescriptor editInternshipDescriptor = new EditInternshipDescriptorBuilder(GOOGLE)
                .withTags(VALID_TAG_FRONTEND)
                .build();

        // Standard
        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_INTERNSHIP + PREFIXED_NAME_GOOGLE
                        + PREFIXED_ROLE_FRONTEND + PREFIXED_TIME_FROM
                        + PREFIXED_TIME_TO + PREFIXED_INTERNSHIP_DESCRIPTION + PREFIXED_TAG_FRONTEND,
                new EditInternshipCommand(INDEX_FIRST_ITEM, editInternshipDescriptor));

        // multiple item types - last item type accepted
        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_PROJECT + ITEM_TYPE_INTERNSHIP + PREFIXED_NAME_GOOGLE
                        + PREFIXED_ROLE_FRONTEND
                        + PREFIXED_TIME_FROM + PREFIXED_TIME_TO + PREFIXED_INTERNSHIP_DESCRIPTION
                        + PREFIXED_TAG_FRONTEND,
                new EditInternshipCommand(INDEX_FIRST_ITEM, editInternshipDescriptor));

        // multiple item names - last name accepted
        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_INTERNSHIP + PREFIXED_NAME_REACT + PREFIXED_NAME_GOOGLE
                        + PREFIXED_ROLE_FRONTEND + PREFIXED_TIME_FROM + PREFIXED_TIME_TO
                        + PREFIXED_INTERNSHIP_DESCRIPTION + PREFIXED_TAG_FRONTEND,
                new EditInternshipCommand(INDEX_FIRST_ITEM, editInternshipDescriptor));

        // multiple item roles - last role accepted
        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_INTERNSHIP + PREFIXED_NAME_GOOGLE
                        + PREFIXED_ROLE_BACKEND + PREFIXED_ROLE_FRONTEND
                        + PREFIXED_TIME_FROM + PREFIXED_TIME_TO + PREFIXED_INTERNSHIP_DESCRIPTION
                        + PREFIXED_TAG_FRONTEND,
                new EditInternshipCommand(INDEX_FIRST_ITEM, editInternshipDescriptor));

        // multiple item froms - last from accepted
        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_INTERNSHIP + PREFIXED_NAME_GOOGLE
                        + PREFIXED_ROLE_BACKEND + PREFIXED_ROLE_FRONTEND
                        + PREFIXED_TIME_FROM_2 + PREFIXED_TIME_FROM + PREFIXED_TIME_TO + PREFIXED_INTERNSHIP_DESCRIPTION
                        + PREFIXED_TAG_FRONTEND,
                new EditInternshipCommand(INDEX_FIRST_ITEM, editInternshipDescriptor));

        // multiple item to - last to accepted
        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_INTERNSHIP + PREFIXED_NAME_GOOGLE
                        + PREFIXED_ROLE_BACKEND + PREFIXED_ROLE_FRONTEND
                        + PREFIXED_TIME_FROM + PREFIXED_TIME_TO_2 + PREFIXED_TIME_TO + PREFIXED_INTERNSHIP_DESCRIPTION
                        + PREFIXED_TAG_FRONTEND,
                new EditInternshipCommand(INDEX_FIRST_ITEM, editInternshipDescriptor));

        // multiple item description - last description accepted
        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_INTERNSHIP + PREFIXED_NAME_GOOGLE
                        + PREFIXED_ROLE_BACKEND + PREFIXED_ROLE_FRONTEND
                        + PREFIXED_TIME_FROM + PREFIXED_TIME_TO + PREFIXED_DESCRIPTION_ORBITAL
                        + PREFIXED_INTERNSHIP_DESCRIPTION + PREFIXED_TAG_FRONTEND,
                new EditInternshipCommand(INDEX_FIRST_ITEM, editInternshipDescriptor));

        // multiple item tags - all tags added
        EditInternshipDescriptor editInternshipDescriptorMultipleTags = new EditInternshipDescriptorBuilder(GOOGLE)
                .withTags(VALID_TAG_FRONTEND, VALID_TAG_JAVA).build();

        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_INTERNSHIP + PREFIXED_NAME_GOOGLE
                        + PREFIXED_ROLE_BACKEND + PREFIXED_ROLE_FRONTEND
                        + PREFIXED_TIME_FROM + PREFIXED_TIME_TO + PREFIXED_INTERNSHIP_DESCRIPTION
                        + PREFIXED_TAG_FRONTEND + PREFIXED_TAG_JAVA,
                new EditInternshipCommand(INDEX_FIRST_ITEM, editInternshipDescriptorMultipleTags));
    }

    @Test
    public void parse_allAddNoteFieldsPresent_success() {
        EditNoteDescriptor editNoteDescriptor = new EditNoteDescriptorBuilder(FINISH_CS_2103)
                .build();

        // Standard
        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_NOTE + PREFIXED_NOTE_NAME + PREFIXED_TIME_TO
                        + PREFIXED_TAG_URGENT,
                new EditNoteCommand(INDEX_FIRST_ITEM, editNoteDescriptor));

        // multiple item types - last type accepted
        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_INTERNSHIP + ITEM_TYPE_NOTE
                        + PREFIXED_NOTE_NAME + PREFIXED_TIME_TO + PREFIXED_TAG_URGENT,
                new EditNoteCommand(INDEX_FIRST_ITEM, editNoteDescriptor));


        // multiple item names - last name accepted
        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_NOTE + PREFIXED_NAME_REACT
                        + PREFIXED_NOTE_NAME + PREFIXED_TIME_TO + PREFIXED_TAG_URGENT,
                new EditNoteCommand(INDEX_FIRST_ITEM, editNoteDescriptor));

        // multiple item time - last time accepted
        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_NOTE + PREFIXED_NOTE_NAME + " t/ 12-2021" + PREFIXED_TIME_TO
                        + PREFIXED_TAG_URGENT,
                new EditNoteCommand(INDEX_FIRST_ITEM, editNoteDescriptor));

        // multiple item tags - all tags added
        EditNoteDescriptor editNoteDescriptorMultipleTags = new EditNoteDescriptorBuilder(FINISH_CS_2103)
                .withTags(VALID_TAG_URGENT, VALID_TAG_FRONTEND)
                .build();

        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_NOTE + PREFIXED_NOTE_NAME
                        + PREFIXED_NOTE_TIME + PREFIXED_TAG_URGENT + PREFIXED_TAG_FRONTEND,
                new EditNoteCommand(INDEX_FIRST_ITEM, editNoteDescriptorMultipleTags));
    }

    @Test
    public void parse_editAllProjectFieldsPresent_success() {
        EditProjectDescriptor editProjectDescriptor = new EditProjectDescriptorBuilder(ORBITAL).build();

        // Standard
        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_PROJECT + PREFIXED_NAME_ORBITAL + PREFIXED_TIME_ORBITAL
                        + PREFIXED_WEBSITE_ORBITAL + PREFIXED_DESCRIPTION_ORBITAL
                        + PREFIXED_TAG_TECH, new EditProjectCommand(INDEX_FIRST_ITEM, editProjectDescriptor));

        // multiple item types - last type accepted
        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_INTERNSHIP + ITEM_TYPE_PROJECT + PREFIXED_NAME_ORBITAL
                        + PREFIXED_TIME_ORBITAL + PREFIXED_WEBSITE_ORBITAL + PREFIXED_DESCRIPTION_ORBITAL
                        + PREFIXED_TAG_TECH, new EditProjectCommand(INDEX_FIRST_ITEM, editProjectDescriptor));

        // multiple item names - last name accepted
        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_PROJECT + PREFIXED_NAME_REACT + PREFIXED_NAME_ORBITAL
                        + PREFIXED_TIME_ORBITAL + PREFIXED_WEBSITE_ORBITAL + PREFIXED_DESCRIPTION_ORBITAL
                        + PREFIXED_TAG_TECH,
                new EditProjectCommand(INDEX_FIRST_ITEM, editProjectDescriptor));

        // multiple item time - last time accepted
        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_PROJECT + PREFIXED_NAME_ORBITAL + PREFIXED_TIME_TO
                        + PREFIXED_TIME_ORBITAL + PREFIXED_WEBSITE_ORBITAL + PREFIXED_DESCRIPTION_ORBITAL
                        + PREFIXED_TAG_TECH,
                new EditProjectCommand(INDEX_FIRST_ITEM, editProjectDescriptor));

        // multiple item website - last website accepted
        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_PROJECT + PREFIXED_NAME_ORBITAL
                        + PREFIXED_TIME_ORBITAL + PREFIXED_WEBSITE_DUKE + PREFIXED_WEBSITE_ORBITAL
                        + PREFIXED_DESCRIPTION_ORBITAL + PREFIXED_TAG_TECH,
                new EditProjectCommand(INDEX_FIRST_ITEM, editProjectDescriptor));

        // multiple item description - last description accepted
        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_PROJECT + PREFIXED_NAME_ORBITAL + PREFIXED_TIME_ORBITAL
                        + PREFIXED_WEBSITE_ORBITAL + PREFIXED_DESCRIPTION_DUKE + PREFIXED_DESCRIPTION_ORBITAL
                        + PREFIXED_TAG_TECH, new EditProjectCommand(INDEX_FIRST_ITEM, editProjectDescriptor));

        // multiple item tags - all tags accepted
        EditProjectDescriptor editProjectDescriptorMultipleTags = new EditProjectDescriptorBuilder(ORBITAL)
                .withTags(VALID_TAG_JAVA, VALID_TAG_FRONTEND).build();

        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_PROJECT + PREFIXED_NAME_ORBITAL + PREFIXED_TIME_ORBITAL
                        + PREFIXED_WEBSITE_ORBITAL + PREFIXED_DESCRIPTION_ORBITAL
                        + PREFIXED_TAG_JAVA + PREFIXED_TAG_FRONTEND, new EditProjectCommand(INDEX_FIRST_ITEM,
                        editProjectDescriptorMultipleTags));
    }

    @Test
    public void parse_editAllResumeFieldsPresent_success() {
        EditResumeDescriptor editResumeDescriptor = new EditResumeDescriptorBuilder(ME_RESUME)
                .withTags(VALID_TAG_FRONTEND).build();

        // Standard
        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_RESUME + PREFIXED_NAME_ME + PREFIXED_TAG_FRONTEND,
                new EditResumeCommand(INDEX_FIRST_ITEM, editResumeDescriptor));


        // multiple item types - last type accepted
        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_INTERNSHIP + ITEM_TYPE_RESUME
                        + PREFIXED_NAME_ME + PREFIXED_TAG_FRONTEND,
                new EditResumeCommand(INDEX_FIRST_ITEM, editResumeDescriptor));

        // multiple item names - last name accepted
        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_RESUME + PREFIXED_NAME_REACT
                        + PREFIXED_NAME_ME + PREFIXED_TAG_FRONTEND,
                new EditResumeCommand(INDEX_FIRST_ITEM, editResumeDescriptor));

        // multiple item tags - all tags accepted
        EditResumeDescriptor editResumeDescriptorMultipleTags = new EditResumeDescriptorBuilder(ME_RESUME)
                .withTags(VALID_TAG_FRONTEND, VALID_TAG_JAVA).build();

        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_RESUME + PREFIXED_NAME_REACT + PREFIXED_NAME_ME
                        + PREFIXED_TAG_FRONTEND + PREFIXED_TAG_JAVA,
                new EditResumeCommand(INDEX_FIRST_ITEM, editResumeDescriptorMultipleTags));
    }

    @Test
    public void parse_editAllSkillFieldsPresent_success() {
        EditSkillDescriptor editSkillDescriptor = new EditSkillDescriptorBuilder(REACT).build();

        // Standard
        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_SKILL + PREFIXED_NAME_REACT
                        + PREFIXED_BASIC + PREFIXED_TAG_TECH + PREFIXED_TAG_FRONTEND,
                new EditSkillCommand(INDEX_FIRST_ITEM, editSkillDescriptor));

        // multiple item types - last type accepted
        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_INTERNSHIP + ITEM_TYPE_SKILL + PREFIXED_NAME_REACT
                        + PREFIXED_BASIC + PREFIXED_TAG_TECH + PREFIXED_TAG_FRONTEND,
                new EditSkillCommand(INDEX_FIRST_ITEM, editSkillDescriptor));

        // multiple item names - last name accepted
        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_SKILL + PREFIXED_NAME_DUKE + PREFIXED_NAME_REACT
                        + PREFIXED_BASIC + PREFIXED_TAG_TECH + PREFIXED_TAG_FRONTEND,
                new EditSkillCommand(INDEX_FIRST_ITEM, editSkillDescriptor));

        // multiple item levels - last level accepted
        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_SKILL + PREFIXED_NAME_REACT + PREFIXED_INTERMEDIATE
                        + PREFIXED_BASIC + PREFIXED_TAG_TECH + PREFIXED_TAG_FRONTEND,
                new EditSkillCommand(INDEX_FIRST_ITEM, editSkillDescriptor));

        // multiple item tags - all tags accepted
        EditSkillDescriptor editSkillDescriptorMultipleTags = new EditSkillDescriptorBuilder(REACT)
                .withTags(VALID_TAG_FRONTEND, VALID_TAG_JAVA).build();

        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_SKILL + PREFIXED_NAME_REACT + PREFIXED_BASIC
                        + PREFIXED_TAG_FRONTEND + PREFIXED_TAG_JAVA,
                new EditSkillCommand(INDEX_FIRST_ITEM, editSkillDescriptorMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {

        // no internship tags
        EditInternshipDescriptor editInternshipDescriptorNoTag = new EditInternshipDescriptorBuilder()
                .withName(VALID_INTERNSHIP_NAME_GOOGLE)
                .withRole(VALID_INTERNSHIP_ROLE_FRONTEND)
                .withFrom(VALID_FROM)
                .withTo(VALID_TO)
                .withDescription(VALID_INTERNSHIP_DESCRIPTION)
                .build();

        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_INTERNSHIP + PREFIXED_NAME_GOOGLE
                        + PREFIXED_ROLE_FRONTEND + PREFIXED_TIME_FROM
                        + PREFIXED_TIME_TO + PREFIXED_INTERNSHIP_DESCRIPTION,
                new EditInternshipCommand(INDEX_FIRST_ITEM, editInternshipDescriptorNoTag));

        // no internship name, no internship role
        EditInternshipDescriptor editInternshipDescriptorNoNameNoRole = new EditInternshipDescriptorBuilder()
                .withFrom(VALID_FROM)
                .withTo(VALID_TO)
                .withDescription(VALID_INTERNSHIP_DESCRIPTION)
                .withTags(VALID_TAG_FRONTEND)
                .build();

        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_INTERNSHIP
                        + PREFIXED_TIME_FROM + PREFIXED_TIME_TO + PREFIXED_INTERNSHIP_DESCRIPTION
                        + PREFIXED_TAG_FRONTEND,
                new EditInternshipCommand(INDEX_FIRST_ITEM, editInternshipDescriptorNoNameNoRole));

        // no note tags
        EditNoteDescriptor editNoteDescriptorNoTag = new EditNoteDescriptorBuilder()
                .withName(VALID_NOTE_NAME_CS2103)
                .withTime(VALID_TO)
                .build();

        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_NOTE + PREFIXED_NOTE_NAME + PREFIXED_TIME_TO,
                new EditNoteCommand(INDEX_FIRST_ITEM, editNoteDescriptorNoTag));

        // no note name, no note tags
        EditNoteDescriptor editNoteDescriptorNoNameNoTag = new EditNoteDescriptorBuilder()
                .withTime(VALID_TO)
                .build();

        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_NOTE + PREFIXED_TIME_TO,
                new EditNoteCommand(INDEX_FIRST_ITEM, editNoteDescriptorNoNameNoTag));

        // no project tags
        EditProjectDescriptor editProjectDescriptorNoTag = new EditProjectDescriptorBuilder()
                .withName(VALID_PROJECT_NAME_ORBITAL)
                .withTime(VALID_TIME_1)
                .withWebsite(VALID_WEBSITE_ORBITAL)
                .withDescription(VALID_DESCRIPTION_ORBITAL)
                .build();

        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_PROJECT + PREFIXED_NAME_ORBITAL + PREFIXED_TIME_ORBITAL
                        + PREFIXED_WEBSITE_ORBITAL + PREFIXED_DESCRIPTION_ORBITAL,
                new EditProjectCommand(INDEX_FIRST_ITEM, editProjectDescriptorNoTag));

        // no project name, no project website
        EditProjectDescriptor editProjectDescriptorNoNameNoTag = new EditProjectDescriptorBuilder()
                .withTime(VALID_TIME_1)
                .withDescription(VALID_DESCRIPTION_ORBITAL)
                .withTags(VALID_TAG_BACKEND)
                .build();

        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_PROJECT + PREFIXED_TIME_ORBITAL
                        + PREFIXED_DESCRIPTION_ORBITAL + PREFIXED_TAG_BACKEND,
                new EditProjectCommand(INDEX_FIRST_ITEM, editProjectDescriptorNoNameNoTag));


        // no resume tags
        EditResumeDescriptor editResumeDescriptorNoTag = new EditResumeDescriptorBuilder()
                .withName(VALID_RESUME_NAME_ME)
                .build();

        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_RESUME + PREFIXED_NAME_ME,
                new EditResumeCommand(INDEX_FIRST_ITEM, editResumeDescriptorNoTag));

        // no skill tags
        EditSkillDescriptor editSkillDescriptorNoTag = new EditSkillDescriptorBuilder()
                .withName(VALID_SKILL_NAME_REACT)
                .withLevel(Level.BASIC)
                .build();

        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_SKILL + PREFIXED_NAME_REACT + PREFIXED_BASIC,
                new EditSkillCommand(INDEX_FIRST_ITEM, editSkillDescriptorNoTag));

        // no skill name, no skill level
        EditSkillDescriptor editSkillDescriptorNoNameNoLevel = new EditSkillDescriptorBuilder()
                .withTags(VALID_TAG_FRONTEND, VALID_TAG_BACKEND)
                .build();

        assertParseSuccess(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_SKILL + PREFIXED_TAG_FRONTEND + PREFIXED_TAG_BACKEND,
                new EditSkillCommand(INDEX_FIRST_ITEM, editSkillDescriptorNoNameNoLevel));
    }

    @Test
    public void parse_compulsoryItemTypeMissing_failure() {
        // missing item prefix
        assertParseFailure(parser,
                VALID_ITEM_INDEX + PREFIXED_NAME_GOOGLE + PREFIXED_ROLE_FRONTEND
                        + PREFIXED_TIME_FROM + PREFIXED_TIME_TO + PREFIXED_INTERNSHIP_DESCRIPTION
                        + PREFIXED_TAG_FRONTEND,
                Item.MESSAGE_CONSTRAINTS);

        // invalid item prefix
        assertParseFailure(parser,
                VALID_ITEM_INDEX + INVALID_TYPE_DESC + PREFIXED_NAME_GOOGLE + PREFIXED_ROLE_FRONTEND
                        + PREFIXED_TIME_FROM + PREFIXED_TIME_TO + PREFIXED_INTERNSHIP_DESCRIPTION
                        + PREFIXED_TAG_FRONTEND,
                Item.MESSAGE_INVALID_ITEM_TYPE);
    }

    @Test
    public void parse_allFieldMissing_failure() {
        String expectedInternshipErrorMessage =
                EditInternshipCommand.MESSAGE_NOT_EDITED;

        // missing all fields in internship prefix
        assertParseFailure(parser, VALID_ITEM_INDEX + ITEM_TYPE_INTERNSHIP,
                expectedInternshipErrorMessage);

        String expectedNoteErrorMessage =
                EditNoteCommand.MESSAGE_NOT_EDITED;

        // missing all fields in note prefix
        assertParseFailure(parser, VALID_ITEM_INDEX + ITEM_TYPE_NOTE,
                expectedNoteErrorMessage);

        String expectedProjectErrorMessage =
                EditProjectCommand.MESSAGE_NOT_EDITED;

        // missing all fields in project prefix
        assertParseFailure(parser, VALID_ITEM_INDEX + ITEM_TYPE_PROJECT,
                expectedProjectErrorMessage);

        String expectedResumeErrorMessage =
                EditResumeCommand.MESSAGE_NOT_EDITED;

        // missing all fields in resume prefix
        assertParseFailure(parser, VALID_ITEM_INDEX + ITEM_TYPE_RESUME,
                expectedResumeErrorMessage);

        String expectedSkillErrorMessage =
                EditSkillCommand.MESSAGE_NOT_EDITED;

        // missing all fields in skill prefix
        assertParseFailure(parser, VALID_ITEM_INDEX + ITEM_TYPE_SKILL,
                expectedSkillErrorMessage);
    }

    @Test
    public void parse_invalidValueAmongValidValues_failure() {
        // invalid name
        assertParseFailure(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_INTERNSHIP + INVALID_NAME_DESC
                        + PREFIXED_ROLE_FRONTEND + PREFIXED_TIME_FROM
                        + PREFIXED_TIME_TO + PREFIXED_INTERNSHIP_DESCRIPTION + PREFIXED_TAG_FRONTEND,
                Name.MESSAGE_CONSTRAINTS);

        // invalid from
        assertParseFailure(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_INTERNSHIP + PREFIXED_NAME_GOOGLE
                        + PREFIXED_ROLE_FRONTEND + INVALID_FROM_DESC
                        + PREFIXED_TIME_TO + PREFIXED_INTERNSHIP_DESCRIPTION + PREFIXED_TAG_FRONTEND,
                Time.MESSAGE_CONSTRAINTS);

        // invalid to
        assertParseFailure(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_INTERNSHIP + PREFIXED_NAME_GOOGLE
                        + PREFIXED_ROLE_FRONTEND + PREFIXED_TIME_FROM
                        + INVALID_TO_DESC + PREFIXED_INTERNSHIP_DESCRIPTION + PREFIXED_TAG_FRONTEND,
                Time.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_INTERNSHIP + PREFIXED_NAME_GOOGLE
                        + PREFIXED_ROLE_FRONTEND + PREFIXED_TIME_FROM
                        + PREFIXED_TIME_TO + PREFIXED_INTERNSHIP_DESCRIPTION + INVALID_TAG_DESC,
                Tag.MESSAGE_CONSTRAINTS);

        // invalid website
        assertParseFailure(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_PROJECT + PREFIXED_NAME_ORBITAL + PREFIXED_TIME_TO
                        + INVALID_WEBSITE_DESC + PREFIXED_DESCRIPTION_ORBITAL,
                Website.MESSAGE_CONSTRAINTS);

        // invalid level
        assertParseFailure(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_SKILL + PREFIXED_NAME_REACT
                        + INVALID_LEVEL_DESC + PREFIXED_TAG_FRONTEND,
                Level.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_singleInvalidValue_failure() {
        // invalid name
        assertParseFailure(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_INTERNSHIP + INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // invalid from
        assertParseFailure(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_INTERNSHIP + INVALID_FROM_DESC,
                Time.MESSAGE_CONSTRAINTS);

        // invalid to
        assertParseFailure(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_INTERNSHIP + INVALID_TO_DESC,
                Time.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_INTERNSHIP + INVALID_TAG_DESC,
                Tag.MESSAGE_CONSTRAINTS);

        // invalid website
        assertParseFailure(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_PROJECT + INVALID_WEBSITE_DESC,
                Website.MESSAGE_CONSTRAINTS);

        // invalid level
        assertParseFailure(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_SKILL + INVALID_LEVEL_DESC,
                Level.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_singleInvalidValueWithSingleValidValue_failure() {
        // invalid name
        assertParseFailure(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_INTERNSHIP + PREFIXED_NAME_GOOGLE + INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // invalid from
        assertParseFailure(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_INTERNSHIP + PREFIXED_TIME_FROM + INVALID_FROM_DESC,
                Time.MESSAGE_CONSTRAINTS);

        // invalid to
        assertParseFailure(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_INTERNSHIP + PREFIXED_TIME_TO + INVALID_TO_DESC,
                Time.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_INTERNSHIP + PREFIXED_TAG_TECH + INVALID_TAG_DESC,
                Tag.MESSAGE_CONSTRAINTS);

        // invalid website
        assertParseFailure(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_PROJECT + PREFIXED_WEBSITE_DUKE + INVALID_WEBSITE_DESC,
                Website.MESSAGE_CONSTRAINTS);

        // invalid level
        assertParseFailure(parser,
                VALID_ITEM_INDEX + ITEM_TYPE_SKILL + PREFIXED_BASIC + INVALID_LEVEL_DESC,
                Level.MESSAGE_CONSTRAINTS);
    }
}

