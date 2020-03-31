package csdev.couponstash.logic.commands;

import static java.util.Objects.requireNonNull;

import csdev.couponstash.commons.core.index.Index;

/**
 * Represents a {@code Command} that requires a specified index to execute upon.
 * IndexedCommands can only be executed in Coupons tab.
 */
public abstract class IndexedCommand extends Command {
    protected final Index targetIndex;

    public IndexedCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }
}
