package seedu.zerotoone.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.zerotoone.commons.core.LogsCenter;
import seedu.zerotoone.logic.commands.Command;
import seedu.zerotoone.logic.commands.CommandResult;
import seedu.zerotoone.logic.commands.exceptions.CommandException;
import seedu.zerotoone.logic.parser.ParserManager;
import seedu.zerotoone.logic.parser.exceptions.ParseException;
import seedu.zerotoone.logic.statistics.StatisticsData;
import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.exercise.ReadOnlyExerciseList;
import seedu.zerotoone.model.schedule.ScheduledWorkout;
import seedu.zerotoone.model.session.CompletedSet;
import seedu.zerotoone.model.session.CompletedWorkout;
import seedu.zerotoone.model.session.OngoingSet;
import seedu.zerotoone.model.workout.ReadOnlyWorkoutList;
import seedu.zerotoone.model.workout.Workout;
import seedu.zerotoone.storage.Storage;
import seedu.zerotoone.ui.util.ViewType;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final ParserManager parser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        parser = new ParserManager();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        logger.info("Parsing User Command: [" + commandText + "]");
        Command command = parser.parse(commandText);
        logger.info("Parsed User Command: " + command.toString());

        logger.info("Executing Command: " + command.toString());
        CommandResult commandResult = command.execute(model);
        logger.info("Executed Command: " + commandResult.toString());
        logger.info("Feedback to user: " + commandResult.getFeedbackToUser());
        logger.info("Is Show About: " + commandResult.isShowAbout());
        logger.info("Is Show Report: " + commandResult.isShowReport());
        logger.info("Is Exit: " + commandResult.isExit());

        try {
            logger.info("Saving Exercise List");
            storage.saveExerciseList(model.getExerciseList());
            logger.info("Saved Exercise List");

            logger.info("Saving Workout List");
            storage.saveWorkoutList(model.getWorkoutList());
            logger.info("Saved Workout List");

            logger.info("Saving Schedule List");
            storage.saveScheduleList(model.getScheduleList());
            logger.info("Saved Schedule List");

            logger.info("Saving Log List");
            storage.saveLogList(model.getLogList());
            logger.info("Saved Log List");
        } catch (IOException ioe) {
            logger.severe("Exception: " + ioe.getMessage());
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ViewType getViewType(String commandText) throws ParseException {
        logger.info("Getting View Type: " + commandText);
        return parser.parseViewType(commandText);
    }

    // -----------------------------------------------------------------------------------------
    // Exercise List
    @Override
    public ReadOnlyExerciseList getExerciseList() {
        return model.getExerciseList();
    }

    @Override
    public ObservableList<Exercise> getFilteredExerciseList() {
        return model.getFilteredExerciseList();
    }

    @Override
    public Path getExerciseListFilePath() {
        return model.getExerciseListFilePath();
    }

    // -----------------------------------------------------------------------------------------
    // Log List
    @Override
    public ObservableList<CompletedWorkout> getLogList() {
        return model.getLogList().getLogList();
    }

    @Override
    public StatisticsData generateStatistics() {
        return Statistics.generate(model.getLogListCopyAsArrayList(),
            model.getStatisticsStartDateRange(), model.getStatisticsEndDateRange());
    }

    @Override
    public ObservableList<CompletedWorkout> getFilteredLogList() {
        return model.getFilteredLogList();
    }

    @Override
    public Path getLogListFilePath() {
        return model.getLogListFilePath();
    }

    // -----------------------------------------------------------------------------------------
    // Workout List
    @Override
    public ReadOnlyWorkoutList getWorkoutList() {
        return model.getWorkoutList();
    }

    @Override
    public ObservableList<Workout> getFilteredWorkoutList() {
        return model.getFilteredWorkoutList();
    }

    @Override
    public Path getWorkoutListFilePath() {
        return model.getWorkoutListFilePath();
    }

    // -----------------------------------------------------------------------------------------
    // Schedule
    @Override
    public ObservableList<ScheduledWorkout> getSortedScheduledWorkoutList() {
        return model.getSortedScheduledWorkoutList();
    }

    // -----------------------------------------------------------------------------------------
    // Session

    @Override
    public ObservableList<OngoingSet> getOngoingSetList() {
        return model.getOngoingSetList().getOngoingSetList();
    }

    @Override
    public ObservableList<CompletedSet> getLastSet() {
        return model.getLastSet().getCompletedSetList();
    }

    @Override
    public ObservableList<Integer> getTimerList() {
        return model.getTimerList().getTimerList();
    }

    @Override
    public void showdownTimer() {
        model.shutdownTimer();
    }
}
