package seedu.address.model.bluetooth;

import java.util.List;

public class CommandList {
    private String instructions;
    private String commandType;

    public CommandList(String CommandType, String instructions) {
        this.commandType = CommandType;
        this.instructions = instructions;
    }

    public String getInstructions() {
        return this.instructions;
    }

    public String getCommandType() {return this.commandType;}
}
