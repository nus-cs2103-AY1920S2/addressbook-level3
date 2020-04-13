package seedu.nova.model.progresstracker;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

/**
 * Represents a progress tracker
 */
public class ProgressTracker {
    private Ip ip;
    private Tp tp;

    /**
     * Creates a ProgressTracker object
     */
    public ProgressTracker() {
        this.ip = new Ip();
        this.tp = new Tp();
    }

    public Ip getIp() {
        return ip;
    }

    public Tp getTp() {
        return tp;
    }

    public PtWeekList getPtWeekList(Project project) {
        return project.getWeekList();
    }

    public ArrayList<PtWeek> getArrayListFromWeekList(PtWeekList ptWeekList) {
        return ptWeekList.getWeekList();
    }

    public PtWeek getPtWeek(PtWeekList ptWeekList, int weekNum) {
        return ptWeekList.getWeek(weekNum);
    }

    public int getNumFromWeek(PtWeek week) {
        return week.getWeekNum();
    }

    public PtTaskList getPtTaskList(PtWeek ptWeek) {
        return ptWeek.getTaskList();
    }

    public ArrayList<PtTask> getArrayListFromTaskList(PtTaskList ptTaskList) {
        return ptTaskList.getList();
    }

    public int getNumTask(PtTaskList ptTaskList) {
        return ptTaskList.getNumTask();
    }

    public PtTask getTaskFromList(PtTaskList ptTaskList, int taskNum) {
        return ptTaskList.getTask(taskNum);
    }

    public PtNote getNoteFromTask(PtTask task) {
        return task.getNote();
    }

    public String listTaskFromList(PtTaskList ptTaskList) {
        return ptTaskList.listTasks();
    }

    public void addTaskToList(PtTaskList ptTaskList, PtTask task) {
        ptTaskList.addTask(task);
    }

    public void deleteTaskFromList(PtTaskList ptTaskList, int taskNum) {
        ptTaskList.deleteTask(taskNum);
    }

    public void editTaskFromList(PtTask task, String taskDesc) {
        task.setTaskDesc(taskDesc);
    }

    public void setDoneInList(PtTask task) {
        task.setDone();
    }

    public void addNoteToTask(PtTask task, String note) {
        task.setNote(note);
    }

    /**
     * Lists tasks in specified project and week
     *
     * @param projectName specified project
     * @param weekNum     specified week
     * @return listing of the tasks in the specified week and project
     */
    public String listPtTask(String projectName, int weekNum) {
        requireNonNull(projectName);

        Project project;
        boolean isIpProject = projectName.toLowerCase().equals("ip");

        if (isIpProject) {
            project = getIp();
        } else {
            project = getTp();
        }

        PtWeekList ptWeekList = getPtWeekList(project);
        PtWeek ptWeek = getPtWeek(ptWeekList, weekNum);
        PtTaskList ptTaskList = getPtTaskList(ptWeek);

        return listTaskFromList(ptTaskList);
    }

    /**
     * adds a pttask to the specified project and week
     *
     * @param projectName specified project
     * @param weekNum     specified week
     * @param task        to be added
     */
    public void addPtTask(String projectName, int weekNum, PtTask task) {
        requireNonNull(projectName);
        requireNonNull(task);

        Project project;
        boolean isIpProject = projectName.toLowerCase().equals("ip");

        if (isIpProject) {
            project = getIp();
        } else {
            project = getTp();
        }

        PtWeekList ptWeekList = getPtWeekList(project);
        PtWeek ptWeek = getPtWeek(ptWeekList, weekNum);
        PtTaskList ptTaskList = getPtTaskList(ptWeek);

        addTaskToList(ptTaskList, task);
    }

    /**
     * deletes task from specified project and week
     *
     * @param projectName specified project
     * @param weekNum     specified week
     * @param taskNum     specified task
     * @return true if successful, false if no task
     */
    public boolean deletePtTask(String projectName, int weekNum, int taskNum) {
        requireNonNull(projectName);

        Project project;
        boolean isIpProject = projectName.toLowerCase().equals("ip");

        if (isIpProject) {
            project = getIp();
        } else {
            project = getTp();
        }

        PtWeekList ptWeekList = getPtWeekList(project);
        PtWeek ptWeek = getPtWeek(ptWeekList, weekNum);
        PtTaskList ptTaskList = getPtTaskList(ptWeek);
        boolean isOutOfBound = getNumTask(ptTaskList) < taskNum;

        if (isOutOfBound) {
            return false;
        } else {
            deleteTaskFromList(ptTaskList, taskNum);
            return true;
        }
    }

