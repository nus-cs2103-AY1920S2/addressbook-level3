package seedu.address.model.hirelah;

import seedu.address.commons.exceptions.IllegalValueException;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

import static java.util.Objects.requireNonNull;
//TREE MAP TO QUERY BY TIME
/**
 * A list of remarks that are associated  with a particular interview session of an {@code Interviewee}.
 * Some remarks are associated with a question, as it is part of the answer to a question, and some remarks do not.
 * Both are able to be inserted to this remark list. Once a remark has been added, it cannot be removed from the list.
 * !!!!!!!!I DO NOT MAKE IT COMPULSORY FOR THE ANSWER TO ONLY BE 1 TIME, can be like null, q1, q1, null, q1, q1.!!!!!!!!!
 * REMARKS AT START AND END OF QUESTION
 *
 * Supports a minimal set of list operations.
 *
 * @see Remark
 */
public class RemarkList {
    private final ArrayList<Remark> remarks = new ArrayList<>();

    public ArrayList<Remark> getRemarks() {
        return remarks;
    }

    /**
     * Adds a remark to the list.
     */
    public void add(Remark toAdd) {
        requireNonNull(toAdd);
        remarks.add(toAdd);
    }

    public Instant getStartRemarkTime() {
        return remarks.get(0).getTime();
    }

    public Remark getRemarkAtTime(long time) {
        Instant getRemarkTime =  remarks.get(1).getTime();
        Duration fromStart = Duration.between(getRemarkTime, getStartRemarkTime());
        long timeDeviation = fromStart.getSeconds();
        Remark nearestRemark = remarks.get(0);
        for (Remark remark:remarks) {
            if (remark!=remarks.get(0) && Duration.between(remark.getTime(), getStartRemarkTime()).toSeconds() < timeDeviation) {
                nearestRemark = remark;
            }
        }
        return nearestRemark;
    }

    public Remark getRemarkAtQuestion(Question question) throws IllegalValueException {
        for (Remark remark : remarks) {
            if (remark.getQuestion().equals(question)) {
                return remark;
            }
        }
        throw new IllegalValueException("There is no remarks for this question");
    }

    public long getTimeOfQuestion(Question question) throws IllegalValueException {
        Remark startOfQuestionRemark = getRemarkAtQuestion(question);
        return Duration.between(startOfQuestionRemark.getTime(), getStartRemarkTime()).toSeconds();
    }

    private int getRemarkListSize() {
        return this.remarks.size();
    }

    public long getLastRemarkTime() {
        return Duration.between(remarks.get(getRemarkListSize()-1).getTime(), getStartRemarkTime()).toSeconds();
    }

    /**
     * Returns true as all remark is considered valid.
     */
    public boolean isValid(Remark remark) {
        return true;
    }

}
