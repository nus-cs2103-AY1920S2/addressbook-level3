package cardibuddy.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import cardibuddy.commons.core.index.Index;
import cardibuddy.commons.util.StringUtil;
import cardibuddy.logic.parser.exceptions.ParseException;
import cardibuddy.model.deck.Title;
import cardibuddy.model.flashcard.Answer;
import cardibuddy.model.flashcard.McqAnswer;
import cardibuddy.model.flashcard.Question;
import cardibuddy.model.flashcard.ShortAnswer;
import cardibuddy.model.flashcard.TfAnswer;
import cardibuddy.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String title} into a {@code Title}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code title} is invalid.
     */
    public static Title parseTitle(String title) throws ParseException {
        requireNonNull(title);
        String trimmedName = title.trim();
        if (!Title.isValidTitle(trimmedName) || title.isBlank()) {
            throw new ParseException(Title.MESSAGE_CONSTRAINTS);
        }
        return new Title(trimmedName);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String question} into a {@code Question}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code question} is invalid.
     */
    public static Question parseQuestion(String question) throws ParseException {
        requireNonNull(question);
        String trimmedQuestion = question.trim();
        if (!Question.isValidQuestion(trimmedQuestion)) {
            throw new ParseException(Question.MESSAGE_CONSTRAINTS);
        }
        return new Question(trimmedQuestion);
    }

    /**
     * Parses a {@code String answer} into a {@code Answer}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code answer} is invalid.
     */
    public static Answer parseAnswer(String answer) throws ParseException {
        requireNonNull(answer);
        String trimmedAnswer = answer.trim();
        if (Answer.isTrueFalseAnswer(answer)) {
            return new TfAnswer(answer);
        } else if (Answer.isMcqAnswer(answer)) {
            return new McqAnswer(answer);
        } else {
            Answer.isValidShortAnswer(trimmedAnswer);
            return new ShortAnswer(trimmedAnswer);
        }
    }
}
