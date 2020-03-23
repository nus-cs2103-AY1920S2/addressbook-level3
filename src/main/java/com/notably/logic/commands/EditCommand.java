//package com.notably.logic.commands;
//
//import static java.util.Objects.requireNonNull;
//
//import com.notably.logic.commands.exceptions.CommandException;
//import com.notably.model.Model;
//import com.notably.model.ModelManager;
//import com.notably.model.block.Body;
//
//public class EditCommand extends Command {
//    public static final String COMMAND_WORD = "edit";
//    private final Body body;
//
//    public EditCommand(Body body) {
//        requireNonNull(body);
//        this.body = body;
//    }
//
//    /**
//     * Edit the Block body of the current directory.
//     * @param notablyModel used to access the tree structure.
//     * @throws CommandException
//     */
//    public void execute(Model notablyModel) {
//        notablyModel.updateCurrentBlock(body);
//    }
//}
//
