package seedu.jelphabot.testutil;

import seedu.jelphabot.model.JelphaBot;
import seedu.jelphabot.model.task.Task;

/**
 * A utility class to help with building JelphaBot objects.
 * Example usage: <br>
 *     {@code JelphaBot ab = new JelphaBotBuilder().withTask(new Task()).build();}
 */
public class JelphaBotBuilder {

    private JelphaBot jelphaBot;

    public JelphaBotBuilder() {
        jelphaBot = new JelphaBot();
    }

    public JelphaBotBuilder(JelphaBot jelphaBot) {
        this.jelphaBot = jelphaBot;
    }

    /**
     * Adds a new {@code Task} to the {@code JelphaBot} that we are building.
     */
    public JelphaBotBuilder withTask(Task task) {
        jelphaBot.addTask(task);
        return this;
    }

    public JelphaBot build() {
        return jelphaBot;
    }
}
