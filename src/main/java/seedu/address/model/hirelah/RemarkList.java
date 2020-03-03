package seedu.address.model.hirelah;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * A list of remarks that are associated  with a particular interview session of an {@code Interviewee}.
 * Some remarks are associated with a question, as it is part of the answer to a question, and some remarks do not.
 * Both are able to be inserted to this remark list. Once a remark has been added, it cannot be removed from the list.
 * An interview session always have a start remark and an end remark, thus it minimally has 2 {@code Remark} objects.
 *
 * Supports a minimal set of list operations.
 *
 * @see Remark
 */
public class RemarkList {
    private final ArrayList<Remark> remarks = new ArrayList<>();
    private final Map<Question, Instant> questionToRemarkMap = new HashMap<>();

    /**
     * Retrieves the remark list encapsulated by {@code RemarkList}.
     */
    public ArrayList<Remark> getRemarks() {
        return remarks;
    }

    /**
     * Adds a remark to the list.
     *
     * @param toAdd The remark to be added to the list.
     */
    public void add(Remark toAdd) {
        requireNonNull(toAdd);
        if (!questionToRemarkMap.containsKey(toAdd.getQuestion())) {
            questionToRemarkMap.put(toAdd.getQuestion(), toAdd.getTime());
        }
        remarks.add(toAdd);
    }

    /**
     * Retrieves the Instant of the first remark of {@code RemarkList}
     * which is the starting time of the interview.
     *
     * @return The Instant when the first remark was created.
     */
    public Instant getStartRemarkTime() {
        assert (remarks.size() > 0);
        return remarks.get(0).getTime();
    }

    /**
     * Retrieves the number of {@code Remark}s in {@code RemarkList}.
     *
     * @return The number of {@code Remark}s in this {@code RemarkList}.
     */
    private int getRemarkListSize() {
        return this.remarks.size();
    }

    /**
     * Retrieves the Instant of the last remark of {@code RemarkList}
     * which is the ending time of the interview.
     *
     * @return The Instant when the last remark was created.
     */
    public Instant getLastRemarkTime() {
        assert (remarks.size() > 1);
        return remarks.get(getRemarkListSize()-1).getTime();
    }

    /**
     * Retrieves the duration of the interview.
     *
     * @return The duration of the interview session in milliseconds.
     */
    public long getInterviewDurationInMs() {
        long interviewDuration = Duration.between(getLastRemarkTime(), getStartRemarkTime()).getSeconds() * 1000;
        return interviewDuration;
    }

    /**
     * Checks if a time is within the duration of the interview.
     * @param timeMs Time that is checked.
     * @return Whether the time is in the range of the interview duration.
     */
    public boolean isTimeInValidRange(long timeMs) {
        return timeMs <= this.getInterviewDurationInMs();
    }

    /**
     * Retrieves {@code Remark} that are created nearest to the given time.
     *
     * @param timeMs Time queried.
     * @return The {@code Remark} that are created nearest to the given time.
     */
    public Remark getRemarkAtTime(long timeMs) {
        Remark nearestRemark = remarks.get(0);
        Duration fromStart = Duration.between(nearestRemark.getTime(), getStartRemarkTime());
        long timeDeviation = timeMs - fromStart.getSeconds();
        for (Remark remark : remarks) {
            if (remark != remarks.get(0) && (timeMs - Duration.between(remark.getTime(), getStartRemarkTime()).toSeconds()) < timeDeviation) {
                nearestRemark = remark;
            }
        }
        return nearestRemark;
    }

    /**
     * Checks if a question is answered during the interview
     * by checking whether there is a {@code Remark} that is associated with it.
     *
     * @param question Question that is checked against.
     * @return Whether the question is has {@code Remark}
     * associated with it as an answer.
     */
    public boolean isQuestionAnswered(Question question) {
       return questionToRemarkMap.containsKey(question);
    }

    /**
     * Retrieves {@code Instant} when this {@code Question}
     * was first given association to a {@code Remark} as its answer.
     *
     * @param question Question that are queried.
     * @return The {@code Instant} when this question
     * was first associated with a {@code Remark}.
     */
    public Instant getTimeOfQuestionInMs (Question question) {
        return questionToRemarkMap.get(question);
    }

}