    /**
     * edits task from specified project and week
     *
     * @param projectName specified project
     * @param weekNum     specified week
     * @param taskNum     specified task
     * @return true if successful, false if no task
     */
    public boolean editPtTask(String projectName, int weekNum, int taskNum, String taskDesc) {
        requireNonNull(projectName);
        requireNonNull(taskDesc);

        Project project;
        boolean isIpProject = projectName.toLowerCase().equals("ip");

        if (isIpProject) {
            project = getIp();
        } else {
            project = getTp();
        }

        PtWeekList ptWeekList = getPtWeekList(project);
        PtWeek ptWeek = getPtWeek(ptWeekList, weekNum);
        PtTaskList ptTaskList = getPtTaskList(ptWeek);
        boolean isOutOfBound = getNumTask(ptTaskList) < taskNum;

        if (isOutOfBound) {
            return false;
        } else {
            PtTask taskEdit = getTaskFromList(ptTaskList, taskNum);

            editTaskFromList(taskEdit, taskDesc);
            return true;
        }
    }

    /**
     * sets done for specified task
     *
     * @param projectName specified project
     * @param weekNum     specified week
     * @param taskNum     specified task
     * @return true if successful, false if no task
     */
    public boolean setDonePtTask(String projectName, int weekNum, int taskNum) {
        requireNonNull(projectName);

        Project project;
        boolean isIpProject = projectName.toLowerCase().equals("ip");

        if (isIpProject) {
            project = getIp();
        } else {
            project = getTp();
        }

        PtWeekList ptWeekList = getPtWeekList(project);
        PtWeek ptWeek = getPtWeek(ptWeekList, weekNum);
        PtTaskList ptTaskList = getPtTaskList(ptWeek);
        boolean isOutOfBound = getNumTask(ptTaskList) < taskNum;

        if (isOutOfBound) {
            return false;
        } else {
            PtTask taskToSetDone = ptTaskList.getTask(taskNum);

            setDoneInList(taskToSetDone);
            return true;
        }
    }

    /**
     * adds ptNote to a specified task
     *
     * @param projectName specified project
     * @param weekNum     specified week
     * @param taskNum     specified task
     * @param note        ptNote to add
     * @return true if successful, false if no task
     */
    public boolean addPtNote(String projectName, int weekNum, int taskNum, String note) {
        requireNonNull(projectName);
        requireNonNull(note);

        Project project;
        boolean isIpProject = projectName.toLowerCase().equals("ip");

        if (isIpProject) {
            project = getIp();
        } else {
            project = getTp();
        }

        PtWeekList ptWeekList = getPtWeekList(project);
        PtWeek ptWeek = getPtWeek(ptWeekList, weekNum);
        PtTaskList ptTaskList = getPtTaskList(ptWeek);
        boolean isOutOfBound = getNumTask(ptTaskList) < taskNum;

        if (isOutOfBound) {
            return false;
        }

        PtTask taskToAddNote = getTaskFromList(ptTaskList, taskNum);
        boolean hasNote = !getNoteFromTask(taskToAddNote).toString().equals("");

        if (hasNote) {
            return false;
        } else {
            addNoteToTask(taskToAddNote, note);
            return true;
        }
    }

    /**
     * deletes the note of the specified task
     *
     * @param projectName specified project
     * @param weekNum     specified week
     * @param taskNum     specified task
     * @return true if successful, false if not successful
     */
    public boolean deletePtNote(String projectName, int weekNum, int taskNum) {
        requireNonNull(projectName);

        Project project;
        boolean isIpProject = projectName.toLowerCase().equals("ip");

        if (isIpProject) {
            project = getIp();
        } else {
            project = getTp();
        }

        PtWeekList ptWeekList = getPtWeekList(project);
        PtWeek ptWeek = getPtWeek(ptWeekList, weekNum);
        PtTaskList ptTaskList = getPtTaskList(ptWeek);
        boolean isOutOfBound = getNumTask(ptTaskList) < taskNum;

        if (isOutOfBound) {
            return false;
        }

        PtTask taskToDeleteNote = getTaskFromList(ptTaskList, taskNum);
        PtNote noteDelete = getNoteFromTask(taskToDeleteNote);
        boolean isEmptyNote = noteDelete.toString().equals("");

        if (isEmptyNote) {
            return false;
        } else {
            addNoteToTask(taskToDeleteNote, "");
            return true;
        }
    }

