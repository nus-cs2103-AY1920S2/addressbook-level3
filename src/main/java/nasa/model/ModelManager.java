package nasa.model;

import static java.util.Objects.requireNonNull;
import static nasa.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import nasa.commons.core.GuiSettings;
import nasa.commons.core.LogsCenter;
import nasa.commons.core.index.Index;
import nasa.model.activity.Activity;
import nasa.model.activity.Deadline;
import nasa.model.activity.Event;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;
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
    private final FilteredList<Module> filteredModules;
    private final HistoryManager<UniqueModuleList> historyManager;

    /**
     * Initializes a ModelManager with the given NasaBook and userPrefs.
     *
     * @param nasaBook    ReadOnlyNasaBook
     * @param historyBook ReadOnlyHistory
     * @param userPrefs   ReadOnlyUserPrefs
     */
    public ModelManager(ReadOnlyNasaBook nasaBook, ReadOnlyHistory<UniqueModuleList> historyBook,
                        ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(nasaBook, userPrefs);

        logger.fine("Initializing with NASA: " + nasaBook + " and user prefs " + userPrefs);

        this.nasaBook = new NasaBook(nasaBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredModules = new FilteredList<>(this.nasaBook.getModuleList());
        this.historyManager = new HistoryManager<>(historyBook);
        initialisation();
    }

    public ModelManager() {
        this(new NasaBook(), new HistoryBook<>(), new UserPrefs());
    }

    public HistoryManager<UniqueModuleList> getHistoryManager() {
        return historyManager;
    }

    /**
     * Startup setup for Nasa book.
     */
    public void initialisation() {
        updateSchedule();
        updateHistory();
        Quote.readFile();
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    /**
     * Update the schedule for each activity.
     */
    public void updateSchedule() {
        nasaBook.scheduleAll();
        updateFilteredActivityList(PREDICATE_SHOW_ALL_ACTIVITIES);
    }

    /**
     * Update the history manager list every time there is a change.
     */
    public void updateHistory() {
        final UniqueModuleList temp = new UniqueModuleList();
        temp.setModules(nasaBook.getDeepCopyList());
        historyManager.add(temp);
    }

    @Override
    public void undoHistory() {
        if (historyManager.undo()) {
            nasaBook.setModuleList(historyManager.getItem().getDeepCopyList());
            updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        }
    }

    @Override
    public boolean redoHistory() {
        boolean hasRedo = historyManager.redo();
        if (hasRedo) {
            nasaBook.setModuleList(historyManager.getItem().getDeepCopyList());
            updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        }
        return hasRedo;
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
        updateHistory();
    }

    @Override
    public void addModule(Module module) {
        nasaBook.addModule(module);
        updateHistory();
        //historyManager.add(new UniqueModuleList().setModules(nasaBook.getList()));
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void addDeadline(ModuleCode target, Deadline deadline) {
        nasaBook.addDeadline(target, deadline);
        updateHistory();
        updateFilteredActivityList(PREDICATE_SHOW_ALL_ACTIVITIES);
    }

    @Override
    public void addEvent(ModuleCode target, Event event) {
        nasaBook.addEvent(target, event);
        updateHistory();
        updateFilteredActivityList(PREDICATE_SHOW_ALL_ACTIVITIES);
    }

    @Override
    public void removeDeadline(ModuleCode target, Deadline deadline) {
        nasaBook.removeDeadline(target, deadline);
        updateHistory();
    }

    @Override
    public void removeEvent(ModuleCode target, Event event) {
        nasaBook.removeEvent(target, event);
        updateHistory();
    }

    @Override
    public void setModule(ModuleCode target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        nasaBook.setModule(target, editedModule);
        updateHistory();
    }

    @Override
    public void setDeadline(ModuleCode moduleCode, Deadline target, Deadline editedDeadline) {
        requireAllNonNull(target, editedDeadline);

        nasaBook.setDeadline(moduleCode, target, editedDeadline);
        updateHistory();
    }

    @Override
    public void setEvent(ModuleCode moduleCode, Event target, Event editedEvent) {
        requireAllNonNull(target, editedEvent);

        nasaBook.setEvent(moduleCode, target, editedEvent);
        updateHistory();
    }

    public boolean setDeadlineSchedule(ModuleCode module, Index index, Index type) {
        requireAllNonNull(module, index, type);
        boolean hasExecuted = nasaBook.setDeadlineSchedule(module, index, type);
        updateHistory();
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        return hasExecuted;
    }

    public boolean setEventSchedule(ModuleCode module, Index index, Index type) {
        requireAllNonNull(module, index, type);
        boolean hasExecuted = nasaBook.setEventSchedule(module, index, type);
        updateHistory();
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        return hasExecuted;
    }

    //=========== Filtered Module List Accessors =============================================================

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

    @Override
    public ObservableList<Event> getFilteredEventList(ModuleCode moduleCode) {
        Module module = nasaBook.getModule(moduleCode);
        return module.getEventList().getActivityList();
    }

    @Override
    public ObservableList<Deadline> getFilteredDeadlineList(ModuleCode moduleCode) {
        Module module = nasaBook.getModule(moduleCode);
        return module.getDeadlineList().getActivityList();
    }

    @Override
    public void sortDeadlineList(SortMethod sortMethod) {
        requireNonNull(sortMethod);
        for (Module module : filteredModules) {
            module.sortDeadlineList(sortMethod);
        }
    }

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
