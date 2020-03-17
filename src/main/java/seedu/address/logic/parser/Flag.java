package seedu.address.logic.parser;

/**
 * A flag that indicate additional command to the system to work in certain way in an arguments string.
 * E.g. '-f' in clear command, force the system clear the command without notify the user.
 */
public class Flag {
    private final String flag;

    public Flag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public String toString() {
        return getFlag();
    }

    @Override
    public int hashCode() {
        return flag == null ? 0 : flag.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Flag)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Flag otherFlag = (Flag) obj;
        return otherFlag.getFlag().equals(getFlag());
    }
}
