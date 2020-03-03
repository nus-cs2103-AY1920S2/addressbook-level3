package seedu.address.model.hirelah;

/**
 * TODO: Javadoc
 */
public class Interviewee {
    private static int uniqueIntervieweeId = 1; // increment every time new Interviewee created
    /*
    TODO:
     map attributes to scores
     fullname
     interviewee ID
     alias
     a file object (CV?)
     Transcript (contains audio recording, and a collection of remarks with some methods to query by time/qn number)

     */
    private String name;
    public Interviewee(String name) {
        this.name = name;
    }

    public double getScore(Attribute attribute) {
        return 1.0;
    }
}
