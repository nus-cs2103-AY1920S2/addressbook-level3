package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.bluetooth.CommandList;

public class HelpCard extends UiPart<Region> {
    private static final String FXML="HelpCard.fxml";

    public final CommandList commandList;

    @javafx.fxml.FXML
    private Label commandType;

    @FXML
    private Label instructions;

    public HelpCard(CommandList commandList, int displayedIndex){
        super(FXML);
        this.commandList = commandList;

        commandType.setText(commandList.getCommandType());
        instructions.setText(commandList.getInstructions());
    }

    @Override
    public boolean equals(Object other){return true;}
}
