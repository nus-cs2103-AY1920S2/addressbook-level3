package seedu.address.model.bluetooth;

import seedu.address.model.bluetooth.formatter.DateFormatterInterface;
import seedu.address.model.bluetooth.formatter.SimpleDateFormatter;

public class Timestamps {
    private Long timestamp;
    private DateFormatterInterface formatter;

    /**
     * Creates a timestamp class initialized to an epoch timing
     * Epoch timings are chosen by default and conversion to other timings / strings will use this value as ground truth
     *
     * @param epochTs   Timestamp in unix epoch format
     */
    public Timestamps(Long epochTs) {
        this.timestamp = epochTs;
        this.formatter = new SimpleDateFormatter();
    }

    /**
     * Defines if the given timestamp is greater or equal to a relative value
     *
     * @param epochStart   Lower limit value
     * @return              Is this timestamp greater or equal to the lower limit?
     */
    public Boolean isGreaterOrEq(int epochStart) {
        return this.timestamp >= epochStart;
    }

    /**
     * Defines if the given timestamp is smaller or equal to a relative value
     *
     * @param epochEnd      Upper limit value
     * @return              Is this timestamp smaller or equal than the upper limit?
     */
    public Boolean isSmallerOrEq(int epochEnd) {
        return this.timestamp <= epochEnd;
    }

    /**
     * Defines if the given timestamp is within a bounded window of timing
     *
     * @param epochStart    Start of the window
     * @param epochEnd      End of the window
     * @return              Is this timestamp bounded within the window?
     */
    public Boolean isBound(int epochStart, int epochEnd){
        return this.isGreaterOrEq(epochStart) && this.isSmallerOrEq(epochEnd);
    }

    /**
     * Makes the timestamp nice looking cause people hate numbers
     *
     * @return              Formatted timestamp string
     */
    public String display() {
        return this.formatter.format(this.timestamp);
    }
}
