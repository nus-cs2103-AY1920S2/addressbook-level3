package seedu.address.model.hirelah;

import java.time.Instant;

/**
 * Stores the remark message that are inserted by the interviewer
 * at any given time of the interview. It can be a part of an answer to
 * a particular question, or just a normal remark.
 */
public class Remark {
    private final Instant time;
    private final String message;
    private final Question question;

    /**
     * Constructs a {@code Remark} which is part of the answer
     * to a question of the interview.
     *
     * @param time The time when the remark was made.
     * @param message The remark message.
     * @param question The question associated with this remark as its answer.
     */
    Remark(Instant time, String message, Question question) {
        this.time = time;
        this.message = message;
        this.question = question;
    }

    /**
     * Constructs a {@code Remark} which is not part of the answer
     * to any question of the interview.
     *
     * @param time The time when the remark was made.
     * @param message The remark message.
     */
    Remark(Instant time, String message) {
        this.time = time;
        this.message = message;
        this.question = null;
    }

    public Instant getTime() {
        return time;
    }

    public Question getQuestion() {
        return question;
    }
}
