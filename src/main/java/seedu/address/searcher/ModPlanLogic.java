package seedu.address.searcher;

import java.util.ArrayList;

/**
 * <h1>Module Planner Logic Class</h1>
 * Just a class to handle logic behind module planning
 */
public class ModPlanLogic {
    /**
     * Function to check if Modules, myMod and toCheck,
     * have clashing lecture slots
     * @param myMod module in question
     * @param toCheck module to check
     * @param sem which sem you planning
     * @return true if there is a clash, false otherwise
     */
    public static boolean checkLectureClash (Module myMod, Module toCheck, int sem) {
        ArrayList<Classes> myModLectures;
        ArrayList<Classes> toCheckLectures;

        if (sem == 1) {
            myModLectures = myMod.getSem1Lectures();
            toCheckLectures = toCheck.getSem1Lectures();
        } else {
            myModLectures = myMod.getSem2Lectures();
            toCheckLectures = toCheck.getSem2Lectures();
        }

        for (Classes myModCurr : myModLectures) {
            String myModCurrDay = myModCurr.getDay();
            int myModCurrStart = Integer.parseInt(myModCurr.getStartTime());
            int myModCurrEnd = Integer.parseInt(myModCurr.getEndTime());

            for (Classes toCheckCurr : toCheckLectures) {
                String toCheckCurrDay = toCheckCurr.getDay();
                int toCheckCurrStart = Integer.parseInt(toCheckCurr.getStartTime());
                int toCheckCurrEnd = Integer.parseInt(toCheckCurr.getEndTime());

                if (myModCurrDay.equals(toCheckCurrDay)) {
                    if (myModCurrStart >= toCheckCurrStart) {
                        if (myModCurrStart >= toCheckCurrEnd) {
                            return false;
                        }
                    } else {
                        if (myModCurrEnd <= toCheckCurrStart) {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Function to check if two classes clash
     * @param myClass first class
     * @param toCheck second class
     * @return true if there is clash false otherwise
     */
    private static boolean checkClassClash (Classes myClass, Classes toCheck) {
        int myClassStart = Integer.parseInt(myClass.getStartTime());
        int myClassEnd = Integer.parseInt(myClass.getEndTime());
        int toCheckStart = Integer.parseInt(myClass.getStartTime());
        int toCheckEnd = Integer.parseInt(toCheck.getEndTime());


        if (myClassStart > toCheckStart) {
            return myClassStart < toCheckEnd;
        } else if (myClassStart == toCheckStart) {
            return true;
        } else {
            return myClassEnd > toCheckStart;
        }
    }
}
