package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.field.Cap;
import seedu.address.model.item.field.Description;
import seedu.address.model.item.field.DisplayPicture;
import seedu.address.model.item.field.Email;
import seedu.address.model.item.field.Github;
import seedu.address.model.item.field.Level;
import seedu.address.model.item.field.Major;
import seedu.address.model.item.field.Name;
import seedu.address.model.item.field.Phone;
import seedu.address.model.item.field.Role;
import seedu.address.model.item.field.Time;
import seedu.address.model.item.field.University;
import seedu.address.model.item.field.Website;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.ItemUtil;
import seedu.address.testutil.ItemIndicesBuilder;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_WEBSITE = "john.a";
    private static final String INVALID_TIME = "13-2019";
    private static final String INVALID_FILE_PATH_1 = "notjpeg.gif";
    private static final String INVALID_FILE_PATH_2 = "/Users/Pictures/someone.gif";
    private static final String INVALID_FILE_PATH_3 = "/Users/Pictures/someone.pdf";
    private static final String INVALID_GITHUB_1 = "-starthyphen";
    private static final String INVALID_GITHUB_2 = "double--hyphen";
    private static final String INVALID_UNIVERSITY = "here is a very long university name that should exceed 50 chars";
    private static final String INVALID_MAJOR = "computer@science";
    private static final String INVALID_CAP_1 = "6.1";
    private static final String INVALID_CAP_2 = "@@";
    private static final String INVALID_CAP_3 = "4.0 abc";
    private static final String INVALID_CAP_4 = "5.0 4.0";
    private static final String INVALID_ROLE = "ComputerScientist@BestPlace";


    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_WEBSITE_1 = "www.github.com/asd";
    private static final String VALID_WEBSITE_2 = "https://www.facebook.com";
    private static final String VALID_DESCRIPTION = "This is a description of the item.";
    private static final String VALID_TIME_1 = "06-2020";
    private static final String VALID_TIME_2 = "08-2018";
    private static final String VALID_FILE_PATH_PNG = "/Users/Pictures/someone.png";
    private static final String VALID_FILE_PATH_JPG = "/Users/Pictures/someone.jpg";
    private static final String VALID_GITHUB = "validgithub";
    private static final String VALID_UNIVERSITY = "National University of Singapore";
    private static final String VALID_MAJOR = "computer science";
    private static final String VALID_CAP_1 = "4 5";
    private static final String VALID_CAP_2 = "4.5394 5.0";
    private static final String VALID_ROLE = "Frontend Engineer";


    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_ITEM, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_ITEM, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseLevel_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseLevel((String) null));
    }

    @Test
    public void parseLevel_invalidValue_throwsNullPointerException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseLevel(VALID_NAME));
        assertThrows(ParseException.class, () -> ParserUtil.parseLevel(INVALID_NAME));
        assertThrows(ParseException.class, () -> ParserUtil.parseLevel("b asic"));
    }

    @Test
    public void parseLevel_validValueWithoutWhitespace_returnsLevel() throws Exception {
        assertEquals(Level.BASIC, ParserUtil.parseLevel("basic"));
        assertEquals(Level.INTERMEDIATE, ParserUtil.parseLevel("intermediate"));
        assertEquals(Level.ADVANCED, ParserUtil.parseLevel("advanced"));
    }

    @Test
    public void parseLevel_validValueWithWhitespace_returnsLevel() throws Exception {
        assertEquals(Level.BASIC, ParserUtil.parseLevel(WHITESPACE + "basic" + WHITESPACE));
        assertEquals(Level.INTERMEDIATE, ParserUtil.parseLevel(WHITESPACE + "intermediate" + WHITESPACE));
        assertEquals(Level.ADVANCED, ParserUtil.parseLevel(WHITESPACE + "advanced" + WHITESPACE));
    }

    @Test
    public void parseLevel_validValueWithCases_returnsLevel() throws Exception {
        assertEquals(Level.BASIC, ParserUtil.parseLevel("bAsIc"));
        assertEquals(Level.INTERMEDIATE, ParserUtil.parseLevel("Intermediate"));
        assertEquals(Level.ADVANCED, ParserUtil.parseLevel("adVanCeD"));
    }

    @Test
    public void parseWebsite_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseWebsite((String) null));
    }

    @Test
    public void parseWebsite_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseWebsite(INVALID_WEBSITE));
    }

    @Test
    public void parseWebsite_validValueWithoutWhitespace_returnsWebsite() throws Exception {
        Website expectedWebsite1 = new Website(VALID_WEBSITE_1);
        Website expectedWebsite2 = new Website(VALID_WEBSITE_2);

        assertEquals(expectedWebsite1, ParserUtil.parseWebsite(VALID_WEBSITE_1));
        assertEquals(expectedWebsite2, ParserUtil.parseWebsite(VALID_WEBSITE_2));
    }

    @Test
    public void parseWebsite_validValueWithWhitespace_returnsTrimmedWebsite() throws Exception {
        String websiteWithWhitespace1 = WHITESPACE + VALID_WEBSITE_1 + WHITESPACE;
        String websiteWithWhitespace2 = WHITESPACE + VALID_WEBSITE_2 + WHITESPACE;
        Website expectedWebsite1 = new Website(VALID_WEBSITE_1);
        Website expectedWebsite2 = new Website(VALID_WEBSITE_2);

        assertEquals(expectedWebsite1, ParserUtil.parseWebsite(websiteWithWhitespace1));
        assertEquals(expectedWebsite2, ParserUtil.parseWebsite(websiteWithWhitespace2));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseDescription_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDescription((String) null));
    }

    @Test
    public void parseDescription_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDescription(""));
    }

    @Test
    public void parseDescription_validValueWithoutWhitespace_returnsDescription() throws Exception {
        assertEquals(new Description(VALID_DESCRIPTION), ParserUtil.parseDescription(VALID_DESCRIPTION));
    }

    @Test
    public void parseDescription_validValueWithWhitespace_returnsTrimmedDescription() throws Exception {
        String descriptionWithWhitespace = WHITESPACE + VALID_DESCRIPTION + WHITESPACE;
        assertEquals(new Description(VALID_DESCRIPTION), ParserUtil.parseDescription(descriptionWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }
    @Test
    public void parseItemType_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseItemType((String) null));
    }

    @Test
    public void parseItemType_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseItemType(INVALID_PHONE));
        assertThrows(ParseException.class, () -> ParserUtil.parseItemType("some bad item type"));
    }

    @Test
    public void parseItemType_validValueWithoutWhitespace_returnsItemType() throws Exception {
        ParserUtil.parseItemType(ItemUtil.INTERNSHIP_ALIAS);
        assertEquals(ItemUtil.INTERNSHIP_ALIAS, ParserUtil.parseItemType(ItemUtil.INTERNSHIP_ALIAS));
        assertEquals(ItemUtil.SKILL_ALIAS, ParserUtil.parseItemType(ItemUtil.SKILL_ALIAS));
        assertEquals(ItemUtil.PROJECT_ALIAS, ParserUtil.parseItemType(ItemUtil.PROJECT_ALIAS));
    }

    @Test
    public void parseItemType_validValueWithWhitespace_returnsTrimmedItemType() throws Exception {
        assertEquals(ItemUtil.RESUME_ALIAS, ParserUtil.parseItemType(WHITESPACE + ItemUtil.RESUME_ALIAS + WHITESPACE));
        assertEquals(ItemUtil.NOTE_ALIAS, ParserUtil.parseItemType(WHITESPACE + ItemUtil.NOTE_ALIAS + WHITESPACE));
    }

    @Test
    public void parseTime_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTime((String) null));
    }

    @Test
    public void parseTime_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTime(INVALID_TIME));
    }

    @Test
    public void parseTime_validValueWithoutWhitespace_returnsTime() throws Exception {
        Time expectedTime1 = new Time(VALID_TIME_1);
        Time expectedTime2 = new Time(VALID_TIME_2);

        assertEquals(expectedTime1, ParserUtil.parseTime(VALID_TIME_1));
        assertEquals(expectedTime2, ParserUtil.parseTime(VALID_TIME_2));
    }

    @Test
    public void parseTime_validValueWithWhitespace_returnsTrimmedTime() throws Exception {
        String websiteWithWhitespace1 = WHITESPACE + VALID_TIME_1 + WHITESPACE;
        String websiteWithWhitespace2 = WHITESPACE + VALID_TIME_2 + WHITESPACE;
        Time expectedTime1 = new Time(VALID_TIME_1);
        Time expectedTime2 = new Time(VALID_TIME_2);

        assertEquals(expectedTime1, ParserUtil.parseTime(websiteWithWhitespace1));
        assertEquals(expectedTime2, ParserUtil.parseTime(websiteWithWhitespace2));
    }

    @Test
    public void parseReditItemIndices_null_returnOptionalEmpty() throws Exception {
        assertEquals(ItemIndicesBuilder.empty(), ParserUtil.parseReditItemIndices(null));
    }

    @Test
    public void parseReditItemIndices_emptyString_returnOptionalOfEmptyArray() throws Exception {
        assertEquals(new ItemIndicesBuilder().build(), ParserUtil.parseReditItemIndices(""));
    }

    @Test
    public void parseReditItemIndices_invalidIndices_returnOptionalOfIndices() throws Exception {
        assertThrows(ParseException.class, () -> ParserUtil.parseReditItemIndices("2 a"));
        assertThrows(ParseException.class, () -> ParserUtil.parseReditItemIndices("a"));
        assertThrows(ParseException.class, () -> ParserUtil.parseReditItemIndices("0"));
        assertThrows(ParseException.class, () -> ParserUtil.parseReditItemIndices("-1"));
    }

    @Test
    public void parseReditItemIndices_validIndicesWithoutWhitespace_returnOptionalOfIndices() throws Exception {
        ItemIndicesBuilder expectedIndices1 = new ItemIndicesBuilder().add(2).add(1).add(3).add(5);
        assertEquals(expectedIndices1.build(), ParserUtil.parseReditItemIndices("2 1 3 5"));

        ItemIndicesBuilder expectedIndices2 = new ItemIndicesBuilder().add(2);
        assertEquals(expectedIndices2.build(), ParserUtil.parseReditItemIndices("2"));
    }


    @Test
    public void parseDisplayPicture_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDisplayPicture((String) null));
    }

    @Test
    public void parseDisplayPicture_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDisplayPicture(INVALID_FILE_PATH_1));
        assertThrows(ParseException.class, () -> ParserUtil.parseDisplayPicture(INVALID_FILE_PATH_2));
        assertThrows(ParseException.class, () -> ParserUtil.parseDisplayPicture(INVALID_FILE_PATH_3));
    }

    @Test
    public void parseDisplayPicture_validValueWithoutWhitespace_returnsDisplayPicture() throws Exception {
        DisplayPicture expectedDisplayPictureJpg = new DisplayPicture(VALID_FILE_PATH_JPG);
        assertEquals(expectedDisplayPictureJpg, ParserUtil.parseDisplayPicture(VALID_FILE_PATH_JPG));

        DisplayPicture expectedDisplayPicturePng = new DisplayPicture(VALID_FILE_PATH_PNG);
        assertEquals(expectedDisplayPicturePng, ParserUtil.parseDisplayPicture(VALID_FILE_PATH_PNG));
    }

    @Test
    public void parseDisplayPicture_validValueWithWhitespace_returnsTrimmedDisplayPicture() throws Exception {
        String pathWithWhitespaceJpg = WHITESPACE + VALID_FILE_PATH_JPG + WHITESPACE;
        DisplayPicture expectedDisplayPictureJpg = new DisplayPicture(VALID_FILE_PATH_JPG);
        assertEquals(expectedDisplayPictureJpg, ParserUtil.parseDisplayPicture(pathWithWhitespaceJpg));

        String pathWithWhitespacePng = WHITESPACE + VALID_FILE_PATH_PNG + WHITESPACE;
        DisplayPicture expectedDisplayPicturePng = new DisplayPicture(VALID_FILE_PATH_PNG);
        assertEquals(expectedDisplayPicturePng, ParserUtil.parseDisplayPicture(pathWithWhitespacePng));
    }

    @Test
    public void parseGithub_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseGithub((String) null));
    }

    @Test
    public void parseGithub_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseGithub(INVALID_GITHUB_1));
        assertThrows(ParseException.class, () -> ParserUtil.parseGithub(INVALID_GITHUB_2));
    }

    @Test
    public void parseGithub_validValueWithoutWhitespace_returnsGithub() throws Exception {
        Github expectedGithub = new Github(VALID_GITHUB);
        assertEquals(expectedGithub, ParserUtil.parseGithub(VALID_GITHUB));
    }

    @Test
    public void parseGithub_validValueWithWhitespace_returnsTrimmedGithub() throws Exception {
        String githubWithWhitespace = WHITESPACE + VALID_GITHUB + WHITESPACE;
        Github expectedGithub = new Github(VALID_GITHUB);
        assertEquals(expectedGithub, ParserUtil.parseGithub(githubWithWhitespace));
    }

    @Test
    public void parseUniversity_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseUniversity((String) null));
    }

    @Test
    public void parseUniversity_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseUniversity(INVALID_UNIVERSITY));
    }

    @Test
    public void parseUniversity_validValueWithoutWhitespace_returnsUniversity() throws Exception {
        assertEquals(new University(VALID_UNIVERSITY), ParserUtil.parseUniversity(VALID_UNIVERSITY));
    }

    @Test
    public void parseUniversity_validValueWithWhitespace_returnsTrimmedUniversity() throws Exception {
        String universityWithWhitespace = WHITESPACE + VALID_UNIVERSITY + WHITESPACE;
        assertEquals(new University(VALID_UNIVERSITY), ParserUtil.parseUniversity(universityWithWhitespace));
    }

    @Test
    public void parseMajor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMajor((String) null));
    }

    @Test
    public void parseMajor_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMajor(INVALID_MAJOR));
    }

    @Test
    public void parseMajor_validValueWithoutWhitespace_returnsMajor() throws Exception {
        assertEquals(new Major(VALID_MAJOR), ParserUtil.parseMajor(VALID_MAJOR));
    }

    @Test
    public void parseMajor_validValueWithWhitespace_returnsTrimmedMajor() throws Exception {
        String majorWithWhitespace = WHITESPACE + VALID_MAJOR + WHITESPACE;
        assertEquals(new Major(VALID_MAJOR), ParserUtil.parseMajor(majorWithWhitespace));
    }

    @Test
    public void parseCap_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCap((String) null));
    }

    @Test
    public void parseCap_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCap(INVALID_CAP_1));
        assertThrows(ParseException.class, () -> ParserUtil.parseCap(INVALID_CAP_2));
        assertThrows(ParseException.class, () -> ParserUtil.parseCap(INVALID_CAP_3));
        assertThrows(ParseException.class, () -> ParserUtil.parseCap(INVALID_CAP_4));
    }

    @Test
    public void parseCap_validValueWithoutTrailingWhitespace_returnsCap() throws Exception {
        Cap expectedCap1 = new Cap("4 5");
        assertEquals(expectedCap1 , ParserUtil.parseCap(VALID_CAP_1));
        Cap expectedCap2 = new Cap("4.54 5.0");
        assertEquals(expectedCap2 , ParserUtil.parseCap(VALID_CAP_2));
    }

    @Test
    public void parseCap_validValueWithTrailingWhitespace_returnsTrimmedCap() throws Exception {
        String capWithWhitespace1 = WHITESPACE + VALID_CAP_1 + WHITESPACE;
        Cap expectedCap1 = new Cap("4 5");
        assertEquals(expectedCap1, ParserUtil.parseCap(capWithWhitespace1));

        String capWithWhitespace2 = WHITESPACE + VALID_CAP_2 + WHITESPACE;
        Cap expectedCap2 = new Cap("4.54 5.0");
        assertEquals(expectedCap2, ParserUtil.parseCap(capWithWhitespace2));
    }

    @Test
    public void parseRole_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRole((String) null));
    }

    @Test
    public void parseRole_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRole(INVALID_ROLE));
    }

    @Test
    public void parseRole_validValueWithoutWhitespace_returnsRole() throws Exception {
        assertEquals(new Role(VALID_ROLE), ParserUtil.parseRole(VALID_ROLE));
    }

    @Test
    public void parseRole_validValueWithWhitespace_returnsTrimmedRole() throws Exception {
        String roleWithWhitespace = WHITESPACE + VALID_ROLE + WHITESPACE;
        assertEquals(new Role(VALID_ROLE), ParserUtil.parseRole(roleWithWhitespace));
    }

    @Test
    public void parseReverse_null_returnsFalse() throws Exception {
        assertFalse(ParserUtil.parseReverse(null));
    }

    @Test
    public void parseReverse_false_returnsFalse() throws Exception {
        assertFalse(ParserUtil.parseReverse("false"));
        assertFalse(ParserUtil.parseReverse("fAlSe"));
        assertFalse(ParserUtil.parseReverse(WHITESPACE + "false" + WHITESPACE));
        assertFalse(ParserUtil.parseReverse(WHITESPACE + "fAlSe" + WHITESPACE));
    }

    @Test
    public void parseReverse_true_returnsTrue() throws Exception {
        assertTrue(ParserUtil.parseReverse("true"));
        assertTrue(ParserUtil.parseReverse("tRuE"));
        assertTrue(ParserUtil.parseReverse(WHITESPACE + "true" + WHITESPACE));
        assertTrue(ParserUtil.parseReverse(WHITESPACE + "tRuE" + WHITESPACE));
    }

    @Test
    public void parseReverse_invalidValue_throwsParseException() throws Exception {
        assertThrows(ParseException.class, () -> ParserUtil.parseReverse("t"));
        assertThrows(ParseException.class, () -> ParserUtil.parseReverse("f"));
        assertThrows(ParseException.class, () -> ParserUtil.parseReverse("a dlksj"));
    }
}
