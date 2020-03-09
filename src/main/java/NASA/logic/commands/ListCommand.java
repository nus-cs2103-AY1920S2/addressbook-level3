package NASA.logic.commands;

import static java.util.Objects.requireNonNull;

import NASA.model.ModelNasa;

public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";

    @Override
    public CommandResult execute(ModelNasa model) {
        requireNonNull(model);
        // TODO add the necessary implementation once model is done
        return new CommandResult("");
    }
}
