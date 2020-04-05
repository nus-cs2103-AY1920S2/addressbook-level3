package csdev.couponstash.model.history;

import java.util.ArrayList;
import java.util.List;

import csdev.couponstash.model.CouponStash;

/**
 * Stores the historic state of the coupons.
 */
public class HistoryManager {
    private List<CouponStash> couponStashStateList;
    private List<String> commandTextHistory;
    private int currStateIndex;

    public HistoryManager(CouponStash initialState) {
        this.currStateIndex = 0;

        this.couponStashStateList = new ArrayList<>();
        this.commandTextHistory = new ArrayList<>();
        this.couponStashStateList.add(initialState);
        // Empty string as initial state had no commands
        this.commandTextHistory.add("");
    }

    /**
     * Add {@code newState} to {@code couponStashList}.
     * {@code command} is the command that triggered the commit.
     * Increments {@code currStateIndex} subsequently.
     * If {@code currentStatePointer} is not pointing at the end of the
     * {@code couponStashStateList}, all coupon stash states after the
     * {@code currentStatePointer} will be purged.
     * @param newState State to add to {@code couponStashList}
     */
    public void commitState(CouponStash newState, String command) {
        int stateSize = couponStashStateList.size();
        if (currStateIndex != stateSize - 1) {

            // Purging all coupon stash states and commandText histories after the currentStatePointer.
            for (int i = currStateIndex + 1; i < stateSize; i++) {
                couponStashStateList.remove(couponStashStateList.size() - 1);
                commandTextHistory.remove(commandTextHistory.size() - 1);
            }
        }

        this.couponStashStateList.add(newState);
        this.commandTextHistory.add(command);
        currStateIndex++;
    }

    /**
     * Returns the next commandText from the commandText history.
     * @return Next command string
     */
    public String getNextCommandText() {
        return this.commandTextHistory.get(currStateIndex + 1);
    }

    /**
     * Revert to previous state. Decrements {@code currStateIndex} subsequently.
     * @return Previous state
     */
    public CouponStash undo() {
        return this.couponStashStateList.get(--currStateIndex).copy();
    }

    /**
     * Revert to state before undo. Increments {@code currStateIndex} subsequently.
     * @return State before undo
     */
    public CouponStash redo() {
        return this.couponStashStateList.get(++currStateIndex).copy();
    }

    /**
     * Check if there is a state to undo to.
     * @return True if there is a state to undo to, false otherwise
     */
    public boolean canUndo() {
        return currStateIndex != 0;
    }

    /**
     * Check if there is a state to redo to.
     * @return True if there is a state to redo to, false otherwise
     */
    public boolean canRedo() {
        return currStateIndex != couponStashStateList.size() - 1;
    }
}
