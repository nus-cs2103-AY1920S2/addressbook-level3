package fithelper.model.calculator;

import static fithelper.commons.core.FeedbackUtils.FEEDBACK_CALORIE_DEFAULT;
import static fithelper.commons.core.FeedbackUtils.FEEDBACK_CALORIE_NEGATIVE;
import static fithelper.commons.core.FeedbackUtils.FEEDBACK_CALORIE_TOO_MUCH;
import static fithelper.commons.core.FeedbackUtils.FEEDBACK_TASK_ALL_COMPLETE;
import static fithelper.commons.core.FeedbackUtils.FEEDBACK_TASK_DEFAULT;
import static fithelper.commons.core.FeedbackUtils.FEEDBACK_TASK_SOME_COMPLETE;
import static fithelper.commons.core.FeedbackUtils.FEEDBACK_TASL_NONE_COMPLETE;

/**
 * This class generates feedbacks based on the task completion and calorie results.
 */
public class FeedbackGenerator {

    private double performance;
    private double totalCalorie;

    public FeedbackGenerator(double performance, double totalCalorie) {
        this.performance = performance;
        this.totalCalorie = totalCalorie;
    }

    /**
     * Generates overall feedback.
     * @return
     */
    public String generateFeedback() {
        String calorieSummary = generateCalorieSummary();
        String performanceSummary = generatePerformanceSummary();
        return calorieSummary + "\n" + performanceSummary;
    }

    /**
     * Generates calorie summary.
     * @return
     */
    public String generateCalorieSummary() {
        if (totalCalorie < 0.0) {
            return FEEDBACK_CALORIE_NEGATIVE;
        } else if (totalCalorie > 1000) {
            return FEEDBACK_CALORIE_TOO_MUCH;
        } else {
            return FEEDBACK_CALORIE_DEFAULT;
        }
    }

    /**
     * Generates task summary.
     * @return
     */
    public String generatePerformanceSummary() {
        if (performance == 0.0) {
            return FEEDBACK_TASL_NONE_COMPLETE;
        } else if (performance >= 100.0) {
            return FEEDBACK_TASK_ALL_COMPLETE;
        } else if (performance >= 66.0) {
            return FEEDBACK_TASK_SOME_COMPLETE;
        } else {
            return FEEDBACK_TASK_DEFAULT;
        }
    }
}
