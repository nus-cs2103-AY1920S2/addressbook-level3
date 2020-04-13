package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_NAME_GOOGLE;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_ROLE_FRONTEND;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_TAG_BACKEND;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_TAG_FRONTEND;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_TAG_JAVA;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_TAG_TECH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BACKEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRONTEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_JAVA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TECH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_ITEM;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.TagPullCommand;
import seedu.address.model.tag.Tag;

public class TagPullCommandParserTest {
    private TagPullCommandParser parser = new TagPullCommandParser();

    @Test
    public void parse_validWithOneTag_success() {
        Set<Tag> tagTech = new HashSet<>();
        tagTech.add(new Tag(VALID_TAG_TECH));

        assertParseSuccess(parser,
                "1" + PREFIXED_TAG_TECH,
                new TagPullCommand(INDEX_FIRST_ITEM, tagTech));

        // Try another tag
        Set<Tag> tagJava = new HashSet<>();
        tagJava.add(new Tag(VALID_TAG_JAVA));

        assertParseSuccess(parser,
                "4" + PREFIXED_TAG_JAVA,
                new TagPullCommand(INDEX_FOURTH_ITEM, tagJava));
    }

    @Test
    public void parse_validWithMultipleTags_success() {
        Set<Tag> twoTags = new HashSet<>();
        twoTags.add(new Tag(VALID_TAG_JAVA));
        twoTags.add(new Tag(VALID_TAG_TECH));

        assertParseSuccess(parser,
                "1" + PREFIXED_TAG_JAVA + PREFIXED_TAG_TECH,
                new TagPullCommand(INDEX_FIRST_ITEM, twoTags));

        Set<Tag> manyTags = new HashSet<>();
        manyTags.add(new Tag(VALID_TAG_JAVA));
        manyTags.add(new Tag(VALID_TAG_TECH));
        manyTags.add(new Tag(VALID_TAG_FRONTEND));
        manyTags.add(new Tag(VALID_TAG_BACKEND));

        assertParseSuccess(parser,
                "1" + PREFIXED_TAG_JAVA + PREFIXED_TAG_TECH + PREFIXED_TAG_FRONTEND + PREFIXED_TAG_BACKEND,
                new TagPullCommand(INDEX_FIRST_ITEM, manyTags));
    }

    @Test
    public void parse_invalidCommand_throwsParseException() {
        // just index, without tags
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TagPullCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TagPullCommand.MESSAGE_USAGE));

        // other prefixes
        assertParseFailure(parser, "1" + PREFIXED_NAME_GOOGLE, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TagPullCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-1" + PREFIXED_ROLE_FRONTEND, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TagPullCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidResumeIndex_throwsParseException() {
        // with tags
        assertParseFailure(parser, "a" + PREFIXED_TAG_FRONTEND, ParserUtil.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "-1" + PREFIXED_TAG_JAVA, ParserUtil.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "0" + PREFIXED_TAG_BACKEND, ParserUtil.MESSAGE_INVALID_INDEX);

        // with multiple tags
        assertParseFailure(parser, "a" + PREFIXED_TAG_FRONTEND + PREFIXED_TAG_JAVA,
                ParserUtil.MESSAGE_INVALID_INDEX);
    }
}
