package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ITEM_TYPE_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.ITEM_TYPE_INVALID;
import static seedu.address.logic.commands.CommandTestUtil.ITEM_TYPE_NOTE;
import static seedu.address.logic.commands.CommandTestUtil.ITEM_TYPE_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.ITEM_TYPE_RESUME;
import static seedu.address.logic.commands.CommandTestUtil.ITEM_TYPE_SKILL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.delete.DeleteCommand;
import seedu.address.logic.commands.delete.DeleteInternshipCommand;
import seedu.address.logic.commands.delete.DeleteNoteCommand;
import seedu.address.logic.commands.delete.DeleteProjectCommand;
import seedu.address.logic.commands.delete.DeleteResumeCommand;
import seedu.address.logic.commands.delete.DeleteSkillCommand;
import seedu.address.model.item.Item;
import seedu.address.testutil.TypicalIndexes;

public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_deleteInternship_success() {
        assertParseSuccess(parser, "3" + ITEM_TYPE_INTERNSHIP,
                new DeleteInternshipCommand(TypicalIndexes.INDEX_THIRD_ITEM));
    }

    @Test
    public void parse_deleteProject_success() {
        assertParseSuccess(parser, "3" + ITEM_TYPE_PROJECT,
                new DeleteProjectCommand(TypicalIndexes.INDEX_THIRD_ITEM));
    }

    @Test
    public void parse_deleteSkill_success() {
        assertParseSuccess(parser, "3" + ITEM_TYPE_SKILL,
                new DeleteSkillCommand(TypicalIndexes.INDEX_THIRD_ITEM));
    }

    @Test
    public void parse_deleteResume_success() {
        assertParseSuccess(parser, "3" + ITEM_TYPE_RESUME,
                new DeleteResumeCommand(TypicalIndexes.INDEX_THIRD_ITEM));
    }

    @Test
    public void parse_deleteNote_success() {
        assertParseSuccess(parser, "3" + ITEM_TYPE_NOTE,
                new DeleteNoteCommand(TypicalIndexes.INDEX_THIRD_ITEM));
    }

    @Test
    public void parse_missingParameters_failure() {
        // Missing both
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

        // Missing type
        assertParseFailure(parser, "3", Item.MESSAGE_CONSTRAINTS);

        // Missing index
        assertParseFailure(parser, ITEM_TYPE_RESUME, ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parser_invalidIndex_failure() {
        assertParseFailure(parser, "abc " + ITEM_TYPE_RESUME, ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parser_invalidType_failure() {
        assertParseFailure(parser, "3 " + ITEM_TYPE_INVALID, Item.MESSAGE_INVALID_ITEM_TYPE);
    }
}
