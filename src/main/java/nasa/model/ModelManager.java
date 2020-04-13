package nasa.model;

import static java.util.Objects.requireNonNull;
import static nasa.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import nasa.commons.core.GuiSettings;
import nasa.commons.core.LogsCenter;
import nasa.commons.core.index.Index;
import nasa.model.activity.Activity;
import nasa.model.activity.ActivityContainsKeyWordsPredicate;
import nasa.model.activity.Deadline;
import nasa.model.activity.Event;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;
import nasa.model.module.NameContainsKeywordsPredicate;
import nasa.model.module.SortMethod;
import nasa.model.module.UniqueModuleList;
import nasa.model.quote.Quote;

/**
 * Represents the in-memory module manager.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final NasaBook nasaBook;
    private final UserPrefs userPrefs;
    private FilteredList<Module> filteredModules;
    private final HistoryManager<UniqueModuleList> historyManager;

    /**
     * Initializes a ModelManager with the given NasaBook and userPrefs.
     *
     * @param nasaBook    ReadOnlyNasaBook
     * @param historyBook ReadOnlyHistory
     * @param userPrefs   ReadOnlyUserPrefs
     */
    public ModelManager(ReadOnlyNasaBook nasaBook, ReadOnlyHistory<UniqueModuleList> historyBook,
                        ReadOnlyHistory<String> uiHistoryBook,
                        ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(nasaBook, userPrefs);

        logger.fine("Initializing with NASA: " + nasaBook + " and user prefs " + userPrefs);

        this.nasaBook = new NasaBook(nasaBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredModules = new FilteredList<>(this.nasaBook.getModuleList());
        this.historyManager = new HistoryManager<>(historyBook, uiHistoryBook);
        initialisation();
    }

    public ModelManager() {
        this(new NasaBook(), new HistoryBook<>(), new HistoryBook<>(), new UserPrefs());
    }

    public HistoryManager<UniqueModuleList> getHistoryManager() {
        return historyManager;
    }

    /**
     * Startup setup for Nasa book.
     */
    public void initialisation() {
        updateSchedule();
        updateHistory("null");
        Quote.readFile();
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        updateFilteredActivityList(PREDICATE_SHOW_ALL_ACTIVITIES);
    }

    /**
     * Update the schedule for each activity.
     */
    @Override
    public void updateSchedule() {
        nasaBook.scheduleAll();
    }

    /**
     * Update the history manager list every time there is a change.
     */
    public void updateHistory(String type) {
        final UniqueModuleList temp = new UniqueModuleList();
        temp.setModules(nasaBook.getDeepCopyList());
        historyManager.add(temp, type);
    }


    /**
     * Update the history manager list every time there is a change.
     */
    public void updateHistory(List<String> input, String type) {
        final UniqueModuleList temp = new UniqueModuleList();
        temp.setModules(nasaBook.getDeepCopyList());

        StringBuilder output = new StringBuilder();
        output.append(type);
        input.forEach(x -> {
            output.append(" ");
            output.append(x);
        });
        historyManager.add(temp, output.toString());
    }

    /**
     * Refresh filter settings.
     */
    public void refreshUi() {
        String temp = historyManager.getUiItem();
        List<String> test = Arrays.asList(temp.trim().split("\\s+"));
        System.out.println(test.toString());
        String type = test.get(0);

        if (!type.equals("null")) {
            if (test.get(1).equals("activity")) {
                test = test.subList(2, test.size());
                if (!test.get(0).equals("null")) {
                    if (type.equals("list") || type.equals("find")) {
                        updateFilteredModuleList(x->true);
                        updateFilteredActivityList(new ActivityContainsKeyWordsPredicate(test));
                    }
                } else {
                    updateFilteredModuleList(x->true);
                }
            } else if (test.get(1).equals("module")) {
                List<String> listTemp = test.subList(2, 3);
                updateFilteredModuleList(new NameContainsKeywordsPredicate(listTemp));
                List<String> activityCurrentlyDisplayed = test.subList(4, test.size());
                updateFilteredActivityList(new ActivityContainsKeyWordsPredicate(activityCurrentlyDisplayed));
            }
        }

        if (type.equals("null")) {
            updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
            updateFilteredActivityList(PREDICATE_SHOW_ALL_ACTIVITIES);
        }
    }

    @Override
    public void undoHistory() {
        boolean hasUndoHistory = historyManager.undo();
        if (hasUndoHistory) {
            nasaBook.setModuleList(historyManager.getItem().getDeepCopyList());
            refreshUi();
        }
    }

    @Override
    public boolean redoHistory() {
        boolean hasRedoHistory = historyManager.redo();
        if (hasRedoHistory) {
            nasaBook.setModuleList(historyManager.getItem().getDeepCopyList());
            refreshUi();
        }
        return hasRedoHistory;
    }

    //=========== UserPrefs ==================================================================================
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getNasaBookFilePath() {
        return userPrefs.getNasaBookFilePath();
    }

    @Override
    public void setNasaBookFilePath(Path nasaBookFilePath) {
        requireNonNull(nasaBookFilePath);
        userPrefs.setNasaBookFilePath(nasaBookFilePath);
    }

    //=========== NasaBook ================================================================================

    @Override
    public void setNasaBook(ReadOnlyNasaBook nasaBook) {
        this.nasaBook.resetData(nasaBook);
        updateHistory("null");
    }

    @Override
    public ReadOnlyNasaBook getNasaBook() {
        return nasaBook;
    }

    @Override
    public ReadOnlyHistory<UniqueModuleList> getHistoryBook() {
        return historyManager.getHistoryBook();
    }

    @Override
    public ReadOnlyHistory<String> getUiHistoryBook() {
        return historyManager.getUiHistoryBook();
    }

    @Override
    public boolean hasModule(ModuleCode module) {
        requireNonNull(module);
        return nasaBook.hasModule(module);
    }

    @Override
    public Module getModule(ModuleCode moduleCode) {
        UniqueModuleList uniqueModuleList = nasaBook.getUniqueModuleList();
        return uniqueModuleList.getModule(moduleCode);
    }

    @Override
    public void deleteModule(ModuleCode target) {
        nasaBook.removeModule(target);
        updateHistory("delete" + currentUiLocation());
    }

    @Override
    public void addModule(Module module) {
        nasaBook.addModule(module);
        updateHistory("add" + currentUiLocation() + " " + module.getModuleCode().toString());
    }

    @Override
    public void addDeadline(ModuleCode target, Deadline deadline) {
        nasaBook.addDeadline(target, deadline);
        updateHistory("deadline" + currentUiLocation() + " " + deadline.getName().name);
    }

    @Override
    public void addEvent(ModuleCode target, Event event) {
        nasaBook.addEvent(target, event);
        updateHistory("event" + currentUiLocation() + " " + event.getName().name);
    }

    @Override
    public void removeDeadline(ModuleCode target, Deadline deadline) {
        nasaBook.removeDeadline(target, deadline);
        updateHistory("deadline" + currentUiLocation());
    }

    @Override
    public void removeEvent(ModuleCode target, Event event) {
        nasaBook.removeEvent(target, event);
        updateHistory("event" + currentUiLocation());
    }

    /* @@author don-tay */
    @Override
    public void setModule(ModuleCode target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        nasaBook.setModule(target, editedModule);
        updateHistory("module" + currentUiLocation());
    }

    @Override
    public void setDeadline(ModuleCode moduleCode, Deadline target, Deadline editedDeadline) {
        requireAllNonNull(target, editedDeadline);

        nasaBook.setDeadline(moduleCode, target, editedDeadline);
        updateHistory("deadline" + currentUiLocation());
    }

    @Override
    public void setEvent(ModuleCode moduleCode, Event target, Event editedEvent) {
        requireAllNonNull(target, editedEvent);

        nasaBook.setEvent(moduleCode, target, editedEvent);
        updateHistory("event" + currentUiLocation());
    }

    /**
     * Find out current UI filter property.
     * @return String
     */
    public String currentUiLocation() {
        StringBuilder location = new StringBuilder();
        if (getFilteredModuleList().size() > 1) {
            //not in Find
            location.append(" activity");
            int i = 0;
            boolean test = false;
            while (i < getFilteredModuleList().size()) {
                if (getFilteredModuleList().get(i).getFilteredDeadlineList().size() == 0) {
                    i++;
                    continue;
                } else {
                    getFilteredModuleList().get(i).getFilteredDeadlineList()
                            .forEach(x-> {
                                location.append(" ");
                                location.append(x.getName().name);
                            });
                    test = true;
                }
                i++;
            }

            i = 0;
            while (i < getFilteredModuleList().size()) {
                if (getFilteredModuleList().get(i).getFilteredEventList().size() == 0) {
                    i++;
                    continue;
                } else {
                    getFilteredModuleList().get(i).getFilteredEventList()
                            .forEach(x-> {
                                location.append(" ");
                                location.append(x.getName().name);
                            });
                    test = true;
                }
                i++;
            }
            System.out.println(location.toString());
            if (!test) {
                location.append(" null");
            }
        } else if (getFilteredModuleList().size() == 1) {
            location.append(" module ");
            location.append(getFilteredModuleList().get(0).getModuleCode().moduleCode);

            location.append(" activity");
            int i = 0;
            boolean test = false;
            while (i < getFilteredModuleList().size()) {
                if (getFilteredModuleList().get(i).getFilteredDeadlineList().size() == 0) {
                    i++;
                    continue;
                } else {
                    getFilteredModuleList().get(i).getFilteredDeadlineList()
                            .forEach(x-> {
                                location.append(" ");
                                location.append(x.getName().name);
                            });
                    test = true;
                }
                i++;
            }

            i = 0;
            while (i < getFilteredModuleList().size()) {
                if (getFilteredModuleList().get(i).getFilteredEventList().size() == 0) {
                    i++;
                    continue;
                } else {
                    getFilteredModuleList().get(i).getFilteredEventList()
                            .forEach(x-> {
                                location.append(" ");
                                location.append(x.getName().name);
                            });
                    test = true;
                }
                i++;
            }
            System.out.println(location.toString());
            if (!test) {
                location.append(" null");
            }
        } else {
            return " null";
        }
        return location.toString();
    }
    /* @@author */

    public boolean setDeadlineSchedule(ModuleCode module, Index index, Index type) {
        requireAllNonNull(module, index, type);
        boolean hasExecuted = nasaBook.setDeadlineSchedule(module, index, type);
        updateHistory("schedule" + currentUiLocation());
        return hasExecuted;
    }

    public boolean setEventSchedule(ModuleCode module, Index index, Index type) {
        requireAllNonNull(module, index, type);
        boolean hasExecuted = nasaBook.setEventSchedule(module, index, type);
        updateHistory("schedule" + currentUiLocation());
        return hasExecuted;
    }

    //=========== Filtered Module List Accessors =============================================================

    @Override
    public boolean hasActivity(ModuleCode moduleCode, Activity activity) {
        return nasaBook.hasActivity(moduleCode, activity);
    }

    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return filteredModules;
    }

    @Override
    public void updateFilteredModuleList(Predicate<Module> predicate) {
        requireNonNull(predicate);
        filteredModules.setPredicate(predicate);
    }

    /* @@author don-tay */
    @Override
    public ObservableList<Event> getFilteredEventList(ModuleCode moduleCode) {
        Module module = nasaBook.getModule(moduleCode);
        return module.getFilteredEventList();
    }

    @Override
    public ObservableList<Deadline> getFilteredDeadlineList(ModuleCode moduleCode) {
        Module module = nasaBook.getModule(moduleCode);
        return module.getFilteredDeadlineList();
    }

    @Override
    public void sortActivityList(SortMethod sortMethod) {
        requireNonNull(sortMethod);
        for (Module module : filteredModules) {
            module.sortDeadlineList(sortMethod);
            module.sortEventList(sortMethod);
        }
        updateHistory("sort" + currentUiLocation());
    }
    /* @@author */

    @Override
    public void updateFilteredActivityList(Predicate<Activity> predicate) {
        for (Module module : getFilteredModuleList()) {
            module.updateFilteredActivityList(predicate);
        }
    }

    @Override
    public String quote() {
        return Quote.getQuote();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return nasaBook.equals(other.nasaBook)
            && userPrefs.equals(other.userPrefs)
            && filteredModules.equals(other.filteredModules);
    }
}
