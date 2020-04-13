package seedu.address.logic.commands.cart;

import static java.util.Objects.requireNonNull;

import java.io.IOException;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.util.PdfExporter;

/**
 * Exports the ingredients in cart to a pdf file.
 */
public class CartExportCommand extends CartCommand {
    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_SUCCESS = "All cart ingredients have been exported to a pdf file!";
    public static final String MESSAGE_USAGE = COMMAND_CATEGORY + " " + COMMAND_WORD
            + ": exports all ingredients inside the cart to a PDF file located in the same folder"
            + "as Cooking Papa\n. Parameters:\n"
            + COMMAND_CATEGORY + " " + COMMAND_WORD;

    private static final String MESSAGE_FILE_NOT_FOUND = "'cart.pdf' is opened in another application. Please close "
            + "it and try again.";

    public CartExportCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            PdfExporter.exportCart(model.getCart().getIngredientList());
        } catch (IOException e) {
            return new CommandResult(MESSAGE_FILE_NOT_FOUND);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || other instanceof CartExportCommand;
    }
}
