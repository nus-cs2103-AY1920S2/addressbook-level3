package seedu.nova.storage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.nova.commons.exceptions.IllegalValueException;
import seedu.nova.model.AddressBook;
import seedu.nova.model.Nova;
import seedu.nova.model.ReadOnlyAddressBook;
import seedu.nova.model.Schedule;
import seedu.nova.model.VersionedAddressBook;
import seedu.nova.model.person.Person;
import seedu.nova.model.plan.Plan;
import seedu.nova.model.plan.StudyPlan;
import seedu.nova.model.plan.Task;
import seedu.nova.model.progresstracker.Ip;
import seedu.nova.model.progresstracker.ProgressTracker;
import seedu.nova.model.progresstracker.Project;
import seedu.nova.model.progresstracker.PtTask;
import seedu.nova.model.progresstracker.PtTaskList;
import seedu.nova.model.progresstracker.PtWeek;
import seedu.nova.model.progresstracker.PtWeekList;
import seedu.nova.model.progresstracker.Tp;
import seedu.nova.model.schedule.event.Event;
import seedu.nova.storage.event.JsonAdaptedEvent;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "nova")
class JsonSerializableNova {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedPtTask> ptTasks = new ArrayList<>();
    private final List<JsonAdaptedEvent> events = new ArrayList<>();
    private final List<JsonAdaptedPlannerTask> tasks = new ArrayList<>();

    //add your list of adapted class objects here

    /**
     * Constructs a {@code JsonSerializableNova} with the given persons.
     */
    @JsonCreator
    public JsonSerializableNova(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                @JsonProperty("ptTasks") List<JsonAdaptedPtTask> ptTasks,
                                @JsonProperty("events") List<JsonAdaptedEvent> events,
                                @JsonProperty("tasks") List<JsonAdaptedPlannerTask> tasks) {
        this.ptTasks.addAll(ptTasks);

        if (persons != null) {
            this.persons.addAll(persons);
        }
        if (events != null) {
            this.events.addAll(events);
        }
        if (tasks != null) {
            this.tasks.addAll(tasks);
        }
    }

    /**
     * Converts a given {@code Nova} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableNova}.
     */
    public JsonSerializableNova(Nova source) {
        persons.addAll(source.getAddressBookNova().getPersonList().stream()
                .map(JsonAdaptedPerson::new).collect(Collectors.toList()));

        ptTasks.addAll(getPtTasks(source));

        events.addAll(source.getScheduleNova().getEventList().stream()
                .map(JsonAdaptedEvent::new).collect(Collectors.toList()));

        tasks.addAll(source.getStudyPlan().getTaskList().stream()
                .map(JsonAdaptedPlannerTask::new).collect(Collectors.toList()));
    }

    /**
     * Converts a given {@code Nova} into a list of adapted objects
     *
     * @param source uture changes to this will not affect the created {@code JsonSerializableNova}.
     * @return a list of JsonAdaptedPtTask
     */
    public List<JsonAdaptedPtTask> getPtTasks(Nova source) {
        List<JsonAdaptedPtTask> list = new ArrayList<>();

        //Add JsonAdaptedPtTasks into ptTasks array
        ProgressTracker pt = source.getProgressTracker();
        Ip ip = pt.getIp();
        Tp tp = pt.getTp();
        PtWeekList ipList = ip.getWeekList();
        PtWeekList tpList = tp.getWeekList();

        for (int i = 1; i < 14; i++) {
            ArrayList<PtTask> currentIpTasks = ipList.getWeek(i).getTaskList().getList();
            ArrayList<PtTask> currentTpTasks = tpList.getWeek(i).getTaskList().getList();

            for (PtTask ptTask : currentIpTasks) {
                JsonAdaptedPtTask task = new JsonAdaptedPtTask(ptTask);
                list.add(task);
            }

            for (PtTask ptTask : currentTpTasks) {
                JsonAdaptedPtTask task = new JsonAdaptedPtTask(ptTask);
                list.add(task);
            }
        }
        return list;
    }

    /**
     * Converts Nova into the model's {@code Nova} object.
     *
     * @return the converted Nova object
     * @throws IllegalValueException
     */
    public Nova toModelType() throws IllegalValueException {
        Nova nova = new Nova();
        VersionedAddressBook ab = toModelTypeAb();
        ProgressTracker pt = toModelTypePt();

        nova.setAddressBookNova(ab);
        nova.setProgressTrackerNova(pt);
        Schedule sc = toModelTypeSchedule();

        nova.setAddressBookNova(ab);
        nova.setScheduleNova(sc);

        Plan plan = toModelTypePlan();
        nova.setStudyPlan(plan);

        return nova;
    }

    /**
     * Converts this plan into the model's {@code Plan} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    private Plan toModelTypePlan() throws IllegalValueException {
        List<Task> taskLst = new ArrayList<>();
        for (JsonAdaptedPlannerTask t : tasks) {
            taskLst.add(t.toTask());
        }
        return new StudyPlan(taskLst);
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public VersionedAddressBook toModelTypeAb() throws IllegalValueException {
        ReadOnlyAddressBook initialState;
        AddressBook ab = new AddressBook();

        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (ab.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            ab.addPerson(person);
        }
        initialState = ab;
        VersionedAddressBook addressBook = new VersionedAddressBook(initialState);

        return addressBook;
    }

    /**
     * Converts this progress tracker into the model's {@code ProgressTracker} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ProgressTracker toModelTypePt() throws IllegalValueException {
        ProgressTracker pt = new ProgressTracker();
        Ip ip = pt.getIp();
        Tp tp = pt.getTp();

        for (JsonAdaptedPtTask adaptedPtTask : ptTasks) {
            PtTaskList taskList;
            PtTask modelTask = adaptedPtTask.toModelType();
            Project project = modelTask.getProject();
            int week = modelTask.getPtWeek();

            if (project instanceof Ip) {
                PtWeekList weekList = ip.getWeekList();
                PtWeek ptWeek = weekList.getWeek(week);
                taskList = ptWeek.getTaskList();
            } else {
                PtWeekList weekList = tp.getWeekList();
                PtWeek ptWeek = weekList.getWeek(week);
                taskList = ptWeek.getTaskList();
            }

            //add to correct project and week
            taskList.addTask(modelTask);
        }
        return pt;
    }

    /**
     * Converts this schedule into the model's {@code Schedule} object.
     */
    public Schedule toModelTypeSchedule() throws IllegalValueException {

        Schedule sc = new Schedule(LocalDate.of(2020, 1, 13),
                LocalDate.of(2020, 5, 3));

        for (JsonAdaptedEvent jsonAdaptedEvent : events) {
            Event event = jsonAdaptedEvent.toModelType();
            sc.addEvent(event);
        }

        return sc;
    }

}
