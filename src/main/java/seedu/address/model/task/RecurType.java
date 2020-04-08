package seedu.address.model.task;

public enum RecurType {
    DAILY {
        @Override 
        public long getInterval() {
            return 1000l*60*60*24;
            // return 60000l; // for testing
        }

        @Override
        public long getDayInterval() {
            return 1;
        }
    },
    WEEKLY {
        @Override 
        public long getInterval() {
            return 1000l*60*60*24*7;
        }

        @Override
        public long getDayInterval() {
            return 7;
        }
    };

    public abstract long getDayInterval();
    public abstract long getInterval();
}