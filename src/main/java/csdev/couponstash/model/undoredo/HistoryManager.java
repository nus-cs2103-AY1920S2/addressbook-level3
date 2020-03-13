package csdev.couponstash.model.undoredo;

import java.util.List;
import java.util.ArrayList;

import csdev.couponstash.model.CouponStash;
import csdev.couponstash.model.coupon.Coupon;

public class HistoryManager {
    private List<CouponStash> couponStashStateList;
    private int currStateIndex;

    public HistoryManager(CouponStash initialState) {
        this.currStateIndex = 0;

        this.couponStashStateList = new ArrayList<>();
        this.couponStashStateList.add(initialState);
    }

    public void commitState(CouponStash newState) {
        this.couponStashStateList.add(newState);
        currStateIndex++;

        // If currentStatePointer is not pointing at the end of the
        // couponStashStateList, all coupon stash states after the
        // currentStatePointer will be purged.
        int stateSize = couponStashStateList.size();
        if (currStateIndex != stateSize - 1) {
            for (int i = currStateIndex + 1; i < stateSize; i++) {
                couponStashStateList.remove(couponStashStateList.size() - 1);
            }
        }
    }

    public CouponStash undo() {
        return this.couponStashStateList.get(--currStateIndex).clone();
    }

    public CouponStash redo() {
        return this.couponStashStateList.get(++currStateIndex).clone();
    }

    public int getCurrStateIndex() {
        return currStateIndex;
    }
}
