package seedu.address.model.task;

public enum RecurType {
    DAILY {
        @Override 
        public long getInterval() {
            // return 1000l*60*60*24;
            return 15000l; // for testing
        }
    },
    WEEKLY {
        @Override 
        public long getInterval() {
            return 1000l*60*60*24*7;
        }
    };

    public abstract long getInterval();
}