    /**
     * edits the note of the specified task
     *
     * @param projectName specified project
     * @param weekNum     specified week
     * @param taskNum     specified task
     * @return true if successful, false if not successful
     */
    public boolean editPtNote(String projectName, int weekNum, int taskNum, String note) {
        requireNonNull(projectName);
        requireNonNull(note);

        Project project;
        boolean isIpProject = projectName.toLowerCase().equals("ip");

        if (isIpProject) {
            project = getIp();
        } else {
            project = getTp();
        }

        PtWeekList ptWeekList = getPtWeekList(project);
        PtWeek ptWeek = getPtWeek(ptWeekList, weekNum);
        PtTaskList ptTaskList = getPtTaskList(ptWeek);
        boolean isOutOfBound = getNumTask(ptTaskList) < taskNum;

        if (isOutOfBound) {
            return false;
        }

        PtTask taskToEditNote = getTaskFromList(ptTaskList, taskNum);
        PtNote noteEdit = getNoteFromTask(taskToEditNote);
        boolean isEmptyNote = noteEdit.toString().equals("");

        if (isEmptyNote) {
            return false;
        } else {
            addNoteToTask(taskToEditNote, note);
            return true;
        }
    }

    /**
     * Returns a list of ptTasks in Ip project
     */
    public ArrayList<PtTask> toIpPtTaskList() {
        ArrayList<PtTask> ipTasks = new ArrayList<>();
        Ip ip = getIp();
        PtWeekList ipWeekList = getPtWeekList(ip);

        for (PtWeek week : getArrayListFromWeekList(ipWeekList)) {
            int weekNum = getNumFromWeek(week);
            PtTaskList ptTaskList = getPtTaskList(week);

            for (PtTask task : getArrayListFromTaskList(ptTaskList)) {
                ipTasks.add(task);
            }
        }
        return ipTasks;
    }

    /**
     * Returns a list of ptTasks in Tp project
     */
    public ArrayList<PtTask> toTpPtTaskList() {
        ArrayList<PtTask> tpTasks = new ArrayList<>();
        Tp tp = getTp();
        PtWeekList tpWeekList = getPtWeekList(tp);

        for (PtWeek week : getArrayListFromWeekList(tpWeekList)) {
            int weekNum = getNumFromWeek(week);
            PtTaskList ptTaskList = getPtTaskList(week);

            for (PtTask task : getArrayListFromTaskList(ptTaskList)) {
                tpTasks.add(task);
            }
        }

        return tpTasks;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof ProgressTracker) {
            boolean ipTaskEqual = true;
            boolean tpTaskEqual = true;

            ArrayList<PtTask> ipTasks = toIpPtTaskList();
            ArrayList<PtTask> tpTasks = toTpPtTaskList();
            ArrayList<PtTask> otherIpTasks = ((ProgressTracker) other).toIpPtTaskList();
            ArrayList<PtTask> otherTpTasks = ((ProgressTracker) other).toTpPtTaskList();

            if (ipTasks.size() != otherIpTasks.size() || tpTasks.size() != otherTpTasks.size()) {
                ipTaskEqual = false;
                tpTaskEqual = false;
            } else {
                for (int i = 0; i < ipTasks.size(); i++) {
                    PtTask ipTaskCurrent = ipTasks.get(i);
                    PtTask otherIpTaskCurrent = otherIpTasks.get(i);

                    if (!ipTaskCurrent.equals(otherIpTaskCurrent)) {
                        ipTaskEqual = false;
                        break;
                    }
                }

                for (int i = 0; i < tpTasks.size(); i++) {
                    PtTask tpTaskCurrent = tpTasks.get(i);
                    PtTask otherTpTaskCurrent = otherIpTasks.get(i);

                    if (!tpTaskCurrent.equals(otherTpTaskCurrent)) {
                        tpTaskEqual = false;
                        break;
                    }
                }
            }
            return ipTaskEqual && tpTaskEqual;
        } else {
            return false;
        }
    }
}
