package seedu.address.model.bluetooth;

import java.util.List;

public class CommandList {
    private List<String> CommandStringList;
    private String CommandType;

    public CommandList(String CommandType, List<String> CommandStringList) {
        this.CommandType = CommandType;
        this.CommandStringList = CommandStringList;
    }

    public List<String> getCommandStringList() {
        return this.CommandStringList;
    }

    public String getCommandType() {return this.CommandType;}

}
