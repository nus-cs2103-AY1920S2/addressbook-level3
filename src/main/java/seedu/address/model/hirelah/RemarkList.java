package seedu.address.model.hirelah;

import static java.util.Objects.requireNonNull;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A list of remarks that are associated  with a particular interview session of an {@code Interviewee}.
 * Some remarks are associated with a question, as it is part of the answer to a question, and some remarks do not.
 * Both are able to be inserted to this remark list. Once a remark has been added, it cannot be removed from the list.
 * An interview session always have a start remark and an end remark, thus it minimally has 2 {@code Remark} objects.
 * It is assumed that there are no 2 Remarks at any particular time.
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
        if (toAdd.getQuestion() != null) {
            if (!questionToRemarkMap.containsKey(toAdd.getQuestion())) {
                questionToRemarkMap.put(toAdd.getQuestion(), toAdd.getTime());
            }
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
        System.out.println(remarks.get(0).getTime().toString());
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
        System.out.println(remarks.get(getRemarkListSize() - 1).getTime().toString());
        return remarks.get(getRemarkListSize() - 1).getTime();
    }

    /**
     * Retrieves the duration of the interview.
     *
     * @return The duration of the interview session in milliseconds.
     */
    public long getInterviewDurationInMs() {
        long interviewDuration = Math.abs((Duration.between(getStartRemarkTime(), getLastRemarkTime()).toMillis()));
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
        long minDeviation = Math.abs(timeMs
                - Math.abs(Duration.between(nearestRemark.getTime(), getStartRemarkTime()).toMillis()));

        for (Remark remark : remarks) {
            long currentDeviation = Math.abs((
                    timeMs - Math.abs((Duration.between(getStartRemarkTime(), remark.getTime()).toMillis())))
            );
            if (remark != remarks.get(0) && (currentDeviation < minDeviation)) {
                minDeviation = currentDeviation;
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
     * @return The {@code Remark} that was
     * was first associated with this {@code Question}.
     */
    public Remark getRemarkOfQuestion(Question question) {
        Instant startOfQuestion = questionToRemarkMap.get(question);
        return getRemarkAtTime(Math.abs(Duration.between(getStartRemarkTime(), startOfQuestion).toMillis()));
    }
}
