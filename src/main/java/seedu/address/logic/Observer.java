package seedu.address.logic;

import seedu.address.logic.commands.exceptions.CommandException;

public interface Observer {
    void update() throws CommandException;
}