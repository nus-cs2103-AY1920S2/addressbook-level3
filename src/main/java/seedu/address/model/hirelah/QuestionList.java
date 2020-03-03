package seedu.address.model.hirelah;

import java.util.ArrayList;

import seedu.address.commons.exceptions.IllegalValueException;

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

public class QuestionList implements IList<Question> {
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

    /**
     * Adds the question to the list, if the input is valid.
     * @param questionDescription The question.
     * @return The outcome message.
     * @throws IllegalValueException If the question already exists.
     */
    public String add(String questionDescription) throws IllegalValueException {
        try {
            Question question = new Question(questionDescription);
            boolean isDuplicate = isDuplicate(question);

            if (isDuplicate) {
                throw new IllegalValueException("This question is already exists!");
            }

            questions.add(question);
            return String.format("Successfully added question: %s", question);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    /**
     * Finds the question based on its index, if the index
     * string entered is a valid integer string.
     * @param questionIndex The string index.
     * @return The corresponding Question instance.
     * @throws IllegalValueException If the questionIndex is not a number or the index is out of bound.
     */
    public Question find(String questionIndex) throws IllegalValueException {
        try {
            int index = Integer.parseInt(questionIndex);

            if (index > questions.size() || index <= 0) {
                throw new IllegalValueException("The index is out of bound");
            }
            return questions.get(index - 1);
        } catch (NumberFormatException e) {
            throw new IllegalValueException("The input is not a number.");
        }
    }

    /**
     * Deletes the question based on its index, if the index
     * string entered is a valid integer string.
     * @param questionIndex The string index.
     * @return The outcome message.
     * @throws IllegalValueException If the questionIndex is not a number or the index is out of bound.
     */
    public String delete(String questionIndex) throws IllegalValueException {
        try {
            int index = Integer.parseInt(questionIndex);

            if (index > questions.size() || index <= 0) {
                throw new IllegalValueException("The index is out of bound");
            }

            Question question = questions.get(index - 1);
            questions.remove(question);
            return String.format("Successfully deleted question: %s", question);
        } catch (NumberFormatException e) {
            throw new IllegalValueException("The input is not a number.");
        }
    }

    private boolean isDuplicate(Question question) {
        return questions.stream().anyMatch(question::equals);
    }
}
