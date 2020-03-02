package seedu.address.model.hirelah;

import java.util.ArrayList;

/*
 * QuestionList
 *
 * CS2103 AY19/20 Semester 2
 * Team Project
 * HireLah!
 *
 * 02 Mar 2020
 *
 */

/**
 * <p>QuestionList class manages the list of questions that
 * have been added by the interviewer. Questions can be retrieve
 * by their ordering.</p>
 * @author AY1920S2-W15-2
 */

public class QuestionList {
    private ArrayList<Question> questions;

    public QuestionList() {
        this.questions = new ArrayList<>();
    }

    /**
     * Lists all the questions that have been added.
     * @return The String represented the list.
     */
    
    public String listQuestions() {
        String message = "";

        for (int i = 1; i <= questions.size(); i++) {
            if (i != 1) {
                message += "\n";
            }
            message += questions.get(i - 1);
        }

        return message;
    }
}
