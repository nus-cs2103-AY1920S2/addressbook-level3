package seedu.address.model.hirelah;

import java.io.File;
import java.util.Map;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.hirelah.exceptions.IllegalActionException;

/**
 * A class to represent Interviewee candidates in a Session. They have
 * - a unique ID per session.
 * - the candidate's full name.
 * - an optional, single, alias.
 * - an optional resume (a file object).
 * - an optional indicating if they have been interviewed, and containing the interview session data
 *   if they have been interviewed.
 */
public class Interviewee {

    public static final String MESSAGE_CONSTRAINTS =
            "Names and aliases should not be numbers, eg. 12345, and should not be blank";

    private String fullName;
    private final int id;
    private Optional<String> alias = Optional.empty();
    private Optional<File> resume = Optional.empty();
    private Optional<InterviewSession> interview = Optional.empty();

    /**
     * Creates a new Interviewee in the system which starts with no alias, no resume and
     * being not yet interviewed.
     *
     * @param fullName The interviewee's full name.
     * @param id The interviewee's unique interviewee id.
     */
    public Interviewee(String fullName, int id) throws IllegalValueException {
        checkValidIdentifier(fullName);

        this.fullName = fullName;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    /**
     * Sets the full name. Allows renaming of Interviewees.
     *
     * @param fullName the new name of the interviewee
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Optional<String> getAlias() {
        return alias;
    }

    /**
     * Gives an alias to the interviewee object if the interviewee has no alias presently.
     *
     * @param alias the alias referring to this interviewee.
     */
    public void giveAlias(String alias) throws IllegalValueException, IllegalActionException {
        checkValidIdentifier(alias);
        if (this.alias.isPresent()) {
            throw new IllegalActionException("Interviewee already has an alias");
        }
        this.alias = Optional.of(alias);
    }

    public Optional<File> getResume() {
        return resume;
    }

    public void setResume(File resume) {
        this.resume = Optional.ofNullable(resume);
    }

    public Optional<InterviewSession> getInterview() {
        return interview;
    }

    /**
     * Gets the score of an interviewee that has already been interviewed.
     * It is guaranteed that this method is only called on Interviewees that have been interviewed.
     *
     * @param attribute the Attribute to retrieve the score for.
     * @return the score of the given Attribute.
     * @throws java.util.NoSuchElementException if Interviewee has not been interviewed.
     *                                          However, it is guaranteed not to occur at runtime
     *                                          as the IntervieweeList is filtered before sorting by score.
     */
    public double getScore(Attribute attribute) {
        return interview.get().getScores().get(attribute);
    }

    /**
     * Saves the interview session data, only if there is no previous interview session data, to prevent
     * overwriting the session data accidentally.
     *
     * @param transcript The transcript of remarks taken down during the interview.
     * @param scores the final attribute scores of the candidate.
     * @param audioRecording the .wav file with the audio recording of the interview.
     */
    public void recordInterview(Transcript transcript, Map<Attribute, Double> scores, File audioRecording) {
        assert this.interview.isEmpty(); // this method should only be called once
        this.interview = Optional.of(new InterviewSession(transcript, scores, audioRecording));
    }

    /**
     * Validates a string meant to be used as an alias or the full name to ensure it is
     * not a valid number, and is not the empty string. Any other string will be considered valid,
     * since there are names that contain punctuation, non-ascii characters and even numbers.
     *
     * @param identifier the alias or name to validate.
     * @throws IllegalValueException if identifier is empty, or is a valid number
     */
    private void checkValidIdentifier(String identifier) throws IllegalValueException {
        try {
            Integer.parseUnsignedInt(identifier);

            // If successfully parsed as an integer, reject the identifier
            throw new IllegalValueException(MESSAGE_CONSTRAINTS);
        } catch (NumberFormatException e) {

            // If identifier only contains whitespace or is an empty string, reject it
            if (identifier.isBlank()) {
                throw new IllegalValueException(MESSAGE_CONSTRAINTS);
            }
        }
    }
}
