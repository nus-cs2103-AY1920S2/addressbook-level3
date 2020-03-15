package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.hirelah.Attribute;

/**
 * AddAttributeCommand describes the behavior when the
 * client wants to add an attribute to the list.
 */

public class AddAttributeCommand extends Command {
    public static final String COMMAND_WORD = "attribute";
    public static final String MESSAGE_DUPLICATE_ATTRIBUTE = "The attribute already exists.";
    public static final String MESSAGE_SUCCESS = "New attribute added: %1$s";
    public static final String MESSAGE_USAGE = "new " + COMMAND_WORD + ": Adds an attribute to the Attribute list. "
            + "Parameters: "
            + "NAME "
            + "Example: new " + COMMAND_WORD + " leadership";


    private final String toAdd;

    /**
     * Creates an AddAttributeCommand to add the specified {@code Attribute}
     */
    public AddAttributeCommand(String attribute) {
        toAdd = attribute;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Attribute> attributes = model.getAttributeList();
        Attribute attribute = new Attribute(toAdd);
        if (attributes.contains(attribute)) {
            throw new CommandException(MESSAGE_DUPLICATE_ATTRIBUTE);
        }

        attributes.add(attribute);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), ToggleView.ATT);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAttributeCommand // instanceof handles nulls
                && toAdd.equals(((AddAttributeCommand) other).toAdd));
    }
}
