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

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.find.FindCommand;
import seedu.address.logic.commands.find.FindInternshipCommand;
import seedu.address.logic.commands.find.FindNoteCommand;
import seedu.address.logic.commands.find.FindProjectCommand;
import seedu.address.logic.commands.find.FindResumeCommand;
import seedu.address.logic.commands.find.FindSkillCommand;
import seedu.address.model.item.Item;
import seedu.address.model.item.field.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_failure() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPreamble_failure() {
        assertParseFailure(parser, ITEM_TYPE_RESUME,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingType_failure() {
        assertParseFailure(parser, "keyword", Item.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidType_failure() {
        assertParseFailure(parser, "keyword " + ITEM_TYPE_INVALID, Item.MESSAGE_INVALID_ITEM_TYPE);
    }

    @Test
    public void parse_findInternship_success() {
        // no leading and trailing whitespaces
        FindInternshipCommand expectedCommand =
                new FindInternshipCommand(new NameContainsKeywordsPredicate(Arrays.asList("Google", "PayPal")));
        assertParseSuccess(parser, "Google PayPal " + ITEM_TYPE_INTERNSHIP, expectedCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Google \n \t PayPal  \t " + ITEM_TYPE_INTERNSHIP, expectedCommand);
    }

    @Test
    public void parse_findProject_success() {
        // no leading and trailing whitespaces
        FindProjectCommand expectedCommand =
                new FindProjectCommand(new NameContainsKeywordsPredicate(Arrays.asList("Orbital", "Duke")));
        assertParseSuccess(parser, "Orbital Duke " + ITEM_TYPE_PROJECT, expectedCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Orbital \n \t Duke  \t " + ITEM_TYPE_PROJECT, expectedCommand);
    }

    @Test
    public void parse_findSkill_success() {
        // no leading and trailing whitespaces
        FindSkillCommand expectedCommand =
                new FindSkillCommand(new NameContainsKeywordsPredicate(Arrays.asList("React", "Git")));
        assertParseSuccess(parser, "React Git " + ITEM_TYPE_SKILL, expectedCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n React \n \t Git  \t " + ITEM_TYPE_SKILL, expectedCommand);
    }

    @Test
    public void parse_findResume_success() {
        // no leading and trailing whitespaces
        FindResumeCommand expectedCommand =
                new FindResumeCommand(new NameContainsKeywordsPredicate(Arrays.asList("Software", "Engineering")));
        assertParseSuccess(parser, "Software Engineering " + ITEM_TYPE_RESUME, expectedCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Software \n \t Engineering  \t " + ITEM_TYPE_RESUME, expectedCommand);
    }

    @Test
    public void parse_findNote_success() {
        // no leading and trailing whitespaces
        FindNoteCommand expectedCommand =
                new FindNoteCommand(new NameContainsKeywordsPredicate(Arrays.asList("homework", "CS2103")));
        assertParseSuccess(parser, "homework CS2103 " + ITEM_TYPE_NOTE, expectedCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n homework \n \t CS2103  \t " + ITEM_TYPE_NOTE, expectedCommand);
    }

}
